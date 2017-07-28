package com.eniro.test.deserializer;

import com.eniro.test.model.company.Coordinates;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class CoordinatesDeserializer extends StdDeserializer<Coordinates> {

    public CoordinatesDeserializer() {
        this(null);
    }

    public CoordinatesDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Coordinates deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = parser.getCodec().readTree(parser);
        Iterator<JsonNode> coordinates = node.get("coordinates").elements();
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(coordinates, Spliterator.ORDERED)
                , false)
                .filter(c -> c.has("use"))
                .findAny()
                .map(c -> {
                    Double latitude = c.get("latitude").asDouble();
                    Double longitude = c.get("longitude").asDouble();
                    return new Coordinates(latitude, longitude);
                }).orElseGet(Coordinates::empty);

    }
}
