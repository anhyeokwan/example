package com.example.example.controller;

import com.example.example.domain.Example;
import com.example.example.repository.ExampleRepository;
import com.example.example.service.ExampleService;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.PrivateKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Log4j2
public class SampleContoller {

    @Autowired
    private ExampleService exampleService;

    @GetMapping(value = "/hello")
    public void hello(Model model) {
        log.info("hello....................");

        model.addAttribute("msg", "HELLO WORLD");

    }

    @GetMapping(value = "/ex/ex1")
    public void ex1(Model model) {
        List<String> list = Arrays.asList("AAA", "BBB", "CCC", "DDD");
        model.addAttribute("list", list);
    }

    class SampleDTO{
        private String p1, p2, p3;

        public String getP1(){
            return p1;
        }

        public String getP2(){
            return p2;
        }

        public String getP3(){
            return p3;
        }
    }

    @GetMapping(value = "/ex/ex2")
    public void ex2(Model model){
        log.info("ex/ex2...............");

        List<String> strList = IntStream.range(1, 10)
                .mapToObj(i -> "Data" + i)
                .collect(Collectors.toList());

        model.addAttribute("list", strList);

        Map<String, String> map = new HashMap<>();
        map.put("A", "AAA");
        map.put("B", "BBB");

        model.addAttribute("map", map);

        SampleDTO sampleDTO = new SampleDTO();
        sampleDTO.p1 = "Value -- p1";
        sampleDTO.p2 = "Value -- p2";
        sampleDTO.p3 = "Value -- p3";

        model.addAttribute("dto", sampleDTO);
    }

    @GetMapping(value = "/ex/ex3")
    public void ex3(Model model) {
        model.addAttribute("arr", new String[]{"AAA", "BBB", "CCC"});
    }

    @GetMapping("/ex/chagneChk")
    public void chagneChk(Model model, @PageableDefault(size=20, sort="idx",direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Example> exList = exampleService.selectAllExample(pageable);
        model.addAttribute("arr", exList);
    }
}
