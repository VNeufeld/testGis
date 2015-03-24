package com.nbb.apps.carreservationv2.base;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class PayTypeJsonDesirializer extends JsonDeserializer<PayType>{

	@Override
	public PayType deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		
		
		return PayType.parseString(parser.getText());
	}

}
