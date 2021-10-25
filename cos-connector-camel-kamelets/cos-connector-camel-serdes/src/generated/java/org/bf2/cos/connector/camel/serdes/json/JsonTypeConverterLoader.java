/* Generated by camel build tools - do NOT edit this file! */
package org.bf2.cos.connector.camel.serdes.json;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.DeferredContextBinding;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConversionException;
import org.apache.camel.TypeConverterLoaderException;
import org.apache.camel.spi.TypeConverterLoader;
import org.apache.camel.spi.TypeConverterRegistry;
import org.apache.camel.support.SimpleTypeConverter;
import org.apache.camel.support.TypeConverterSupport;
import org.apache.camel.util.DoubleMap;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@SuppressWarnings("unchecked")
@DeferredContextBinding
public final class JsonTypeConverterLoader implements TypeConverterLoader, CamelContextAware {

    private CamelContext camelContext;

    public JsonTypeConverterLoader() {
    }

    @Override
    public void setCamelContext(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    public CamelContext getCamelContext() {
        return camelContext;
    }

    @Override
    public void load(TypeConverterRegistry registry) throws TypeConverterLoaderException {
        registerConverters(registry);
    }

    private void registerConverters(TypeConverterRegistry registry) {
        addTypeConverter(registry, com.fasterxml.jackson.databind.JsonNode.class, com.fasterxml.jackson.dataformat.avro.AvroSchema.class, false,
            (type, exchange, value) -> getJsonTypeConverter().asJsonSchema((com.fasterxml.jackson.dataformat.avro.AvroSchema) value));
        addTypeConverter(registry, com.fasterxml.jackson.databind.JsonNode.class, org.apache.avro.Schema.class, false,
            (type, exchange, value) -> getJsonTypeConverter().asJsonSchema((org.apache.avro.Schema) value));
        addTypeConverter(registry, org.bf2.cos.connector.camel.serdes.json.JsonFormatSchema.class, com.fasterxml.jackson.databind.JsonNode.class, false,
            (type, exchange, value) -> getJsonTypeConverter().asJsonFormatSchema((com.fasterxml.jackson.databind.JsonNode) value));
        addTypeConverter(registry, org.bf2.cos.connector.camel.serdes.json.JsonFormatSchema.class, java.lang.String.class, false,
            (type, exchange, value) -> getJsonTypeConverter().asJsonFormatSchema((java.lang.String) value));
    }

    private static void addTypeConverter(TypeConverterRegistry registry, Class<?> toType, Class<?> fromType, boolean allowNull, SimpleTypeConverter.ConversionMethod method) { 
        registry.addTypeConverter(toType, fromType, new SimpleTypeConverter(allowNull, method));
    }

    private volatile org.bf2.cos.connector.camel.serdes.json.JsonTypeConverter jsonTypeConverter;
    private org.bf2.cos.connector.camel.serdes.json.JsonTypeConverter getJsonTypeConverter() {
        if (jsonTypeConverter == null) {
            jsonTypeConverter = new org.bf2.cos.connector.camel.serdes.json.JsonTypeConverter();
            CamelContextAware.trySetCamelContext(jsonTypeConverter, camelContext);
        }
        return jsonTypeConverter;
    }
}
