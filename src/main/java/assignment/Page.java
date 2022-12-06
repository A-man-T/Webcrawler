package assignment;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * The Page class holds anything that the QueryEngine returns to the server.  The field and method
 * we provided here is the bare minimum requirement to be a Page - feel free to add anything you
 * want as long as you don't break the getURL method.
 *
 *
 */

public class Page implements Serializable {

    private URL url;
    private ArrayList<String> contents;
    private HashMap<String, ArrayList<Integer>> words;
    //Stores what word we are on in the page for consecutive words
    private int location;


    /**
     * Creates a Page with a given URL.
     * @param url The url of the page.
     */
    public Page(URL url) {
        this.url = url;
        this.words = new HashMap<>();
        int location =0;
        contents = new ArrayList<>();
    }

    public ArrayList<String> getContentsString() {
        return contents;
    }

    public HashMap<String, ArrayList<Integer>> getWords() {
        return words;
    }

    //checks if a word is already in the hashmap, if so adds the new location to its integer ArrayList,
    // if not inserts the word
    public void addContents(String s) {
        s = s.toLowerCase();
        if(words.containsKey(s)){
            words.get(s).add(location);
        }
        else{
            words.put(s,new ArrayList<Integer>());
            words.get(s).add(location);
        }
        contents.add(s);
        location++;

        return;
    }

    /**
     * @return the URL of the page.
     */
    public URL getURL() { return url; }
}
