package com.example.example.service;

import com.example.example.domain.Member;

import java.util.HashMap;

public interface MeberService {

    HashMap<String, Object> loginMember(String memberId, String memberPw);

}
