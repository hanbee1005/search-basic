package com.example.searchbasic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SearchService {
    private final SearchKeywordRepository searchKeywordRepository;

    public SearchService(SearchKeywordRepository searchKeywordRepository) {
        this.searchKeywordRepository = searchKeywordRepository;
    }

    @Cacheable(value = "keywordCount", key = "#keyword")
    @Transactional(readOnly = true)
    public SearchKeywordDto getCount(String keyword) {
        SearchKeyword searchKeyword = searchKeywordRepository.findById(keyword)
                .orElseThrow(() -> new IllegalArgumentException("Not Found keyword=" + keyword));

        return new SearchKeywordDto(searchKeyword);
    }

    @Transactional
    public SearchKeywordDto save(String keyword) {
        SearchKeyword searchKeyword = searchKeywordRepository.findForUpdateByKeyword(keyword)
                .orElse(new SearchKeyword(keyword, 0L));

        searchKeyword.increaseSearchCnt();
        log.info("[SearchService.save] search count = {}", searchKeyword.getSearchCnt());

        return new SearchKeywordDto(searchKeywordRepository.save(searchKeyword));
    }
}
