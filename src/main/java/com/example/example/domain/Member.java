package com.example.example.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
@ToString
@Table(name = "TBL_MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    @Column(name = "memberId", length = 500)
    private String memberId;

    @Column(name = "memberPw", length = 500)
    private String memberPw;

    @Column(name = "memberPhone", length = 500)
    private String memberPhone;
}
