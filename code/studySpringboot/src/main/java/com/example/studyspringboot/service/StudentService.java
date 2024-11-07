/**
 * 功能：
 * 作者：
 * 日期：2024/5/1 下午9:38
 */
package com.example.studyspringboot.service;

import com.example.studyspringboot.entity.Student;
import com.example.studyspringboot.exception.ServiceException;
import com.example.studyspringboot.mapper.StudentMapper;
import com.example.studyspringboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentMapper studentMapper;

    public void insertStudent(Student student) {
        studentMapper.insert(student);
    }

    public void deleteStudent(String sno) {
        studentMapper.delete(sno);
    }

    public void updateStudent(Student student) {
        studentMapper.update(student);
    }

    public List<Student> selectAll() {
        return studentMapper.selectAll();
    }

    public Student selectBySno(String sno) {
        return studentMapper.selectBySno(sno);
    }

    public Student selectBySname(String sname) {
        return studentMapper.selectBySname(sname);
    }
}
