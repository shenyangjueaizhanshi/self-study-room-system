package com.example.selfstudyroomsystem.controller;

import com.example.selfstudyroomsystem.dao.TestDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试控制器")
@RequestMapping("/test")
@RestController
public class Test {
    @Autowired
    private TestDAO  testDAO;
    @ApiOperation(value = "测试接口")
    @PostMapping("/t")
    public String test(@RequestBody com.example.selfstudyroomsystem.bean.Test test){
       testDAO.insert(test);
        return "添加成功";
    }
}
