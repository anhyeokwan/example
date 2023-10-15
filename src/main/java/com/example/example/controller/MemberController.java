package com.example.example.controller;

import com.example.example.config.SessionConfig;
import com.example.example.domain.Member;
import com.example.example.service.MeberService;
import com.example.example.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping(value = "/member")
@RequiredArgsConstructor // @autowired 설정 안해도 됨, 다른 인터페이스를 불러올 시 final로 선언
public class MemberController {

    private final MeberService memberService;

    private SessionConfig sessionConfig = SessionConfig.getInstance();

    @GetMapping(value = "/loginFrm")
    public String loginFrm(){
        return "member/login";
    }

    @ResponseBody
    @PostMapping("/ajax/login")
    public JSONObject login(HttpSession session, HttpServletRequest request, @RequestParam Map<String, Object> map) {
        // 결과 코드 및 메세지를 담는 객체
        JSONObject jsonObject = new JSONObject();

        String code = "200";
        String message = "로그인에 성공하였습니다.";

        if (map == null) {
            code = "500";
            message = "처리 중 에러가 발생하였습니다.";
            jsonObject.put("message", message);
        }

        if (code.equals("200")) {
            String memberId = (String) map.get("memberId"); // 회원 아이디
            String memberPw = (String) map.get("memberPw"); // 회원 비밀번호

            HashMap<String, Object> memberMap = memberService.loginMember(memberId, memberPw);

            code = (String) memberMap.get("code");

            if (code.equals("500")) {
                message = (String) memberMap.get("message");
                jsonObject.put("message", message);
            } else {
                log.info("ip : " + request.getRemoteAddr());
                Member member = (Member) memberMap.get("member");

                HttpSession httpSession = request.getSession();
                httpSession.setMaxInactiveInterval(60 * 60);

                httpSession.setAttribute("member", member);

            }

        }

        jsonObject.put("code", code);

        return jsonObject;
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {

        log.info("memberId : " + session.getId());

        session.invalidate();

        return "redirect:/member/loginFrm";
    }

}
