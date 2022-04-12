package toppan.example.toppan.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j   // Логер
@Service
public class NameFileDoc {

    public String NameWeekFileDoc(String tsc){
        String filename;
        switch (tsc) {
            case "РСЦ 1840":
                filename = "c:\\RSC1840\\Temp_rubin_1840.docx";
                break;
            case "ТСЦ 1841":
                filename = "c:\\RSC1840\\Temp_rubin_1841.docx";
                break;
            case "ТСЦ 1842":
                filename = "c:\\RSC1840\\Temp_rubin_1842.docx";
                break;
            case "ТСЦ 1843":
                filename = "c:\\RSC1840\\Temp_rubin_1843.docx";
                break;
            case "ТСЦ 1844":
                filename = "c:\\RSC1840\\Temp_rubin_1844.docx";
                break;
            case "ТСЦ 1845":
                filename = "c:\\RSC1840\\Temp_rubin_1845.docx";
                break;
            default:
                filename = "C:\\RSC1840\\Temp_rubin.docx";
        }
        log.info("Взято фашд - Тижневий ЗВІТ - tsc: {}", tsc);
        return filename;
    }
}
