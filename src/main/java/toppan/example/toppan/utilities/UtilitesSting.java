package toppan.example.toppan.utilities;

public class UtilitesSting {

    // поиск N-го вхождения подстроки в строке
    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = -1;
        do {
            pos = str.indexOf(substr, pos + 1);
        } while (n-- > 0 && pos != -1);
        return pos;
    }
}
