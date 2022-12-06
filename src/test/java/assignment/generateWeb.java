package assignment;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;


public class generateWeb {

    int numberOfFiles = 10000;
    @Test
    public void generateTextOnlyWebGraph(){
        int i = 0;
        String file = i+".txt";
        BufferedWriter bw = null;
        while(i<numberOfFiles) {
            try {
                i++;
                file = i+ ".txt";
                bw = new BufferedWriter(new FileWriter("./testWeb/" + file));
                bw.write("<html><head><title>New Page</title></head><body><p>This is Body</p></body></html>");
                bw.write("<a href=\""+(i+1)+".txt\">This is a link</a>");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Test
    public void checkTextOnlyWebGraph() throws ClassNotFoundException, IOException {
        WebCrawler.main(new String[] {"file:/Users/amantewari/Turing/prog7/testWeb/1.txt"});
        WebQueryEngine wqe = WebQueryEngine.fromIndex((WebIndex) Index.load("index.db"));
        String query = "a";
        assertEquals(wqe.query(query).size(),numberOfFiles);
        /*
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

         */

    }

}
