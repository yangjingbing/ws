package com.ws;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WsApplicationTests {

    @Test
    public void contextLoads() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "2");
        String put = map.put("1", "3");
        System.out.println(put);
    }

}
