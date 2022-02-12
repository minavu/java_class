package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AirlineDumper;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.MalformedURLException;

public class XmlDumper implements AirlineDumper<Airline> {
    private final AirlineXmlHelper helper;
    private final Writer writer;

    public XmlDumper(Writer writer) throws IOException {
        helper = new AirlineXmlHelper();
        this.writer = writer;
    }

    @Override
    public void dump(Airline airline) throws IOException {

    }

    public Document createXmlDocument() {
        String systemID = null;
        Document doc = null;

        try {
            File dtd = new File("airline.dtd");
            systemID = dtd.toURL().toString();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Airline DTD cannot be parsed into URl.");
        }
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation dom = builder.getDOMImplementation();
            DocumentType docType = dom.createDocumentType("airline", null, systemID);
            doc = dom.createDocument(null, "airline", docType);
        } catch (ParserConfigurationException e) {

        }
        return doc;
    }
}
