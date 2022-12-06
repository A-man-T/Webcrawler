package assignment;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * A web-index which efficiently stores information about pages. Serialization is done automatically
 * via the superclass "Index" and Java's Serializable interface.
 *
 *
 */
public class WebIndex extends Index {
    /**
     * Needed for Serialization (provided by Index) - don't remove this!
     */
    private static final long serialVersionUID = 1L;

    //The WebIndex simply stores an ArrayList of all the Page objects which are successfully parsed
    private ArrayList<Page> pages;


    public WebIndex(){
        pages = new ArrayList<>();
    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    public void add(Page p) {
        pages.add(p);
    }





}



//old code
    /*
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

     */