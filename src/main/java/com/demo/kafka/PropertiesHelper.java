package com.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
public class PropertiesHelper {
    public static Properties getProperties() throws Exception {

        Properties props = null;
        try (InputStream input = SimpleProducer.class.getClassLoader().getResourceAsStream("config.properties")) {

            props = new Properties();

            if (input == null) {
                throw new Exception("Sorry, unable to find config.properties");
            }

            //load a properties file from class path, inside static method
            props.load(input);

            //get the property value and print it out
            System.out.println(props.getProperty("default.topic"));
            System.out.println(props.getProperty("boostrap.servers"));
            System.out.println(props.getProperty("key.serializer"));
            System.out.println(props.getProperty("value.serializer"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return props;
    }

}
