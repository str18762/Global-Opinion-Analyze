/**
 * 功能：
 * 作者：
 * 日期：2024/11/5 上午8:55
 */
package com.example.studyspringboot.service;

import com.example.studyspringboot.entity.TestPerson;
import com.example.studyspringboot.mapper.TestPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    TestPersonRepository personRepository;

    public void createNode() {
        TestPerson person = new TestPerson();
        person.setName("我是测试Name");
        personRepository.save(person);
    }
}
