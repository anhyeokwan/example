package com.example.example.repository;

import com.example.example.BoardRepository;
import com.example.example.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("title.." + (i + 1))
                    .content("content.." + (i + 1))
                    .writer("writer.." + (i + 1))
                    .build();

            Board result = boardRepository.save(board);
            log.info("Bno : " + result.getBno());
        });
    }

    @Test
    public void testSelectOne(){
        Long bno = 101L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = null;
        try {
            board = result.orElseThrow();
        }catch (NoSuchElementException e){

        }
        if (board == null) {
            log.info("해당하는 정보가 없습니다.");
        }else{
            log.info(board);
        }

    }

    @Test
    public void updateTest() {
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();

        board.change("update..title 100", "update..content 100");

        boardRepository.save(board);
    }

    @Test
    public void deleteTest(){
        Long bno = 1L;

        Optional<Board> result = boardRepository.findById(bno);
        Board board = null;

        try {
            board = result.orElseThrow();
        } catch (NoSuchElementException e) {

        }

        if (board == null) {
            log.info("해당하는 정보가 없습니다.");
            return;
        }else{
            boardRepository.deleteById(bno);
            log.info("삭제를 완료하였습니다.");
        }
    }

    @Test
    public void testPaging() {
        // 1 page order by bno desc
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count : " + result.getTotalElements());
        log.info("total pages : " + result.getTotalPages());
        log.info("page number : " + result.getNumber());
        log.info("page size : " + result.getSize());

    }
}
