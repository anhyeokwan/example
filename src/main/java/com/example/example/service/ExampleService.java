package com.example.example.service;

import com.example.example.domain.Example;
import com.example.example.repository.ExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    @Autowired
    private ExampleRepository exampleRepository;

    public Page<Example> selectAllExample(Pageable pageable) {
        Page<Example> pageList = exampleRepository.findAll(pageable);
        return pageList;
    }
}
