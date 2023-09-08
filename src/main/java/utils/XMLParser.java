package utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class XMLParser {
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document document;

    public XMLParser() {
        factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        document = null;
    }

    public XMLParser fromXMLString(String xmlString) {
        try {
            document = builder.parse(new ByteArrayInputStream(xmlString.getBytes()));
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public XMLParser fromXMLFile(String xmlFilePath) {
        try {
            document = builder.parse(xmlFilePath);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public String getStringValueByXPath(String xmlPath) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.compile(xmlPath).evaluate(
                    document, XPathConstants.NODESET);
            Node nNode = nodeList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                return eElement.getTextContent();
            }
        } catch (NullPointerException | XPathExpressionException e) {
            System.out.println("[ERROR] Element not found at XPath: " + xmlPath);
        }
        return "";
    }
}
