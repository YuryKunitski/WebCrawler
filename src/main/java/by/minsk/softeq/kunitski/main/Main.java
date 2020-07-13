package by.minsk.softeq.kunitski.main;

import by.minsk.softeq.kunitski.servise.CsvWriter;
import by.minsk.softeq.kunitski.servise.Parser;
import by.minsk.softeq.kunitski.servise.Sorter;
import by.minsk.softeq.kunitski.servise.Statistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Objects;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static String SEED_URL = "https://en.wikipedia.org/wiki/Elon_Musk";
    private static final String ALL_STAT_FILE_NAME = "WebCrawlerAllStats.csv";
    private static final String SORTED_STAT_FILE_NAME = "WebCrawlerSortedStats.csv";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Parser parser = new Parser();
        parser.parse(SEED_URL, 0);
        
        HashMap<String, HashMap<String, Integer>> allStats = Statistic.getAllStats();
        CsvWriter.writeToFile(allStats, ALL_STAT_FILE_NAME);

        HashMap<String, HashMap<String, Integer>> sortedStats = Sorter.sortPagesByTotalHits(allStats);
        if (Objects.nonNull(sortedStats)) {
            sortedStats.forEach((key, value) -> logger.info("url = {}, Numbers are {}", key, value));
            CsvWriter.writeToFile(sortedStats, SORTED_STAT_FILE_NAME);
        }

        long finish = System.currentTimeMillis();
        logger.info("Working time  - {} milliseconds", finish - start);
    }
}
