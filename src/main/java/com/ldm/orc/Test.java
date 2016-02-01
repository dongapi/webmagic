
package com.ldm.orc;

import java.io.File;
import java.io.IOException;


/**
  * @ClassName: Test
  * @Description: 验证码识别测试类
  * @author Administrator
  * @date 2016年1月27日 下午5:51:53
  */
public class Test {

    public static void main(String[] args) {
    	
    	for(int i=1;i<8;i++){
    		String path = "pics/"+i+".jpg";
			try {
				String valCode = new OCR().recognizeText(new File(path), null);
				System.out.println(i+".jpg:"+valCode.trim());
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
}
