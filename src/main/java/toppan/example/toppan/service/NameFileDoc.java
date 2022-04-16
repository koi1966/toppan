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
        log.info("Взято файл - Тижневий ЗВІТ - tsc: {}", filename);
        return filename;
    }

    public String NameMonthFileDoc(String tsc){
        String filename;
        switch (tsc) {
            case "РСЦ 1840":
                filename = "c:\\RSC1840\\Temp_rubin_Mounth_1840.docx";
                break;
            case "ТСЦ 1841":
                filename = "c:\\RSC1840\\Temp_rubin_Mounth_1841.docx";
                break;
            case "ТСЦ 1842":
                filename = "c:\\RSC1840\\Temp_rubin_Mounth_1842.docx";
                break;
            case "ТСЦ 1843":
                filename = "c:\\RSC1840\\Temp_rubin_Mounth_1843.docx";
                break;
            case "ТСЦ 1844":
                filename = "c:\\RSC1840\\Temp_rubin_Mounth_1844.docx";
                break;
            case "ТСЦ 1845":
                filename = "c:\\RSC1840\\Temp_rubin_Mounth_1845.docx";
                break;
            default:
                filename = "C:\\RSC1840\\Temp_rubin_Mounth.docx";
        }
        log.info("Взято фаіл - Місячний ЗВІТ - {}", tsc);
        return filename;
    }

    public String NameFileOut(String tsc){
        String filenameOut;
        switch (tsc) {
            case "РСЦ 1840":
                filenameOut = "1840";
                break;
            case "ТСЦ 1841":
                filenameOut = "1841";
                break;
            case "ТСЦ 1842":
                filenameOut = "1842";
                break;
            case "ТСЦ 1843":
                filenameOut = "1843";
                break;
            case "ТСЦ 1844":
                filenameOut = "1844";
                break;
            case "ТСЦ 1845":
                filenameOut = "1845";
                break;
            default:
                filenameOut = "184x";
        }
        return filenameOut;
    }
}
