package edu.pdx.cs410J.mina8;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

class Project4Test {
  ArrayList<String> argsListWithAllArgsSampler = new ArrayList<>(Arrays.asList("abc", "123", "pdx", "1/1/2022", "10:00", "am", "hnl", "1/1/2022", "11:00", "pm"));

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project4.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("Developer:      Mina Vu"));
    }
  }
  @Test
  void flightIsCreatedFromArgumentsList() {
    Flight flight = new Flight(argsListWithAllArgsSampler);
    assertThat(flight.toString(), containsString(argsListWithAllArgsSampler.get(1)));
  }
}
