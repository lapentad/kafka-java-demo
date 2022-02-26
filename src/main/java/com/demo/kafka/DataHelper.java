package com.demo.kafka;

import java.util.Random;
import org.json.simple.JSONObject;

public class DataHelper {

    public static String getRandomString() {
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

    public static JSONObject getMessageLogEntryJSON(String topic, String key, String message){
        JSONObject obj = new JSONObject();

        obj.put("topic", topic);
        obj.put("key", key);
        obj.put("message", message);

        return obj;
    }


}
