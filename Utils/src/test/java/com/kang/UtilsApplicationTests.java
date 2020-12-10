package com.kang;

import com.util.UtilsApplication;
import com.util.utils.Apple;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UtilsApplication.class)
public class UtilsApplicationTests {


	@Autowired
	private Apple apple;

	@Test
	public void contextLoads() {
		System.out.println(" 历史问题  ");
	}

	private String  tmpPath = "";

	private String  s3Url = "";

	@Test
	public void upload(){
		apple.showName();
	}


}
