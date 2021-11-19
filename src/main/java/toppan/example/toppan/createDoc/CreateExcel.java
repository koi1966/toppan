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
    XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream("C:/demo/Temp_rubin.xlsx"));
    FileOutputStream fileOut = new FileOutputStream("C:/demo/rubin.xlsx");
    XSSFSheet sheet1 = wb.getSheet("Rubin");
    XSSFRow row22 = sheet1.getRow(21);
    XSSFCell cell5 = row22.getCell(5);
    cell5.setCellValue(str[0]);

    XSSFRow row24 = sheet1.getRow(23);
    XSSFCell cell24 = row24.getCell(5);
    cell24.setCellValue(str[1]);
//
    XSSFRow row227 = sheet1.getRow(21);
    XSSFCell cell7 = row227.getCell(7);
    cell7.setCellValue(str[2]);
//
    XSSFRow row247 = sheet1.getRow(23);
    XSSFCell cell = row247.getCell(7);
    cell.setCellValue(str[3]);

    wb.write(fileOut);
//    log.info("Written xls file");
    fileOut.close();





//    POIFSFileSystem fs = new POIFSFileSystem( new FileInputStream("C:/demo/77.xlsx"));
//    XSSFWorkbook wb = new  XSSFWorkbook(fs, true);
////    Загрузит xls, сохранив его структуру (включая macros). Затем вы можете изменить его,
//
//            HSSFSheet sheet1 = wb.getSheet(rubinStr);
////    а потом сохраните его.
//
//    FileOutputStream fileOut = new FileOutputStream("C:/demo/77.xlsx");
//    wb.write(fileOut);
//    fileOut.close();


        //    HSSFWorkbook workbook = new HSSFWorkbook();
//    HSSFSheet sheet = workbook.createSheet("Employees sheet");
//
//    List<Employee> list = EmployeeDAO.listEmployees();
//
////    rubinList.setPidrozdil();
////    Создаём на листе строку, используя    createRow();
////    Создаём в строке ячейку —             createCell();
////    Задаём значение ячейки через          setCellValue();
//
//    int rownum = 0;
//    Cell cell;
//    Row row;
//    //
//    HSSFCellStyle style = createStyleForTitle(workbook);
//
//    row = sheet.createRow(rownum);
//
//    // За минулий тиждень
////    cell = row.createCell(2, CellType.STRING);
////    cell.setCellValue("За минулий тиждень");
////    cell.setCellStyle(style);
//    // З початку року
////    cell = row.createCell(3, CellType.STRING);
////    cell.setCellValue("З початку року");
////    cell.setCellStyle(style);
//
//
//    String[] str = rubinStr.split(",");
//
//
//    rownum++;
//    row = sheet.createRow(rownum);
//
//    // EmpNo (C)
//    cell = row.createCell(2, CellType.STRING);
//    cell.setCellValue(str[0]);
//    // EmpName (D)
//    cell = row.createCell(3, CellType.STRING);
//    cell.setCellValue(str[2]);
//
//    rownum++;
//
//        row = sheet.createRow(rownum);
//
//        //  (C)
//        cell = row.createCell(2, CellType.STRING);
//        cell.setCellValue(str[1]);
////        (D)
//        cell = row.createCell(3, CellType.STRING);
//        cell.setCellValue(str[3]);
//
//
////        // Salary (C)
////        cell = row.createCell(2, CellType.NUMERIC);
////        cell.setCellValue(emp.getSalary());
////        // Grade (D)
////        cell = row.createCell(3, CellType.NUMERIC);
////        cell.setCellValue(emp.getGrade());
////        // Bonus (E)
////        String formula = "0.1*C" + (rownum + 1) + "*D" + (rownum + 1);
////        cell = row.createCell(4, CellType.FORMULA);
////        cell.setCellFormula(formula);
////    }
//
//    File file = new File("C:/demo/rubin.xls");
//    file.getParentFile().mkdirs();
//
//    FileOutputStream outFile = null;
//    try {
//        outFile = new FileOutputStream(file);
//    } catch (FileNotFoundException e) {
//        e.printStackTrace();
//    }
//    workbook.write(outFile);
////    System.out.println("Created file: " + file.getAbsolutePath());

}

}
