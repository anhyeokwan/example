package com.example.example.repository;

import com.example.example.domain.Example;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
