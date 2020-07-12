package by.minsk.softeq.kunitski.servise;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class PageSorter {

    private static final int TOP_PAGES_NUMBER = 10;

    /**
     * @param allStatsMap map which contain all stats data
     * @return map with top 10 pages, sorted by total hits
     */
    public static HashMap<String, HashMap<String, Integer>> sortPagesByTotalHits(HashMap<String, HashMap<String, Integer>> allStatsMap) {
        return allStatsMap.entrySet().stream()
                .sorted((first, second) -> second.getValue().get(TermStatistic.TOTAL).compareTo(first.getValue().get(TermStatistic.TOTAL)))
                .limit(TOP_PAGES_NUMBER)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
