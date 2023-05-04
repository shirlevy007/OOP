package ascii_art.img_to_char;

import java.awt.Color;

import image.Image;

import java.util.*;
import java.util.function.BiFunction;

public class BrightnessImgCharMatcher {
    /**
     * according to instructions
     */
    private static final double RED_VAL = 0.2126;
    private static final double GREEN_VAL = 0.7152;
    private static final double BLUE_VAL = 0.0722;
    private static final int FORMULA_255 = 255;
    /**
     * according to instructions
     */
    private final int PIXELS_RES = 16;
    /**
     * font choice according to instructions
     */
    private final String font;
    /**
     * image
     */
    private final Image img;
    /**
     * initial minimum brightness according to instructions
     */
    private double minBrightness = 255;
    /**
     * initial maximum brightness according to instructions
     */
    private double maxBrightness = 0;
    /**
     * a map of all chars as val sorted by their brightness values as keys
     */
    private TreeMap<Double, Character> brightnessCharMap;
    /**
     * saves last brightnesses of sub images if resolution is same, empty if resolution is changed
     */
    private HashMap<int[], Double> brightnessOfSubsMap;


    /**
     *
     * @param img image
     * @param font font choice
     */
    public BrightnessImgCharMatcher(Image img, String font) {

        this.img = img;
        this.font = font;
        this.brightnessOfSubsMap = new HashMap<>();
    }

    /**
     *
     * @param c char in set
     * @return char brightness value
     */
    private double normalizedByResolution(char c) {
        boolean[][] boolBrightness = CharRenderer.getImg(c, PIXELS_RES, font);
        int sum = 0;
        for (int i = 0; i < boolBrightness.length; i++) {
            for (int j = 0; j < boolBrightness[0].length; j++) {
                if (boolBrightness[i][j]) {
                    sum++;
                }
            }
        }
        return (double) sum / PIXELS_RES;
    }

    /**
     *
     * @param c char in set
     * @return final brightness value of c according to formula given
     */
    private double normalized(char c) {
        double normalizedByRes = normalizedByResolution(c);
        return (normalizedByRes - minBrightness) / (maxBrightness - minBrightness);
    }

    /**
     *
     * @param topLeft of sub image
     * @param size of sub image edge
     * @return final brightness value of sub images according to instructions
     */
    private double convertSubPic(int[] topLeft, int size) {
        double sumGrey = 0;
        int pixelsCount = 0;
        double greyPixel;
        Color pix;
        for (int i = topLeft[0]; i < topLeft[0] + size; i++) {
            for (int j = topLeft[1]; j < topLeft[1] + size; j++) {
                pix = this.img.getPixel(i, j);
                greyPixel = pix.getRed() * RED_VAL + pix.getGreen() * GREEN_VAL + pix.getBlue() * BLUE_VAL;
                sumGrey += greyPixel;
                pixelsCount++;
            }
        }
        return sumGrey / (pixelsCount * FORMULA_255);
    }

    /**
     * creates the map as described above
     * @param charSet set of chars from user to be ordered in the map
     */
    private void createBrightnessCharMap(Character[] charSet) {
        for (Character c : charSet) {
            double val = normalizedByResolution(c);
            minBrightness = Math.min(val, minBrightness);
            maxBrightness = Math.max(val, maxBrightness);
        }
        this.brightnessCharMap = new TreeMap<Double, Character>();
        for (Character c : charSet) {
            double val = normalized(c);
            this.brightnessCharMap.put(val, c);
        }
    }

    /**
     *
     * @param val brightness of image
     * @return char with the closest brightness value to val
     */
    private Character closestValInMap(double val) {
        Map.Entry<Double, Character> low = brightnessCharMap.floorEntry(val);
        Map.Entry<Double, Character> high = brightnessCharMap.ceilingEntry(val);
        Character res = null;
        if (low != null && high != null) {
            res = Math.abs(val - low.getKey()) < Math.abs(val - high.getKey())
                    ? low.getValue() : high.getValue();
        }
        else if (low != null || high != null) {
            res = low != null ? low.getValue() : high.getValue();
        }
        return res;
    }

    /**
     * to be used whenever resolution is changed - sub images' brightnesses are changed
     */
    public void removeAllBrightness(){
        brightnessOfSubsMap.clear();
    }

    /**
     * @param numCharsInRow num of chars in row. will predict num of chars in col.
     * @param charSet       chars which builds the pic. no char appears twice. not all in use necessarily.
     * @return char 2D array representing the image in ASCII art
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet) {
        if (charSet.length < 1){
            return new char[][] {{}};
        }
        createBrightnessCharMap(charSet);

        int sizeSub = img.getWidth() / numCharsInRow;
        int numCharsInCol = img.getHeight() / sizeSub;

        char[][] asciiArt = new char[numCharsInCol][numCharsInRow];

        if (brightnessOfSubsMap.isEmpty()){
            for (int[] topLeft : img.subPicsTopLeft(sizeSub)) {
                double val = convertSubPic(topLeft, sizeSub);
                this.brightnessOfSubsMap.put(topLeft, val);
                Character c = closestValInMap(val);
                if (c != null) {
                    asciiArt[topLeft[0] / sizeSub][topLeft[1] / sizeSub] = c;
                }
            }
        }
        else {
            for (var topLeft : this.brightnessOfSubsMap.keySet()) {
                Character c = closestValInMap(this.brightnessOfSubsMap.get(topLeft));
                if (c != null) {
                    asciiArt[topLeft[0] / sizeSub][topLeft[1] / sizeSub] = c;
                }
            }
        }

        return asciiArt;
    }
}
