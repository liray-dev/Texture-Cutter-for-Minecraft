package ru.muwwy.helpers;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileHelper {

    public static File selectFile(File startFolder) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл");
        fileChooser.setCurrentDirectory(startFolder);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Изображения", "png", "jpg", "jpeg");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }

    public static File selectDirectory(File startFolder) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите папку");
        fileChooser.setCurrentDirectory(startFolder);

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static File buildPath(File file, File output) {
        String fileParent = file.getParent();
        String outputParent = output.getPath();

        if (fileParent.equals(outputParent)) {
            File i = new File(output.getPath() + "/" + getDate());
            i.mkdirs();
            return i;
        }
        output.mkdirs();
        return output;
    }

    public static String getDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return now.format(formatter);
    }

}
