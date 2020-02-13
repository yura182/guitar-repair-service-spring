package com.yura.repair.controller;

import com.yura.repair.configuration.LoginSuccessHandler;
import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.Role;
import com.yura.repair.service.ReviewService;
import com.yura.repair.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    private final static UserDto USER_DTO = getUserDto();

    @MockBean
    private UserService userService;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private MockMvc mvc;

    @Test
    public void mainShouldReturnIndexPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser
    public void loginShouldRedirectLoggedUser() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void registerShouldReturnRegisterPage() throws Exception {
        mvc.perform(get("/register"))
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("userDto"));
    }

    @Test
    @WithMockUser
    public void registerShouldRedirectLoggedUser() throws Exception {
        mvc.perform(get("/register"))
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void registerShouldRegisterUser() throws Exception {
        mvc.perform(post("/register")
                .flashAttr("userDto", USER_DTO)
                .param("passwordConfirmation", USER_DTO.getPassword()))
                .andExpect(view().name("redirect:/login"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(userService).register(any(UserDto.class));
    }

    @Test
    public void registerShouldNotRegisterUserForInvalidUser() throws Exception {
        mvc.perform(post("/register")
                .flashAttr("userDto", UserDto.builder().name("Yura").password("12345678").build())
                .param("passwordConfirmation", "12345678"))
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    public void registerShouldNotRegisterUserForInvalidConfirmationPassword() throws Exception {
        mvc.perform(post("/register")
                .flashAttr("userDto", USER_DTO)
                .param("passwordConfirmation", "12345"))
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("confirmationError"));
    }

    private static UserDto getUserDto() {
        return UserDto.builder()
                .name("Yura")
                .surname("Petrashenko")
                .email("yura@gmail.com")
                .password("123456789")
                .phone("0665005050")
                .role(Role.CLIENT)
                .build();
    }
}