package edu.pdx.cs410J.mina8;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class ConverterIT extends InvokeMainTestCase {
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Converter.class, args );
    }

    @Test
    void callingWithWrongNumberOfArgumentsWillThrowException() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        result = invokeMain("textFile");
        assertThat(result.getExitCode(), equalTo(1));
        result = invokeMain("textFile", "xmlFile", "extraFile");
        assertThat(result.getExitCode(), equalTo(1));
    }
}