package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;

import java.io.*;

/**
 * This is the converter class to turn a text file with airline data into an xml file
 * with airline data using the airline.dtd file.
 */
public class Converter {
    /**
     * This is the main method for this class to allow usage from command line.
     * @param args The arguments provided by users from the command line.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("This class requires 2 arguments." + USAGE_GUIDE);
            System.exit(1);
        }
        try {
            FileReader reader = new FileReader(args[0]);
            FileWriter writer = new FileWriter(args[1]);
            Converter converter = new Converter(reader, writer);
            converter.convert();
        } catch (FileNotFoundException e) {
            printErrorMessageAndExitSystem("The text file cannot be found." + USAGE_GUIDE);
        } catch (IOException e) {
            printErrorMessageAndExitSystem("The xml file cannot be opened." + USAGE_GUIDE);
        } catch (ParserException e) {
            printErrorMessageAndExitSystem("The text file cannot be parsed." + USAGE_GUIDE);
        }
        System.out.println("Converter says: Conversion was successful.");
        System.exit(0);
    }

    private static final String USAGE_GUIDE = " See below for usage guide.\n" +
            "usage: java -cp target/airline-2022.0.0.jar edu.pdx.cs410J.mina8.Converter textFile xmlFile";
    private final Reader reader;
    private final Writer writer;

    /**
     * This is the constructor that takes a reader and a writer.
     * @param reader The reader containing airline data
     * @param writer The writer to dump the airline data.
     * @throws IOException If a file cannot be found.
     */
    public Converter(Reader reader, Writer writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
    }

    /**
     * This method parses the data in the text file to create an airline object.
     * @return An airline object with associated flights.
     * @throws ParserException If the text file cannot be parsed.
     */
    public Airline parse() throws ParserException {
        TextParser parser = new TextParser(reader);
        return parser.parse();
    }

    /**
     * This method dumps the airline object into an xml file conforming to airline.dtd.
     * @param airline An airline object with associated flights.
     * @throws IOException If the xml file cannot be found.
     */
    public void dump(Airline airline) throws IOException {
        XmlDumper dumper = new XmlDumper(writer);
        dumper.dump(airline);
    }

    /**
     * This is a convenient method for users to call to perform both parsing and dumping serially.
     * @throws ParserException If the text file cannot be parsed.
     * @throws IOException If the xml file cannot be found.
     */
    public void convert() throws ParserException, IOException {
        Airline airline = this.parse();
        this.dump(airline);
    }

    /**
     * This method prints a message to standard error and exits the system with an error code of 1.
     * @param message -A string containing the message to print to standard error.
     */
    private static void printErrorMessageAndExitSystem(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
