package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConverterTest {
    String textFile = "fileWithValidArgs.txt";
    String textFileInvalid = "fileWithInvalidArgs.txt";
    String xmlFile = "fileForXmlWriting.xml";
    InputStream resource = getClass().getResourceAsStream(textFile);
    InputStream resourceInvalid = getClass().getResourceAsStream(textFileInvalid);
    File xmlOutputTestFile = new File(xmlFile);

    @Test
    void canCreateConverterObject() throws IOException {
        xmlOutputTestFile.deleteOnExit();
        Converter converter = new Converter(new InputStreamReader(resource), new FileWriter(xmlOutputTestFile));
        assertThat(converter, instanceOf(Converter.class));
    }

    @Test
    void canCreateAirlineFromValidTextFile() throws IOException, ParserException {
        xmlOutputTestFile.deleteOnExit();
        Converter converter = new Converter(new InputStreamReader(resource), new FileWriter(xmlOutputTestFile));
        Airline airline = converter.parse();
        assertThat(airline, instanceOf(Airline.class));
    }

    @Test
    void throwsExceptionWhenGivenTextFileWithInvalidArgsForAirline() throws ParserException, IOException {
        xmlOutputTestFile.deleteOnExit();
        Converter converter = new Converter(new InputStreamReader(resourceInvalid), new FileWriter(xmlOutputTestFile));
        assertThrows(IllegalArgumentException.class, converter::parse);
    }

    @Test
    void canWriteAirlineToXmlFile() throws ParserException, IOException {
        xmlOutputTestFile.deleteOnExit();
        Converter converter = new Converter(new InputStreamReader(resource), new FileWriter(xmlOutputTestFile));
        Airline airline = converter.parse();
        converter.dump(airline);

        XmlParser parser = new XmlParser(new FileInputStream(xmlOutputTestFile));
        Airline airlineFromDumpedAndParsedXmlFile = parser.parse();
        assertThat(airline.getName(), equalTo(airlineFromDumpedAndParsedXmlFile.getName()));
        assertThat(airline.getFlights().size(), equalTo(airlineFromDumpedAndParsedXmlFile.getFlights().size()));
    }

    @Test
    void canUseOneConvertMethodToCallParseAndDumpActionsAndPerformSameAsTestAbove() throws IOException, ParserException {
        xmlOutputTestFile.deleteOnExit();
        Converter converter = new Converter(new InputStreamReader(resource), new FileWriter(xmlOutputTestFile));
        converter.convert();

        TextParser textParser = new TextParser(new InputStreamReader(getClass().getResourceAsStream(textFile)));
        Airline airline = textParser.parse();

        XmlParser parser = new XmlParser(new FileInputStream(xmlOutputTestFile));
        Airline airlineFromDumpedAndParsedXmlFile = parser.parse();
        assertThat(airline.getName(), equalTo(airlineFromDumpedAndParsedXmlFile.getName()));
        assertThat(airline.getFlights().size(), equalTo(airlineFromDumpedAndParsedXmlFile.getFlights().size()));
    }
}
