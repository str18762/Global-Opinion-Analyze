/**
 * 功能：
 * 作者：
 * 日期：2024/5/1 下午9:34
 */
package com.example.studyspringboot.controller;

import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.entity.Course;
import com.example.studyspringboot.entity.Student;
import com.example.studyspringboot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public Result add(@RequestBody Course course) {
        try {
            courseService.insertCourse(course);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return Result.error("插入数据库错误");
            } else {
                return Result.error("系统错误");
            }
        }
        return Result.success("插入成功", course.getCno());
    }

    /**
     * @param
     */
    @DeleteMapping("/delete/{cno}")
    public Result delete(@PathVariable Integer cno) {
        try {
            courseService.deleteCourse(cno);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return Result.error("删除数据错误");
            } else {
                return Result.error("系统错误");
            }
        }
        return Result.success("删除成功", cno);
    }

    /**
     * 修改数据，只允许根据用户ID修改密码（目前）
     */
    @PutMapping("/update")
    public Result update(@RequestBody Course course) {
        try {
            courseService.updateCourse(course);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return Result.error("修改失败");
            } else {
                return Result.error("系统错误");
            }
        }
        return Result.success("200", "修改成功", course.getCno());
    }

    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Course> courses = courseService.selectAll();
        return Result.success("查询成功", courses);
    }

    /**
     * 通过用户ID或用户名来查找唯一用户（ID和用户名都是该用户的唯一标识）
     */
    @GetMapping("/selectByCno/{cno}")
    public Result selectByCno(@PathVariable Integer cno) {
        Course theCno = courseService.selectByCno(cno);
        return Result.success("查询成功", theCno);
    }

    @GetMapping("/selectByCname/{cname}")
    public Result selectByCname(@PathVariable String cname) {
        Course theCname = courseService.selectByCname(cname);
        return Result.success("查询成功", theCname);
    }
    @GetMapping("/getCourseSelection/{cno}")
    public Result getCourseSelection(@PathVariable Integer cno) {
        List<Student> students = courseService.getCourseSelection(cno);
        return Result.success("查询成功", students);
    }
}
