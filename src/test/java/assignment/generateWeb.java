package assignment;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;


public class generateWeb {

    int numberOfFiles = 1000;
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
        assertEquals(wqe.query("1").size(),0);

    }

    @RepeatedTest(1)
    public void generateAndTestForests() throws ClassNotFoundException,IOException{
        int i = 0;
        String file = i+".txt";
        BufferedWriter bw = null;
        int random = 0;

        while(random<5&&i<numberOfFiles) {
            try {
                random = (int) (Math.random()*6);
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
        int pages =i+1;

        while(i<numberOfFiles) {
            try {
                i++;
                file = i+ ".txt";
                bw = new BufferedWriter(new FileWriter("./testWeb/" + file));
                bw.write("<html><head><title>New Page</title></head><body><p>This is Body</p></body></html>");
                bw.write("<a href=\""+(-1)+".txt\">This is a link</a>");
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        WebCrawler.main(new String[] {"file:/Users/amantewari/Turing/prog7/testWeb/1.txt"});
        WebQueryEngine wqe = WebQueryEngine.fromIndex((WebIndex) Index.load("index.db"));
        assertEquals(wqe.query("(a|!a)").size(),pages);

    }






}
