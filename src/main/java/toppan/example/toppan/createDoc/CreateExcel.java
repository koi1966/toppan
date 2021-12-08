package toppan.example.toppan.createDoc;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class CreateExcel {

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

public void CreateF(String rubinStr) throws IOException {

    String[] str = rubinStr.split(",");
    XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream("C:/RSC1840/Temp_rubin.xlsx"));
    FileOutputStream fileOut = new FileOutputStream("C:/RSC1840/rubin.xlsx");
//    XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream("Temp_rubin.xlsx"));
//    FileOutputStream fileOut = new FileOutputStream("rubin.xlsx");
    XSSFSheet sheet1 = wb.getSheet("Rubin");
    XSSFRow row22 = sheet1.getRow(21);
    XSSFCell cell5 = row22.getCell(5);
    cell5.setCellValue(str[0]);

    XSSFRow row24 = sheet1.getRow(23);
    XSSFCell cell24 = row24.getCell(5);
    cell24.setCellValue(str[1]);

    XSSFRow row227 = sheet1.getRow(21);
    XSSFCell cell7 = row227.getCell(7);
    cell7.setCellValue(str[2]);

    XSSFRow row247 = sheet1.getRow(23);
    XSSFCell cell = row247.getCell(7);
    cell.setCellValue(str[3]);
// дата - суббота
    XSSFRow dat_p = sheet1.getRow(13);
    XSSFCell cell_p = dat_p.getCell(7);
    cell_p.setCellValue(str[4]);

    wb.write(fileOut);
//    log.info("Written xls file");
    fileOut.close();
    wb.close();
}

}
