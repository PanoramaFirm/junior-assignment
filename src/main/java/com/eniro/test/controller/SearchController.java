package com.eniro.test.controller;

import com.eniro.test.exception.UnknownResponseFormatException;
import com.eniro.test.model.company.Company;
import com.eniro.test.search.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

@Controller
public class SearchController {

    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(
            @RequestParam(value = "searchWord") String searchWord,
            @RequestParam(value = "filter", required = false) String filterRegex,
            Map<String, Object> model) {
        List<Company> companies = searchService.parallelSearch(searchWord, filterRegex);
        model.put("companies", companies);
        model.put("resultsCount", companies.size());
        return "searchResults";
    }


    @ExceptionHandler(PatternSyntaxException.class)
    public String invalidRegexHandler(Map<String, Object> model) {
        model.put("error", "Provided regex pattern is invalid");
        return "index";
    }

    @ExceptionHandler(UnknownResponseFormatException.class)
    public String invalidJsonResponse(Map<String, Object> model) {
        model.put("error", "It seems that the api has changed");
        return "index";
    }

}
