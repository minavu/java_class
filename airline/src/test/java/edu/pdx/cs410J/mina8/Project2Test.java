package edu.pdx.cs410J.mina8;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A unit test for code in the <code>Project2</code> class.  This is different
 * from <code>Project2IT</code> which is an integration test (and can handle the calls)
 * to {@link System#exit(int)} and the like.
 */
class Project2Test {

  /**
   * Tests that the README.txt file is accessible as a resource.
   * @throws IOException -Thrown if the README.txt file cannot be read.
   */
  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project2.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("Developer:      Mina Vu"));
    }
  }

  /**
   * Tests that the options and arguments lists can be created.
   */
  @Test
  void optsAndArgsListsAreCreatedFromArguments() {
    String[] opts = {"-opt1", "-opt2", "-opt3"};
    String[] args = {"arg1", "arg2", "arg3", "arg4", "arg5", "arg6"};
    String[] both = {"-opt1", "-opt2", "-opt3", "arg1", "arg2", "arg3", "arg4", "arg5", "arg6"};
    ArrayList<String> optsList = new ArrayList<>();
    ArrayList<String> argsList = new ArrayList<>();
    Project2.createOptionsAndArgumentsListsFromCommandLineArguments(both, optsList, argsList);
    assertThat(optsList.size(), equalTo(opts.length));
    assertThat(argsList.size(), equalTo(args.length));
  }

  /**
   * Tests that including too many options will throw an exception.
   */
  @Test
  void optionsExceedingCountWillThrowException() {
    String[] opts = {"-print", "-print", "-print", "-print"};
    ArrayList<String> optsList = new ArrayList<>();
    ArrayList<String> argsList = new ArrayList<>();
    Project2.createOptionsAndArgumentsListsFromCommandLineArguments(opts, optsList, argsList);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Project2.checkOptionsListForCountReadmeAndInvalidOption(optsList));
    assertThat(exception.getMessage(), containsString("too many options"));
  }

  /**
   * Tests that including an invalid option will throw an exception.
   */
  @Test
  void invalidOptionWillThrowException() {
    ArrayList<String> optsList = new ArrayList<>(Arrays.asList("-print", "-badOption"));
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Project2.checkOptionsListForCountReadmeAndInvalidOption(optsList));
    assertThat(exception.getMessage(), containsString("not a valid option"));
  }

  /**
   * Tests that an Airline can be created and as the name that is given.
   */
  @Test
  void airlineIsCreatedFromArgumentsList() {
    ArrayList<String> argsList = new ArrayList<>(Arrays.asList("abc", "123", "pdx", "1/1/2022", "10:00", "nyc", "1/1/2022", "11:00"));
    Airline airline = Project2.createAirlineFromArgumentsList(argsList);
    assertThat(airline.getName().toLowerCase(), equalTo(argsList.get(0)));
  }

  /**
   * Tests that a Flight can be created with the given data.
   */
  @Test
  void flightIsCreatedFromArgumentsList() {
    ArrayList<String> argsList = new ArrayList<>(Arrays.asList("abc", "123", "pdx", "1/1/2022", "10:00", "nyc", "1/1/2022", "11:00"));
    Flight flight = Project2.createFlightFromArgumentsList(argsList);
    assertThat(flight.toString(), containsString(argsList.get(1)));
  }

  /**
   * Tests that an airline can be created from file or even if the file does not exist.
   */
  @Test
  void canCreateAirlineFromFileOrEmptyAirlineIfFileDoesNotExist() {
    String goodFileName = "src/test/resources/edu/pdx/cs410J/mina8/resources/fileWithValidArgs.txt";
    String badFileName = "src/test/resources/edu/pdx/cs410J/mina8/resources/thisFileDoesNotExist";
    String airlineName = "abc";
    Airline airline = Project2.createAirlineFromTextFileOrNewAirlineIfFileDoesNotExist(goodFileName, airlineName);
    assertThat(airline.getName(), equalTo(airlineName));
    airline = Project2.createAirlineFromTextFileOrNewAirlineIfFileDoesNotExist(badFileName, airlineName);
    assertThat(airline.getName(), equalTo(airlineName));
  }

  /**
   * Tests that an airline can be written to file or throw exception if the file does not exist.
   * @throws IOException If a file cannot be found.
   */
  @Test
  void canWriteAirlineToFileOrThrowExceptionOtherwise() throws IOException {
    String goodFileName = "src/test/resources/edu/pdx/cs410J/mina8/resources/tempFilesToDelete/goodFileName.txt";
    String badFileName = "src/test/resources/edu/pdx/cs410J/mina8/resources/tempFilesToDelet/bad:FileName.txt/";
    Airline airline = new Airline("Tester Name");
    Project2.writeAirlineToTextFile(goodFileName, airline);
    BufferedReader reader = new BufferedReader(new FileReader(goodFileName));
    String read = reader.readLine();
    assertThat(read, containsString(airline.getName()));
    assertThrows(IllegalArgumentException.class, () -> Project2.writeAirlineToTextFile(badFileName, airline));
  }

}
