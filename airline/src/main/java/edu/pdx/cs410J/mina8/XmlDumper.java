package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AirlineDumper;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Calendar;
import java.util.Date;

public class XmlDumper implements AirlineDumper<Airline> {
    private final Writer writer;
    String systemID = "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd";

    public XmlDumper(Writer writer) throws IOException {
        this.writer = writer;
    }

    public Document createXmlDocument() throws ParserConfigurationException {
        Document doc;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation dom = builder.getDOMImplementation();
        DocumentType docType = dom.createDocumentType("airline", null, systemID);
        doc = dom.createDocument(null, "airline", docType);
        return doc;
    }

    @Override
    public void dump(Airline airline) throws IOException {
        try {
            Document doc = createXmlDocument();
            Element air = doc.getDocumentElement();

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(airline.getName()));
            air.appendChild(name);

            airline.getFlights().forEach(flight -> {
                Element flightElement = doc.createElement("flight");
                Element number = doc.createElement("number");
                number.appendChild(doc.createTextNode(String.valueOf(flight.getNumber())));
                flightElement.appendChild(number);

                Element src = doc.createElement("src");
                src.appendChild(doc.createTextNode(flight.getSource()));
                flightElement.appendChild(src);

                generateDateTimeElement(doc, flight.getDeparture(), flightElement, "depart");

                Element dest = doc.createElement("dest");
                dest.appendChild(doc.createTextNode(flight.getDestination()));
                flightElement.appendChild(dest);

                generateDateTimeElement(doc, flight.getArrival(), flightElement, "arrive");

                air.appendChild(flightElement);
            });
                dumperToWriter(doc);


        } catch (DOMException | ParserConfigurationException | TransformerException e) {
            throw new IOException(e);
        }
    }

    private void generateDateTimeElement(Document doc, Date flightDate, Element flight, String elementName) {
        Element depart = doc.createElement(elementName);
        Element d_date = doc.createElement("date");
        Calendar d_calendar = Calendar.getInstance();
        d_calendar.setTime(flightDate);
        d_date.setAttribute("day", String.valueOf(d_calendar.get(Calendar.DAY_OF_MONTH)));
        d_date.setAttribute("month", String.valueOf((d_calendar.get(Calendar.MONTH))+1));
        d_date.setAttribute("year", String.valueOf(d_calendar.get(Calendar.YEAR)));
        Element d_time = doc.createElement("time");
        d_time.setAttribute("hour", String.valueOf(d_calendar.get(Calendar.HOUR_OF_DAY)));
        d_time.setAttribute("minute", String.valueOf(d_calendar.get(Calendar.MINUTE)));
        depart.appendChild(d_date);
        depart.appendChild(d_time);
        flight.appendChild(depart);
    }

    private void dumperToWriter(Node doc) throws TransformerException {
        Source src = new DOMSource(doc);
        Result res = new StreamResult(writer);
        TransformerFactory xFactory = TransformerFactory.newInstance();
        Transformer xForm = xFactory.newTransformer();
        xForm.setOutputProperty(OutputKeys.INDENT, "yes");
        xForm.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, systemID);
        xForm.transform(src, res);
    }

}
