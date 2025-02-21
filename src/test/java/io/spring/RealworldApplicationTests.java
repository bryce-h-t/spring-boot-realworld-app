package io.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.config.location=classpath:application-test.properties",
    "spring.main.allow-bean-definition-overriding=true"
})
public class RealworldApplicationTests {

	@Test
	public void contextLoads() {
	}

}
