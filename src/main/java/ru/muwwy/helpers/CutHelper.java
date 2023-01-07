package ru.muwwy.helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CutHelper {

    public static void trimAndExportImage(File compressImage, int width, int height) {
        try {
            BufferedImage inputImage = ImageIO.read(compressImage);

            int inputWidth = inputImage.getWidth();
            int inputHeight = inputImage.getHeight();

            int rows = inputHeight / height;
            int cols = inputWidth / width;

            String name = compressImage.getName().split("\\.")[0];

            int index = 0;
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    BufferedImage outputImage = inputImage.getSubimage(y * width, x * height, width, height);

                    ImageIO.write(outputImage, "png", new File(compressImage.getParent() + "/" + name + String.format(" %d.png", index)));
                    index++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
