package by.minsk.softeq.kunitski.servise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StatisticTest {

    private Statistic statistic;
    private HashMap<String, HashMap<String, Integer>> allExpectedStats = new HashMap<>();

    @BeforeEach
    void setUp() {
        statistic = new Statistic();
        Statistic.setAllStats(new HashMap<>());
        allExpectedStats = new HashMap<>();
        initMap();
    }

    @Test
    void collectStatsWorking() {
        statistic.collectStats("Musk Musk Musk Tesla Elon Mask other text", "url1");
        assertEquals(allExpectedStats, Statistic.getAllStats());
    }

    @Test
    void collectStatsInvalidText() {
        statistic.collectStats(null, "url1");
        assertEquals(0, Statistic.getAllStats().size());
    }

    @Test
    void collectStatsInvalidUrl() {
        statistic.collectStats("Musk Musk Musk Tesla Elon Mask other text", null);
        assertEquals(0, Statistic.getAllStats().size());
    }

    @Test
    void collectStatsTextNotContainNecessaryData() {
        statistic.collectStats("other text", null);
        assertEquals(0, Statistic.getAllStats().size());
    }

    private void initMap() {
        HashMap<String, Integer> expectedTermStat = new HashMap<>();
        
        expectedTermStat.put("Musk", 3);
        expectedTermStat.put("Elon Mask", 1);
        expectedTermStat.put("Tesla", 1);
        expectedTermStat.put("Total", 5);
        expectedTermStat.put("Gigafactory", 0);

        allExpectedStats.put("url1", expectedTermStat);
    }
}