package toppan.example.toppan.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j   // Логер
public class UtilitesSting {

    // поиск N-го вхождения подстроки в строке
    // str - строка
    // substr - разделитель
    //
    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = -1;
        do {
            pos = str.indexOf(substr, pos + 1);
        } while (n-- > 0 && pos != -1);
        return pos;
    }
}
