package com.javarang.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JavarangService {

    @Transactional
    public String save(String json) throws Exception;


    public List getBlogList() throws Exception;

    //Result_SearchData findByUserBankData(String uniKey, String tid);

    public String pre_transfer_call(String pwd, String tid, String uniKey, String sumry, String drMemo);
}
