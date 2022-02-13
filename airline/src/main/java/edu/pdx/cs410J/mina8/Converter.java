package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;

import java.io.*;

public class Converter {
    private final Reader reader;
    private final Writer writer;

    public Converter(Reader reader, Writer writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
    }

    public Airline parse() throws ParserException {
        TextParser parser = new TextParser(reader);
        return parser.parse();
    }

    public void dump(Airline airline) throws IOException {
        XmlDumper dumper = new XmlDumper(writer);
        dumper.dump(airline);
    }

    public void convert() throws ParserException, IOException {
        Airline airline = this.parse();
        this.dump(airline);
    }
}
