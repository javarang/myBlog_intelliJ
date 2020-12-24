package com.javarang.rep;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DBRepository {
    List<Map<String, Object>> getRndKeyYN(Map<String, Object> args);
    List<Map<String, Object>> getTradeInfoList();
    List<Map<String, Object>> getBlogList();

    void insertTokken(Map<String, Object> args);
}
