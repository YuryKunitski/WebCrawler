package by.minsk.softeq.kunitski.servise;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TermStatistic {

    // <term, count of term>
    private static HashMap<String, Integer> termStats = new HashMap<>();

    @Getter
    // <URL, map with stats of terms>
    private static HashMap<String, HashMap<String, Integer>> allStats = new HashMap<>();
    private static final Term[] ALL_TERMS = Term.values();
    public static final String TOTAL = "Total";

    /**
     * Collect stats of terms on the page's text
     *
     * @param pageText all text of page
     */
    public static void collectStats(String pageText, String url) {
        if (Arrays.stream(ALL_TERMS).anyMatch(term -> pageText.contains(term.toString()))) {
            for (Term term : ALL_TERMS) {
                int termCount = 0;

                Pattern p = Pattern.compile(term.toString(), Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(pageText);
                while (m.find()) {
                    termCount++;
                }
                termStats.put(term.toString(), termCount);
            }
            addTotalHits();
            allStats.put(url, termStats);
        }
    }

    /**
     * @return map with total stats of terms
     */
    private static void addTotalHits() {
        int totalCount = termStats.values().stream().mapToInt(stat -> stat).sum();
        termStats.put(TOTAL, totalCount);
    }

}
