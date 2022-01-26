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
   * @throws IOException  Thrown if the README.txt file cannot be read.
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

  @Test
  void optsAndArgsListsAreCreatedFromArguments() throws IOException {
    String[] opts = {"-opt1", "-opt2", "-opt3"};
    String[] args = {"arg1", "arg2", "arg3", "arg4", "arg5", "arg6"};
    String[] both = {"-opt1", "-opt2", "-opt3", "arg1", "arg2", "arg3", "arg4", "arg5", "arg6"};
    ArrayList<String> optsList = new ArrayList<>();
    ArrayList<String> argsList = new ArrayList<>();
    Project2.createOptionsAndArgumentsListsFromCommandLineArguments(both, optsList, argsList);
    assertThat(optsList.size(), equalTo(opts.length));
    assertThat(argsList.size(), equalTo(args.length));
  }

  @Test
  void optionsExceedingCountWillThrowException() throws IOException {
    String[] opts = {"-readme", "-print", "-textFile", "-badOption"};
    ArrayList<String> optsList = new ArrayList<>();
    ArrayList<String> argsList = new ArrayList<>();
    Project2.createOptionsAndArgumentsListsFromCommandLineArguments(opts, optsList, argsList);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Project2.checkOptionsListForCountReadmeAndInvalidOption(optsList));
    assertThat(exception.getMessage(), containsString("too many options"));
  }

  @Test
  void invalidOptionWillThrowException() {
    ArrayList<String> optsList = new ArrayList<>(Arrays.asList("-print", "-badOption"));
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Project2.checkOptionsListForCountReadmeAndInvalidOption(optsList));
    assertThat(exception.getMessage(), containsString("not a valid option"));
  }

  @Test
  void airlineIsCreatedFromArgumentsList() {
    ArrayList<String> argsList = new ArrayList<>(Arrays.asList("abc", "123", "pdx", "1/1/2022", "10:00", "nyc", "1/1/2022", "11:00"));
    Airline airline = Project2.createAirlineFromArgumentsList(argsList);
    assertThat(airline.getName().toLowerCase(), equalTo(argsList.get(0)));
  }

  @Test
  void flightIsCreatedFromArgumentsList() {
    ArrayList<String> argsList = new ArrayList<>(Arrays.asList("abc", "123", "pdx", "1/1/2022", "10:00", "nyc", "1/1/2022", "11:00"));
    Flight flight = Project2.createFlightFromArgumentsList(argsList);
    assertThat(flight.toString(), containsString(argsList.get(1)));
  }

}
