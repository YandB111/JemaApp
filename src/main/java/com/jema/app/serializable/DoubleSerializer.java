package com.jema.app.serializable;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class DoubleSerializer extends JsonSerializer<Double> {

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    	 DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Format without trailing .00
         String formattedValue = decimalFormat.format(value);
         gen.writeString(formattedValue);
     }
}