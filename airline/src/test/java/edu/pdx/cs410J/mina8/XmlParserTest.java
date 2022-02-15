package edu.pdx.cs410J.mina8;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.InputMismatchException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XmlParserTest {
    InputStream resourceValidAirlineXml = getClass().getResourceAsStream("valid-airline.xml");
    InputStream resourceInvalidAirlineXml = getClass().getResourceAsStream("invalid-airline.xml");
    InputStream resourceCreatedFromTextFile = getClass().getResourceAsStream("fileForXmlReading.xml");
    InputStream resourceXmlWithInvalidArgs = getClass().getResourceAsStream("fileForXmlReadingWithInvalidArgs.xml");
    XmlParser parser = new XmlParser(new InputStreamReader(resourceValidAirlineXml));
    XmlParser parserForXmlFromTextFile = new XmlParser(new InputStreamReader(resourceCreatedFromTextFile));

    @Test
    void canCreateXmlParserObject() throws IOException {
        assertThat(new XmlParser(new InputStreamReader(resourceValidAirlineXml)), instanceOf(XmlParser.class));
        resourceValidAirlineXml.close();
    }

    @Test
    void canCreateDOMTreeFromValidXmlFile() throws IOException {
        XmlParser parser = new XmlParser(new InputStreamReader(resourceValidAirlineXml));
        assertThat(parser.createDOMTree(), instanceOf(Document.class));
        resourceValidAirlineXml.close();
    }

    @Test
    void canParseXmlFileToCreateAirline() throws ParserException {
        Airline airline = parser.parse();
        assertThat(airline.getName(), equalTo("Valid Airlines"));
        assertThat(airline.getFlights().size(), equalTo(2));
        Collection<Flight> flights = airline.getFlights();
        assertThat(flights.iterator().next().toString(), equalTo("Flight 1437 departs BJX at 9/25/20 5:00 PM arrives CMN at 9/26/20 3:56 AM"));
    }

    @Test
    void parsingXmlFileNotConformingToDTDWillThrowException() throws ParserException {
        XmlParser parserOfBadXmlFile = new XmlParser(new InputStreamReader(resourceInvalidAirlineXml));
        assertThrows(IllegalArgumentException.class, parserOfBadXmlFile::parse);
    }

    @Test
    void canParseXmlFileFromTextFileToCreateAirline() throws ParserException {
        Airline airline = parserForXmlFromTextFile.parse();
        assertThat(airline.getName(), equalTo("Abc"));
        assertThat(airline.getFlights().size(), equalTo(11));
    }

    @Test
    void parsingXmlFileWithAirlineNameDifferentThanNewFlightInfoWillThrowException() throws ParserException {
        assertThrows(InputMismatchException.class, () -> (new XmlParser(new InputStreamReader(resourceValidAirlineXml))).parse("notAnAirlineName"));
    }

    @Test
    void parsingXmlFileWithAirlineNameSameAsNewFlightInfoWillReturnAirline() throws ParserException {
        Airline airline = (new XmlParser(new InputStreamReader(resourceValidAirlineXml))).parse("Valid Airlines");
        assertThat(airline.getName(), equalTo("Valid Airlines"));
    }

    @Test
    void creatingFlightFromXmlFileContainingInvalidArgsWillThrowException() throws ParserException {
        assertThrows(IllegalArgumentException.class, () -> (new XmlParser(new InputStreamReader(resourceXmlWithInvalidArgs))).parse("Abc"));
    }
}
