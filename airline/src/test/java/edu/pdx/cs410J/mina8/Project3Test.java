package edu.pdx.cs410J.mina8;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A unit test for code in the <code>Project3</code> class.  This is different
 * from <code>Project3IT</code> which is an integration test (and can handle the calls)
 * to {@link System#exit(int)} and the like.
 */
class Project3Test {
  ArrayList<String> argsListWithAllArgsSampler = new ArrayList<>(Arrays.asList("abc", "123", "pdx", "1/1/2022", "10:00", "am", "hnl", "1/1/2022", "11:00", "pm"));

  /**
   * Tests that the README.txt file is accessible as a resource.
   * @throws IOException -Thrown if the README.txt file cannot be read.
   */
  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project3.class.getResourceAsStream("README.txt")
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
  }

  /**
   * Tests that including too many options will throw an exception.
   */
  @Test
  void optionsExceedingCountWillThrowException() {
  }

  /**
   * Tests that including an invalid option will throw an exception.
   */
  @Test
  void invalidOptionWillThrowException() {
  }

  /**
   * Tests that an Airline can be created and as the name that is given.
   */
  @Test
  void airlineIsCreatedFromArgumentsList() {
  }

  /**
   * Tests that a Flight can be created with the given data.
   */
  @Test
  void flightIsCreatedFromArgumentsList() {
    Flight flight = new Flight(argsListWithAllArgsSampler);
    assertThat(flight.toString(), containsString(argsListWithAllArgsSampler.get(1)));
  }

  /**
   * Tests that an airline can be created from file or even if the file does not exist.
   */
  @Test
  void canCreateAirlineFromFileOrEmptyAirlineIfFileDoesNotExist() {
  }

  /**
   * Tests that an airline can be written to file or throw exception if the file does not exist.
   * @throws IOException If a file cannot be found.
   */
  @Test
  void canWriteAirlineToFileOrThrowExceptionOtherwise() throws IOException {
  }

}
