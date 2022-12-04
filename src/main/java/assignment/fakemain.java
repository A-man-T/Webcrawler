package assignment;

import java.util.ArrayList;
import java.util.Queue;

public class fakemain {


    public static void main(String[] args) {

        WebQueryEngine eng = new WebQueryEngine(new WebIndex());
        eng.tokenize("penis me");
        Queue<Token> one = eng.tokens;
        eng.tokenize("((wealth & fame    bitches)  happiness)");
        Queue<Token> two = eng.tokens;
        System.out.println("asfd");
    }


}
