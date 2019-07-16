package com.taskmanager;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;


public class TaskUtil {

	 private static final String CHARACTER = "a";

	    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	        return mapper.writeValueAsBytes(object);
	    }
	    
	    public static String ObjecttoJSON(Object object) throws IOException{
	    	ObjectMapper mapper = new ObjectMapper();
		    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		    ObjectWriter ow =  mapper.writer().withDefaultPrettyPrinter();
		    String requestJson=ow.writeValueAsString(object);
		    return requestJson;
	    }
	    

	    public static String createStringWithLength(int length) {
	        StringBuilder builder = new StringBuilder();

	        for (int index = 0; index < length; index++) {
	            builder.append(CHARACTER);
	        }

	        return builder.toString();
	    }
}
