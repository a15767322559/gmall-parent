package com.ming.gmall.logger.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ming.gamall.common.constants.GamllConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class LoggerController{

    @Autowired
    KafkaTemplate<String,String>  kafkaTemplate;

    @RequestMapping("/log")
    public String log(@RequestParam("logString") String logString){

        JSONObject jsonObject = JSON.parseObject(logString);
        jsonObject.put("ts",System.currentTimeMillis());
        log.info(logString);

        if ("startup".equals(jsonObject.get("type"))){
            kafkaTemplate.send("GMALL_STARTUP",jsonObject.toJSONString());

        }else {
            kafkaTemplate.send("GMALL_EVENT",jsonObject.toJSONString());
        }

        return "success";
    }

}
