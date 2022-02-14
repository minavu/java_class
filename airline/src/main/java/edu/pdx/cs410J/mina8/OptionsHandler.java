package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * This class handles parsing the argument list and executing the options for Project4 class.
 */
public class OptionsHandler {
    private enum OptionsEnum {
        README,
        PRINT,
        TEXTFILE,
        PRETTY,
        XMLFILE;

        /**
         * This method takes a string and turns it into an enum constant.
         * @param string A string to transform into enum.
         * @return An enum constant.
         */
        public static OptionsEnum toEnum(String string) {
            switch (string) {
                case "-README":
                    return README;
                case "-print":
                    return PRINT;
                case "-textFile":
                    return TEXTFILE;
                case "-pretty":
                    return PRETTY;
                case "-xmlFile":
                    return XMLFILE;
                default:
                    throw new IllegalArgumentException("Error in options enum. " + string + " is not an option.");
            }
        }
    }

    EnumMap<OptionsEnum, String> options = new EnumMap<>(OptionsEnum.class);

    /**
     * This method extracts all options and the associated parameters and returns the rest of the arguments in an array list.
     * @param args The array of arguments from the command line.
     * @return The arguments for a new flight without any options.
     */
    public ArrayList<String> extractAllOptionsAndAssociatedParamsReturnLeftoverArgs(String[] args) {
        ArrayList<String> argsList = new ArrayList<>();
        for (int i = 0; i < args.length; ++i) {
            if (args[i].startsWith("-")) {
                switch (args[i]) {
                    case "-README":
                    case "-print":
                        options.put(OptionsEnum.toEnum(args[i]), "");
                        break;
                    case "-textFile":
                        try {
                            if (args[i + 1].startsWith("-")) {
                                throw new IllegalArgumentException("A file name was not provided to the -textFile option.");
                            }
                            validateTextFileName(args[i + 1]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            throw new IllegalArgumentException("A file name was not provided to the -textFile option.");
                        }
                        options.put(OptionsEnum.toEnum(args[i]), args[++i]);
                        break;
                    case "-pretty":
                        try {
                            if (args[i + 1].equals("-") || !args[i + 1].matches("-.+")) {
                                options.put(OptionsEnum.toEnum(args[i]), args[++i]);
                            } else {
                                throw new IllegalArgumentException("A file name was not provided to the -pretty option.");
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            throw new IllegalArgumentException("A file name was not provided to the -pretty option.");
                        }
                        break;
                    case "-xmlFile":
                        try {
                            if (args[i + 1].startsWith("-")) {
                                throw new IllegalArgumentException("A file name was not provided to the -xmlFile option.");
                            }
                            validateTextFileName(args[i + 1]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            throw new IllegalArgumentException("A file name was not provided to the -xmlFile option.");
                        }
                        options.put(OptionsEnum.toEnum(args[i]), args[++i]);
                        break;
                    default:
                        throw new IllegalArgumentException("OptionsHandler says: Error: " + args[i] + " is not a valid option.");
                }
            } else {
                argsList.add(args[i].toLowerCase());
            }
        }
        return argsList;
    }

    /**
     * This method executes the README option and exits the system.
     */
    public void handleOptionREADME() {
        if (options.containsKey(OptionsEnum.README)) {
            try {
                InputStream readme = Project4.class.getResourceAsStream("README.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
                String line = "";
                while (reader.ready()) {
                    line += reader.readLine() + "\n";
                }
                System.out.println(line);
                System.exit(0);
            } catch (NullPointerException e) {
                System.out.println("README.txt not found.");
                System.exit(1);
            } catch (IOException e) {
                System.out.println("README.txt could not be read.");
                System.exit(1);
            }
        }
    }

    /**
     * This method calls all options that should occur before creating a new flight.
     * It makes sure that options exclusive of each other will not appear together.
     * @param airlineName The name of the airline for the new flight.
     * @return An airline object using the airline name. Could be an empty airline or filled with flights.
     * @throws IllegalArgumentException If options exclusive of each other were present in the command line.
     */
    public Airline handleAllBeforeOptions(String airlineName) throws IllegalArgumentException {
        if (options.containsKey(OptionsEnum.TEXTFILE) && options.containsKey(OptionsEnum.XMLFILE)) {
            throw new IllegalArgumentException("Both -textFile and -xmlFile options cannot be selected at the same time.");
        }
        if (options.containsKey(OptionsEnum.TEXTFILE)) {
            return handleOptionTextFileParse(airlineName);
        }
        if (options.containsKey(OptionsEnum.XMLFILE)) {
            return handleOptionXmlFileParse(airlineName);
        }
        return new Airline(airlineName);
    }

    /**
     * This method calls all options that should occur before ending the program.
     * @param airline The airline object created during the life of the program.
     * @param newFlight The new flight created from the command line arguments.
     */
    public void handleAllAfterOptions(Airline airline, Flight newFlight) {
        if (options.containsKey(OptionsEnum.PRINT)) {
            handleOptionPrint(newFlight);
        }
        if (options.containsKey(OptionsEnum.TEXTFILE)) {
            handleOptionTextFileDump(airline);
        }
        if (options.containsKey(OptionsEnum.XMLFILE)) {
            handleOptionXmlFileDump(airline);
        }
        if (options.containsKey(OptionsEnum.PRETTY)) {
            handleOptionPretty(airline);
        }
    }

    /**
     * This method handles the xml file parsing option by first checking that the option was called.
     * If the option was not called, an empty airline will be created.
     * @param airlineName The airline name of the new flight to be created.
     * @return An airline created from an xml file or an empty airline.
     * @throws IllegalArgumentException If something goes wrong while creating an airline flights.
     */
    public Airline handleOptionXmlFileParse(String airlineName) throws IllegalArgumentException {
        if (options.containsKey(OptionsEnum.XMLFILE)) {
            String fileName = options.get(OptionsEnum.XMLFILE);
            try {
                File file = new File(fileName);
                if (!file.exists()) {
                    throw new IOException();
                }
                XmlParser parser = new XmlParser(new FileInputStream(file));
                return parser.parse(airlineName);
            } catch (IOException | ParserException e) {
                return new Airline(airlineName);
            }
        } else {
            return new Airline(airlineName);
        }
    }

    /**
     * This method handles the xml file dump option.
     * @param airline The airline object to dump to file.
     */
    public void handleOptionXmlFileDump(Airline airline) {
        if (options.containsKey(OptionsEnum.XMLFILE)) {
            String fileName = options.get(OptionsEnum.XMLFILE);
            try {
                XmlDumper dumper = new XmlDumper(new FileWriter(fileName));
                dumper.dump(airline);
            } catch (IOException e) {
                throw new IllegalArgumentException("Cannot write Airline to the named xml file.");
            }
        }
    }

    /**
     * This method executes the textFile option by parsing the file and checking with the airline name.
     * @param airlineName The airline name of the new flight.
     * @return An airline created from the file or an empty airline.
     * @throws IllegalArgumentException If both -textFile and -xmlFile options are present.
     */
    public Airline handleOptionTextFileParse(String airlineName) throws IllegalArgumentException {
        if (options.containsKey(OptionsEnum.TEXTFILE)) {
            String fileName = options.get(OptionsEnum.TEXTFILE);
            try {
                TextParser parser = new TextParser(fileName);
                return parser.parse(airlineName);
            } catch (FileNotFoundException | ParserException e) {
                return new Airline(airlineName);
            }
        } else {
            return new Airline(airlineName);
        }
    }

    /**
     * This method executes the textFile option by dumping the airline into the file.
     * @param airline The airline with flights to dump.
     */
    public void handleOptionTextFileDump(Airline airline) {
        if (options.containsKey(OptionsEnum.TEXTFILE)) {
            String fileName = options.get(OptionsEnum.TEXTFILE);
            try {
                TextDumper dumper = new TextDumper(fileName);
                dumper.dump(airline);
            } catch (IOException e) {
                throw new IllegalArgumentException("Cannot write Airline to the named text file.");
            }
        }
    }

    /**
     * This method executes the print option to standard output.
     * @param newFlight The new flight to print.
     */
    public void handleOptionPrint(Flight newFlight) {
        if (options.containsKey(OptionsEnum.PRINT)) {
            System.out.println(newFlight);
        }
    }

    /**
     * This method executes the pretty print option by first checking where to print.
     * @param airline The airline with flights to print.
     */
    public void handleOptionPretty(Airline airline) {
        if (options.containsKey(OptionsEnum.PRETTY)) {
            String fileName = options.get(OptionsEnum.PRETTY);
            if (fileName.equals("-")) {
                PrettyPrinter prettyPrinter = new PrettyPrinter();
                prettyPrinter.dump(airline);
            } else {
                try {
                    PrettyPrinter prettyPrinter = new PrettyPrinter(fileName);
                    prettyPrinter.dump(airline, fileName);
                } catch (IOException e) {
                    throw new IllegalArgumentException("Cannot pretty print Airline to the named text file.");
                }
            }
        }
    }

    /**
     * This method checks the file name parameter for the textFile option and ensures its validity.
     * @param filename -A string containing the file name.
     * @throws IllegalArgumentException -If the file name is not valid.
     */
    protected static void validateTextFileName(String filename) throws IllegalArgumentException {
        File file = new File(filename + ".temp");
        boolean created = false;
        try {
            created = file.createNewFile();
        } catch (IOException e) {
            throw new IllegalArgumentException("Error from OptionsHandler: The file name provided is not valid.");
        } finally {
            if (created) {
                file.delete();
            }
        }
    }
}
