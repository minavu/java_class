package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.InputMismatchException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 */
public class OptionsHandlerTest {
  String fileWithValidArgsTxt = "src/test/resources/edu/pdx/cs410J/mina8/fileWithValidArgs.txt";
  String[] argsSamplerWithAllOptionsAndJustAirlineName = {"-README", "-print", "-textFile", fileWithValidArgsTxt, "-pretty", "prettyParam", "abc"};
  String[] argsSamplerWithoutTextFileOption = {"-README", "-print", "-pretty", "prettyParam", "abc"};
  ArrayList<String> argsList = null;
  Airline airlineTester = null;
  OptionsHandler optionsHandlerTester = new OptionsHandler();
  /**
   *
   */
  @Test
  void optionsHandlerObjectCanBeCreated() {
    assertThat(new OptionsHandler(), instanceOf(OptionsHandler.class));
  }

  @Test
  void optionsHandlerCanExtractAllOptionsAndParamsFromArgsList() {
    argsList = optionsHandlerTester.extractAllOptionsAndAssociatedParamsReturnLeftoverArgs(argsSamplerWithAllOptionsAndJustAirlineName);
    assertThat(argsList.size(), equalTo(1));
  }

  @Test
  void emptyAirlineIsCreatedWhenTextFileOptionIsNotPresent() {
    String airlineNameTester = "No Airline Name";
    airlineTester = optionsHandlerTester.handleOptionTextFileParse(airlineNameTester);
    assertThat(airlineTester.getName(), equalTo(airlineNameTester));
    assertThat(airlineTester.getFlights().size(), equalTo(0));
  }

  @Test
  void optionsHandlerCanCreateFullAirlineWithFlightsWhenTextFileOptionAndParamIsPresentAndValid() {
    argsList = optionsHandlerTester.extractAllOptionsAndAssociatedParamsReturnLeftoverArgs(argsSamplerWithAllOptionsAndJustAirlineName);
    airlineTester = optionsHandlerTester.handleOptionTextFileParse("Abc");
    assertThat(airlineTester.getName(), equalTo("Abc"));
  }

  @Test
  void exceptionThrownWhenAirlineNameInFileDoesNotMatchNewFlightInfo() {
    argsList = optionsHandlerTester.extractAllOptionsAndAssociatedParamsReturnLeftoverArgs(argsSamplerWithAllOptionsAndJustAirlineName);
    assertThrows(InputMismatchException.class, () -> optionsHandlerTester.handleOptionTextFileParse("Wrong Name"));
  }

  @Test
  void optionsHandlerCanWriteToTextFile() throws FileNotFoundException, ParserException {
    argsList = optionsHandlerTester.extractAllOptionsAndAssociatedParamsReturnLeftoverArgs(argsSamplerWithAllOptionsAndJustAirlineName);
    airlineTester = optionsHandlerTester.handleOptionTextFileParse("Abc");
    optionsHandlerTester.handleOptionTextFileDump(airlineTester);
    TextParser parser = new TextParser(fileWithValidArgsTxt);
    Airline newAirlineTester = parser.parse("Abc");
    assertThat(airlineTester.getName(), equalTo(newAirlineTester.getName()));
  }
}
