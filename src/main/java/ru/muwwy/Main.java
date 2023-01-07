package ru.muwwy;

import ru.muwwy.helpers.CustomPanel;
import ru.muwwy.methods.Blur;
import ru.muwwy.methods.Pixelate;
import ru.muwwy.methods.Resize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static ru.muwwy.helpers.ImageHelper.apply;
import static ru.muwwy.helpers.CutHelper.trimAndExportImage;
import static ru.muwwy.helpers.FileHelper.*;

public class Main {

    public static JFrame frame = new JFrame();
    public static CustomPanel top = new CustomPanel();
    public static CustomPanel image = new CustomPanel();
    public static CustomPanel blurImage = new CustomPanel();
    public static CustomPanel bottom = new CustomPanel();
    public static JCheckBox resize = new JCheckBox("Resize");
    public static JCheckBox pixelate = new JCheckBox("Pixelate");
    public static JCheckBox blur = new JCheckBox("Blur");

    public static void main(String[] args) {
        initComponents();
    }

    private static void initComponents() {
        GridLayout layout = new GridLayout(2, 4);
        frame.setLayout(layout);

        frame.setTitle("Texture Cutter for Minecraft v2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        final JTextField size = new JTextField("16x16");
        final JTextField param = new JTextField("64x64");
        final JButton button = new JButton("Select a file..");


        button.addActionListener(ae -> {
            String[] sizes = size.getText().split("x");
            String[] p = param.getText().split("x");

            // Clear old Images
            Main.image.updateUI();
            Main.blurImage.updateUI();

            boolean bool = action(p, Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1]));

            if (bool) {
                System.out.println("Good!");

//                String message = "\"Successful!\"";
//                JOptionPane.showMessageDialog(new JFrame(), message, "COMPLETED",
//                        JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Bad!");

                String message = "\"File not found!\"\n" + "Set a normal path!";
                JOptionPane.showMessageDialog(new JFrame(), message, "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        ItemListener itemListener = e -> {
            JCheckBox checkbox = (JCheckBox) e.getItem();
            String text = checkbox.getText();
            switch (text) {
                case "Resize":
                    param.setText("64x64");
                    blur.setSelected(false);
                    pixelate.setSelected(false);
                    break;
                case "Pixelate":
                    param.setText("2");
                    blur.setSelected(false);
                    resize.setSelected(false);
                    break;
                case "Blur":
                    param.setText("6");
                    resize.setSelected(false);
                    pixelate.setSelected(false);
                    break;
            }
        };
        resize.addItemListener(itemListener);
        pixelate.addItemListener(itemListener);
        blur.addItemListener(itemListener);

        top.setLayout(layout);
        top.setPanelSize(500, 200);

        image.setLayout(layout);
        image.setPanelSize(500, 200);

        blurImage.setLayout(layout);
        blurImage.setPanelSize(500, 200);

        bottom.setLayout(layout);
        bottom.setPanelSize(500, 200);

        top.add(size);
        top.add(param);
        top.add(image);
        top.add(blurImage);
        bottom.add(resize);
        bottom.add(pixelate);
        bottom.add(blur);
        bottom.add(button);

        frame.add(top);
        frame.add(bottom);

        frame.pack();
        frame.setVisible(true);

        resize.setSelected(true);
    }

    public static boolean action(String[] param, int width, int height) {
        try {
            // Jar path
            File jarFilePath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

            // Selected File
            File file = selectFile(jarFilePath);
            if (file == null) return false;

            // Selected Folder
            File output = selectDirectory(new File(file.getParent()));
            if (output == null) return false;

            // Method
            BufferedImage method;
            if (resize.isSelected()) {
                method = Resize.resize(file, Integer.parseInt(param[0]), Integer.parseInt(param[1]));
            } else if (pixelate.isSelected()) {
                method = Pixelate.pixelate(file, Integer.parseInt(param[0]));
            } else if (blur.isSelected()) {
                method = Blur.blur(file, Integer.parseInt(param[0]));
            } else {
                method = Resize.resize(file, Integer.parseInt(param[0]), Integer.parseInt(param[1]));
            }

            // Folder name
            File folderNameResult = buildPath(file, output);

            // Apply Compression
            File compressImage = apply(method, file, folderNameResult);

            // Cut and Save
            trimAndExportImage(compressImage, width, height);
            return true;
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setIcon(BufferedImage image) {
        frame.setIconImage(image);
    }

    public static void drawImage(CustomPanel panel, BufferedImage img) {
        panel.setPanelSize(img.getWidth() * 3, img.getHeight() * 3);
        panel.getGraphics().drawImage(img, 0, 0, img.getWidth() / 2, img.getHeight() / 2, null);
    }

}
