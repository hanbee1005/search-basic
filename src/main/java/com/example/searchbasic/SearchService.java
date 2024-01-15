package com.example.searchbasic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SearchService {
    private final SearchKeywordRepository searchKeywordRepository;

    public SearchService(SearchKeywordRepository searchKeywordRepository) {
        this.searchKeywordRepository = searchKeywordRepository;
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
