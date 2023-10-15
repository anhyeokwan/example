package com.example.example.service;

import com.example.example.domain.Member;
import com.example.example.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MeberService{

    private final MemberRepository memberRepository;

    @Override
    public HashMap<String, Object> loginMember(String memberId, String memberPw) {

        HashMap<String, Object> map = new HashMap<>();

        String code = "200";
        String message = "";

        Member member = memberRepository.loginMember(memberId, memberPw);

        if (member == null) { // 아이디 또는 비밀번호가 일치하지 않는 경우
            code = "500";
            message = "아이디 또는 패스워드를 확인해 주세요";

            map.put("message", message);
        }

        map.put("code", code);
        map.put("member", member);

        return map;
    }
}
