package com.kimbactran.magicpostbe.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.spire.xls.Worksheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.spire.xls.Workbook;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class PdfHandler {
    public void convertExcelToPdf() {
        try {
            FileInputStream inputStream = new FileInputStream("C:\\magic-post-be\\src\\main\\resources\\Order\\export.xlsx");
            XSSFWorkbook workbookStatic = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbookStatic.getSheetAt(0);
            Document document = new Document(PageSize.A4);
            FileOutputStream fos = new FileOutputStream("D:\\QRImg\\exTitlample.pdf");
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            document.open();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            workbookStatic.write(stream);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(stream.toByteArray()));

            Image img = Image.getInstance("D:\\QRImg\\example.png");
            document.newPage();
            document.add(img);
            document.close();
            workbookStatic.close();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void convertExcelToPdfV2() throws IOException, DocumentException {
//                    XSSFSheet sheet = workbook.getSheetAt(0);
        Workbook workbook = new Workbook();
        workbook.loadFromFile("C:\\magic-post-be\\src\\main\\resources\\Order\\export.xlsx");

        workbook.getConverterSetting().setSheetFitToPage(true);

        //Get the first worksheet
        Worksheet worksheet = workbook.getWorksheets().get(0);

        //Convert to PDF and save the resulting document to a specified path
        worksheet.saveToPdf("C:\\magic-post-be\\src\\main\\resources\\Order\\WorksheetToPdf.pdf");


        FileOutputStream fos = new FileOutputStream("D:\\QRImg\\example.pdf");
        PdfReader reader = new PdfReader("C:\\magic-post-be\\src\\main\\resources\\Order\\WorksheetToPdf.pdf");
        PdfStamper stamper = new PdfStamper(reader,fos);
//        stamper.add
//        Image img = Image.getInstance("D:\\QRImg\\example.png");
//        img.scaleToFit(50, 50);
//        document.add(img);
//        document.close();

        Image image = Image.getInstance("D:\\QRImg\\example.png");
        PdfImage stream = new PdfImage(image, "", null);
        stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
        PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
        image.scaleToFit(90, 90);
        image.setDirectReference(ref.getIndirectReference());
        image.setAbsolutePosition(700, 500);
        PdfContentByte over = stamper.getOverContent(1);
        over.addImage(image);
        stamper.close();
        reader.close();

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
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
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


    private static void addPDFData(List<String> list, PdfPTable table) {
        list
                .forEach(column -> {
                    PdfPCell header = new PdfPCell();
                    header.setPhrase(new Phrase(column));
                    table.addCell(header);
                });
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

//    public void exportPdfFinal() throws Exception {
//        // Create Workbook to load Excel file
//        com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook("C:\\magic-post-be\\src\\main\\resources\\Order\\export.xlsx");
//
//        // Save the document in PDF format
//        PdfSaveOptions options = new PdfSaveOptions();
//
//        // To render sheet2 only
//        options.setPageIndex(0);
//        options.setPageCount(0);
//        workbook.save("D:\\QRImg\\Excel-to-PDF.pdf", SaveFormat.PDF);
//    }


    public ByteArrayInputStream exportPdfFinal() throws Exception {
//        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setSuffix("D:\\QRImg\\MagicPostOrderTemplate.html");
//        templateResolver.setTemplateMode("HTML");
//
//        TemplateEngine templateEngine = new TemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver);


        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("to", "Baeldung.com");

        String html = templateEngine.process("MagicPostOrderTemplate", context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        renderer.finishPDF();
        return new ByteArrayInputStream(outputStream.toByteArray());

    }


    public void exportPdfFinal2() throws IOException {
        File inputHTML = new File("C:\\magic-post-be\\src\\main\\resources\\MagicPostOrderTemplate.html");
        org.jsoup.nodes.Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings()
                .syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);

        try (OutputStream os = new FileOutputStream("D:\\QRImg\\message.pdf")) {
            String baseUri = FileSystems.getDefault()
                    .getPath("src/main/resources/")
                    .toUri()
                    .toString();
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri("D:\\QRImg\\message.pdf");
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(document), baseUri);
            builder.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
