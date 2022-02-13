package edu.pdx.cs410J.mina8;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.InputMismatchException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XmlParserTest {
    InputStream resource = getClass().getResourceAsStream("valid-airline.xml");
    InputStream resourceCreatedFromTextFile = getClass().getResourceAsStream("fileForXmlReading.xml");
    XmlParser parser = new XmlParser(resource);
    XmlParser parserForXmlFromTextFile = new XmlParser(resourceCreatedFromTextFile);

    @Test
    void canCreateXmlParserObject() {
        assertThat(new XmlParser(resource), instanceOf(XmlParser.class));
    }

    @Test
    void canCreateDOMTreeFromValidXmlFile() throws IOException {
        assertThat(parser.createDOMTree(), instanceOf(Document.class));
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
    void canParseXmlFileFromTextFileToCreateAirline() throws ParserException {
        Airline airline = parserForXmlFromTextFile.parse();
        assertThat(airline.getName(), equalTo("Abc"));
        assertThat(airline.getFlights().size(), equalTo(2));
    }

    @Test
    void parsingXmlFileWithAirlineNameDifferentThanNewFlightInfoWillThrowException() throws ParserException {
        assertThrows(InputMismatchException.class, () -> (new XmlParser(resource)).parse("notAnAirlineName"));
    }
}
