package com.eniro.test.model.company;

import com.eniro.test.deserializer.CoordinatesDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = CoordinatesDeserializer.class)
public class Coordinates {
    private Double latitude;
    private Double longitude;

    public static Coordinates empty() {
        return new Coordinates(null, null);
    }
}
