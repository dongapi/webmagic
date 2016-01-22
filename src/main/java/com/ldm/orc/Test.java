
package com.ldm.orc;

import java.io.File;
import java.io.IOException;

public class Test {

    /** */
    /**
     * @param args
     */
    public static void main(String[] args) {
    	
    	for(int i=1;i<8;i++){
    		String path = "pics/"+i+".jpg";
			try {
				String valCode = new OCR().recognizeText(new File(path), null);
				System.out.println(i+".jpg:"+valCode.trim());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    }

}
