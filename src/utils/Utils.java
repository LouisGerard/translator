package utils;

public class Utils {
    public static <T> boolean arrayContains(T[] array, T needle) {
        for (T item : array)
            if (needle == item)
                return true;
        return false;
    }

    public static <T> int multiIndexOf(String text, Character[] needles) {
        for (int i = 0; i < text.length(); ++i) {
            for (char needle : needles)
                if (needle == text.charAt(i))
                    return i;
        }
        return -1;
    }
}
