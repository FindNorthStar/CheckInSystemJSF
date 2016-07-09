package edu.bupt.checkinsystem.util;

/**
 * This is the TextUtils class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/7/9 12:21
 */
public class TextUtils {

    public static boolean isEmpty(String textToBeValidate) {
        if (textToBeValidate == null) {
            return true;
        }

        textToBeValidate = textToBeValidate.replaceAll("\\s+", "");
        return textToBeValidate.equals("");
    }
}
