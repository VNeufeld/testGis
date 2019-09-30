package com.dev.gis.connector.sunny;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PaymentTypeJsonSerializer extends JsonSerializer<PaymentType> {

	@Override
	public void serialize(PaymentType value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		
		//System.out.println("PaymentTypeJsonSerializer value = "+value+ " result = "+Integer.toString(value.getOrdinal()));
		//jgen.writeStringField("paymentType","2");
		jgen.writeString(Integer.toString(value.getOrdinal()));
		
	}
	

}
