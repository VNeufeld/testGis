package com.dev.gis.connector.sunny;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;



public class AvailabilityJsonDesirializer extends JsonDeserializer<Availability>{

	@Override
	public Availability deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		
		return Availability.parseString(parser.getText());
	}

}
