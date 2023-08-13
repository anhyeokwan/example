package com.example.example.repository;

import com.example.example.domain.Board;
import com.example.example.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

    @Query("select b from Board b where b.title like concat('%',:keyword,'%') ")
    Page<Board> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
