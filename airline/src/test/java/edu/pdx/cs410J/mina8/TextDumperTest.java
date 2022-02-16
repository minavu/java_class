package edu.pdx.cs410J.mina8;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperTest {
  @Test
  void nonexistentFileNameWillBeCreatedAsNewFile() throws IOException {
    File newFile = new File((new Date()).toString() + ".txt");
    newFile.deleteOnExit();
    assertThat(newFile.exists(), equalTo(false));
    TextDumper dumper = new TextDumper(new FileWriter(newFile));
    assertThat(newFile.exists(), equalTo(true));
  }

  @Test
  void airlineWithMultipleFlightsIsWrittenToFileWithDelimiterAndNewline() throws IOException {
    InputStream inputStream = this.getClass().getResourceAsStream("fileWithValidArgs.txt");
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    String readAirline1 = reader.readLine();
    ArrayList<String> args1 = new ArrayList<>(Arrays.asList(readAirline1.split("\\|")));
    String readAirline2 = reader.readLine();
    ArrayList<String> args2 = new ArrayList<>(Arrays.asList(readAirline2.split("\\|")));
    Airline airline = new Airline(args1.get(0));
    Flight flight = new Flight(args1);
    airline.addFlight(flight);
    flight = new Flight(args2);
    airline.addFlight(flight);

    File writeFileName = new File("fileWithTestAirline.txt");
    writeFileName.deleteOnExit();
    TextDumper dumper = new TextDumper(new FileWriter(writeFileName));
    dumper.dump(airline);

    BufferedReader readWrittenFile = new BufferedReader(new FileReader(writeFileName));
    assertThat(readWrittenFile.readLine(), containsString(readAirline1));
    assertThat(readWrittenFile.readLine(), containsString(readAirline2));
  }
}
