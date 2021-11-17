package toppan.example.toppan.createDoc;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import toppan.example.toppan.models.Employee;
import toppan.example.toppan.models.EmployeeDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet("Employees sheet");

    List<Employee> list = EmployeeDAO.listEmployees();

//    rubinList.setPidrozdil();
//    Создаём на листе строку, используя    createRow();
//    Создаём в строке ячейку —             createCell();
//    Задаём значение ячейки через          setCellValue();

    int rownum = 0;
    Cell cell;
    Row row;
    //
    HSSFCellStyle style = createStyleForTitle(workbook);

    row = sheet.createRow(rownum);

    // За минулий тиждень
    cell = row.createCell(2, CellType.STRING);
    cell.setCellValue("За минулий тиждень");
    cell.setCellStyle(style);
    // З початку року
    cell = row.createCell(3, CellType.STRING);
    cell.setCellValue("З початку року");
    cell.setCellStyle(style);


    String[] str = rubinStr.split(",");


    rownum++;
    row = sheet.createRow(rownum);

    // EmpNo (A)
    cell = row.createCell(2, CellType.STRING);
    cell.setCellValue(str[0]);
    // EmpName (B)
    cell = row.createCell(3, CellType.STRING);
    cell.setCellValue(str[2]);

    rownum++;

        row = sheet.createRow(rownum);

        //  (A)
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue(str[1]);
//        (B)
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue(str[3]);


//        // Salary (C)
//        cell = row.createCell(2, CellType.NUMERIC);
//        cell.setCellValue(emp.getSalary());
//        // Grade (D)
//        cell = row.createCell(3, CellType.NUMERIC);
//        cell.setCellValue(emp.getGrade());
//        // Bonus (E)
//        String formula = "0.1*C" + (rownum + 1) + "*D" + (rownum + 1);
//        cell = row.createCell(4, CellType.FORMULA);
//        cell.setCellFormula(formula);
//    }

    File file = new File("C:/demo/rubin.xls");
    file.getParentFile().mkdirs();

    FileOutputStream outFile = null;
    try {
        outFile = new FileOutputStream(file);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    workbook.write(outFile);
//    System.out.println("Created file: " + file.getAbsolutePath());

}

}
