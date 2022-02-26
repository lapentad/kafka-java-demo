package com.demo.kafka;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Random;
import org.json.simple.JSONObject;

public class SimpleProducerTest {

    public String getRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Test
    public void canCreateProducerAndPostToTopicTest() throws Exception {
        SimpleProducer producer = new SimpleProducer("test-topic");
        String message = DataHelper.getRandomString();
        String key = String.valueOf(2);
        producer.send(key,message);
    }

    @Test
    public void canProduceToStreamTest() throws Exception {
        SimpleProducer producer = new SimpleProducer("test-topic");
        producer.run(10);
    }

    @Test
    public void canUseJSONObject(){
        JSONObject obj = new JSONObject();

        obj.put("topic", "mytopc");
        obj.put("key", "myKey");
        obj.put("message", "myMessage");

        String str = obj.toString();
        Assert.assertEquals(str.getClass(),String.class);
    }



}
