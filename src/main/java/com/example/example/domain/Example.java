package com.example.example.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_example")
@Getter
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "car")
    private String car;

    @Column(name = "auto_bike")
    private String auto_bike;
}
