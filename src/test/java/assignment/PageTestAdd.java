package assignment;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PageTestAdd {

    @Test
    public void checkAdd() throws IOException, ClassNotFoundException {
        WebCrawler.main(new String[] {"file:/Users/amantewari/Turing/prog7/president96/index.html"});
        WebIndex web = (WebIndex) Index.load("index.db");
        WebQueryEngine wqe = WebQueryEngine.fromIndex((WebIndex) Index.load("index.db"));
        Scanner scan = new Scanner(new File("index.txt"));
        while(scan.hasNext()){
            assertEquals(web.getPages().get(0).getContentsString(),scan.next());
        }

    }

}