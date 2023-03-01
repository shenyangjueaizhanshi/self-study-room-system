package com.example.selfstudyroomsystem.logback;

import lombok.Data;


@Data
public class LogBean {
    private String url;
    private String classMethod;
    private String httpMethod;
    private String ip;
    private String requestParams;
    private String requestTime;
    private String responseResult;
    private String responseTime;
}
