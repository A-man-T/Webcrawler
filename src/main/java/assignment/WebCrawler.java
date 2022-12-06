package assignment;

import java.io.*;
import java.net.*;
import java.util.*;

import org.attoparser.simple.*;
import org.attoparser.config.ParseConfiguration;

/**
 * The entry-point for WebCrawler; takes in a list of URLs to start crawling from and saves an index
 * to index.db.
 */
public class WebCrawler {

    /**
    * The WebCrawler's main method starts crawling a set of pages.  You can change this method as
    * you see fit, as long as it takes URLs as inputs and saves an Index at "index.db".
    */
    public static void main(String[] args) {

        // Basic usage information
        if (args.length == 0) {
            System.err.println("Error: No URLs specified.");
            System.exit(1);
        }

        // We'll throw all the args into a queue for processing.
        Queue<URL> remaining = new LinkedList<>();
        for (String url : args) {
            try {
                remaining.add(new URL(url));
            } catch (MalformedURLException e) {
                // Throw this one out!
                System.err.printf("Error: URL '%s' was malformed and will be ignored!%n", url);
            }
        }

        // Create a parser from the attoparser library, and our handler for markup.
        WebIndex webIndex = new WebIndex();
        ISimpleMarkupParser parser = new SimpleMarkupParser(ParseConfiguration.htmlConfiguration());
        CrawlingMarkupHandler handler = new CrawlingMarkupHandler();
        handler.setWebIndex(webIndex);


        //make a visited hashset to ensure that we don't get into an infinite loop
        HashSet<String> visited = new HashSet<String>();



        //make a counter to observe the number of sites we are crawling
        int i = 0;
        int skipped = 0;
        // Try to start crawling, adding new URLS as we see them.
        // add the try catch in such a way that it keeps going even if it shows
        try {
            while (!remaining.isEmpty()) {
                URL url = remaining.poll();
                String urlString = url.toString();

                if(urlString.contains("?"))
                    urlString = urlString.substring(0, urlString.indexOf("?"));

                if(urlString.contains("#"))
                    urlString = urlString.substring(0, urlString.indexOf("#"));

                if(visited.contains(urlString)){
                    continue;
                }
                else{
                    visited.add(urlString);
                }
                handler.setCurrentURL(url);
                // Parse the next URL's page
                try {

                    parser.parse(new InputStreamReader(url.openStream()), handler);
                    i++;
                    // Add any new URLs
                    remaining.addAll(handler.newURLs());
                }catch(Exception e){
                    skipped++;
                }
            }
            handler.getIndex().save("index.db");
        } catch (Exception e) {
            // Bad exception handling :(
            System.err.println("Error: Index generation failed!");
            e.printStackTrace();
            System.exit(1);
        }
        int toomany =0;
        for(Page p: webIndex.getPages())
            if(p.getWords().keySet().size()==0)
                toomany++;
        //System.out.println(toomany);
        System.out.println(i);
        //System.out.println(skipped);
    }
}
