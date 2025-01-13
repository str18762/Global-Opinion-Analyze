/**
 * 功能：
 * 作者：
 * 日期：2024/11/5 上午9:10
 */
package com.example.studyspringboot.service;

import com.example.studyspringboot.entity.CharaNode;
import com.example.studyspringboot.mapper.CharaNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharaNodeService {
    @Autowired
    private CharaNodeRepository charaNodeRepository;

    public List<CharaNode> getAll() {
        return charaNodeRepository.findAll();
    }

    public CharaNode selectByName(String name) {
        CharaNode charaNode = charaNodeRepository.findByName(name);
        return charaNode;
    }
}
