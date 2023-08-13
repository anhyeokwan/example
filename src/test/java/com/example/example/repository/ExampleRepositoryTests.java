package com.example.example.repository;

import com.example.example.domain.Example;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ExampleRepositoryTests {

    @Autowired
    private ExampleRepository exampleRepository;

    @Test
    public void exampleInsert() {

        IntStream.rangeClosed(0, 100).forEach(i -> {
            Example example = Example.builder()
                    .car("car.." + i)
                    .auto_bike("auto_bike.." + i)
                    .build();

            Example result = exampleRepository.save(example);
            log.info("examble idx : " + result.getIdx());
        });

    }

    @Test
    public void selectOneExample() {

        Long idx = 100L;

        Optional<Example> example = exampleRepository.findByIdx(idx);

        Example result = null;

        try {
            result = example.orElseThrow();
        }catch (NoSuchElementException e){

        }

        if (result == null) {
            log.info("해당하는 값이 없습니다.");
        }else{
            log.info("idx : " + result.getIdx());
            log.info("car : " + result.getCar());
            log.info("auto_bike : " + result.getAuto_bike());
        }
    }

    @Test
    public void exapmleUpdateTest() {

        Long idx = 100L;

        // 해당 정보가 있는지 검사
        Optional<Example> example = exampleRepository.findByIdx(idx);

        try {
            Example result = example.orElseThrow();

            result.change("제네시스", "야마하");

            Example updateEx = exampleRepository.save(result);

            log.info("원하시는 정보가 수정되었습니다.");
        } catch (NoSuchElementException e) {
            log.info("원하시는 정보가 없습니다.");
            return;
        }

    }

    @Test
    public void searchTest() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("idx").descending());
        Page<Example> resultPage = exampleRepository.findByKeyword("..3", pageable);

        if(resultPage.getSize() <= 0){
            log.info("해당하는 데이터가 없습니다.");
        }else{
            log.info("총 페이지 네비의 개수는 " + resultPage.getSize() + "입니다.");
        }

    }
}
