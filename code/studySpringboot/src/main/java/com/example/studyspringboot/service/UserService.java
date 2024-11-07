/**
 * 功能：
 * 作者：
 * 日期：2024/5/1 下午9:38
 */
package com.example.studyspringboot.service;

import com.example.studyspringboot.entity.User;
import com.example.studyspringboot.exception.ServiceException;
import com.example.studyspringboot.mapper.UserMapper;
import com.example.studyspringboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void insertUser(User user) {
        userMapper.insert(user);
    }

    public void deleteUser(Integer id) {
        userMapper.delete(id);
    }

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public User login(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser == null||!user.getPassword().equals(dbUser.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        //生成token
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public User register(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser != null) {
            throw new ServiceException("用户名已存在");
        }else {
            userMapper.insert(user);
        }
        User t = userMapper.selectByUsername(user.getUsername());
        return t;
    }

    public User updateUser(User user) {
        userMapper.update(user);
        return userMapper.selectById(user.getId());
    }

    public int getCollectNum(int id) {
        return userMapper.getCollectNum(id);
    }
}
