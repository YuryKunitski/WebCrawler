Task:
Implement a web crawler that traverses websites following predefined link depth (8 by default) and max visited pages limit (10000 by default).
Web crawler starts from predefined URL (seed) and follows links found to dive deeper.
The main purpose of this crawler to detect the presence of some terms on the page and collect statistics, e.g.

Seed:
	https://en.wikipedia.org/wiki/Elon_Musk
  
Terms:
Tesla, Musk, Gigafactory, Elon Mask

All stat data should be serialized into CSV file (no predefined sort)
Top 10 pages by total hits must be printed to separate CSV file and console (sorted by total hits)

To start the WebCrawler app just run by.minsk.softeq.kunitski.main.Main.main() method; 
