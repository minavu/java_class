package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperTest {

  /**
   * Tests that a given file name that did not exist will be created.
   * @throws IOException -If the file is not a regular file, cannot be created, or cannot be opened.
   */
  @Test
  void nonexistentFileNameWillBeCreatedAsNewFile() throws IOException {
    Date newDate = new Date();
    String newFileName = "resources/tempFilesToDelete/" + newDate + ".txt";
    assertThat((new File(newFileName)).exists(), equalTo(false));
    TextDumper dumper = new TextDumper(newFileName);
    assertThat((new File(newFileName)).exists(), equalTo(true));
  }

  /**
   * Tests that an Airline with one flight can be written to file.
   * @throws IOException -If the file is not a regular file, cannot be created, or cannot be opened.
   */
  @Test
  void airlineWithOneFlightIsWrittenToFileWithDelimiter() throws IOException {
    String readFileName = "resources/fileWithValidArgs.txt";
    BufferedReader reader = new BufferedReader(new FileReader(readFileName));
    String readAirline = reader.readLine();
    ArrayList<String> args = new ArrayList<>(Arrays.asList(readAirline.split("\\|")));
    Airline airline = new Airline(args.get(0));
    Flight flight = new Flight(args);
    airline.addFlight(flight);

    String writeFileName = "resources/tempFilesToDelete/fileWithTestAirline.txt";
    TextDumper dumper = new TextDumper(writeFileName);
    dumper.dump(airline);

    BufferedReader readWrittenFile = new BufferedReader(new FileReader(writeFileName));
    assertThat(readWrittenFile.readLine(), containsString(readAirline));
  }

  /**
   * Tests that an Airline with many flights can all be written to file.
   * @throws IOException -If the file is not a regular file, cannot be created, or cannot be opened.
   */
  @Test
  void airlineWithMultipleFlightsIsWrittenToFileWithDelimiterAndNewline() throws IOException {
    String readFileName = "resources/fileWithValidArgs.txt";
    BufferedReader reader = new BufferedReader(new FileReader(readFileName));
    String readAirline1 = reader.readLine();
    ArrayList<String> args1 = new ArrayList<>(Arrays.asList(readAirline1.split("\\|")));
    String readAirline2 = reader.readLine();
    ArrayList<String> args2 = new ArrayList<>(Arrays.asList(readAirline2.split("\\|")));
    Airline airline = new Airline(args1.get(0));
    Flight flight = new Flight(args1);
    airline.addFlight(flight);
    flight = new Flight(args2);
    airline.addFlight(flight);

    String writeFileName = "resources/tempFilesToDelete/fileWithTestAirline.txt";
    TextDumper dumper = new TextDumper(writeFileName);
    dumper.dump(airline);

    BufferedReader readWrittenFile = new BufferedReader(new FileReader(writeFileName));
    assertThat(readWrittenFile.readLine(), containsString(readAirline1));
    assertThat(readWrittenFile.readLine(), containsString(readAirline2));
  }
}
