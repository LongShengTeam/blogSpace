package com.site.blog.my.core;

import cn.hutool.core.io.resource.ResourceUtil;
import com.site.blog.my.core.entity.Question;
import com.site.blog.my.core.dao.QuestionMapper;
import com.site.blog.my.core.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class MyBlogApplicationTests {
	@Resource
	private AdminUserService adminUserService;
	@Resource
	private QuestionMapper questionMapper;

	@Test
	public  void demo(){
		Question question = questionMapper.selectById(1L);
		log.info("question--->{}",question);
		Question question2=new Question();
		question2.setQuestionDesc("11");
		question2.setQuestionType(1);
		question2.setOption1("11");
		question2.setOption2("11");
		questionMapper.insert(question2);


	}
	@Test
	public void contextLoads() {
	}

	@Test
	public void updatepassword(){
		adminUserService.updatePassword(1,"123456","123456");
	}

	@Test
	public void txtfileDemo(){
		BufferedReader utf8Reader = ResourceUtil.getUtf8Reader("aaaa.sql");
		HashMap<Integer, List> tiMap = new HashMap<>();
		HashMap<Integer, List> daanMap = new HashMap<>();
		final Integer[] count = {1};
		utf8Reader.lines().forEach(row -> {
			if (row.contains("第") && row.contains("页")) {

			} else {

				if (count[0].toString().equals(row.split(" ")[0])) {
					ArrayList<Object> list = new ArrayList<>();
					ArrayList<Object> daanlist = new ArrayList<>();
					list.add(row);
					tiMap.put(count[0], list);
					daanMap.put(count[0], daanlist);

					if (count[0] == 1065) {
						count[0]++;
						count[0]++;
					} else {
						count[0]++;
					}


				} else {
//                    System.out.println(count[0]);
					if (count[0] == 1067) {
						tiMap.get((count[0] - 2)).add(row);
					} else {
						tiMap.get((count[0] - 1)).add(row);
					}


				}

				if ((row.contains("A") && !row.contains("A．"))) {
					if (count[0] == 1067) {
						daanMap.get((count[0] - 2)).add("A");
					} else {
						daanMap.get((count[0] - 1)).add("A");
					}
				} else if ((row.contains("B") && !row.contains("B．"))) {
					if (count[0] == 1067) {
						daanMap.get((count[0] - 2)).add("B");
					} else {
						daanMap.get((count[0] - 1)).add("B");
					}
				} else if ((row.contains("C") && !row.contains("C．"))) {
					if (count[0] == 1067) {
						daanMap.get((count[0] - 2)).add("C");
					} else {
						daanMap.get((count[0] - 1)).add("C");
					}
				} else if ((row.contains("D") && !row.contains("D．"))) {
					if (count[0] == 1067) {
						daanMap.get((count[0] - 2)).add("D");
					} else {
						daanMap.get((count[0] - 1)).add("D");
					}
				} else if ((row.contains("E") && !row.contains("E．"))) {
					if (count[0] == 1067) {
						daanMap.get((count[0] - 2)).add("E");
					} else {
						daanMap.get((count[0] - 1)).add("E");
					}
				}

			}

		});

		daanMap.forEach((k, v) -> {
			System.out.println(k);
			System.out.println(v);
		});
	}
}
