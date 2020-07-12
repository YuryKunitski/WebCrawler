package by.minsk.softeq.kunitski.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CsvWriter {

    private static final String CSV_SEPARATOR = ",";
    private static Logger logger = LoggerFactory.getLogger(CsvWriter.class);

    /**
     * @param allStatsMap map with all web crawler stats
     * @param fileName which will be created or updated if it is already exists
     */
    public static void writeToFile(HashMap<String, HashMap<String, Integer>> allStatsMap, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            for (Map.Entry<String, HashMap<String, Integer>> crawlerStatSet : allStatsMap.entrySet()) {
                StringBuilder statsBuilder = new StringBuilder();
                statsBuilder.append("URl - ");
                statsBuilder.append(crawlerStatSet.getKey());
                statsBuilder.append(CSV_SEPARATOR);

                HashMap<String, Integer> termStatsMap = crawlerStatSet.getValue();
                statsBuilder.append("Numbers are ");
                statsBuilder.append(CSV_SEPARATOR);

                for (Map.Entry<String, Integer> termStatsSet : termStatsMap.entrySet()) {
                    statsBuilder.append(termStatsSet.getKey());
                    statsBuilder.append(" - ");
                    statsBuilder.append(termStatsSet.getValue());
                    statsBuilder.append(" hits");
                    statsBuilder.append(CSV_SEPARATOR);
                }

                bw.write(statsBuilder.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
