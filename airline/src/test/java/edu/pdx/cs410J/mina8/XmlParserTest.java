package edu.pdx.cs410J.mina8;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class XmlParserTest {
    InputStream resource = getClass().getResourceAsStream("valid-airline.xml");
    XmlParser parser = new XmlParser(resource);

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
        assertThat(airline.getName(), equalTo("abc"));
    }
}
