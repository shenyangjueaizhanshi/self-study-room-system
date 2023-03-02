package com.example.selfstudyroomsystem.dao;

import com.example.selfstudyroomsystem.bean.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface TestDAO {

    int insert(@Param("test")Test test);
}
