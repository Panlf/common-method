package com.plf.bson;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.undercouch.bson4jackson.BsonFactory;

public class ReadBSON {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		InputStream is = new  FileInputStream("src/main/resources/person.bson");
        try {
            ObjectMapper mapper = new ObjectMapper(new BsonFactory());
           
            Map<?, ?> result = mapper.readValue(is, Map.class);
            System.out.println(result);
        } finally {
            is.close();
        }
	}
}
