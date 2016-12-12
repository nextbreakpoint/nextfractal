package com.nextbreakpoint.nextfractal.core.javaFX;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

import java.io.InputStream;

public class Icons {
    private Icons() {}

    public static ImageView createIconImage(Class clazz, String name, double percentage) {
        int size = (int)Math.rint(Screen.getPrimary().getVisualBounds().getWidth() * percentage);
        InputStream stream = clazz.getResourceAsStream(name);
        ImageView image = new ImageView(new Image(stream));
        image.setSmooth(true);
        image.setFitWidth(size);
        image.setFitHeight(size);
        return image;
    }

    public static ImageView createIconImage(Class clazz, String name) {
        return createIconImage(clazz, name, computeOptimalIconPercentage());
    }

    public static double computeOptimalIconPercentage() {
        double size = 0.015;

        Screen screen = Screen.getPrimary();

        if (screen.getDpi() > 100 || screen.getVisualBounds().getWidth() > 1200) {
            size = 0.016;
        }

        if (screen.getDpi() > 200 || screen.getVisualBounds().getWidth() > 2400) {
            size = 0.018;
        }

        return size;
    }

    public static double computeOptimalLargeIconPercentage() {
        double size = 0.017;

        Screen screen = Screen.getPrimary();

        if (screen.getDpi() > 100 || screen.getVisualBounds().getWidth() > 1200) {
            size = 0.018;
        }

        if (screen.getDpi() > 200 || screen.getVisualBounds().getWidth() > 2400) {
            size = 0.020;
        }

        return size;
    }
}
