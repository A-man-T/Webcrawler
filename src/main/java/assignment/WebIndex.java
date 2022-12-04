package assignment;
import java.net.URL;

import java.util.ArrayList;
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

    HashMap<Page, HashMap<String, ArrayList<Integer>>> pages = new HashMap<>();

    public void add(Page url, String word, int location) {
        //System.out.println(word);
        if (pages.containsKey(url)) {

            if(pages.get(url).containsKey(word)){
                pages.get(url).get(word).add(location);
            }
            else{
                pages.get(url).put(word,new ArrayList<Integer>());
                pages.get(url).get(word).add(location);
            }

        }
        else{
            pages.put(url,new HashMap<String, ArrayList<Integer>>());
            pages.get(url).put(word,new ArrayList<Integer>());
            pages.get(url).get(word).add(location);

        }

    }



}
