package com.site.blog.my.core;

import com.site.blog.my.core.service.AdminUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBlogApplicationTests {
	@Resource
	private AdminUserService adminUserService;
	@Test
	public void contextLoads() {
	}

	@Test
	public void updatepassword(){
		adminUserService.updatePassword(1,"123456","123456");
	}
}
