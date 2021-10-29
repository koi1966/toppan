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

public void CreateF() throws IOException {
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet("Employees sheet");

    List<Employee> list = EmployeeDAO.listEmployees();

    int rownum = 0;
    Cell cell;
    Row row;
    //
    HSSFCellStyle style = createStyleForTitle(workbook);

    row = sheet.createRow(rownum);

    // EmpNo
    cell = row.createCell(0, CellType.STRING);
    cell.setCellValue("EmpNo");
    cell.setCellStyle(style);
    // EmpName
    cell = row.createCell(1, CellType.STRING);
    cell.setCellValue("EmpNo");
    cell.setCellStyle(style);
    // Salary
    cell = row.createCell(2, CellType.STRING);
    cell.setCellValue("Salary");
    cell.setCellStyle(style);
    // Grade
    cell = row.createCell(3, CellType.STRING);
    cell.setCellValue("Grade");
    cell.setCellStyle(style);
    // Bonus
    cell = row.createCell(4, CellType.STRING);
    cell.setCellValue("Bonus");
    cell.setCellStyle(style);

    // Data
    for (Employee emp : list) {
        rownum++;
        row = sheet.createRow(rownum);

        // EmpNo (A)
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue(emp.getEmpNo());
        // EmpName (B)
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue(emp.getEmpName());
        // Salary (C)
        cell = row.createCell(2, CellType.NUMERIC);
        cell.setCellValue(emp.getSalary());
        // Grade (D)
        cell = row.createCell(3, CellType.NUMERIC);
        cell.setCellValue(emp.getGrade());
        // Bonus (E)
        String formula = "0.1*C" + (rownum + 1) + "*D" + (rownum + 1);
        cell = row.createCell(4, CellType.FORMULA);
        cell.setCellFormula(formula);
    }
    File file = new File("C:/demo/employee.xls");
    file.getParentFile().mkdirs();

    FileOutputStream outFile = null;
    try {
        outFile = new FileOutputStream(file);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    workbook.write(outFile);
    System.out.println("Created file: " + file.getAbsolutePath());

}

}
