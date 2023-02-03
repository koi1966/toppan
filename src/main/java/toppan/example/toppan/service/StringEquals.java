package toppan.example.toppan.service;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StringEquals {

    public static boolean equalsSQL(String str1, String str2) {
        return Objects.equals(str1, str2);
    }

    public static void send(String str){

    }

}
