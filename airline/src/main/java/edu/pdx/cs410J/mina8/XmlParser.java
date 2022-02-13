package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class XmlParser implements AirlineParser<Airline> {
    AirlineXmlHelper helper = new AirlineXmlHelper();
    private final InputStream inputStream;

    public XmlParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Airline parse() throws ParserException {
        Airline airline = null;
        Document doc = createDOMTree();

        Element air = doc.getDocumentElement();
        Node name = air.getFirstChild();
        airline = new Airline(name.getNodeValue());

        Node flight = name.getNextSibling();
        while (flight != null) {
            NodeList args = flight.getChildNodes();
            ArrayList<String> argsList = new ArrayList<>();
            for (int i = 0; i < args.getLength(); ++i) {
                if (args.item(i).getNodeName() == "depart" || args.item(i).getNodeName() == "arrive") {
                    Node date = args.item(i).getFirstChild();
                    NamedNodeMap dateAttr = date.getAttributes();
                    String departDate = dateAttr.item(1).getNodeValue() + "/" + dateAttr.item(0).getNodeValue() + "/" + dateAttr.item(2).getNodeValue();
                    argsList.add(departDate);

                    Node time = args.item(i).getLastChild();
                    NamedNodeMap timeAttr = time.getAttributes();
                    String departTime = timeAttr.item(0).getNodeValue() + ":" + timeAttr.item(1).getNodeValue();
                    SimpleDateFormat time24Hour = new SimpleDateFormat("H:mm");
                    try {
                        Date timeToChangeFormat = time24Hour.parse(departTime);
                        departTime = new SimpleDateFormat("K:mm a").format(timeToChangeFormat);
                    } catch (ParseException e) {

                    }
                    argsList.add(departTime);
                } else {
                    argsList.add(args.item(i).getNodeValue());
                }
            }
            Flight newFlight = new Flight(argsList);
            airline.addFlight(newFlight);
        }

        return airline;
    }

    public Document createDOMTree() {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(helper);
            doc = builder.parse(inputStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {

        }
        return doc;
    }
}
