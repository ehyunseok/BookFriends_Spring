package com.daney.bookfriends;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("Test")
class BookfriendsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void exampleTest() {
		assertThat(1 + 1).isEqualTo(2);
	}

}
