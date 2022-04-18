/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubao.utils;

/**
 *
 * @author CND
 */
import hieubao.category.CategoryDTO;
import hieubao.product.ProductDTO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.catalina.connector.InputBuffer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XmlUtil {

    public static void GenerateXmlFile(String xmlFilePath) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuild = documentFactory.newDocumentBuilder();
        Document document = documentBuild.newDocument();

        //root element
        Element root = document.createElement("root");
        document.appendChild(root);

        // categories element
        Element categories = document.createElement("categories");
        root.appendChild(categories);

        for (int i = 1; i <= 5; i++) {
            // category
            Element category = document.createElement("category");
            Element idCategory = document.createElement("idCategory");
            idCategory.setTextContent("" + i);
            category.appendChild(idCategory);
            Element nameCategory = document.createElement("nameCategory");
            nameCategory.setTextContent("Nike " + i);
            category.appendChild(nameCategory);
            categories.appendChild(category);
        }

        // products element
        Element products = document.createElement("products");
        root.appendChild(products);

        for (int i = 1; i <= 1000; i++) {
            // product
            Element product = document.createElement("product");
            Element idProduct = document.createElement("id");
            idProduct.setTextContent("" + i);
            product.appendChild(idProduct);
            Element idCate = document.createElement("idCategory");
            idCate.setTextContent("" + ((i / 200) + 1));
            product.appendChild(idCate);
            Element name = document.createElement("name");
            name.setTextContent("Nike A" + i);
            product.appendChild(name);
            Element image = document.createElement("image");
            image.setTextContent("http://www.imgbb/123");
            product.appendChild(image);
            Element price = document.createElement("price");
            price.setTextContent("10000");
            product.appendChild(price);
            Element dateCreate = document.createElement("dateCreate");
            dateCreate.setTextContent("22/04/2000");
            product.appendChild(dateCreate);
            Element description = document.createElement("description");
            description.setTextContent("khong co doi giay nay thi thoi");
            product.appendChild(description);
            products.appendChild(product);
        }

        //create the xml file 
        // transform  the DOM object to an XML file 
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath));

        // If you use
        // StreamResult result = new StreamResult(System.out);
        // the output will be pushed to the standard output ...
        // You can use that for debugging 
        transformer.transform(domSource, streamResult);
    }

    public static boolean ValidationXMLSchame(String xsdPath, String xmlPath) throws IOException, SAXException {
        SchemaFactory factory
                = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new File(xsdPath));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new File(xmlPath)));

        return true;
    }

    public static void writeFile(HttpServletRequest request, Part filePart, String path) throws IOException, ServletException {
        InputStream fileContent = filePart.getInputStream();
        FileOutputStream fos = new FileOutputStream(path, false);
        try {
            int read;
            byte[] bytes = new byte[InputBuffer.DEFAULT_BUFFER_SIZE];
            while ((read = fileContent.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (fileContent != null) {
                fileContent.close();
            }
        }
    }

    public static Workbook exportXmlToExcel(String pathSave, List<ProductDTO> products, List<CategoryDTO> categories) throws FileNotFoundException, IOException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Products");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("No");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("ID");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Category");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Image");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Price");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Date Create");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("Description");
        headerCell.setCellStyle(headerStyle);

        //body
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(false);
        int length = products.size();
        for (int i = 0; i < length; i++) {
            Row row = sheet.createRow(i + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue(i + 1);
            cell.setCellStyle(style);

            ProductDTO product = products.get(i);
            cell = row.createCell(1);
            cell.setCellValue(product.getId());
            cell.setCellStyle(style);

            String category = "";
            for (CategoryDTO category1 : categories) {
                if (category1.getId().equals(products.get(i).getIdCategory())) {
                    category = products.get(i).getName();
                }
            }
            cell = row.createCell(2);
            cell.setCellValue(category);
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(product.getName());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(product.getImage());
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue("$" + product.getPrice());
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(product.getDateCreate());
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue(product.getDescription());
            cell.setCellStyle(style);
        }

        return workbook;
    }
}
