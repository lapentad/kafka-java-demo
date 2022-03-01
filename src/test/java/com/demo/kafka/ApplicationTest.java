package com.demo.kafka;

import org.junit.After;
import org.junit.Before;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ApplicationTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();


    @Before
    public void setUpStreams() throws UnsupportedEncodingException {
        System.setOut(new PrintStream(outContent,true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(errContent,true, StandardCharsets.UTF_8));
    }

    @After
    public void cleanUpStreams() {
        //System.setOut(outContent);
        //System.setErr(outContent);
    }

    @Test
    public void canRunApplicationForProducer() throws Exception {
        Application.main(new String[] {"producer","mycooltopic", "10"});
    }

    @Test
    public void canRunApplicationForConsumer() throws Exception {
        Application.main(new String[] {"consumer","mycooltopic", "10"});
    }

    @Test
    public void cannotRunApplicationForConsumer() throws Exception {
        Application.main(new String[] {});
        final String standardOutput = outContent.toString();
        System.out.println(standardOutput);
    }
}
