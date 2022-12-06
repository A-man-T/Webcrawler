package assignment;

import java.util.*;
import java.net.*;
import org.attoparser.simple.*;

/**
 * A markup handler which is called by the Attoparser markup parser as it parses the input;
 * responsible for building the actual web index.
 *
 * TODO: Implement this!
 */
public class CrawlingMarkupHandler extends AbstractSimpleMarkupHandler {

    private ArrayList<URL> newLinks;
    private URL currentURL;
    private boolean ignore = false;

    private boolean madePage;
    private Page currentPage;
    private WebIndex webIndex;

    public CrawlingMarkupHandler() {
        newLinks = new ArrayList<>();
    }


    public void setCurrentURL(URL currentURL) {
        this.currentURL = currentURL;
        this.currentPage = new Page(currentURL);
        madePage = false;
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
        // TODO: Implement this!
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
        // TODO: Implement this.

        //System.out.println("Start of document");
    }

    /**
    * Called when the parser finishes reading a document.
    * @param endTimeNanos    the current time (in nanoseconds) when parsing ends
    * @param totalTimeNanos  the difference between current times at the start
    *                        and end of parsing
    * @param line            the line of the document where parsing ends
    * @param col             the column of the document where the parsing ends
    */
    public void handleDocumentEnd(long endTimeNanos, long totalTimeNanos, int line, int col) {
        // TODO: Implement this.
        webIndex.add(currentPage);
        //("End of document");
    }

    /**
    * Called at the start of any tag.
    * @param elementName the element name (such as "div")
    * @param attributes  the element attributes map, or null if it has no attributes
    * @param line        the line in the document where this element appears
    * @param col         the column in the document where this element appears
    */
    public void handleOpenElement(String elementName, Map<String, String> attributes, int line, int col) {
        // TODO: Implement this.
        //System.out.println("Start element: " + elementName);
        URL toAdd;
        if(elementName.equals("script")||elementName.equals("style"))
            ignore = true;
        if (attributes != null) {
            for (String key : attributes.keySet()) {
                //System.out.println("Key: " + key + ", Value: " + attributes.get(key));
                if(key.equals("href")) {
                    try {
                        toAdd = new URL(currentURL, attributes.get(key));
                        newLinks.add(toAdd);
                    } catch (MalformedURLException e) {
                        //System.err.println("Key: " + key + ", Value: " + attributes.get(key));
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
    public void handleCloseElement(String elementName, int line, int col) {
        // TODO: Implement this.
        ignore = false;
        //System.out.println("End element:   " + elementName);
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
        // TODO: Implement this.
        //System.out.print("Characters:    \"");
        String currentWord = "";



        if(ignore){
            return;
        }

        for(int i = start; i < start + length; i++) {
            // Instead of printing raw whitespace, we're escaping it


            /*
            if(!Character.isLetterOrDigit(ch[i])&&!Character.isWhitespace(ch[i]))
                continue;

             */





            switch(ch[i]) {
                /*
                case ' ':
                    if(!currentWord.equals(""))

                    currentWord = null;

                 */
                case '\\':

                    //System.out.print("\\\\");
                    break;
                case '"':

                    //System.out.print("\\\"");
                    break;
                case '\n':

                    //System.out.print("\\n");;
                case '\r':

                    //System.out.print("\\r");
                    break;
                case '\t':

                    //System.out.print("\\t");
                    break;
                default:
                    if(!Character.isLetterOrDigit(ch[i])) {
                        if(!currentWord.equals("")) {
                            currentPage.addContents(currentWord);
                            //current working
                            /*
                            if(!madePage){
                                webIndex.add(currentPage);
                                madePage = true;
                            }

                             */
                            //webIndex.add(currentPage, currentWord, i);
                        }
                        currentWord = "";
                    }
                    else {
                        currentWord += ch[i];
                    }

                    //System.out.print(ch[i]);
                    break;
            }
        }
        if(!currentWord.equals("")){
            currentPage.addContents(currentWord);
            //current working
            /*
            if(!madePage){
                webIndex.add(currentPage);
                madePage = true;
            }

             */
            //webIndex.add(currentPage, currentWord, start+length);
        }

        //System.out.print("\"\n");
    }
}
