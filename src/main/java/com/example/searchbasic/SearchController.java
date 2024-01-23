package com.example.searchbasic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search/count")
    public SearchKeywordDto getCount(@RequestParam String keyword) {
        log.info("[SearchController.getCount] keyword = {}", keyword);
        return searchService.getCount(keyword);
    }

    @PostMapping("/search")
    public SearchKeywordDto search(String keyword) {
        log.info("[SearchController.search] keyword = {}", keyword);
        return searchService.save(keyword);
    }

}
