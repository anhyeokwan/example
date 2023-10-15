package com.example.example.service;

import com.example.example.domain.Board;
import com.example.example.dto.BoardDTO;
import com.example.example.dto.PageRequestDTO;
import com.example.example.dto.PageResponseDTO;

import java.util.HashMap;
import java.util.Map;

public interface BoardService {

    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    HashMap<String, Object> boardSave(Map<String, Object> map);
}
