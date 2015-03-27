package com.dev.gis.connector.sunny;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class PaymentTypeJsonDesirializer extends JsonDeserializer<PaymentType>{

	@Override
	public PaymentType deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		//System.out.println("PaymentTypeJsonDesirializer value = "+parser.getText()+ " result = "+PaymentType.parseString(parser.getText()));
		
		
		return PaymentType.parseString(parser.getText());
	}

}
