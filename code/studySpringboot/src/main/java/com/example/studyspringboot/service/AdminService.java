package com.example.studyspringboot.service;

import com.example.studyspringboot.entity.*;
import com.example.studyspringboot.entity.Character;
import com.example.studyspringboot.mapper.AdminMapper;
import com.example.studyspringboot.mapper.CharacterMapper;
import com.example.studyspringboot.mapper.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private CharacterMapper characterMapper;
    public PageBean<User> listAllUser(Integer pageNum, Integer pageSize, String userName) {
        PageBean<User> pb = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);
        List<User> as = adminMapper.listAllUser(userName,"用户");
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<User> p = (Page<User>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;

    }

    public void delete(Integer id) {
        adminMapper.delete(id);
    }

    public void batchDelete(List<Integer> ids) {
        for (Integer id : ids) {
            adminMapper.delete(id);
        }
    }

    public void resetPassword(Integer id) {
        adminMapper.resetPassword(id,"123");
    }

    public PageBean<User> listAllAdmin(Integer pageNum, Integer pageSize) {
        PageBean<User> pb = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);
        List<User> as = adminMapper.listAllAdmin("管理员");
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<User> p = (Page<User>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;

    }

    public PageBean<Character> listAllInfluencePerson(Integer pageNum, Integer pageSize,String userName) {
        PageBean<Character> pb = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);
        List<Character> as = characterMapper.selectAll();
//                :characterMapper.selectByName(userName);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<Character> p = (Page<Character>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    public void deleteInfluence(Integer id) {
        adminMapper.deleteInfluence(id);
    }

    public void batchDeleteInfluence(List<Integer> ids) {
        for (Integer id : ids) {
            adminMapper.deleteInfluence(id);
        }
    }

    public void updateInfluencePerson(Character character) {
        adminMapper.updateInfluencePerson(character);
    }

    public PageBean<News_old> listAllNews(Integer pageNum, Integer pageSize,String newWhat) {
        PageBean<News_old> pb = new PageBean<>();


        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);
        List<News_old> as = newWhat==null?adminMapper.listAllNews()
                :adminMapper.listNewsByWhat(newWhat);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<News_old> p = (Page<News_old>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    public void deleteNew(String where) {
        adminMapper.deleteNew(where);
    }

    public void batchDeleteNew(List<String> wheres) {
        for (String where : wheres) {
            adminMapper.deleteNew(where);
        }
    }

    public PageBean<EmotionVO> listAllEmotion(Integer pageNum, Integer pageSize, String detail,String name) {
        PageBean<EmotionVO> pb = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);
        List<EmotionVO> as = detail==null?adminMapper.listAllEmotion()
                :adminMapper.listEmotionByDetail(detail,name);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<EmotionVO> p = (Page<EmotionVO>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    public void deleteEmotion(EmotionVO emotionVO) {
        adminMapper.deleteEmotion(emotionVO);
    }

    public void batchDeleteEmotion(List<EmotionVO> emotionVOS) {
        for (EmotionVO emotionVO : emotionVOS) {
            adminMapper.deleteEmotion(emotionVO);
        }
    }

    public PageBean<EmotionExtraVO> listAllExtraEmotion(Integer pageNum, Integer pageSize, String general, String name) {
        PageBean<EmotionExtraVO> pb = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);
        List<EmotionExtraVO> as = general==null?adminMapper.listAllExtraEmotion()
                :adminMapper.listExtraEmotionByDetail(general,name);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<EmotionExtraVO> p = (Page<EmotionExtraVO>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    public void deleteExtraEmotion(EmotionExtraVO emotionExtraVO) {
        adminMapper.deleteExtraEmotion(emotionExtraVO);
    }

    public void batchDeleteExtraEmotion(List<EmotionExtraVO> emotionExtraVOS) {
        for (EmotionExtraVO emotionExtraVO : emotionExtraVOS) {
            adminMapper.deleteExtraEmotion(emotionExtraVO);
        }
    }
}
