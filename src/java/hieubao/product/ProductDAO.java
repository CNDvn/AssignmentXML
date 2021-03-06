/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubao.product;

import hieubao.utils.DBHelper;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
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
public class ProductDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    private void closeDB() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public void addNew(String fileName, ProductDTO dto)
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

        //get <products>
        Node nodeProducts = doc.getElementsByTagName("products").item(0);

        Element product = doc.createElement("product");
        Element idProduct = doc.createElement("id");
        idProduct.setTextContent(dto.getId());
        product.appendChild(idProduct);
        Element idCate = doc.createElement("idCategory");
        idCate.setTextContent(dto.getIdCategory());
        product.appendChild(idCate);
        Element name = doc.createElement("name");
        name.setTextContent(dto.getName());
        product.appendChild(name);
        Element image = doc.createElement("image");
        image.setTextContent(dto.getImage());
        product.appendChild(image);
        Element price = doc.createElement("price");
        price.setTextContent("" + dto.getPrice());
        product.appendChild(price);
        Element dateCreate = doc.createElement("dateCreate");
        dateCreate.setTextContent(dto.getDateCreate());
        product.appendChild(dateCreate);
        Element description = doc.createElement("description");
        description.setTextContent(dto.getDescription());
        product.appendChild(description);
        nodeProducts.appendChild(product);

        //create the xml file 
        // transform  the DOM object to an XML file 
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File(fileName));
        transformer.transform(domSource, streamResult);
    }

    public List<ProductDTO> getProducts(String fileName, String category, String filter)
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

        //get <product>
        NodeList list = doc.getElementsByTagName("product");

        List<ProductDTO> products = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String idCategory = element.getElementsByTagName("idCategory").item(0).getTextContent();
                if (category == null) {
                    String name = element.getElementsByTagName("name").item(0).getTextContent();

                    String id = element.getElementsByTagName("id").item(0).getTextContent();
                    String image = element.getElementsByTagName("image").item(0).getTextContent();
                    double price = Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent());
                    String dateCreate = element.getElementsByTagName("dateCreate").item(0).getTextContent();
                    String description = element.getElementsByTagName("description").item(0).getTextContent();
                    products.add(new ProductDTO(id, idCategory, name, image, price, dateCreate, description));

                } else if (idCategory.equals(category)) {
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    if (filter == null) {
                        filter = "";
                    }

                    if (name.indexOf(filter) > 0) {
                        String id = element.getElementsByTagName("id").item(0).getTextContent();
                        String image = element.getElementsByTagName("image").item(0).getTextContent();
                        double price = Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent());
                        String dateCreate = element.getElementsByTagName("dateCreate").item(0).getTextContent();
                        String description = element.getElementsByTagName("description").item(0).getTextContent();
                        products.add(new ProductDTO(id, idCategory, name, image, price, dateCreate, description));
                    }
                    if (filter == "") {
                        String id = element.getElementsByTagName("id").item(0).getTextContent();
                        String image = element.getElementsByTagName("image").item(0).getTextContent();
                        double price = Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent());
                        String dateCreate = element.getElementsByTagName("dateCreate").item(0).getTextContent();
                        String description = element.getElementsByTagName("description").item(0).getTextContent();
                        products.add(new ProductDTO(id, idCategory, name, image, price, dateCreate, description));
                    }
                }

            }
        }
        return products;
    }

    public boolean updateProductById(String fileName, ProductDTO newProduct)
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

        //get <product>
        NodeList list = doc.getElementsByTagName("product");
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String oldId = element.getElementsByTagName("id").item(0).getTextContent();
                if (newProduct.getId().equals(oldId)) {
                    element.getElementsByTagName("name").item(0).setTextContent(newProduct.getName().trim());
                    element.getElementsByTagName("image").item(0).setTextContent(newProduct.getImage().trim());
                    element.getElementsByTagName("price").item(0).setTextContent("" + newProduct.getPrice());
                    element.getElementsByTagName("dateCreate").item(0).setTextContent(newProduct.getDateCreate().trim());
                    element.getElementsByTagName("description").item(0).setTextContent(newProduct.getDescription().trim());
                    element.getElementsByTagName("idCategory").item(0).setTextContent(newProduct.getIdCategory().trim());
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

    public ProductDTO deleteProductById(String fileName, String id)
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

        //get <product>
        NodeList list = doc.getElementsByTagName("product");

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String idProduct = element.getElementsByTagName("id").item(0).getTextContent();

                if (id.equals(idProduct)) {
                    String idCategory = element.getElementsByTagName("idCategory").item(0).getTextContent();
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String image = element.getElementsByTagName("image").item(0).getTextContent();
                    double price = Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent());
                    String dateCreate = element.getElementsByTagName("dateCreate").item(0).getTextContent();
                    String description = element.getElementsByTagName("description").item(0).getTextContent();

                    Node products = doc.getElementsByTagName("products").item(0);
                    products.removeChild(element);

                    //create the xml file 
                    // transform  the DOM object to an XML file 
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource domSource = new DOMSource(doc);
                    StreamResult streamResult = new StreamResult(new File(fileName));
                    transformer.transform(domSource, streamResult);

                    return new ProductDTO(idProduct, idCategory, name, image, price, dateCreate, description);

                }

            }
        }
        return null;
    }

//    public void importXMLFIleToDatabase(List<ProductDTO> products) throws NamingException, SQLException {
//        con = DBHelper.getConnect();
//        try {
//            if (con != null) {
//                String sql = "INSERT INTO products(id, idCategory, name, image, price, description) VALUES(?, ?, ?, ?, ?, ?)";
//                stm = con.prepareStatement(sql);
//                for (ProductDTO product : products) {
//                    stm.setString(1, product.getId());
//                    stm.setString(2, product.getIdCategory());
//                    stm.setString(3, product.getName());
//                    stm.setString(4, product.getImage());
//                    stm.setDouble(5, product.getPrice());
//                    stm.setString(6, product.getDescription());
//                    stm.executeUpdate();
//                }
//            }
//        } finally {
//            closeDB();
//        }
//    }
    public void importXMLFIleToDatabase(String fileName, List<ProductDTO> products) throws ParserConfigurationException,
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

        //get <products>
        Node nodeProducts = doc.getElementsByTagName("products").item(0);

        for (ProductDTO dto : products) {
            Element product = doc.createElement("product");
            Element idProduct = doc.createElement("id");
            idProduct.setTextContent(dto.getId());
            product.appendChild(idProduct);
            Element idCate = doc.createElement("idCategory");
            idCate.setTextContent(dto.getIdCategory());
            product.appendChild(idCate);
            Element name = doc.createElement("name");
            name.setTextContent(dto.getName());
            product.appendChild(name);
            Element image = doc.createElement("image");
            image.setTextContent(dto.getImage());
            product.appendChild(image);
            Element price = doc.createElement("price");
            price.setTextContent("" + dto.getPrice());
            product.appendChild(price);
            Element dateCreate = doc.createElement("dateCreate");
            dateCreate.setTextContent(dto.getDateCreate());
            product.appendChild(dateCreate);
            Element description = doc.createElement("description");
            description.setTextContent(dto.getDescription());
            product.appendChild(description);
            nodeProducts.appendChild(product);
        }

        //create the xml file 
        // transform  the DOM object to an XML file 
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File(fileName));
        transformer.transform(domSource, streamResult);
    }
}
