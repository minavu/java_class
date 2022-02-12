package edu.pdx.cs410J.mina8;

import org.junit.jupiter.api.Test;
import org.w3c.dom.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XmlDumperTest {
    File testFile = new File("test.xml");
    String dtd = "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd";

    @Test
    void canCreateXmlDumperObject() throws IOException {
        testFile.deleteOnExit();
        assertThat(new XmlDumper(new FileWriter(testFile)), instanceOf(XmlDumper.class));
    }

    @Test
    void canCreateEmptyXmlDocument() throws IOException {
        testFile.deleteOnExit();
        XmlDumper dumper = new XmlDumper(new FileWriter(testFile));
        assertThat(dumper.createXmlDocument(), instanceOf(Document.class));
    }
}
