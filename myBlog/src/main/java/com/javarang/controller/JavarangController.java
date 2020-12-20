package com.javarang.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.javarang.service.JavarangService;
import com.javarang.service.PreProcessService;
import com.javarang.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class JavarangController {


    //@Value("${url.stoliiv}")
    private String stoliiv_url;

    @Autowired
    private JavarangService javarangService;

    @Autowired
    private PreProcessService preProcessService;

    //@Autowired
    //private RevTransDBRepository  revTransDBRepository;


    @ResponseBody
    @RequestMapping(value = "/firstRun", method = RequestMethod.GET)
    public String firstRun(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String str = issueToken(request, response);

        return "";
    }


    @ResponseBody
    @RequestMapping(value = "/issueToken", method = RequestMethod.GET)
    public String issueToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //String strJson = CommonUtil.convSTRtoNull(request.getParameter("inputParam"));
        String strJson = "{\"tradeKey\":\"1111\"}";
        String resultJson = null;

        resultJson = javarangService.save(strJson);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tradeKey", "1111");
        preProcessService.inputSession(request, response, paramMap);



        return resultJson;


    }




    @ResponseBody
    @RequestMapping(value = "/revTrans/startPreData", method = RequestMethod.GET)
    public String startPreData(HttpServletRequest httpServletRequest){
        String jsonstr = "";
        try {
            jsonstr = CommonUtil.getRequestStr(httpServletRequest);

        } catch (IOException e) {
            //log.error("startPreData IOException");

        }

        String rsdata="";
        //rsdata = javarangService.save(jsonstr);
        return rsdata;
    }



    @ResponseBody
    @RequestMapping(value = "/revTrans/appCallScreen/{chcode}/{tid}", method = RequestMethod.POST)
    public ModelAndView screencall(@PathVariable String chcode, @PathVariable String tid, HttpServletRequest httpServletRequest) throws IOException
    {
        ModelAndView mv = new ModelAndView();

        String jsonstr = "";
 
        try {
            jsonstr = CommonUtil.getRequestStr(httpServletRequest);
        } catch (IOException e) {
            log.error("appCallScreen IOException");
        }

        JsonObject jsonobject = new JsonObject();


        log.debug("appCallScreen jsonstr: {}", jsonstr);
        try {
            jsonobject = (JsonParser.parseString(jsonstr)).getAsJsonObject();
        } catch (JsonParseException e) {
            log.error("appCallScreen jsonParser error");
        }

        String uniKey = (jsonobject.get("uniKey")).getAsString();

        log.debug("appCallScreen uniKey: {}", uniKey);
        log.debug("appCallScreen tid: {}", tid);

        if (uniKey != null && uniKey.length() != 0) {
            //Result_SearchData findResult_searchHistories = null;

            //findResult_searchHistories = javarangService.findByUserBankData(uniKey, tid);
/*
			String wdAccount = findResult_searchHistories.getWdAccount();
			String dpAccount = findResult_searchHistories.getDpAccount();
			int transferPrice = (int) findResult_searchHistories.getTransferPrice();
	*/
            String comma_cash = "";


            mv.setViewName("/index");
            mv.addObject("uniKey", uniKey);
	/*
			mv.addObject("tid", findResult_searchHistories.getTid());
			mv.addObject("ciNo", findResult_searchHistories.getCiNo());
			mv.addObject("channelCode", findResult_searchHistories.getChannelCode());
			mv.addObject("wdAccount", javarangService.replaceAcno(wdAccount));
			mv.addObject("wdName", findResult_searchHistories.getWdName());
			mv.addObject("dpAccount", javarangService.replaceAcno(dpAccount));
			mv.addObject("dpName", findResult_searchHistories.getDpName());
			mv.addObject("transferPrice", comma_cash);
			mv.addObject("transferName", findResult_searchHistories.getTransferName());
			mv.addObject("loginType", findResult_searchHistories.getLoginType());
			mv.addObject("accessToken", findResult_searchHistories.getAccessToken());
		*/
        } else {
            log.error("appCallScreen uniKey null");
            mv.setViewName("/error");
            mv.addObject("successCode","9999");
            mv.addObject("resultMessage", "uniKey null");
            mv.addObject("tid", tid);
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/screenapi/transfer/checkStatus", method = RequestMethod.POST)
    public String check_status(HttpServletRequest httpServletRequest) {
        String jsonstr = "";
        try {
            jsonstr = CommonUtil.getRequestStr(httpServletRequest);
        } catch (IOException e) {
            //log.error("startPreData IOException");
        }
        JsonObject jsonobject = new JsonObject();


        try {
            //jsonobject = (JSONObject) jsonParser.parse(jsonstr);
            jsonobject = (JsonParser.parseString(jsonstr)).getAsJsonObject();
        } catch (JsonParseException e) {
            //log.error("check_status jsonParser error");
        }

        String rsdata;

        //rsdata = javarangService.check_status((JsonParser.parseString("tid")).getAsJsonObject());

        JsonObject resbody = new JsonObject();

        //resbody.put("check_status", rsdata);
        //resbody.add("check_status", JsonParser.parseString(rsdata));
        return resbody.toString();
    }


    @RequestMapping(value = "/revTrans/preTransferCall", method = RequestMethod.GET)
    public ModelAndView pre_transfer_call(HttpServletRequest httpServletRequest) {

        ModelAndView mv = new ModelAndView();
        String tid = httpServletRequest.getParameter("tid");
        String uniKey = httpServletRequest.getParameter("uniKey");

        String sumry = httpServletRequest.getParameter("hidsumry");
        String drMemo = httpServletRequest.getParameter("hiddrMemo");

        String aUnpacked = "";

        String successCode = "";
        String resultMessage = "";
        JsonObject result_body = new JsonObject();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);


        log.debug("Unpack pwd : {}", aUnpacked);

        if (aUnpacked != null && aUnpacked.length() != 0) {
            String pwd = aUnpacked;
//			String pwd = "0000";
            String rsdata = javarangService.pre_transfer_call(pwd, tid, uniKey, sumry, drMemo);

            log.info("transfer_call rsdata : {}", rsdata);
            System.out.println("transfer_call rsdata : {}"+ rsdata);

            JsonObject jsonobject = new JsonObject();
            //JSONParser jsonParser = new JSONParser();
            JsonObject jsondataHeader = new JsonObject();
            JsonObject jsondataBody = new JsonObject();

            try {
                jsonobject = (JsonParser.parseString(rsdata)).getAsJsonObject();

                //jsondataHeader = (JSONObject) jsonobject.get("dataHeader");
                jsondataHeader = (jsonobject.get("dataHeader")).getAsJsonObject();
                jsondataBody = (jsonobject.get("dataBody")).getAsJsonObject();


                successCode =  jsondataHeader.get("successCode").getAsString();
                resultMessage =(jsondataHeader.get("resultMessage")).getAsString();

                if (successCode.equals("0")) {
                    successCode = "0000";

					/*
					String wdAccount = (String) jsondataBody.get("bzwkIdiviOutptBook/drwotAcno");
					String dpAccount = (String) jsondataBody.get("bzwkIdiviOutptBook/mnrcvAcno");
					int transferPrice = ((Long) jsondataBody.get("bzwkIdiviOutptBook/trsaccAmt")).intValue();
					int balance = ((Long) jsondataBody.get("bzwkIdiviOutptBook/trsaccAfBal")).intValue();


					String comma_cash = "";
					comma_cash = String.format("%,d", transferPrice);

					String comma_balance = "";
					comma_balance = String.format("%,d", balance);
					*/

                    mv.setViewName("/result");
                    mv.addObject("successCode", successCode);
                    mv.addObject("resultMessage", resultMessage);
					/*
					mv.addObject("wdAccount", javarangService.replaceAcno(wdAccount));//�빊�뮄�닊�④쑴伊�
					mv.addObject("dpAccount", javarangService.replaceAcno(dpAccount));//占쎌뿯疫뀀뜃�롩넫占�
					mv.addObject("pre_price", jsondataBody.get("bzwkIdiviOutptBook/trsaccBfBal"));//占쎌뵠筌ｋ똻�읈占쎌삆占쎈만
					mv.addObject("transferPrice", comma_cash);//占쎌뵠筌ｋ떯�닊占쎈만
					mv.addObject("after_Price", jsondataBody.get("bzwkIdiviOutptBook/trsaccFee"));//占쎌뵠筌ｋ똻�땾占쎈땾�뙴占�
					mv.addObject("balance", comma_balance);//占쎌뵠筌ｋ똾�뜎占쎌삆占쎈만
					mv.addObject("wdName", jsondataBody.get("bzwkIdiviOutptBook/rqstrFname"));//�빊�뮄�닊占쎌뵥
					mv.addObject("sumry", jsondataBody.get("bzwkIdiviOutptBook/sumry"));//占쎌읅占쎌뒄
					mv.addObject("dpName", jsondataBody.get("bzwkIdiviOutptBook/draweFname"));//獄쏆룆�뮉占쎄텢占쎌뿺
					mv.addObject("MemoInptYn", jsondataBody.get("bzwkIdiviOutptBook/drwotHistMemoInptYn"));//�빊�뮄�닊占쎄땀占쎈열 筌롫뗀�걟占쎌뿯占쎌젾占쎈연�겫占�
					mv.addObject("drwotHistMemo", jsondataBody.get("bzwkIdiviOutptBook/drwotHistMemo"));//�빊�뮄�닊占쎄땀占쎈열 筌롫뗀�걟
					*/
                } else {
					if (resultMessage.matches(".*鍮꾨�踰덊샇 �솗�씤 諛붾엻�땲�떎.*")) {
						successCode = "0002";
					}else if (resultMessage.matches(".*異쒓툑媛��뒫湲덉븸�씠 遺�議깊빀�땲�떎.*")){
						successCode = "0003";
					}else if (resultMessage.matches(".*Access token does not exist.*")){
						successCode = "0004";
						resultMessage = "Access token ERROR";
					}else if (resultMessage.matches(".*蹂댁븞�옣移� 誘몃컻湲� 怨좉컼�엯�땲�떎.*")){
						successCode = "0005";
					}else if (resultMessage.matches(".*異쒓툑怨꾩쥖濡� �벑濡앸맂 怨꾩쥖媛� �븘�떃�땲�떎.*")){
						successCode = "0006";
					}else if (resultMessage.matches(".*�엯異쒖떇�썝�옣�씠 議댁옱�븯吏� �븡�뒿�땲�떎.*")){
						successCode = "0007";				
					}else {
						resultMessage = "�씠泥닿굅�옒�떎�뙣";
						successCode = "0001";
					}
                    mv.setViewName("/error");
                    mv.addObject("successCode",successCode);
                    mv.addObject("resultMessage",resultMessage);
                }

                mv.addObject("tid",tid);




                result_body.addProperty("successCode", successCode);
                result_body.addProperty("resultMessage", resultMessage);
                result_body.addProperty("tid", tid);


                //log.debug("successCode : {}", successCode);
                //log.debug("resultMessage : {}", resultMessage);

                //mysqlRepository.update_status(successCode, tid);
                ResponseEntity<String> result = CommonUtil.callApi(stoliiv_url, result_body, httpHeaders);
            } catch(Exception e){

            }
        } else {


            result_body.addProperty("successCode", "9999");
            result_body.addProperty("resultMessage", "椰꾧퀡�삋�뿆�뫁�꺖");
            result_body.addProperty("tid", tid);


            log.debug("椰꾧퀡�삋�뿆�뫁�꺖");

            //mysqlRepository.update_status("9999", tid);
            ResponseEntity<String> result = CommonUtil.callApi(stoliiv_url, result_body, httpHeaders);

            mv.setViewName("/error2");
            mv.addObject("successCode","9999");
            mv.addObject("resultMessage", "椰꾧퀡�삋�뿆�뫁�꺖");
            mv.addObject("tid",tid);
        }
        return mv;
    }
    /*
        @RequestMapping(value = "/test1")
        public String test1() {
            String a = javarangService.scpEnc("0000");
            String b = javarangService.scpEncDB("0000");
            return "test";
        }
        */
    @RequestMapping(value = "/error2")
    public String error() {
        return "error2";
    }
}
