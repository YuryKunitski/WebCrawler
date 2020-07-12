package by.minsk.softeq.kunitski.servise;

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

public class Parser {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private HashSet<String> links;
    private int pageCounter = 0;
    private static final int LIMIT_PAGES = 10_000;
    private static final int MAX_DEPTH = 8;
    private final static Pattern EXCLUSIONS = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");


    public Parser() {
        links = new HashSet<>();
    }

    public void parse(String url, int depth) {

        if (depth < MAX_DEPTH && shouldVisit(url)) {

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
                        if (pageCounter >= LIMIT_PAGES) {
                            break;
                        }
                        String innerUrl = link.attr("abs:href");
                        parse(innerUrl, depth);
                    }
                }
            } catch (IOException e) {
                logger.error("something went wrong with URL - {}", url);
                logger.error(e.getMessage(), e);
            }
        }
    }

    public boolean shouldVisit(String url) {
        return !url.isEmpty() && !links.contains(url) && url.startsWith("https://") && !EXCLUSIONS.matcher(url).matches();
    }

}
