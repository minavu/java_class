package edu.pdx.cs410J.mina8;

import org.junit.jupiter.api.Test;
import org.w3c.dom.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class XmlDumperTest {
    File testFile = new File("test.xml");
    ArrayList<String> argsListWithAllArgsSampler1 = new ArrayList<>(Arrays.asList("abc", "123", "pdx", "1/1/2022", "9:15", "am", "hnl", "1/1/2022", "11:30", "pm"));
    ArrayList<String> argsListWithAllArgsSampler2 = new ArrayList<>(Arrays.asList("abc", "123", "mia", "1/1/2022", "9:15", "am", "hnl", "1/1/2022", "11:30", "pm"));
    Airline airline = new Airline("abc");
    Flight flight1 = new Flight(argsListWithAllArgsSampler1);
    Flight flight2 = new Flight(argsListWithAllArgsSampler2);

    @Test
    void canCreateXmlDumperObject() throws IOException {
        testFile.deleteOnExit();
        assertThat(new XmlDumper(new FileWriter(testFile)), instanceOf(XmlDumper.class));
    }

    @Test
    void canCreateEmptyXmlDocument() throws IOException, ParserConfigurationException {
        testFile.deleteOnExit();
        XmlDumper dumper = new XmlDumper(new FileWriter(testFile));
        assertThat(dumper.createXmlDocument(), instanceOf(Document.class));
    }

    @Test
    void canDumpAirlineToXmlDocument() throws IOException {
        testFile.deleteOnExit();
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        XmlDumper dumper = new XmlDumper(new FileWriter(testFile));
        dumper.dump(airline);
    }
}
