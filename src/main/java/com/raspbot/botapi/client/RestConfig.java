package com.raspbot.botapi.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

import java.io.IOException;

public class RestConfig {

    public static void init() {
        Unirest.setObjectMapper(new ObjectMapper() {

            private com.fasterxml.jackson.databind.ObjectMapper mapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            {
                mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
            }

            public <T> T readValue(String value, Class<T> valueType) {

                try {
                    return mapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object o) {
                try {
                    return mapper.writeValueAsString(o);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
