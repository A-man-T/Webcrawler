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
    public LinkedList<Token> tokens = new LinkedList<>();




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
    public Collection<Page> query(String query){
        //trim out the extra spaces in query
        //pass in the keyset of the webindex
        //make query lowercase
        query = query.trim().replaceAll("\\s+", " ").toLowerCase();
        tokenize(query);
        ArrayList<Page>[] results = new ArrayList[2];
        try {
            results = parseQuery(index.getPages());
            while (!tokens.isEmpty())
                results = parseQuery(results[0]);
        }
        catch (Exception e){
            System.err.println("Invalid Query");
            return new ArrayList<>();
        }
        System.out.println(results[0].size());

        // TODO: Implement this!
        return results[0];
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
            for(Page page:p){
                if(page.getWords().containsKey(t.toString()))
                    validPages[0].add(page);
                else{
                    validPages[1].add(page);
                }
            }

            //validPages[0] = //everything that has the word
            //validPages[1] = //everything that doesn't have the word
        }
        else if(t instanceof phraseToken){
            String[] phrase = t.toString().split(" ");
            for(Page page:p){

                if(page.getWords().containsKey(phrase[0])&&page.getContentsString().contains(t.toString()))
                    validPages[0].add(page);
                else{
                    validPages[1].add(page);
                }
            }
            //validPages[0] = //everything that has the phrase
            //validPages[1] = //everything that doesn't have the phrase
        }

        else if(t instanceof notToken){
            t = tokens.poll();
            for(Page page:p){
                if(page.getWords().containsKey(t.toString()))
                    validPages[1].add(page);
                else{
                    validPages[0].add(page);
                }
            }
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
            else if (c == '!')
                tokens.add(new notToken());
            else if (c=='"'){
                int start = i;
                i++;
                c = stream.charAt(i);
                 while(c!='"'){
                    i++;
                    c = stream.charAt(i);
                }
                 tokens.add(new phraseToken(stream.substring(start+1,i)));
            }
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
                    if (stream.charAt(j) == '&' || stream.charAt(j) == '|' || stream.charAt(j) == '(' || stream.charAt(j) == ')'||stream.charAt(j) == '"'||stream.charAt(j) == '!') {
                        j--;
                        break;
                    } else {
                        j++;
                    }
                }
                String trim;
                try {
                     trim = stream.substring(i, j + 1).trim();
                }
                catch(Exception e){
                     trim = stream.substring(i, j).trim();
                }

                /*
                if(trim.contains("\"")) {
                    trim = trim.replaceAll("\"", "");
                    if (!(trim.isBlank()))
                        tokens.add(new phraseToken(trim));
                }

                 */

//removed the else if here
                 if (trim.contains(" ")){
                    String temp[] = trim.split(" ");
                    for(String s:temp) {
                        tokens.add(new wordToken(s));
                        //tokens.add(new AndToken());
                    }
                    //tokens.removeLast();
                }


                else{
                    tokens.add(new wordToken(trim));
                }
                i=j;
            }
            i++;
        }
    }




}
