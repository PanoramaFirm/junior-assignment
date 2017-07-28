package com.eniro.test.search;

import com.eniro.test.exception.UnknownResponseFormatException;
import com.eniro.test.model.company.Company;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Builder
class Search {

    private String term;

    static Search forTerm(String word) {
        return new Search(word);
    }

    List<Company> performOn(RestTemplate restTemplate, EniroApiConfiguration apiConfiguration) {
        JsonNode responseJson = restTemplate.getForObject(buildUriFor(term, apiConfiguration), JsonNode.class);
        try {
            return Arrays.asList(new ObjectMapper().treeToValue(responseJson.get("adverts"), Company[].class));
        } catch (JsonProcessingException e) {
            throw new UnknownResponseFormatException();
        }

    }

    URI buildUriFor(String term, EniroApiConfiguration apiConfiguration) {
        return UriComponentsBuilder.fromHttpUrl(apiConfiguration.getSearchUrl())
                .queryParam("profile", apiConfiguration.getProfile())
                .queryParam("key", apiConfiguration.getKey())
                .queryParam("country", apiConfiguration.getCountry())
                .queryParam("version", apiConfiguration.getVersion())
                .queryParam("search_word", term)
                .build().encode().toUri();
    }

}
