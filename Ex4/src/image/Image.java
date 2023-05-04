package image;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Facade for the image module and an interface representing an image.
 * @author Dan Nirel
 */
public interface Image {
    /**
     *
     * @param x height coordinate
     * @param y width coordinate
     * @return Color of the new pixel array of the image at coordinates
     */
    Color getPixel(int x, int y);

    /**
     *
     * @return width of image
     */
    int getWidth();

    /**
     *
     * @return height of image
     */
    int getHeight();

    /**
     * Open an image from file. Each dimensions of the returned image is guaranteed
     * to be a power of 2, but the dimensions may be different.
     * @param filename a path to an image file on disk
     * @return an object implementing Image if the operation was successful,
     * null otherwise
     */
    static Image fromFile(String filename) {
        try {
            return new FileImage(filename);
        } catch(IOException ioe) {
            return null;
        }
    }

    /**
     * Allows iterating the pixels' colors by order (first row, second row and so on).
     * @return an Iterable<Color> that can be traversed with a foreach loop
     */
    default Iterable<Color> pixels() {
        return new ImageIterableProperty<>(
                this, this::getPixel);
    }

    /**
     *
     * @param size of sub pic
     * @return list of topLeft - coordinates to top left corners to each sub pic
     */
    default ArrayList<int[]> subPicsTopLeft (int size){
        ArrayList<int[]> topLefts = new ArrayList<>();
        for (int i = 0; i < getHeight(); i+=size) {
            for (int j = 0; j < getWidth(); j+=size) {
                topLefts.add(new int[]{i,j});
            }
        }
        return topLefts;
    }
}
