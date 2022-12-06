package assignment;

import java.util.*;
import java.net.*;
import org.attoparser.simple.*;

/**
 * A markup handler which is called by the Attoparser markup parser as it parses the input;
 * responsible for building the actual web index.
 *

 */
public class CrawlingMarkupHandler extends AbstractSimpleMarkupHandler {

    //The CrawlingMarkupHandler keeps track of the following fields
    private ArrayList<URL> newLinks; //new URLs to pass to WebCrawler
    private URL currentURL; //currentURL crawling
    private boolean ignore = false; //ignore if encountered style or script tag
    private Page currentPage; //Page Object made for current URL
    private WebIndex webIndex; //webIndex to write to

    public CrawlingMarkupHandler() {
        newLinks = new ArrayList<>();
    }


    public void setCurrentURL(URL currentURL) {
        this.currentURL = currentURL;
        this.currentPage = new Page(currentURL);
    }

    public void setWebIndex(WebIndex webIndex) {
        this.webIndex = webIndex;
    }


    /**
    * This method returns the complete index that has been crawled thus far when called.
    */
    public Index getIndex() {
        return webIndex;
    }

    /**
    * This method returns any new URLs found to the Crawler; upon being called, the set of new URLs
    * should be cleared.
    */
    public List<URL> newURLs() {
        ArrayList<URL> newLinks1 = (ArrayList<URL>) newLinks.clone();
        newLinks.clear();
        return newLinks1;
    }

    /**
    * These are some of the methods from AbstractSimpleMarkupHandler.
    * All of its method implementations are NoOps, so we've added some things
    * to do; please remove all the extra printing before you turn in your code.
    *
    * Note: each of these methods defines a line and col param, but you probably
    * don't need those values. You can look at the documentation for the
    * superclass to see all of the handler methods.
    */

    /**
    * Called when the parser first starts reading a document.
    * @param startTimeNanos  the current time (in nanoseconds) when parsing starts
    * @param line            the line of the document where parsing starts
    * @param col             the column of the document where parsing starts
    */
    public void handleDocumentStart(long startTimeNanos, int line, int col) {
    }

    /**
    * Called when the parser finishes reading a document.
    * @param endTimeNanos    the current time (in nanoseconds) when parsing ends
    * @param totalTimeNanos  the difference between current times at the start
    *                        and end of parsing
    * @param line            the line of the document where parsing ends
    * @param col             the column of the document where the parsing ends
    */
    //if the parser successfully makes it to the end of a document add it to the WebIndex
    public void handleDocumentEnd(long endTimeNanos, long totalTimeNanos, int line, int col) {
        webIndex.add(currentPage);
    }

    /**
    * Called at the start of any tag.
    * @param elementName the element name (such as "div")
    * @param attributes  the element attributes map, or null if it has no attributes
    * @param line        the line in the document where this element appears
    * @param col         the column in the document where this element appears
    */
    //Checks if the element has a script of style tag, or if it contains a hyperlink to a new page and process it accordingly
    public void handleOpenElement(String elementName, Map<String, String> attributes, int line, int col) {
        URL toAdd;
        if(elementName.equals("script")||elementName.equals("style"))
            ignore = true;
        if (attributes != null) {
            for (String key : attributes.keySet()) {
                if(key.equals("href")) {
                    try {
                        toAdd = new URL(currentURL, attributes.get(key));
                        newLinks.add(toAdd);
                    } catch (MalformedURLException e) {
                        System.err.println("HTML code is not valid, ignoring and contiuning to parse...");
                    }
                }
            }
        }

    }

    /**
    * Called at the end of any tag.
    * @param elementName the element name (such as "div").
    * @param line        the line in the document where this element appears.
    * @param col         the column in the document where this element appears.
    */
    //Ensure that we only ignore elements with a script or style tag
    public void handleCloseElement(String elementName, int line, int col) {
        ignore = false;
    }

    /**
    * Called whenever characters are found inside a tag. Note that the parser is not
    * required to return all characters in the tag in a single chunk. Whitespace is
    * also returned as characters.
    * @param ch      buffer containing characters; do not modify this buffer
    * @param start   location of 1st character in ch
    * @param length  number of characters in ch
    */
    public void handleText(char[] ch, int start, int length, int line, int col) {

        //word to add to the page class
        String currentWord = "";

        //If the text is associated with a script or style tag
        if(ignore){
            return;
        }

        //considering the char[] with the appropriate word
        for(int i = start; i < start + length; i++) {
             //building the next word to add to the page, character by character

            //if we hit a non-alphanumeric character
            if(!Character.isLetterOrDigit(ch[i])) {
                if(!currentWord.equals("")) {
                    //if currentWord is not blank and we reached a non-alphanumeric character
                    currentPage.addContents(currentWord);
                }
                currentWord = "";
            }
            else {
                currentWord += ch[i];
            }

            }

        //Edge case where we end on an non-alphanumeric character
        if(!currentWord.equals("")){
            currentPage.addContents(currentWord);
        }
    }
}
