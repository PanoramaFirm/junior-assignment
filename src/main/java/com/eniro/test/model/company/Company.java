package com.eniro.test.model.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {

    private int eniroId;
    @JsonProperty("companyInfo")
    private Info info;
    private Address address;
    @JsonProperty("location")
    private Coordinates coordinates;
    private List<PhoneNumber> phoneNumbers;
    @JsonProperty("homepage")
    private String homePageUrl;
    @JsonProperty("infoPageLink")
    private String infoPageUrl;

}
