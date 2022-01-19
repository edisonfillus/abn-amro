package com.abnamro.assessment;

import com.abnamro.assessment.shared.security.SecurityTestConfig;
import com.abnamro.assessment.users.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(SecurityTestConfig.class)
@SpringBootTest
class AssessmentApplicationTests {

	private final UserService userService;

	@Autowired
	public AssessmentApplicationTests(UserService userService) {
		this.userService = userService;
	}

	@Test
	void contextLoads() {
		assertThat(userService).isNotNull();
	}

}
