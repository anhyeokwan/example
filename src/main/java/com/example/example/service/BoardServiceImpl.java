package com.example.example.service;

import com.example.example.domain.Board;
import com.example.example.dto.BoardDTO;
import com.example.example.dto.PageRequestDTO;
import com.example.example.dto.PageResponseDTO;
import com.example.example.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor // (final로 선언해야 함) 클래스의 필드에 대한 생성자(생성자 자동 생성) 즉, service단에서 repository를 불러올 때마다 autowired를 안해줘도 됌
@Transactional // 커밋 롤백 자동 실행 및 동시성 문제 조절
public class BoardServiceImpl implements BoardService{

    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;


    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class); // boardDto를 Board Entity로 변환

        Long bno = boardRepository.save(board).getBno();

        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {

        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {

        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        Board board = result.orElseThrow();

        board.change(boardDTO.getTitle(), boardDTO.getContent());

        boardRepository.save(board);

    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(result.getNumberOfElements())
                .build();
    }

    @Override
    public HashMap<String, Object> boardSave(Map<String, Object> map) {

        HashMap<String, Object> resultMap = new HashMap<>();

        String code = "200";
        String message = "정상적으로 처리되었습니다.";

        if (map == null) {
            code = "500";
            message = "처리 중 에러가 발생하였습니다.";
        }else{
            String title = (String) map.get("title");
            String content = (String) map.get("content");
            String writer = (String) map.get("writer");

            if (title == "") {code = "503"; message = "필수값이 없습니다.(title)";}
            else if(content == ""){code = "503"; message = "필수값이 없습니다.(content)";}
            else if(writer == ""){code = "503"; message = "필수값이 없습니다.(writer)";}

            if (code.equals("200")) {

                // builder pattern
                Board board = Board.builder()
                        .title(title)
                        .content(content)
                        .writer(writer)
                        .build();

                Board boardRegister = boardRepository.save(board);

                if (boardRegister == null) {
                    code = "500";
                    message = "처리 중 에러가 발생하였습니다.";
                }

            }

        }
        resultMap.put("code", code);
        resultMap.put("message", message);

        return resultMap;
    }

}
