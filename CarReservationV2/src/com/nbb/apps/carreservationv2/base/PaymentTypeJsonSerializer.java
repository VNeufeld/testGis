package com.nbb.apps.carreservationv2.base;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class PaymentTypeJsonSerializer extends JsonSerializer<PaymentType> {

	@Override
	public void serialize(PaymentType value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		
		//System.out.println("PaymentTypeJsonSerializer value = "+value+ " result = "+Integer.toString(value.getOrdinal()));
		//jgen.writeStringField("paymentType","2");
		jgen.writeString(Integer.toString(value.getOrdinal()));
		
	}
	

}
