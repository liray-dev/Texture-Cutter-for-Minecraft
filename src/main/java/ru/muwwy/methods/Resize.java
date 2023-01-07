package ru.muwwy.methods;

import ru.muwwy.helpers.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resize {

    public static BufferedImage resize(File file, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(file);

        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        newImage.getGraphics().drawImage(resizedImage, 0, 0, null);

        ImageHelper.show(originalImage, newImage);

        return newImage;
    }

}
