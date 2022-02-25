package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

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
        HOST,
        PORT,
        SEARCH_AIRLINE,
        SEARCH_SRC,
        SEARCH_DEST;

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
                case "-host":
                    return HOST;
                case "-port":
                    return PORT;
                case "searchAirline":
                    return SEARCH_AIRLINE;
                case "searchSrc":
                    return SEARCH_SRC;
                case "searchDest":
                    return SEARCH_DEST;
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
    public String[] extractAllOptionsAndAssociatedParamsReturnLeftoverArgs(String[] args) {
        ArrayList<String> argsList = new ArrayList<>();
        for (int i = 0; i < args.length; ++i) {
            if (args[i].startsWith("-")) {
                switch (args[i]) {
                    case "-README":
                    case "-print":
                        options.put(OptionsEnum.toEnum(args[i]), "");
                        break;
                    case "-host":
                        try {
                            if (args[i + 1].startsWith("-")) {
                                throw new IllegalArgumentException("You must provide a hostname to the -host option (i.e. localhost).");
                            }
                            options.put(OptionsEnum.toEnum(args[i]), args[++i]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            throw new IllegalArgumentException("You must provide a hostname to the -host option (i.e. localhost).");
                        }
                        break;
                    case "-port":
                        try {
                            int port = Integer.parseInt(args[i + 1]);
                            options.put(OptionsEnum.toEnum(args[i++]), String.valueOf(port));
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            throw new IllegalArgumentException("You must provide a port number to the -port option (i.e. 8080).");
                        }
                        break;
                    case "-search":
                        try {
                            options.put(OptionsEnum.SEARCH_AIRLINE, args[++i]);
                            options.put(OptionsEnum.SEARCH_SRC, args[++i]);
                            options.put(OptionsEnum.SEARCH_DEST, args[++i]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            throw new IllegalArgumentException("You must provide an airline, src, and dest to the -search option.");
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("OptionsHandler says: Error: " + args[i] + " is not a valid option.");
                }
            } else {
                argsList.add(args[i]);
            }
        }
        String[] argsArray = argsList.toArray(new String[0]);
        return argsArray;
    }

    /**
     * This method executes the README option and exits the system.
     */
    public void handleOptionREADMEOtherwiseContinue() {
        if (options.containsKey(OptionsEnum.README)) {
            try {
                InputStream readme = Project5.class.getResourceAsStream("README.txt");
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

    public String handleOptionHost() {
        if (options.containsKey(OptionsEnum.HOST)) {
            if (!options.containsKey(OptionsEnum.PORT)) {
                throw new IllegalArgumentException("You must provide a host and port to connect to the server.");
            }
            return options.get(OptionsEnum.HOST);
        }
        return null;
    }

    public int handleOptionPort() {
        if (options.containsKey(OptionsEnum.PORT)) {
            if (!options.containsKey(OptionsEnum.HOST)) {
                throw new IllegalArgumentException("You must provide a host and port to connect to the server.");
            }
            return Integer.parseInt(options.get(OptionsEnum.PORT));
        }
        return -1;
    }

    public void handleOptionSearchOtherwiseContinue() throws ParserException, IOException, HttpRequestHelper.RestException {
        if (options.containsKey(OptionsEnum.SEARCH_AIRLINE)) {
            String host = options.get(OptionsEnum.HOST);
            int port = Integer.parseInt(options.get(OptionsEnum.PORT));
            AirlineRestClient client = new AirlineRestClient(host, port);

            String airline = options.get(OptionsEnum.SEARCH_AIRLINE);
            String src = options.get(OptionsEnum.SEARCH_SRC);
            String dest = options.get(OptionsEnum.SEARCH_DEST);
            String result = client.searchAirlineSrcDest(airline, src, dest);
            System.out.println(result);
            System.exit(0);
        }
    }

    /**
     * This method executes the print option to standard output.
     * @param newFlight The new flight to print.
     */
    public void handleOptionPrintOtherwiseContinue(String newFlight) {
        if (options.containsKey(OptionsEnum.PRINT)) {
            System.out.println(newFlight);
            System.exit(0);
        }
    }
}
