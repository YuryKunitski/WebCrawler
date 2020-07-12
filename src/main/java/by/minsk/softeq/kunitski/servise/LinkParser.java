package by.minsk.softeq.kunitski.servise;

import by.minsk.softeq.kunitski.exception.StopRecursiveMethodException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.regex.Pattern;

public class LinkParser {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private HashSet<String> links;
    private int pageCounter = 0;
    private static final int LIMIT_PAGES = 100;
    private static final int MAX_DEPTH = 8;
    private final static Pattern EXCLUSIONS = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");


    public LinkParser() {
        links = new HashSet<>();
    }

    public void parse(String url, int depth) throws StopRecursiveMethodException {

        // the fastest way to quit out of recursive method
        if (pageCounter >= LIMIT_PAGES) {
            throw new StopRecursiveMethodException("Limit of pages was reached");
        }

        if (shouldVisit(url) && depth < MAX_DEPTH) {

            links.add(url);

            try {
                Document document = Jsoup.connect(url).get();   //need to handle
                Elements linksOnPage = document.select("a[href]");
                Element pageBody = document.body();

                if (Objects.nonNull(pageBody)) {

                    String pageText = pageBody.text();
                    Statistic statistic = new Statistic();
                    statistic.collectStats(pageText, url);

                    depth++;
                    pageCounter++;

                    for (Element link : linksOnPage) {
                        String innerUrl = link.attr("abs:href");
                        parse(innerUrl, depth);
                    }
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public boolean shouldVisit(String url) {
        return !EXCLUSIONS.matcher(url).matches() && !links.contains(url) && !url.isEmpty();
    }

}
