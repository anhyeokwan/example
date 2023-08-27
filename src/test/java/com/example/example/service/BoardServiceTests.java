package com.example.example.service;

import com.example.example.domain.Board;
import com.example.example.dto.BoardDTO;
import com.example.example.dto.PageRequestDTO;
import com.example.example.dto.PageResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        log.info(boardService.getClass().getName()); // 클래스의 이름을 그대로 출력 ex) com.example.example.service.BoardServiceImpl$$EnhancerBySpringCGLIB$$db98b81

        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample Title..")
                .content("Sample Content..")
                .writer("Sample Writer..")
                .build();

        Long bno = boardService.register(boardDTO);
        log.info("bno : " + bno);
    }

    @Test
    public void readOne() {
        Long bno = 101L;

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info("boardDTO bno : " + boardDTO.getBno());
        log.info("boardDTO title : " + boardDTO.getTitle());
        log.info("boardDTO content : " + boardDTO.getContent());
        log.info("boardDTO writer : " + boardDTO.getWriter());

    }

    @Test
    public void testModify() {

        ModelMapper modelMapper = new ModelMapper();
        // 변경에 필요한 데이터만
        Board board = Board.builder()
                .bno(101L)
                .title("update...101")
                .content("Updated content 101...")
                .build();

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
        boardService.modify(boardDTO);
    }

    @Test
    public void testRemove() {
        Long bno = 101L;
        boardService.remove(bno);
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);
    }
}
