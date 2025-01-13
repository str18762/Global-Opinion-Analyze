package com.example.studyspringboot.service;

import com.example.studyspringboot.entity.SnapshotVO;
import com.example.studyspringboot.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    public void saveSnapshot(String username,String url) {
        LocalDate date = LocalDate.now();
        fileMapper.saveSnapshot(username,url,date);
    }

    public Map<LocalDate, List<String>> selectAllSnapshot(String username) {
        List<SnapshotVO> snapshotList = fileMapper.selectAllSnapshot(username);
        Map<LocalDate, List<String>> result = new TreeMap<>(new MyComparator());
        snapshotList.stream().forEach(snapshotVO -> {
            List<String> list = result.getOrDefault(snapshotVO.getDate(), new ArrayList<>());
            list.add(snapshotVO.getUrl());
            result.put(snapshotVO.getDate(),list);
        });
        return result;
    }

    class MyComparator implements Comparator<LocalDate>{
        @Override
        public int compare(LocalDate o1, LocalDate o2) {
            return o2.compareTo(o1);
        }
    }
}
