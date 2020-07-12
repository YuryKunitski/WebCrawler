package by.minsk.softeq.kunitski.main;

import by.minsk.softeq.kunitski.exception.StopRecursiveMethodException;
import by.minsk.softeq.kunitski.servise.CsvWriter;
import by.minsk.softeq.kunitski.servise.LinkParser;
import by.minsk.softeq.kunitski.servise.PageSorter;
import by.minsk.softeq.kunitski.servise.TermStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static String fileNameAllStat = "WebCrawlerAllStats.csv";
    private static String fileNameSortedStat = "WebCrawlerSortedStats.csv";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        LinkParser linkParser = new LinkParser();
        try {
            linkParser.parse("https://en.wikipedia.org/wiki/Elon_Musk", 0);
            linkParser.parse("", 0);
        } catch (StopRecursiveMethodException e) {
            //this is expected behavior
            logger.info(e.getMessage());
        }

        HashMap<String, HashMap<String, Integer>> allStats = TermStatistic.getAllStats();
        HashMap<String, HashMap<String, Integer>> sortedStats = PageSorter.sortPagesByTotalHits(allStats);

        sortedStats.forEach((key, value) -> logger.info("url = {}, Numbers are {}", key, value));

        CsvWriter.writeToFile(allStats, fileNameAllStat);
        CsvWriter.writeToFile(sortedStats, fileNameSortedStat);

        long finish = System.currentTimeMillis();
        System.out.println("Time  - " + (finish - start));
    }
}
