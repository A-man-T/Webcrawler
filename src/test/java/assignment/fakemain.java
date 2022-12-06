package assignment;

import java.util.ArrayList;
import java.util.Queue;

public class fakemain {


    public static void main(String[] args) {
        WebQueryEngine eng = new WebQueryEngine(new WebIndex());
        eng.tokenize("healo me");
        Queue<Token> one = eng.tokens;
        eng.tokenize("((wealth & fame    lol)  happiness)");
        Queue<Token> two = eng.tokens;
    }


}
