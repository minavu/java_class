package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    /**
     * This main method grabs the command line arguments and parses between options and new flight
     * arguments.  Options are handled and new flight data will be sent to the client to send to
     * the servlet.
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            printErrorMessageAndUsageGuideAndExitSystem(MISSING_ARGS);
        }
        try {
            OptionsHandler optionsHandler = new OptionsHandler();
            String[] argsList = optionsHandler.extractAllOptionsAndAssociatedParamsReturnLeftoverArgs(args);
            optionsHandler.handleOptionREADMEOtherwiseContinue();
            String host = optionsHandler.handleOptionHost();
            int port = optionsHandler.handleOptionPort();
            optionsHandler.handleOptionSearchOtherwiseContinue();

            AirlineRestClient client = new AirlineRestClient(host, port);
            if (argsList.length == 1) {
                String result = client.queryAirline(argsList[0]);
                System.out.println(result);
            } else {
                String result = client.addNewFlight(argsList);
                optionsHandler.handleOptionPrintOtherwiseContinue(result);
            }
        } catch (IllegalArgumentException | ParserException e) {
            printErrorMessageAndUsageGuideAndExitSystem(e.getMessage());
        } catch (IOException e) {
            printErrorMessageAndUsageGuideAndExitSystem("Error occurred when connecting to server: " + e.getMessage());
        } catch (HttpRequestHelper.RestException e) {
            printErrorMessageAndUsageGuideAndExitSystem("RestException occurred: " + e.getMessage());
        }

        System.exit(0);
    }

    /**
     * This method prints a message to standard error and exits the system with an error code of 1.
     * @param message A string containing the message to print to standard error.
     */
    private static void printErrorMessageAndUsageGuideAndExitSystem(String message) {
        String USAGE_GUIDE =
                " See below for usage guide. Project 5.\n" +
                        "usage: java -jar target/airline-client.jar [options] <args>\n" +
                        "\targs are (in this order):\n" +
                        "\t\tairline\t\t\tThe name of the airline\n" +
                        "\t\tflightNumber\tThe flight number\n" +
                        "\t\tsrc\t\t\t\tThree-letter code of departure airport\n" +
                        "\t\tdepart\t\t\tDeparture date and time (24-hour time)\n" +
                        "\t\tdest\t\t\tThree-letter code of arrival airport\n" +
                        "\t\tarrive\t\t\tArrival date and time (24-hour time)\n" +
                        "\toptions are (options may appear in any order):\n" +
                        "\t\t-host hostname\tHost computer on which the server runs\n" +
                        "\t\t-port port\t\tPort on which the server is listening\n" +
                        "\t\t-search\t\t\tSearch for flights\n" +
                        "\t\t-print\t\t\tPrints a description of the new flight\n" +
                        "\t\t-README\t\t\tPrints a README for this project and exits\n" +
                        "\tDate and time should be in the format: mm/dd/yyyy hh:mm am/pm";
        System.err.println(message + USAGE_GUIDE);
        System.exit(1);
    }
}