package com.example.example.repository;

import com.example.example.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.memberId = :memberId and m.memberPw = :memberPw")
    Member loginMember(@Param("memberId") String memberId, @Param("memberPw") String memberPw);

}
