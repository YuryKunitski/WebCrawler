package by.minsk.softeq.kunitski.servise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SorterTest {

    private HashMap<String, HashMap<String, Integer>> expectedStats;
    private static final String EXPECTED_SORTED_MAP_STRING = "{url4={Musk=100, Total=200, Tesla=100}, url2={Musk=20, Total=40, Tesla=20}, url3={Musk=10, Total=20, Tesla=10}, url1={Musk=5, Total=10, Tesla=5}}";

    @BeforeEach
    void setUp() {
        expectedStats = new HashMap<>();
        initMap();
    }

    @Test
    void sortPagesByTotalHitsWorking() {
        expectedStats = Sorter.sortPagesByTotalHits(expectedStats);
        assertEquals(EXPECTED_SORTED_MAP_STRING, expectedStats.toString());
    }

    @Test
    void sortPagesByTotalHitsNull() {
        expectedStats = Sorter.sortPagesByTotalHits(null);
        assertNull(expectedStats);
    }
    
    private void initMap() {
        String url1 = "url1";
        String url2 = "url2";
        String url3 = "url3";
        String url4 = "url4";

        String val1 = "Tesla";
        String val2 = "Musk";
        String val3 = "Total";

        HashMap<String, Integer> mapsVal1 = new HashMap<>();
        HashMap<String, Integer> mapsVal2 = new HashMap<>();
        HashMap<String, Integer> mapsVal3 = new HashMap<>();
        HashMap<String, Integer> mapsVal4 = new HashMap<>();

        mapsVal1.put(val1, 5);
        mapsVal1.put(val2, 5);
        mapsVal1.put(val3, 10);

        mapsVal2.put(val1, 20);
        mapsVal2.put(val2, 20);
        mapsVal2.put(val3, 40);

        mapsVal3.put(val1, 10);
        mapsVal3.put(val2, 10);
        mapsVal3.put(val3, 20);

        mapsVal4.put(val1, 100);
        mapsVal4.put(val2, 100);
        mapsVal4.put(val3, 200);

        expectedStats = new HashMap<>();

        expectedStats.put(url1, mapsVal1);
        expectedStats.put(url2, mapsVal2);
        expectedStats.put(url3, mapsVal3);
        expectedStats.put(url4, mapsVal4);
    }
}