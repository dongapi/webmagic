
package com.ldm.orc;

import java.io.File;
import java.io.IOException;

public class Test {

    /** */
    /**
     * @param args
     */
    public static void main(String[] args) {
        String path = "C:/Users/Administrator/Desktop/111/60.jpg";
        try {
            String valCode = new OCR().recognizeText(new File(path), null);
            System.out.println(valCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
