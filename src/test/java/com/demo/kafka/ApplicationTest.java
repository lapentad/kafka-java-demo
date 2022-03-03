package com.demo.kafka;

import org.junit.After;
import org.junit.Before;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;

public class ApplicationTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Test (enabled=false)
    public void canRunApplicationForProducer() throws Exception {
        Application.main(new String[] {"producer","mycooltopic", "10"});
    }

    @Test (enabled=false)
    public void canRunApplicationForConsumer() throws Exception {
        Application.main(new String[] {"consumer","mycooltopic", "10"});
    }

    @Test (enabled=false)
    public void cannotRunApplicationForConsumer() throws Exception {
        Application.main(new String[] {});
        final String standardOutput = outContent.toString();
        System.out.println(standardOutput);
    }
}
