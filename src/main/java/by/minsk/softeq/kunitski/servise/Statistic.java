package by.minsk.softeq.kunitski.servise;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Statistic {

    @Getter
    @Setter
    private static HashMap<String, HashMap<String, Integer>> allStats = new HashMap<>();    // <URL, map with stats of terms>
    private HashMap<String, Integer> termStats = new HashMap<>();    // <term, count of term>
    private static final Term[] ALL_TERMS = Term.values();
    public static final String TOTAL = "Total";

    /**
     * Collect stats of terms on the page's text
     *
     * @param pageText all text of page
     */
    public void collectStats(String pageText, String url) {
        if (shouldCollect(pageText, url)) {
            for (Term term : ALL_TERMS) {
                int allTermCountCoincidences = countTermCoincidences(term, pageText);
                termStats.put(term.toString(), allTermCountCoincidences);
            }
            addTotalHits();
            allStats.put(url, termStats);
        }
    }

    /**
     * @return map with total stats of terms
     */
    private void addTotalHits() {
        int totalCount = termStats.values().stream().mapToInt(stat -> stat).sum();
        termStats.put(TOTAL, totalCount);
    }

    /**
     * @param term to be counted
     * @param text in which the term will be searched
     * @return the number of matches of the term found in the text
     */
    private int countTermCoincidences(Term term, String text) {
        int count = 0;
        Pattern p = Pattern.compile(term.toString(), Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * @param text to check
     * @param url to check
     * @return false - when input data is invalid or text do not contain necessary data, else - true
     */
    private boolean shouldCollect(String text, String url) {
        return Objects.nonNull(text) && Objects.nonNull(url) && Arrays.stream(ALL_TERMS).anyMatch(term -> text.contains(term.toString()));
    }
}
