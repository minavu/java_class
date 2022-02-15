package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;

/**
 * This class parses a xml file to create an airline with associated flights.
 * It checks against a known airline.dtd file before parsing.
 */
public class XmlParser implements AirlineParser<Airline> {
    AirlineXmlHelper helper = new AirlineXmlHelper();
    private final Reader reader;
    private String airlineName;

    public XmlParser(Reader reader) {
        this.reader = reader;
    }

    /**
     * This method creates a DOM tree from the parsed xml file. While creating the DOM tree,
     * the builder checks against the airline.dtd file to create a conforming tree.
     * @return The DOM tree document.
     * @throws IllegalArgumentException If anything goes wrong while reading and parsing the xml file.
     */
    public Document createDOMTree() throws IllegalArgumentException {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);
            document = builder.parse(new InputSource(reader));
        } catch (ParserConfigurationException | SAXException | IOException | IllegalArgumentException e) {
            throw new IllegalArgumentException("XmlParser says: Something went wrong when reading xml file. " + e.getMessage());
        }
        return document;
    }

    /**
     * This method parses the DOM tree to create an airline object with any flights associated.
     * @return An airline created using the DOM tree from the xml file.
     * @throws ParserException If anything goes wrong while parsing the DOM tree.
     * @throws IllegalArgumentException If anything goes wrong while creating the airline.
     */
    @Override
    public Airline parse() throws ParserException, IllegalArgumentException {
        Document doc = createDOMTree();
        airlineName = doc.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
        Airline airline = new Airline(airlineName);

        airline = addFlightsToAirline(doc, airline);

        return airline;
    }

    /**
     * This method parses the DOM tree to create an airline object with any flights associated.
     * It will check that the airline from the xml file is the same as the new flight.
     * @param matchAirlineName The airline of the new flight to match with that in the xml file.
     * @return An airline object.
     * @throws ParserException If anything goes wrong while parsing the DOM tree.
     * @throws IllegalArgumentException If anything goes wrong while creating the airline.
     */
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

    /**
     * This method adds all flights associated with the airline as noted in the xml file.
     * It walks the DOM tree to find the arguments to create a flight.
     * @param doc The DOM tree document.
     * @param airline An empty airline.
     * @return An airline with all its associated flights.
     */
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
