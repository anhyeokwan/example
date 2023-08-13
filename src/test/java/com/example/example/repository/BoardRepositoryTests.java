package com.example.example.repository;

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
    public void testSelectOne() {
        Long bno = 101L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = null;
        try {
            board = result.orElseThrow();
        } catch (NoSuchElementException e) {

        }
        if (board == null) {
            log.info("해당하는 정보가 없습니다.");
        } else {
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
    public void deleteTest() {
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
        } else {
            boardRepository.deleteById(bno);
            log.info("삭제를 완료하였습니다.");
        }
    }

    @Test
    public void testPaging() {
        // 1 page order by bno desc
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count : " + result.getTotalElements()); // 전체 결과 데이터의 총 개수
        log.info("total pages : " + result.getTotalPages());  // 필요한 페이지의 총 개수
        log.info("page number : " + result.getNumber());  // 현재 페이지의 인덱스(0부터 시작)를 반환
        log.info("page size : " + result.getSize());  // 한 페이지에 보여지는 항목의 개수

    }

    @Test
    public void searchTest() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findByKeyword("..3", pageable);

        log.info("result Size : " + result.getSize());

    }

    @Test
    public void searchTest1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        boardRepository.search1(pageable);
//        Page<Board> boardPage = boardRepository.search1(pageable);
//
//        if(boardPage.getTotalElements() <= 0){
//            log.info("데이터가 없습니다.");
//        }else{
//            for (int i = 0; i < boardPage.getTotalElements(); i++) {
//                Board board = boardPage.getContent().get(i);
//                log.info("bno : " + board.getBno());
//                log.info("title : " + board.getTitle());
//                log.info("content : " + board.getContent());
//                log.info("writer : " + board.getWriter());
//            }
//        }

    }

    @Test
    public void searchAllTest() {

        String[] types = {"t", "w", "c"};
        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> boards = boardRepository.searchAll(types, keyword, pageable);

        // 전체 페이지
        log.info(boards.getTotalPages());

        // 페이지 사이즈
        log.info(boards.getSize());

        // 페이지 번호
        log.info(boards.getNumber());

        // 이전 페이지 유무
        log.info("이전페이지 유무 : " + boards.hasPrevious());

        // 다음 페이지 유무
        log.info("다음 페이지 유무 : " + boards.hasNext());

        boards.getContent().forEach(board -> log.info(board));
    }
}
