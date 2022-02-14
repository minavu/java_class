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

/**
 * This class parses an xml file to create an airline with associated flights.
 * It checks against a known airline.dtd before parsing.
 */
public class XmlParser implements AirlineParser<Airline> {
    AirlineXmlHelper helper = new AirlineXmlHelper();
    private final InputStream inputStream;
    private String airlineName;

    public XmlParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Document createDOMTree() {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);
            document = builder.parse(inputStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalArgumentException("XmlParser says: Something went wrong when reading xml file.");
        }
        return document;
    }

    @Override
    public Airline parse() throws ParserException, IllegalArgumentException {
        Document doc = createDOMTree();
        airlineName = doc.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
        Airline airline = new Airline(airlineName);

        airline = addFlightsToAirline(doc, airline);

        return airline;
    }

    public Airline parse(String matchAirlineName) throws ParserException, IllegalArgumentException {
        Document doc = createDOMTree();
        airlineName = doc.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
        if (!airlineName.equals(matchAirlineName)) {
            throw new InputMismatchException("XmlParser says: Airline name in xml file does not match \"" + matchAirlineName + "\". It is " + airlineName + ".");
        }
        Airline airline = new Airline(airlineName);

        airline = addFlightsToAirline(doc, airline);

        return airline;
    }

    public Airline addFlightsToAirline(Document doc, Airline airline) {
        NodeList allFlights = doc.getElementsByTagName("flight");
        for (int i = 0; i < allFlights.getLength(); ++i) {
            ArrayList<String> argsList = new ArrayList<>();
            argsList.add(airlineName);
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
