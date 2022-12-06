package assignment;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

/**
 * A query engine which holds an underlying web index and can answer textual queries with a
 * collection of relevant pages.
 *
 */
public class WebQueryEngine {

    private final WebIndex index;
    private final LinkedList<Token> tokens;//stores the tokenized version of a query


    public WebQueryEngine(WebIndex index){
        this.index = index;
        tokens = new LinkedList<>();
    }

    /**
     * Returns a WebQueryEngine that uses the given Index to construct answers to queries.
     *
     * @param index The WebIndex this WebQueryEngine should use.
     * @return A new WebQueryEngine ready to be queried.
     */
    public static WebQueryEngine fromIndex(WebIndex index) {
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
        //trim and replace the extra spaces in query
        //make query lowercase
        query = query.trim().replaceAll("\\s+", " ").toLowerCase();

        tokenize(query);
        ArrayList<Page>[] results;
        try {
            //pass in the pages from the webindex
            results = parseQuery(index.getPages());
            while (!tokens.isEmpty())
                //repeatedly calls parse query and stores the pages that match the criteria.
                results = parseQuery(results[0]);
        }
        //issue with tokenizing/parsing a query (Invalid Query Handling)
        catch (Exception e){
            System.err.println("Invalid Query");
            return new ArrayList<>();
        }

        //catches issues with queries made by the GUI
        try {
            return results[0];
        }
        catch (Exception e){
            System.err.println("Invalid Query");
            return new ArrayList<>();
        }
    }

    //Parsing a query to find the Pages that meet our condition
    public ArrayList<Page>[] parseQuery(ArrayList<Page> p){
        ArrayList<Page>[] validPages = new ArrayList[2];
        Token t = tokens.poll();

        //validPages[0]- stores all the Pages in p that meet our token conditions
        //validPages[1]- stores the remaining of p that don't meet our condition
        validPages[0] = new ArrayList<>();
        validPages[1] = new ArrayList<>();

        if(t instanceof LeftParenToken){
            //recursively calls parseQuery to build the valid pages for the first non-operator token
           ArrayList<Page>[] left = parseQuery(p);
           Token op = tokens.poll();
           if(op instanceof OrToken){
               //if we hit an Or, everything that works for our previous correct pages works,
               // and we search the incorrect array to sort the remaining Pages in their proper place
               validPages[0].addAll(left[0]);
               ArrayList<Page>[] right = parseQuery(left[1]);
               validPages[0].addAll(right[0]);
               validPages[1].addAll(right[1]);
           }
           else{
               //if we hit an and, everything that works for our previous correct pages is added into our search space,
               // and we search the search space to sort the Pages in their proper place
               validPages[1].addAll(left[1]);
               ArrayList<Page>[] right = parseQuery(left[0]);
               validPages[0].addAll(right[0]);
               validPages[1].addAll(right[1]);
           }
           //should be a right parenthesis
           tokens.poll();
        }

        //validPages[0] = every page that has the word
        //validPages[1] = every page that doesn't have the word
        else if(t instanceof wordToken){
            for(Page page:p){
                if(page.getWords().containsKey(t.toString()))
                    validPages[0].add(page);
                else{
                    validPages[1].add(page);
                }
            }


        }
        //validPages[0] = every page that has the phrase
        //validPages[1] = every page that doesn't have the phrase
        else if(t instanceof phraseToken){
            //split the phrase into words
            String[] phrase = t.toString().split(" ");

            for(Page page:p){
                //check if it has the first word
                if(page.getWords().containsKey(phrase[0])) {
                    //find the indicies of the first word
                    ArrayList<Integer> indicies = new ArrayList<>(page.getWords().get(phrase[0]));
                    ArrayList<Integer> goThrough = new ArrayList<>(indicies);
                    int counter=0;
                    for(String s:phrase){
                        indicies.clear();
                        indicies.addAll(goThrough);
                        for(int i:indicies){
                            //if the next word after the index of previous one doesn't match the next String in the phrase,
                            // remove it from consideration
                            //two different arraylists are using to prevent concurrent modification exception
                            if(((i+counter)>=page.getContentsString().size())||!page.getContentsString().get(i+counter).equals(s))
                                goThrough.remove((Integer) i);
                        }
                        counter++;
                    }
                    //if there are still indices left it is a page which contains the phrase
                    if(!goThrough.isEmpty())
                        validPages[0].add(page);
                    else{
                        validPages[1].add(page);
                    }
                }
                //if it doesn't have the first word it isn't a valid page
                else{
                    validPages[1].add(page);
                }
            }
        }

        //By grammar rules we know the next token should be a word and we add it to the proper array
        // depending on if it has the word or not
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

        //Error in parsing the query
        else{
            return null;
        }

        return validPages;
    }





    //Converts a query into tokens and populates the token array
    private void tokenize(String stream) {
        tokens.clear();
        int i = 0;

        if (stream.isBlank())
            return;

        try {
            while (i < stream.length()) {
                //white space doesn't need tokens
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
                //phrase query
                else if (c == '"') {
                    int start = i;
                    i++;
                    c = stream.charAt(i);
                    while (c != '"') {
                        i++;
                        c = stream.charAt(i);
                    }
                    tokens.add(new phraseToken(stream.substring(start + 1, i).trim()));
                } else {
                    //we reach a text character
                    /* read until blank or operator;
                    rewind the stream one character if it was an operator
                    return a Token that contains a reference to the word;
                     */
                    int j = i;
                    while (j + 1 < stream.length() && (stream.charAt(j + 1) != ')')) {
                        if (stream.charAt(j) == ' ')
                            if (Character.isLetterOrDigit(stream.charAt(j + 1)))
                                j++;
                            else
                                break;
                        if (stream.charAt(j) == '&' || stream.charAt(j) == '|' || stream.charAt(j) == '(' || stream.charAt(j) == ')' || stream.charAt(j) == '"' || stream.charAt(j) == '!') {
                            j--;
                            break;
                        } else {
                            j++;
                        }
                    }
                    //Edge Case Handling if we are at the end of the query
                    String trim;
                    try {
                        trim = stream.substring(i, j + 1).trim();
                    } catch (Exception e) {
                        trim = stream.substring(i, j).trim();
                    }

                    //Implied And Case. add the tokens as words and query will treat it as an AndToken between them
                    if (trim.contains(" ")) {
                        String[] temp = trim.split(" ");
                        for (String s : temp) {
                            tokens.add(new wordToken(s));
                        }
                    } else {
                        tokens.add(new wordToken(trim));
                    }
                    i = j;
                }
                i++;
            }
        }
        //error in tokenizing
        catch (Exception e) {
            return;
        }
    }
}
