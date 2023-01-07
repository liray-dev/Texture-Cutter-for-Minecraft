package ru.muwwy.helpers;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel {

    private int width, height;

    public CustomPanel() {
        super(true);
        width = 200;
        height = 200;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void setPanelSize(int width, int height) {
        this.width = width;
        this.height = height;
    }


}