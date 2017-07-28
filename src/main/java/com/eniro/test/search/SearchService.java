package com.eniro.test.search;

import com.eniro.test.filter.CompanyFilter;
import com.eniro.test.filter.RegexCompanyFilter;
import com.eniro.test.model.company.Company;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchService {

    private static final String SEARCH_WORD_DELIMITER = ",";

    private RestTemplate restTemplate = new RestTemplate();
    private EniroApiConfiguration eniroApiConfiguration;

    public SearchService(EniroApiConfiguration eniroApiConfiguration) {
        this.eniroApiConfiguration = eniroApiConfiguration;
    }

    public List<Company> parallelSearch(String joinedWords, String filterRegex) {

        CompanyFilter companyFilter = StringUtils.isEmpty(filterRegex) ?
                (Company c) -> true  //TODO explain
                : RegexCompanyFilter.of(filterRegex);

        return Arrays.stream(joinedWords.split(SEARCH_WORD_DELIMITER))
                .parallel()
                .map(Search::forTerm)
                .map(search -> search.performOn(restTemplate, eniroApiConfiguration))
                .flatMap(List::stream)
                .filter(companyFilter::matches)
                .collect(Collectors.toList());
    }
}
