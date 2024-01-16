package com.example.searchbasic;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name ="jakarta.persistence.lock.timeout", value = "10000")})
    Optional<SearchKeyword> findForUpdateByKeyword(String keyword);
}
