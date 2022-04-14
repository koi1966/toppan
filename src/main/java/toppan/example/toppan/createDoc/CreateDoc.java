package toppan.example.toppan.createDoc;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;
import toppan.example.toppan.models.ReportData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class CreateDoc {

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }


//    public void createDoc(ReportData reportData, String monthUA, String year, String tsc, String filename) throws IOException {
    public String createDoc(ReportData reportData, String tsc, LocalDate to, String filename) throws IOException {
//        String separator = File.separator;
//        String path = "c:\\rsc1840\\Rubin"+tsc+".docx";
//        String path = "c:\\rsc1840\\texst.txt";
//        String path = "c:" + separator + "rsc1840" + separator + "texst.txt";

        //Blank Document
        XWPFDocument document = new XWPFDocument(new FileInputStream(filename));

        XWPFTable table = document.getTableArray(1);
        XWPFTableRow tableRowOne = table.getRow(1);
        //Получить все абзацы
        List<XWPFParagraph> list = document.getParagraphs();
        //Получить все таблицы
        List<XWPFTable> tables = document.getTables();
        //  ккккккккккккккккккккккккккккккккккккккккккккк
        tableRowOne.getCell(2).setText(reportData.getRequestOld());
        tableRowOne.getCell(3).setText(reportData.getRequest());
        tableRowOne = table.getRow(2);
        tableRowOne.getCell(2).setText(reportData.getIssuedOld());
        tableRowOne.getCell(3).setText(reportData.getIssued());
//  ккккккккккккккккккккккккккккккккккккккккккккк
        for (XWPFParagraph p : document.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains("tsc")) {
                        text = text.replace("tsc", tsc);
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("dat")) {
                        text = text.replace("dat", reportData.getMonthUA()+" "+reportData.getYearTxt());
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("dak")) {
                        text = text.replace("dak", reportData.getMinusMonthUA()+" "+reportData.getYearTxt());
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("to")) {
                        text = text.replace("to", to.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                        r.setText(text, 0);
                    }
                }
            }
        }
        for (XWPFTable tbl : document.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            String text = r.getText(0);
                            if (text != null && text.contains("dak")) {
                                text = text.replace("dak", reportData.getMinusMonthUA()+" "+reportData.getYearTxt());
                                r.setText(text, 0);
                            }
                            if (text != null && text.contains("das")) {
                                text = text.replace("das", reportData.getMinusYear());
                                r.setText(text, 0);
                            }
                        }
                    }
                }
            }
        }
//        String separator = File.separator;

//        String path = "c:" + separator + "rsc1840" + separator + "Rubin"+tsc+".docx";
        String path = "c:/rsc1840/Rubin"+tsc+".docx";
//        String path = "c:/rsc1840/Rubin.docx";
        String pathOut = path.replaceAll("(?<! ) (?! )", "_");
        document.write(new FileOutputStream(pathOut));
        document.close();
//        out.close();
//        System.out.println(path.replaceAll("(?<! ) (?! )", "_"));
//        EmailFilename.send("o.klymchuk@zhi.hsc.gov.ua","c:/RSC1840/rubin.docx");
        return pathOut;
    }

}

