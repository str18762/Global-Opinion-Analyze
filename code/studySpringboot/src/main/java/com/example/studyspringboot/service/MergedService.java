package com.example.studyspringboot.service;

import com.example.studyspringboot.entity.Merged;
import com.example.studyspringboot.mapper.MergedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MergedService {

    @Autowired
    private MergedMapper mergedMapper;

    public List<Merged> selectAll() {
        return mergedMapper.selectAll();
    }

    public Merged selectById(Integer id) {
        return mergedMapper.selectById(id);
    }

    public List<Merged> selectByName(String name) {
        return mergedMapper.selectByName(name);
    }
}
