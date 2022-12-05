package assignment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SanityChecks {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        WebCrawler.main(new String[] {"file:/Users/amantewari/Turing/prog7/president96/index.html"});
        WebQueryEngine wqe = WebQueryEngine.fromIndex((WebIndex) Index.load("index.db"));
        Scanner scan = new Scanner(new File("test_queries_green.txt"));
        System.out.println("Start Here:            ");
        ArrayList<Integer> results = new ArrayList<>();
        while(scan.hasNext()) {
            String query = scan.nextLine();
            try {
                results.add(wqe.query(query).size());
            }
            catch(Exception e){
                System.err.print("failed on this one");
                System.err.println(query);
            }
        }
    }
}