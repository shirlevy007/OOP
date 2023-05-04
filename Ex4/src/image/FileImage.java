package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.lang.reflect.Array;
import java.util.List;

/**
 * A package-private class of the package image.
 *
 * @author Dan Nirel
 */
class FileImage implements Image {
    /**
     * according to instructions - power of 2
     */
    private final static int BASE = 2;
    /**
     * default color according to instructions
     */
    private final static Color DEFAULT_COLOR = new Color(255, 255, 255);
    /**
     * image width
     */
    private final int width;
    /**
     * image height
     */
    private final int height;
    /**
     * array of image pixels after padding
     */
    private final Color[][] pixelArray;
    /**
     * image from user
     */
    private final java.awt.image.BufferedImage img;

    /**
     *
     * @param x origin height\width
     * @return closest power of 2 (higher)
     */
    private static int nextPower2(int x) {
        double logged = (Math.log(x) / Math.log(BASE));
        int intLogged = (int) Math.ceil(logged);
        return (int) Math.pow(BASE, intLogged);
    }

    /**
     *
     * @param filename path to image file
     * @throws IOException if wrong path
     */
    public FileImage(String filename) throws IOException {
        java.awt.image.BufferedImage im = ImageIO.read(new File(filename));
        img=im;
        int origWidth = im.getWidth(), origHeight = im.getHeight();
        //im.getRGB(x, y)); getter for access to a specific RGB rates

        height = nextPower2(origHeight);
        int marginHeight = (height - origHeight) / 2;
        width = nextPower2(origWidth);
        int marginWidth = (width - origWidth) / 2;

        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i < marginHeight || i >= origHeight + marginHeight) {
                    pixelArray[i][j] = DEFAULT_COLOR;
                    continue;
                }
                if (j < marginWidth || j >= origWidth + marginWidth) {
                    pixelArray[i][j] = DEFAULT_COLOR;
                    continue;
                }
                pixelArray[i][j] = new Color(im.getRGB(j- marginWidth, i- marginHeight));
            }
        }
    }

    /**
     *
     * @return width of image
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return height of image
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     *
     * @param x height coordinate
     * @param y width coordinate
     * @return Color of padded image at coordinates
     */
    @Override
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

}
