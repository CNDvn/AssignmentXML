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
import java.io.File;
import java.io.IOException;
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

        for (int i = 1; i <= 1000; i++) {
            // category
            Element category = document.createElement("category");
            Element idCategory = document.createElement("idCategory");
            idCategory.setTextContent("P0003");
            category.appendChild(idCategory);
            Element nameCategory = document.createElement("nameCategory");
            nameCategory.setTextContent("Guci Guci");
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
            idProduct.setTextContent("C0112");
            product.appendChild(idProduct);
            Element idCate = document.createElement("idCategory");
            idCate.setTextContent("P0003");
            product.appendChild(idCate);
            Element name = document.createElement("name");
            name.setTextContent("Nike A33");
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
}
