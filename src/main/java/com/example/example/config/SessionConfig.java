package com.example.example.config;

import com.example.example.domain.Member;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Configuration
public class SessionConfig {

    private static SessionConfig singleton = null;

    public static SessionConfig getInstance() {

        if(singleton == null) singleton = new SessionConfig();

        return singleton;
    }

    public int sessionChk(Member member, HttpSession session) {

        ArrayList<Member> memberList = new ArrayList<>();

        if (member != null) {
            memberList.add(member);
        }

        if (memberList.size() > 1) {

        }

        return 0;
    }

}
