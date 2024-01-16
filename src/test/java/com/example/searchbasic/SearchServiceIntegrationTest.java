package com.example.searchbasic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CompletableFuture;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class SearchServiceIntegrationTest {
    @Autowired
    private SearchService searchService;

    @Autowired
    private SearchKeywordRepository searchKeywordRepository;

    private final String existingKeyword = "존재하는 검색어";

    private final Long existingSearchCnt = 22L;

    @BeforeEach
    void setUp() {
        searchKeywordRepository.deleteAll();
        searchKeywordRepository.save(new SearchKeyword(existingKeyword, existingSearchCnt));
    }

    @Test
    @DisplayName("존재하는 키워드가 검색되는 케이스")
    void exist_keyword() {
        //when
        SearchKeywordDto res = searchService.save(existingKeyword);

        //then
        Assertions.assertEquals(existingSearchCnt + 1, res.getSearchCnt());
    }

    @Test
    @DisplayName("존재하는 키워드가 동시에 검색되는 케이스")
    void exist_keyword_concurrently() {
        //when
        CompletableFuture<SearchKeyword> future = CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> searchService.save(existingKeyword)),
                CompletableFuture.runAsync(() -> searchService.save(existingKeyword))
        ).thenApply((a) -> searchKeywordRepository.findById(existingKeyword).orElseThrow());

        //then
        SearchKeyword searched = future.join();
        Assertions.assertEquals(existingSearchCnt + 2, searched.getSearchCnt());
    }
}
