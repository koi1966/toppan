package toppan.example.toppan.createDoc;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class CreateDoc {

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }


    public void CreateDoc() throws IOException {

//        XWPFDocument document = new XWPFDocument(new FileInputStream("C:/demo/rubin1.docx"));
//
//        XWPFTable table = document.getTableArray(0);
//
//        org.apache.xmlbeans.XmlCursor cursor = table.getCTTbl().newCursor();
//        cursor.toEndToken(); //now we are at end of the CTTbl
//        //there always must be a next start token. Either a p or at least sectPr.
//        while(cursor.toNextToken() != org.apache.xmlbeans.XmlCursor.TokenType.START);
//        XWPFParagraph newParagraph = document.insertNewParagraph(cursor);
//        XWPFRun run = newParagraph.createRun();
//        run.setText("inserted new text");
//
//        document.write(new FileOutputStream("C:/demo/rubin1.docx"));
//        document.close();

        //Blank Document
        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(new File("C:/demo/create_table.docx"));


        //create table
//        XWPFTable table = document.createTable();
        XWPFTable table = document.getTablePos();
//        //create first row
        XWPFTableRow tableRowOne = table.getRow(2);
//        tableRowOne.getCell(2).setText("1");
//        tableRowOne.addNewTableCell().setText("2");
//        tableRowOne.addNewTableCell().setText("3");

        //create second row
//        XWPFTableRow tableRowTwo = table.createRow();
//        tableRowTwo.getCell(0).setText("col one, row two");
//        tableRowTwo.getCell(1).setText("col two, row two");
//        tableRowTwo.getCell(2).setText("col three, row two");
//
//        //create third row
//        XWPFTableRow tableRowThree = table.createRow();
//        tableRowThree.getCell(0).setText("col one, row three");
//        tableRowThree.getCell(1).setText("col two, row three");
//        tableRowThree.getCell(2).setText("col three, row three");

        document.write(out);
        out.close();
//        System.out.println("create_table.docx written successully");


    }
}

