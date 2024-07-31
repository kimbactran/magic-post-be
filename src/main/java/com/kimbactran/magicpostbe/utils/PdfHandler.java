package com.kimbactran.magicpostbe.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.kimbactran.magicpostbe.entity.OrderInfo;
import com.spire.xls.Worksheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.spire.xls.Workbook;

import org.springframework.stereotype.Component;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class PdfHandler {
    public void convertExcelToPdf(XSSFWorkbook workbook) {
        try {
//            XSSFSheet sheet = workbook.getSheetAt(0);
            Document document = new Document(PageSize.A4);
            FileOutputStream fos = new FileOutputStream("D:\\QRImg\\exTitlample.pdf");
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            document.open();
            Image img = Image.getInstance("D:\\QRImg\\example.png");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(baos.toByteArray()));
            document.add(img);



            workbook.close();
            document.close();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void convertExcelToPdfV2() {
        //            XSSFSheet sheet = workbook.getSheetAt(0);
        Document document = new Document(PageSize.A4);
        Workbook workbook = new Workbook();
        workbook.loadFromFile("C:\\magic-post-be\\src\\main\\resources\\Order\\export.xlsx");

        workbook.getConverterSetting().setSheetFitToWidth(true);

        //Get the first worksheet
        Worksheet worksheet = workbook.getWorksheets().get(0);

        //Convert to PDF and save the resulting document to a specified path
        worksheet.saveToPdf("C:\\magic-post-be\\src\\main\\resources\\Order\\WorksheetToPdf.pdf");

    }


    public void exportToPdf() throws IOException, DocumentException {
        FileInputStream inputStream = new FileInputStream("C:\\magic-post-be\\src\\main\\resources\\Order\\export.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:\\magic-post-be\\src\\main\\resources\\Order\\example.pdf"));
        document.open();
        XSSFSheet worksheet = workbook.getSheetAt(0);
        PdfPTable table = new PdfPTable(9);
        Iterator<Row> rowIterator = worksheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0) {
                continue;
            }
            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                Cell cell = row.getCell(i);
                String cellValue;
                switch (cell.getCellType()) {
                    case STRING:
                        cellValue = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        cellValue = String.valueOf(BigDecimal.valueOf(cell.getNumericCellValue()));
                        break;
                    case BLANK:
                    default:
                        cellValue = "";
                        break;
                }
                PdfPCell cellPdf = new PdfPCell(new Phrase(cellValue));
                table.addCell(cellPdf);
            }
        }
        document.add(table);
        document.close();
        workbook.close();
    }

    public static List<String> getRow(int index, Sheet sheet) {
        List<String> list = new ArrayList<>();

        for (Cell cell : sheet.getRow(index)) {
            switch (cell.getCellType()) {
                case STRING:
                    list.add(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    list.add(String.valueOf(cell.getNumericCellValue()));
                    break;
                case BOOLEAN:
                    list.add(String.valueOf(cell.getBooleanCellValue()));
                    break;
                case FORMULA:
                    list.add(cell.getCellFormula().toString());
                    break;
            }
        }

        return list;
    }

}
