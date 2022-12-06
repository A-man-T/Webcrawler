package assignment;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;


public class SanityChecks {


    @Test
    public void sanityGreenChecks() throws ClassNotFoundException, IOException {
        WebCrawler.main(new String[] {"file:/Users/amantewari/Turing/prog7/president96/index.html"});
        WebQueryEngine wqe = WebQueryEngine.fromIndex((WebIndex) Index.load("index.db"));
        Scanner scan = new Scanner(new File("test_queries_green.txt"));
        Scanner answerScanner = new Scanner(new File("green_answers.txt"));
        System.out.println("Start Here:            ");
        ArrayList<Integer> results = new ArrayList<>();
        while(scan.hasNext()) {
            String query = scan.nextLine();
            try {
                assertEquals(wqe.query(query).size(),answerScanner.nextInt());
                //results.add(wqe.query(query).size());
            }
            catch(Exception e){
                System.err.print("failed on this one");
                System.err.println(query);
            }
        }
    }
}