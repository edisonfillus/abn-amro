package com.abnamro.assessment;

import com.abnamro.assessment.shared.security.SecurityTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(SecurityTestConfig.class)
@SpringBootTest
class AssessmentApplicationTests {

	@Test
	void contextLoads() {
	}

}
