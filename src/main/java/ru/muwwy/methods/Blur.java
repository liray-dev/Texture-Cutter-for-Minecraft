package ru.muwwy.methods;

import ru.muwwy.helpers.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Blur {

    private static int clamp(int var, int min, int max) {
        if (var <= min) return min;
        else return Math.min(var, max);
    }

    public static BufferedImage blur(File file, int quality) throws IOException {
        BufferedImage img = ImageIO.read(file);
        BufferedImage b = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = b.createGraphics();

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {

                //horizontal

                int[] red = new int[quality * 2], green = new int[quality * 2], blue = new int[quality * 2];
                int[] pixels = new int[quality * 2];

                for (int i = 0; i < pixels.length; i++) {
                    pixels[i] = img.getRGB(clamp(x - clamp(quality / 2, 0, quality) + i, 0, img.getWidth() - 1), clamp(y - clamp(quality / 2, 0, quality), 0, img.getHeight() - 1));

                    red[i] = (pixels[i] >> 16) & 0xff;
                    green[i] = (pixels[i] >> 8) & 0xff;
                    blue[i] = (pixels[i]) & 0xff;
                }

                int red_t = 0, green_t = 0, blue_t = 0;

                for (int i = 0; i < pixels.length; i++) {
                    red_t += red[i];
                    green_t += green[i];
                    blue_t += blue[i];
                }

                int r = red_t / (quality * 2);
                int gr = green_t / (quality * 2);
                int bl = blue_t / (quality * 2);


                g.setColor(new Color(r, gr, bl));
                g.fillRect(x, y, 1, 1);

            }
        }
        g.dispose();

        ImageHelper.show(img, b);

        return b;
    }

}