package com.javarang.service.impl;

import com.javarang.rep.DBRepository;
import com.javarang.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;


public class DBServiceImpl implements DBService {

    @Autowired
    private DBRepository DBRepository;

    @Override
    public List<Map<String, Object>> getTradeInfoList() {
        return DBRepository.getTradeInfoList();
    }

    @Override
    public List<Map<String, Object>> getBlogList() {
        return DBRepository.getBlogList();
    }

    @Override
    public void insertTokken(Map<String, Object> args) {

        DBRepository.insertTokken(args);

    }

    @Override
    public void writeNewContent(Map<String, Object> args) {
        DBRepository.writeNewContent(args);
    }
}
