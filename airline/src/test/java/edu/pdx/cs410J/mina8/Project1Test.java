package edu.pdx.cs410J.mina8;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can handle the calls)
 * to {@link System#exit(int)} and the like.
 */
class Project1Test {

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project1.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("This is a README file!"));
    }
  }

  @Test
  void validNumberOfArgs() {
    Project1.main(new String[]{"test", "test", "test", "test", "test", "test"});
    assertThat(Project1.argsCount, equalTo(6));
  }

  @Test
  void firstArgIsFlightNumber() {
    Project1.main(new String[]{"test", "test", "test", "test", "test", "test"});
  }
}
