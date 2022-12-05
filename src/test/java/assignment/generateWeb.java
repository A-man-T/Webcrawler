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

    @Test
    public void generateTextOnlyWeb(){

        String file = "1.txt";

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("./testWeb/"+file));
            bw.write("<html><head><title>New Page</title></head><body><p>This is Body</p></body></html>");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
