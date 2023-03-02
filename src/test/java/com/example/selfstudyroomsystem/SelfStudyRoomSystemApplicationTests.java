package com.example.selfstudyroomsystem;

import com.example.selfstudyroomsystem.dao.TestDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SelfStudyRoomSystemApplicationTests {
    @Autowired
    private TestDAO testDAO;
    @Test
    void contextLoads() {
    }

}
