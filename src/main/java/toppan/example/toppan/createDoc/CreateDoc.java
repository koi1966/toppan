package toppan.example.toppan.createDoc;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
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


    public void CreateDoc(String rubinStr) throws IOException {
        String[] str = rubinStr.split(",");

        XWPFDocument document = new XWPFDocument(new FileInputStream("C:/demo/rubin.docx"));

        XWPFTable table = document.getTableArray(0);

        org.apache.xmlbeans.XmlCursor cursor = table.getCTTbl().newCursor();
        cursor.toEndToken(); //теперь мы находимся в конце CTTbl

        //всегда должен быть следующий стартовый жетон. Либо p, либо хотя бы  sectPr.

        while (cursor.toNextToken() != org.apache.xmlbeans.XmlCursor.TokenType.START) ;
        XWPFParagraph newParagraph = document.insertNewParagraph(cursor);
        XWPFRun run = newParagraph.createRun();

        table.getRow(0).getCell(0).getText();

        run.setText("inserted new text  ЛЯ ЛЯ");

//        run.setText(rubinStr);

        document.write(new FileOutputStream("C:/demo/rubin.docx"));
        document.close();
    }
//    //Blank Document
//    XWPFDocument document = new XWPFDocument();
//
//    //Write the Document in file system
//    FileOutputStream out = new FileOutputStream(new File("create_table.docx"));
//
//    //create table
//    XWPFTable table = document.createTable();
//
//    //create first row
//    XWPFTableRow tableRowOne = table.getRow(0);
//      tableRowOne.getCell(0).setText("col one, row one");
//      tableRowOne.addNewTableCell().setText("col two, row one");
//      tableRowOne.addNewTableCell().setText("col three, row one");
//
//    //create second row
//    XWPFTableRow tableRowTwo = table.createRow();
//      tableRowTwo.getCell(0).setText("col one, row two");
//      tableRowTwo.getCell(1).setText("col two, row two");
//      tableRowTwo.getCell(2).setText("col three, row two");
//
//    //create third row
//    XWPFTableRow tableRowThree = table.createRow();
//      tableRowThree.getCell(0).setText("col one, row three");
//      tableRowThree.getCell(1).setText("col two, row three");
//      tableRowThree.getCell(2).setText("col three, row three");
//
//      document.write(out);
//      out.close();
//      System.out.println("create_table.docx written successully");
//}
// -------------------------------------------------------------------------
////        XWPFDocument doc = new XWPFDocument(new FileInputStream("C:/demo/rubin1.docx"));
//        XWPFDocument doc= new XWPFDocument();
//        //Write the Document in file system
//        FileOutputStream out = new FileOutputStream(new File("C:/demo/create_table.docx"));
//
//
////        XWPFDocument doc = new XWPFDocument(new FileInputStream("C:/demo/create_table.docx"));
//        for(XWPFTable table : doc.getTables()) {
//            for(XWPFTableRow row : table.getRows()) {
//                for(XWPFTableCell cell : row.getTableCells()) {
//                    System.out.println("cell text: " + cell.getText());
//                    row.getCell(2).setText("Вася");
//                }
//            }
////            doc.write(out);
//        }
////        doc.write(new FileOutputStream("C:/demo/rubin1.docx"));
//        doc.write(out);
//        doc.close();

        // ---------------------------------------------------------------------------------------------------
//        File file = null;
//        FileOutputStream fos = null;
//        XWPFDocument document = null;
//        XWPFParagraph para = null;
//        XWPFRun run = null;
//        try {
//            // Create the first paragraph and set it's text.
//            document = new XWPFDocument();
//            para = document.createParagraph();
//
//            para.setAlignment(ParagraphAlignment.CENTER);
//
//            para.setSpacingAfter(100);
//
//            para.setSpacingAfterLines(10);
//            run = para.createRun();
//            run.addBreak();    // similar to new line
//            run.addBreak();
//
//            XWPFTable table = document.createTable(4, 3);
//
//            table.setRowBandSize(1);
//            table.setWidth(1);
//            table.setColBandSize(1);
//            table.setCellMargins(1, 1, 100, 30);
//
//            table.setStyleID("finest");
//
//
//            table.getRow(1).getCell(1).setText("EXAMPLE OF TABLE");
//            table.getRow(2).getCell(1).setText("fine");
//            XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
//            p1.setAlignment(ParagraphAlignment.CENTER);
//            XWPFRun r1 = p1.createRun();
//            r1.setBold(true);
//            r1.setText("Test Name");
//            r1.setItalic(true);
//            r1.setFontFamily("Courier");
//            r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
//            r1.setTextPosition(100);
//
//            //Locating the cell values
//            table.getRow(0).getCell(1).setText("Value");
//            table.getRow(0).getCell(2).setText("Normal Ranges");
//
//            table.getRow(2).getCell(2).setText("numeric values");
//
//            table.setWidth(120);
//
//            file = new File("C:/demo/create_table.docx");
//            if(file.exists())
//                file.delete();
//
//
//            FileOutputStream out = new FileOutputStream(file);
//            document.write(out);
//            out.close();
//        } catch(Exception e){e.printStackTrace();}
//    }

// -------------------------------------------------------------------------

}

