package io.spring.api;

import io.spring.api.security.WebSecurityConfig;
import io.spring.application.UserQueryService;
import io.spring.application.data.UserData;
import io.spring.core.service.JwtService;
import io.spring.core.user.User;
import io.spring.core.user.UserRepository;
import io.spring.infrastructure.mybatis.readservice.UserReadService;
import io.spring.api.exception.CustomizeExceptionHandler;
import io.spring.JacksonCustomizations;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = {})
@TestPropertySource(locations = "classpath:application-test.properties")
@Import({
  WebSecurityConfig.class,
  JacksonCustomizations.class,
  ValidationAutoConfiguration.class,
  CustomizeExceptionHandler.class,
  UserService.class
})
abstract class TestWithCurrentUser {
    @Autowired
    protected MockMvc mvc;

    @MockBean protected UserRepository userRepository;
    @MockBean protected JwtService jwtService;
    @MockBean protected UserReadService userReadService;

    protected User user;
    protected UserData userData;
    protected String token;
    protected String email;
    protected String username;
    protected String defaultAvatar;

    // MockMvc already autowired as 'mvc' above

    protected void userFixture() {
        email = "john@jacob.com";
        username = "johnjacob";
        defaultAvatar = "https://static.productionready.io/images/smiley-cyrus.jpg";

        user = new User(email, username, "123", "", defaultAvatar);
        when(userRepository.findByUsername(eq(username))).thenReturn(Optional.of(user));
        when(userRepository.findById(eq(user.getId()))).thenReturn(Optional.of(user));

        userData = new UserData(user.getId(), email, username, "", defaultAvatar);
        when(userReadService.findById(eq(user.getId()))).thenReturn(userData);

        token = "token";
        when(jwtService.getSubFromToken(eq(token))).thenReturn(Optional.of(user.getId()));
    }

    @BeforeEach
    public void setUp() throws Exception {
        userFixture();
    }
}
