package by.minsk.softeq.kunitski.servise;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class Sorter {

    private static final int TOP_PAGES_NUMBER = 10;

    /**
     * @param allStatsMap map which contain all stats data
     * @return map with top 10 pages, sorted by total hits
     */
    public static HashMap<String, HashMap<String, Integer>> sortPagesByTotalHits(HashMap<String, HashMap<String, Integer>> allStatsMap) {
        return allStatsMap.entrySet().stream()
                .sorted((first, second) -> second.getValue().get(Statistic.TOTAL).compareTo(first.getValue().get(Statistic.TOTAL)))
                .limit(TOP_PAGES_NUMBER)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
