package com.nbb.apps.carreservationv2.base;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class PaymentTypeJsonDesirializer extends JsonDeserializer<PaymentType>{

	@Override
	public PaymentType deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		//System.out.println("PaymentTypeJsonDesirializer value = "+parser.getText()+ " result = "+PaymentType.parseString(parser.getText()));
		
		
		return PaymentType.parseString(parser.getText());
	}

}
