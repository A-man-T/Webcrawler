package assignment;
import java.net.URL;

import java.util.HashMap;

/**
 * A web-index which efficiently stores information about pages. Serialization is done automatically
 * via the superclass "Index" and Java's Serializable interface.
 *
 * TODO: Implement this!
 */
public class WebIndex extends Index {
    /**
     * Needed for Serialization (provided by Index) - don't remove this!
     */
    private static final long serialVersionUID = 1L;

    // TODO: Implement all of this! You may choose your own data structures an internal APIs.
    // You should not need to worry about serialization (just make any other data structures you use
    // here also serializable - the Java standard library data structures already are, for example).



   //URL, words and position of words

    HashMap<URL, HashMap<String,int[]>> pages = new HashMap<>();

    public void add(URL url, String word, int location){
        //System.out.println(word);
        if(pages.containsKey(url))
            System.out.println("hello");
    }



}
