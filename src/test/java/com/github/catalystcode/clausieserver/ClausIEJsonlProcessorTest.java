package com.github.catalystcode.clausieserver;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

/**
 * @implNote All test examples are taken from Table 1 in the <a href="http://resources.mpi-inf.mpg.de/d5/clausie/clausie-www13.pdf>ClausIE paper</a>.
 */
public class ClausIEJsonlProcessorTest {
    private static ClausIEJsonlProcessor processor;

    @BeforeClass
    public static void beforeClass() {
        processor = new ClausIEJsonlProcessor();
    }

    private void runTest(String input, String expectedOutput) throws IOException {
        InputStream instream = new ByteArrayInputStream(input.getBytes(UTF_8));
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        processor.process(instream, outstream);
        assertEquals(expectedOutput, outstream.toString(UTF_8.name()));
    }

    @Test
    public void processWithSingleRelationPerSentence() throws IOException {
        runTest(
            "AE remained in Princeton.\n" +
                "AE is smart.\n" +
                "AE has won the Nobel Prize\n" +
                "RSAS gave AE the Nobel Prize.\n" +
                "The doorman showed AE to his office.\n" +
                "AE declared the meeting open.",

            "{\"argument\":\"in Princeton\",\"line\":0,\"subject\":\"AE\",\"relation\":\"remained\"}\n" +
                "{\"argument\":\"smart\",\"line\":1,\"subject\":\"AE\",\"relation\":\"is\"}\n" +
                "{\"argument\":\"the Nobel Prize\",\"line\":2,\"subject\":\"AE\",\"relation\":\"has won\"}\n" +
                "{\"argument\":\"AE the Nobel Prize\",\"line\":3,\"subject\":\"RSAS\",\"relation\":\"gave\"}\n" +
                "{\"argument\":\"AE to his office\",\"line\":4,\"subject\":\"The doorman\",\"relation\":\"showed\"}\n" +
                "{\"argument\":\"office\",\"line\":4,\"subject\":\"his\",\"relation\":\"has\"}\n" +
                "{\"argument\":\"the meeting open\",\"line\":5,\"subject\":\"AE\",\"relation\":\"declared\"}\n"
        );
    }

    @Test
    public void processWithMultipleRelationsPerSentence() throws IOException {
        runTest(
            "AE remained in Princeton until his death.\n" +
                "AE is a scientist of the 20th century.\n" +
                "AE has won the Nobel Prize in 1921.",

            "{\"argument\":\"in Princeton until his death\",\"line\":0,\"subject\":\"AE\",\"relation\":\"remained\"}\n" +
                "{\"argument\":\"in Princeton\",\"line\":0,\"subject\":\"AE\",\"relation\":\"remained\"}\n" +
                "{\"argument\":\"death\",\"line\":0,\"subject\":\"his\",\"relation\":\"has\"}\n" +
                "{\"argument\":\"a scientist of the 20th century\",\"line\":1,\"subject\":\"AE\",\"relation\":\"is\"}\n" +
                "{\"argument\":\"a scientist\",\"line\":1,\"subject\":\"AE\",\"relation\":\"is\"}\n" +
                "{\"argument\":\"the Nobel Prize in 1921\",\"line\":2,\"subject\":\"AE\",\"relation\":\"has won\"}\n"
        );
    }
}
