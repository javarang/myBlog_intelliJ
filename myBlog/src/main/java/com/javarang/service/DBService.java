package com.javarang.service;

import java.util.List;
import java.util.Map;

public interface DBService {
    List<Map<String, Object>> getTradeInfoList();

    void insertTokken(Map<String, Object> args);
}
