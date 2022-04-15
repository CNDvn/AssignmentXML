/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubao.category;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author CND
 */
public class CategoryDAO {

    public void addNew(String fileName, CategoryDTO category)
            throws ParserConfigurationException,
            SAXException,
            IOException,
            TransformerConfigurationException,
            TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // optional, but recommended
        // process XML securely, avoid attacks like XML External Entities (XXE)
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(new File(fileName));

        // optional, but recommended
        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        //get <category>
        Node nodeCategories = doc.getElementsByTagName("categories").item(0);

        Element categoryElement = doc.createElement("category");
        Element idElement = doc.createElement("idCategory");
        idElement.setTextContent(category.getId());
        categoryElement.appendChild(idElement);

        Element nameElement = doc.createElement("nameCategory");
        nameElement.setTextContent(category.getName());
        categoryElement.appendChild(nameElement);

        nodeCategories.appendChild(categoryElement);

        //create the xml file 
        // transform  the DOM object to an XML file 
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File(fileName));
        transformer.transform(domSource, streamResult);
    }

    public List<CategoryDTO> getAll(String fileName)
            throws ParserConfigurationException,
            SAXException,
            IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // optional, but recommended
        // process XML securely, avoid attacks like XML External Entities (XXE)
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(new File(fileName));

        // optional, but recommended
        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        //get <category>
        NodeList list = doc.getElementsByTagName("category");

        List<CategoryDTO> categories = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String id = element.getElementsByTagName("idCategory").item(0).getTextContent();
                String name = element.getElementsByTagName("nameCategory").item(0).getTextContent();

                CategoryDTO category = new CategoryDTO(id, name);
                categories.add(category);
            }
        }

        return categories;
    }

    public boolean updateById(String fileName, String id, String newName)
            throws ParserConfigurationException,
            SAXException,
            IOException,
            TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // optional, but recommended
        // process XML securely, avoid attacks like XML External Entities (XXE)
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(new File(fileName));

        // optional, but recommended
        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        //get <category>
        NodeList list = doc.getElementsByTagName("category");

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String oldId = element.getElementsByTagName("idCategory").item(0).getTextContent();
                if (id.equals(oldId)) {
                    element.getElementsByTagName("nameCategory").item(0).setTextContent(newName);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource domSource = new DOMSource(doc);
                    StreamResult streamResult = new StreamResult(new File(fileName));
                    transformer.transform(domSource, streamResult);
                    return true;
                }
            }
        }
        return false;
    }

    public CategoryDTO deleteCategoryById(String fileName, String id)
            throws ParserConfigurationException,
            SAXException,
            IOException,
            TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // optional, but recommended
        // process XML securely, avoid attacks like XML External Entities (XXE)
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(new File(fileName));

        // optional, but recommended
        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        //get <category>
        NodeList list = doc.getElementsByTagName("category");

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String idCategory = element.getElementsByTagName("idCategory").item(0).getTextContent();
                if (id.equals(idCategory)) {
                    Element nameCategory = (Element) element.getElementsByTagName("nameCategory").item(0);
                    Element categories = (Element) doc.getElementsByTagName("categories").item(0);
                    categories.removeChild(element);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource domSource = new DOMSource(doc);
                    StreamResult streamResult = new StreamResult(new File(fileName));
                    transformer.transform(domSource, streamResult);
                    return new CategoryDTO(idCategory, nameCategory.getTextContent());
                }
            }
        }

        return null;
    }
}
