package com.javarang.service;

import java.util.List;
import java.util.Map;

public interface DBService {
    List<Map<String, Object>> getTradeInfoList();
    List<Map<String, Object>> getBlogList();

    void insertTokken(Map<String, Object> args);
    void writeNewContent(Map<String, Object> args);

}
