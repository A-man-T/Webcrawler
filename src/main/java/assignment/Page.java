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
 * TODO: Implement this!
 */
public class Page implements Serializable {
    // The URL the page was located at.
    private URL url;
    int location;

    public ArrayList<String> getContentsString() {
        return contents;
    }


    private ArrayList<String> contents = new ArrayList<>();

    public HashMap<String, ArrayList<Integer>> getWords() {
        return words;
    }

    private HashMap<String, ArrayList<Integer>> words;





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
     * Creates a Page with a given URL.
     * @param url The url of the page.
     */
    public Page(URL url) {
        this.url = url;
        this.words = new HashMap<>();
        int location =0;
    }

    /**
     * @return the URL of the page.
     */
    public URL getURL() { return url; }
}
