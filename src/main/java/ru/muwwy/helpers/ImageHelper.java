package ru.muwwy.helpers;

import ru.muwwy.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageHelper {

    public static File apply(BufferedImage image, File file, File folder) {
        String format = file.getPath().split("\\.")[1];
        String result = folder + "/" + folder.getName() + "." + format;
        try {
            ImageIO.write(image, format, new File(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(result);
    }

    public static void show(BufferedImage source, BufferedImage modified) {
        Main.setIcon(source);
        Main.drawImage(Main.image, source);
        Main.drawImage(Main.blurImage, modified);
    }

}
