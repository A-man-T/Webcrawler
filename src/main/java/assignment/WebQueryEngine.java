package assignment;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

/**
 * A query engine which holds an underlying web index and can answer textual queries with a
 * collection of relevant pages.
 *
 * TODO: Implement this!
 */
public class WebQueryEngine {

    private WebIndex index;
    public Queue<Token> tokens = new LinkedList<>();




    public WebQueryEngine(WebIndex index){
        this.index = index;
    }

    /**
     * Returns a WebQueryEngine that uses the given Index to construct answers to queries.
     *
     * @param index The WebIndex this WebQueryEngine should use.
     * @return A WebQueryEngine ready to be queried.
     */
    public static WebQueryEngine fromIndex(WebIndex index) {
        // TODO: Implement this!
        WebQueryEngine newEngine = new WebQueryEngine(index);
        return newEngine;
    }

    /**
     * Returns a Collection of URLs (as Strings) of web pages satisfying the query expression.
     *
     * @param query A query expression.
     * @return A collection of web pages satisfying the query.
     */
    public Collection<Page> query(String query) {
        //trim out the extra spaces in query
        //pass in the keyset of the webindex

        HashSet<Page> pages = new HashSet<>();
        // TODO: Implement this!
        return new LinkedList<>();
    }

    public ArrayList<Page>[] parseQuery(ArrayList<Page> p){
        ArrayList<Page>[] validPages = new ArrayList[2];
        Token t = tokens.poll();
        validPages[0] = new ArrayList<>();
        validPages[1] = new ArrayList<>();

        if(t instanceof LeftParenToken){
           ArrayList<Page>[] left = parseQuery(p);
           Token op = tokens.poll();
           if(op instanceof OrToken){
               validPages[0].addAll(left[0]);
               ArrayList<Page>[] right = parseQuery(left[1]);
               validPages[0].addAll(right[0]);
               validPages[1].addAll(right[1]);
           }
           else{
               validPages[1].addAll(left[1]);
               ArrayList<Page>[] right = parseQuery(left[0]);
               validPages[0].addAll(right[0]);
               validPages[1].addAll(right[1]);
           }
           tokens.poll();

        }
        else if(t instanceof wordToken){


            //validPages[0] = //everything that has the word
            //validPages[1] = //everything that doesn't have the word
        }
        else if(t instanceof phraseToken){
            //validPages[0] = //everything that has the word
            //validPages[1] = //everything that doesn't have the word
        }
        else{
            return null;
        }

        return validPages;
    }





    //switch this to private later
    public void tokenize(String stream) {
        tokens.clear();
        int i = 0;

        if (stream.isBlank())
            return;

        while (i < stream.length()) {
            while (Character.isWhitespace(stream.charAt(i)))
                i++;
            char c = stream.charAt(i);
            if (c == '&')
                tokens.add(new AndToken());
            else if (c == '|')
                tokens.add(new OrToken());
            else if (c == '(')
                tokens.add(new LeftParenToken());
            else if (c == ')')
                tokens.add(new RightParenToken());
            else {
                int j = i;
                /*
                read until blank or operator;
                rewind the stream one character if it was an operator
                return a Token that contains a reference to the word;
                 */

                while (j + 1 < stream.length()&&(stream.charAt(j+1)!=')')) {
                    if (stream.charAt(j) == ' ')
                        if(Character.isLetterOrDigit(stream.charAt(j+1)))
                            j++;
                        else
                            break;
                    if (stream.charAt(j) == '&' || stream.charAt(j) == '|' || stream.charAt(j) == '(' || stream.charAt(j) == ')') {
                        j--;
                        break;
                    } else {
                        j++;
                    }
                }
                if(stream.substring(i, j + 1).trim().contains(" "))
                    tokens.add(new phraseToken(stream.substring(i, j + 1).trim()));
                else{
                    tokens.add(new wordToken(stream.substring(i, j + 1).trim()));
                }
                i=j;
            }
            i++;
        }
    }




}
