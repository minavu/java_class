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
import java.util.InputMismatchException;

public class XmlParser implements AirlineParser<Airline> {
    AirlineXmlHelper helper = new AirlineXmlHelper();
    private final InputStream inputStream;

    public XmlParser(InputStream inputStream) {
        this.inputStream = inputStream;
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

    @Override
    public Airline parse() throws ParserException, IllegalArgumentException {
        Airline airline;
        Document doc = createDOMTree();

        Node name = doc.getElementsByTagName("name").item(0).getFirstChild();
        airline = new Airline(name.getNodeValue());

        NodeList allFlights = doc.getElementsByTagName("flight");
        for (int i = 0; i < allFlights.getLength(); ++i) {
            ArrayList<String> argsList = new ArrayList<>();
            argsList.add(name.getNodeValue());
            NodeList flightChildrenNodes = allFlights.item(i).getChildNodes();
            for (int j = 0; j < flightChildrenNodes.getLength(); ++j) {
                Node flightArg = flightChildrenNodes.item(j);
                switch (flightArg.getNodeName()) {
                    case "number":
                    case "src":
                    case "dest":
                        argsList.add(flightArg.getFirstChild().getNodeValue());
                        break;
                    case "depart":
                    case "arrive":
                        NodeList dateTimeChildrenNodes = flightArg.getChildNodes();
                        String departDate = null;
                        String departTime = null;
                        for (int k = 0; k < dateTimeChildrenNodes.getLength(); ++k) {
                            Node date = dateTimeChildrenNodes.item(1);
                            NamedNodeMap dateAttr = date.getAttributes();
                            departDate = dateAttr.item(1).getNodeValue() + "/" + dateAttr.item(0).getNodeValue() + "/" + dateAttr.item(2).getNodeValue();

                            Node time = dateTimeChildrenNodes.item(3);
                            NamedNodeMap timeAttr = time.getAttributes();
                            departTime = timeAttr.item(0).getNodeValue() + ":" + timeAttr.item(1).getNodeValue();
                            SimpleDateFormat time24Hour = new SimpleDateFormat("H:mm");
                            try {
                                Date timeToChangeFormat = time24Hour.parse(departTime);
                                departTime = new SimpleDateFormat("K:mm a").format(timeToChangeFormat);
                            } catch (ParseException e) {

                            }
                        }
                        argsList.add(departDate);
                        argsList.add(departTime.split(" ")[0]);
                        argsList.add(departTime.split(" ")[1]);

                        break;
                    default:
                        break;
                }
            }
            Flight newFlight;
            try {
                newFlight = new Flight(argsList);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("XmlParser says: Error when creating new flight: " + e.getMessage());
            }
            airline.addFlight(newFlight);
        }
        return airline;
    }

    public Airline parse(String matchAirlineName) throws ParserException, IllegalArgumentException {
        Airline airline;
        Document doc = createDOMTree();

        Node name = doc.getElementsByTagName("name").item(0).getFirstChild();
        if (!name.getNodeValue().equals(matchAirlineName)) {
            throw new InputMismatchException("XmlParser says: Airline name in xml file does not match \"" + matchAirlineName + "\". It is " + name.getNodeValue() + ".");
        }
        airline = new Airline(name.getNodeValue());

        NodeList allFlights = doc.getElementsByTagName("flight");
        for (int i = 0; i < allFlights.getLength(); ++i) {
            ArrayList<String> argsList = new ArrayList<>();
            argsList.add(name.getNodeValue());
            NodeList flightChildrenNodes = allFlights.item(i).getChildNodes();
            for (int j = 0; j < flightChildrenNodes.getLength(); ++j) {
                Node flightArg = flightChildrenNodes.item(j);
                switch (flightArg.getNodeName()) {
                    case "number":
                    case "src":
                    case "dest":
                        argsList.add(flightArg.getFirstChild().getNodeValue());
                        break;
                    case "depart":
                    case "arrive":
                        NodeList dateTimeChildrenNodes = flightArg.getChildNodes();
                        String departDate = null;
                        String departTime = null;
                        for (int k = 0; k < dateTimeChildrenNodes.getLength(); ++k) {
                            Node date = dateTimeChildrenNodes.item(1);
                            NamedNodeMap dateAttr = date.getAttributes();
                            departDate = dateAttr.item(1).getNodeValue() + "/" + dateAttr.item(0).getNodeValue() + "/" + dateAttr.item(2).getNodeValue();

                            Node time = dateTimeChildrenNodes.item(3);
                            NamedNodeMap timeAttr = time.getAttributes();
                            departTime = timeAttr.item(0).getNodeValue() + ":" + timeAttr.item(1).getNodeValue();
                            SimpleDateFormat time24Hour = new SimpleDateFormat("H:mm");
                            try {
                                Date timeToChangeFormat = time24Hour.parse(departTime);
                                departTime = new SimpleDateFormat("K:mm a").format(timeToChangeFormat);
                            } catch (ParseException e) {

                            }
                        }
                        argsList.add(departDate);
                        argsList.add(departTime.split(" ")[0]);
                        argsList.add(departTime.split(" ")[1]);

                        break;
                    default:
                        break;
                }
            }
            Flight newFlight;
            try {
                newFlight = new Flight(argsList);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("XmlParser says: Error when creating new flight: " + e.getMessage());
            }
            airline.addFlight(newFlight);
        }
        return airline;
    }
}
