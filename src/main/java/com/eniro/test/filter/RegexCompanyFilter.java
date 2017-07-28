package com.eniro.test.filter;

import com.eniro.test.model.company.Company;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCompanyFilter implements CompanyFilter {

    private final Pattern compiledPattern;

    private RegexCompanyFilter(Pattern compiledPattern) {
        this.compiledPattern = compiledPattern;
    }

    @Override
    public boolean matches(final Company company) {
        return valuesAsStringList(company).stream()
                .filter(s -> !StringUtils.isEmpty(s))
                .map(compiledPattern::matcher)
                .anyMatch(Matcher::find);
    }

    private List<String> valuesAsStringList(Company company) {
        return leavesAsStrings(new ObjectMapper().valueToTree(company));

    }

    private List<String> leavesAsStrings(JsonNode node) {
        final List<String> strings = new ArrayList<>();
        node.elements().forEachRemaining(element -> {
            if (element.isTextual()) {
                strings.add(element.asText());
            } else if (element.isContainerNode()) {
                strings.addAll(leavesAsStrings(element));
            }
        });
        return strings;
    }

    public static RegexCompanyFilter of(final String filterRegex) {
        return new RegexCompanyFilter(Pattern.compile(filterRegex));
    }
}
