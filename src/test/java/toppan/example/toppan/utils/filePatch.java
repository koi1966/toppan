package toppan.example.toppan.utils;

import org.apache.pdfbox.io.RandomAccessFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class filePatch {
    public static void main(String[] args) throws IOException {
        String string;

        File file = new File(new File("Rubin.doc").getAbsolutePath());
        System.out.println(file);

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "Rubin1841.docx");
            string = randomAccessFile.toString();
            randomAccessFile.close();
            System.out.print(string);
        } catch (FileNotFoundException ex) {}
    }
}
