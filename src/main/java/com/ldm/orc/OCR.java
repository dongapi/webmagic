
package com.ldm.orc;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;

import org.jdesktop.swingx.util.OS;

public class OCR {
    private final String LANG_OPTION = "-l"; 
    private final String OPTION = "-psm"; 
    private final String EOL = System.getProperty("line.separator");
    private String tessPath = "E:\\software\\Tesseract-OCR";

    // private String tessPath = new File("tesseract").getAbsolutePath();

    public String recognizeText(File imageFile, String imageFormat) throws Exception {
        File tempImage = imageFile;// ImageIOHelper.createImage(imageFile,imageFormat);

        BufferedImage image;
        image = ImageIO.read(imageFile);
        int width = image.getTileWidth();
        int height = image.getTileHeight();

        Map<Integer, Integer> mapColor = new HashMap<Integer, Integer>();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                int color = image.getRGB(i, j);
                Integer count = mapColor.get(color);
                if (count == null)
                    count = 0;
                count++;
                mapColor.put(color, count);
            }
        List<Map.Entry<Integer, Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(
                mapColor.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Entry<Integer, Integer> arg0,
                    Entry<Integer, Integer> arg1) {
                return arg1.getValue() - arg0.getValue();
            }
        });
        list = list.subList(0, 5);// ʵ��ʵ���з��ֺܲ����룬ͬһ����ĸ�Ŵ�ᷢ�ֲ�һ�����ɫ

        int intBack = list.get(0).getKey();
        Set<Integer> setColor = new HashSet<Integer>();
        for (Map.Entry<Integer, Integer> entry : list) {
            setColor.add(entry.getKey());
        }
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                int color = image.getRGB(i, j);
                if (setColor.contains(color))
                    continue;
                image.setRGB(i, j, intBack);
            }


//        File file = new File(imageFile.getParentFile(), "temp.jpg");
//        ImageIO.write(image, "JPEG", file);
//        BufferedImage image_ex;
//        image_ex = ImageIO.read(imageFile);
//        ImageFilter filter = new ImageFilter(image_ex);
//        ;
//        File file_ex = new File(imageFile.getParentFile(), "temp_ex.jpg");
//        ImageIO.write(filter.lineGrey(), "JPEG", file_ex);

        File outputFile = new File(imageFile.getParentFile(), "output");
        StringBuffer strB = new StringBuffer();
        List<String> cmd = new ArrayList<String>();
        if (OS.isWindowsXP()) {
            cmd.add(tessPath + "\\tesseract");
        } else if (OS.isLinux()) {
            cmd.add("tesseract");
        } else {
            cmd.add(tessPath + "\\tesseract");
        }
        cmd.add("");
        cmd.add(outputFile.getAbsolutePath());
        cmd.add(LANG_OPTION);
        // cmd.add("chi_sim");
        cmd.add("eng");
        cmd.add(OPTION);
        cmd.add("7");

        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(imageFile.getParentFile());

        cmd.set(1, imageFile.getAbsolutePath()/* tempImage.getName() */);
        System.out.println("cmd:"+cmd.toString());
        pb.command(cmd);
        pb.redirectErrorStream(true);

        Process process = pb.start();
        // tesseract.exe 1.jpg 1 -l chi_sim
        int w = process.waitFor();
        // tempImage.delete();

        if (w == 0) {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(
                    outputFile.getAbsolutePath() + ".txt"), "UTF-8"));

            String str;
            while ((str = in.readLine()) != null) {
                strB.append(str).append(EOL);
            }
            in.close();
        } else {
            String msg;
            switch (w) {
                case 1:
                    msg = "Errors accessing files.There may be spaces in your image's filename.";
                    break;
                case 29:
                    msg = "Cannot recongnize the image or its selected region.";
                    break;
                case 31:
                    msg = "Unsupported image format.";
                    break;
                default:
                    msg = "Errors occurred.";
            }
            // tempImage.delete();
            throw new RuntimeException(msg);
        }
        new File(outputFile.getAbsolutePath() + ".txt").delete();
        return strB.toString();
    }
}
