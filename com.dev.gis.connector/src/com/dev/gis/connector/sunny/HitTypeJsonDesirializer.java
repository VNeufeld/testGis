package com.dev.gis.connector.sunny;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class HitTypeJsonDesirializer extends JsonDeserializer<HitType>{


	@Override
	public HitType deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		return HitType.parseString(parser.getText());
	}

}
