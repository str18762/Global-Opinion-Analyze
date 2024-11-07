/**
 * 功能：
 * 作者：
 * 日期：2024/5/1 下午9:38
 */
package com.example.studyspringboot.service;

import com.example.studyspringboot.entity.Course;
import com.example.studyspringboot.entity.Student;
import com.example.studyspringboot.exception.ServiceException;
import com.example.studyspringboot.mapper.CourseMapper;
import com.example.studyspringboot.mapper.StudentMapper;
import com.example.studyspringboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    CourseMapper courseMapper;
    @Autowired
    StudentMapper studentMapper;
    public void insertCourse(Course course) {
        courseMapper.insert(course);
    }

    public void deleteCourse(Integer cno) {
        courseMapper.delete(cno);
    }

    public void updateCourse(Course course) {
        courseMapper.update(course);
    }

    public List<Course> selectAll() {
        return courseMapper.selectAll();
    }

    public Course selectByCno(Integer cno) {
        return courseMapper.selectByCno(cno);
    }

    public Course selectByCname(String cname) {
        return courseMapper.selectByCname(cname);
    }
    public List<Student> getCourseSelection(Integer cno) {
        return studentMapper.findStudentsByCourseNumber(cno);
    }
}
