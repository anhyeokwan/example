package com.example.example.controller;

import com.example.example.dto.BoardDTO;
import com.example.example.dto.PageRequestDTO;
import com.example.example.dto.PageResponseDTO;
import com.example.example.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/register")
    public String registerFrm() {
        return "board/registerFrm";
    }

    @ResponseBody
    @PostMapping("/ajax/register")
    public JSONObject registerBoard_POST(Model model, @RequestParam Map<String, Object> map
    , @RequestParam("multipartFile") MultipartFile multipartFile, HttpServletRequest request){

        int currentThreadCnt = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(currentThreadCnt);

        JSONObject jsonObject = new JSONObject();

        String code = "200";
        String message = "정상처리 되었습니다.";

        if (map == null) {
            code = "500";
            message = "처리 중 오류가 발생하였습니다.";
        }else{

            Future<Void> future = (Future<Void>) executorService.submit(() -> {
                log.info("filebool 1 : " + (multipartFile != null));

                if (multipartFile != null) {
                    String filePath = request.getSession().getServletContext().getRealPath("/template/upload/board");
                    String fileName = multipartFile.getOriginalFilename();
                    log.info("fileName : " + fileName);
                    log.info("filePath : " + filePath);

                    try {
                        FileInputStream fis = new FileInputStream(filePath + "/" +  fileName);
                        BufferedInputStream bis = new BufferedInputStream(fis);

                        FileOutputStream fos = new FileOutputStream(filePath + "/" +  fileName);
                        BufferedOutputStream bos = new BufferedOutputStream(fos);

                        byte[] getBytes = multipartFile.getBytes();

                        bos.write(getBytes);
                        bos.close();

                    } catch (IOException e) {

                    }


                }
            });

            // 모든 작업 완료 후 스레드 풀 종료
            executorService.shutdown();

            HashMap<String, Object> registerMap = boardService.boardSave(map);
            code = (String) registerMap.get("code");
            message = (String) registerMap.get("message");

        }

        jsonObject.put("code", code);
        jsonObject.put("message", message);

        return jsonObject;
    }
}
