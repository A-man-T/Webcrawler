package assignment;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Queue;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @Test
    public void tokenizerTest() throws  IOException {
        Scanner scan = new Scanner(new File("test_queries_green.txt"));
        WebQueryEngine eng = new WebQueryEngine(new WebIndex());
        eng.tokenize("healo me");
        Queue<Token> one = eng.tokens;
        eng.tokenize("((wealth & fame    lol)  happiness)");
        Queue<Token> two = eng.tokens;
        while(scan.hasNextLine()){
            String s = scan.nextLine();
            System.out.println(s);
            eng.tokenize(s);
        }
    }

}