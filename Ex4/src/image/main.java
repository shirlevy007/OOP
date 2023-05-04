package image;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_art.img_to_char.CharRenderer;

import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        Image img = Image.fromFile("C:/Users/shirl/JavaProjects/Ex4/ex4/2inRow.jpeg");
        BrightnessImgCharMatcher charMatcher = new BrightnessImgCharMatcher(img, "Ariel");
        Character[] charSet =new Character[] {'m', 'o'};
        var chars = charMatcher.chooseChars(2, charSet);
        System.out.println(Arrays.deepToString(chars));

    }
}
