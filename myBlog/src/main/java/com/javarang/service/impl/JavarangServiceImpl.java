package com.javarang.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.javarang.model.Result_SearchData;
import com.javarang.rep.DBRepository;
import com.javarang.service.JavarangService;
import com.javarang.utils.CommonUtil;
import com.javarang.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class JavarangServiceImpl implements JavarangService {
// 
//    @Value("${url.shared_gw}")
//    private String shared_gw;
//
//    @Value("${url.select_agree}")
//    private String select_agree;
//
//    @Value("${url.saml_url}")
//    private String saml_url;
//
//    @Value("${url.access_token_url}")
//    private String access_token_url;

//    @Value("${url.bank_dev_gw}")
    private String bank_dev_gw;

//    @Value("${url.transfer_pre_dev}")
    private String transfer_pre_dev;

//    @Value("${url.transfer_dev}")
    private String transfer_dev;

//    @Value("${url.stoliiv}")
    private String stoliiv_url;

    @Autowired
    private DBRepository DBRepository;



    //@Value("${damo.ini}")
    private String damo_ini;


    @SuppressWarnings("unchecked")
    @Override
    public String save(String json) throws Exception {

        JsonObject jsonobject = new JsonObject();
        JsonObject resbody = new JsonObject();

        try {
            jsonobject = (JsonParser.parseString(json)).getAsJsonObject();

            String tradeKey = CommonUtil.convSTRtoNull((jsonobject.get("tradeKey")).getAsString());
            log.info("tradeKey="+tradeKey);

            if (!tradeKey.equals("")) {
                boolean ExistRndKey = true;
                String rndKey = "";
                Map<String, Object> paramValue = null;

                while(ExistRndKey) {
                    rndKey = RandomUtil.CreateRandomString();
                    log.info("rndKey="+rndKey);
                    paramValue = new HashMap<String, Object>();
                    paramValue.put("rndToken", rndKey);

                    List<Map<String, Object>> rndKeyList = DBRepository.getRndKeyYN(paramValue);

                    if(rndKeyList.size()==0) {
                        ExistRndKey=false;
                    }
                }

                if(!ExistRndKey) {
                    DBRepository.insertTokken(paramValue);
                }

                resbody.addProperty("rndKey", rndKey);

            } else {
                log.error("tid null");
                resbody.addProperty("uniKey", "save data error");
            }

        } catch (JsonParseException e) {
            log.error("save data jsonParser error");
            throw new JsonParseException("save data jsonParser error");

        } catch (Exception e) {
            log.error("save data error");
            log.error(e.getMessage());
            throw new JsonParseException("save data error");

        }

        return resbody.toString();
    }

    @Override
    public List getBlogList() throws Exception {
        return DBRepository.getBlogList();
    }


    @SuppressWarnings("unchecked")
    @Override
    public String pre_transfer_call(String pwd, String tid, String uniKey, String sumry, String drMemo) {
        String result = "";
        String apikey = "";
        String accesstoken = "";
        String successCode = "";
        Result_SearchData findResult_search = null;

        log.debug("transfer call uniKey : {}", uniKey);
        log.debug("transfer call tid : {}", tid);
        log.debug("transfer call pwd : {}", pwd);

        //findResult_search = search_Data(uniKey, tid);

        ResponseEntity<String> resultEntity = null;
        try {
            JsonObject body = new JsonObject();
            JsonObject dataHeader = new JsonObject();
            JsonObject dataBody = new JsonObject();
 
            accesstoken = findResult_search.getAccessToken();
            apikey = findResult_search.getAppKey();



            dataHeader.addProperty("encType", "2");// �븫�샇�솕�쑀�삎 1:洹몃９�궗�븫�샇�솕諛⑹떇, 2:AES256諛⑹떇 (湲곕낯媛�:1)


            dataBody.addProperty("bzwkIdiviInptDvsn/drwotAcno", findResult_search.getWdAccount() == null ? "" : findResult_search.getWdAccount()); // 異쒓툑怨꾩쥖踰덊샇

            body.add("dataHeader", dataHeader);
            body.add("dataBody", dataBody);


            String hsKey = "";
            hsKey = CommonUtil.getHashKey(body.toString(), accesstoken, "UTF-8"); // �쟾臾� �쐞蹂�議곕갑吏� �궎

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            httpHeaders.add("Authorization", "Bearer " + accesstoken);
            httpHeaders.add("hsKey", hsKey);
            httpHeaders.add("ciNo", findResult_search.getCiNo() == null ? "" : findResult_search.getCiNo());
            httpHeaders.add("apiKey", apikey);
            httpHeaders.add("Content-Type", "application/json; charset=utf-8");
            httpHeaders.add("Accept", "application/json; charset=utf-8");
            httpHeaders.add("charset", "UTF-8");

            resultEntity = CommonUtil.callApi(bank_dev_gw + transfer_pre_dev, body, httpHeaders);

            //successCode = JsonPath.parse(((String) resultEntity.getBody()).replaceAll("\r", "").replaceAll("\t", "")).read("$.dataHeader.successCode");
            successCode = "";

        } catch (Exception e) {
            log.error("�삁鍮꾧굅�옒 API ERROR");
        }

        if (successCode.equals("0")) {
            result = transfer_call(pwd, accesstoken, apikey, findResult_search, sumry, drMemo);//蹂멸굅�옒
        } else {
            result = resultEntity.getBody().replaceAll("\r", "").replaceAll("\t", "");
        }

        // �씪�쉶�꽦 Key �룓湲�
        try {
            //mysqlRepository.update_abrogation_key(uniKey, tid);
        } catch (Exception e) {
            //log.error("uniKey delete error");
        }
        return result;
    }



    @SuppressWarnings("unchecked")
    public String transfer_call(String pwd, String accesstoken, String apikey, Result_SearchData findResult_search, String sumry, String drMemo) {
        String result = "";
        ResponseEntity<String> resultEntity = null;

        //try {

        String resultMessage = "";
        String successCode = "";
        String resultCode = "";
        String rndKey = "";
        JsonObject body = new JsonObject();
        JsonObject dataHeader = new JsonObject();
        JsonObject dataBody = new JsonObject();

        dataHeader.addProperty("resultCode", resultCode);// 0000: �꽦怨�
        dataHeader.addProperty("resultMessage", resultMessage);// �븫�샇�솕�쑀�삎 1:洹몃９�궗�븫�샇�솕諛⑹떇, 2:AES256諛⑹떇 (湲곕낯媛�:1)
        dataHeader.addProperty("successCode", successCode);// 0: �꽦怨� 1: �떎�뙣
        dataHeader.addProperty("category", "API");// �븫�샇�솕�쑀�삎 1:洹몃９�궗�븫�샇�솕諛⑹떇, 2:AES256諛⑹떇 (湲곕낯媛�:1)

        dataBody.addProperty("rndKey", rndKey);// �옖�뜡�궎


        body.add("dataHeader", dataHeader);
        body.add("dataBody", dataBody);

        String hsKey1 = "";
        hsKey1 = CommonUtil.getHashKey(body.toString(), accesstoken, "UTF-8"); // �쟾臾� �쐞蹂�議곕갑吏� �궎

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Authorization", "Bearer " + accesstoken);
        httpHeaders.add("hsKey", hsKey1);
        httpHeaders.add("ciNo", findResult_search.getCiNo() == null ? "" : findResult_search.getCiNo());
        httpHeaders.add("apiKey", apikey);
        httpHeaders.add("Content-Type", "application/json; charset=utf-8");
        httpHeaders.add("Accept", "application/json; charset=utf-8");
        httpHeaders.add("charset", "UTF-8");

        resultEntity = CommonUtil.callApi(bank_dev_gw + transfer_dev, body, httpHeaders);

        result = resultEntity.getBody().replaceAll("\r", "").replaceAll("\t", "");
        //} catch()
        //}
        return result;
    }



    //AES256 start
    public static String enc_aes256(String strPlain) {
        String aesPwd = "12345678901234567890123456789012";
        String aesLv = "1234567890123456";

        String encStr = "";
        try {
            SecretKey clsKey = new SecretKeySpec(aesPwd.getBytes(),"AES");
            IvParameterSpec clsIv = new IvParameterSpec(aesLv.getBytes());

            Cipher clsCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            clsCipher.init(Cipher.ENCRYPT_MODE, clsKey, clsIv);
            byte[] arrEnc = clsCipher.doFinal(strPlain.getBytes("utf-8"));
            encStr = Base64.getEncoder().encodeToString(arrEnc);
        }catch(Exception e){
            e.printStackTrace();
        }
        //log.debug("enc_aesStr : {}", encStr);
        return encStr;
    }

    //AES256 end


}
