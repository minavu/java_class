package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PrettyPrinterTest {
  String fileWithValidArgsTxt = "src/test/resources/edu/pdx/cs410J/mina8/fileWithValidArgs.txt";
  String fileWithValidArgsForPrettyPrintingTxt = "src/test/resources/edu/pdx/cs410J/mina8/fileWithValidArgsForPrettyPrinting.txt";

  @Test
  void prettyPrintToStandardOut() throws IOException, ParserException {
    TextParser parser = new TextParser(fileWithValidArgsTxt);
    Airline airline = parser.parse("Abc");
    PrettyPrinter prettyPrinter = new PrettyPrinter();
    StringBuilder output = prettyPrinter.dumpAid(airline);
    assertThat(output.toString(), containsString("Printing all flight information"));
  }

  @Test
  void prettyPrintToFile() throws IOException, ParserException {
    TextParser parser = new TextParser(fileWithValidArgsTxt);
    Airline airline = parser.parse("Abc");
    PrettyPrinter prettyPrinter = new PrettyPrinter(fileWithValidArgsForPrettyPrintingTxt);
    StringBuilder output = prettyPrinter.dumpAid(airline);
    assertThat(output.toString(), containsString("Printing all flight information"));
  }
}
