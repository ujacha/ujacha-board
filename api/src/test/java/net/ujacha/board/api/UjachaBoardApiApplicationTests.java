package net.ujacha.board.api;

import net.ujacha.board.api.common.TestProfile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(TestProfile.TEST)
class UjachaBoardApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
