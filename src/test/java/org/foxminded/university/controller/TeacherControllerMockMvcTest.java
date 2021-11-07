package org.foxminded.university.controller;

import junit.framework.Assert;
import org.foxminded.university.config.web.WebConfiguration;
import org.foxminded.university.dto.TeacherDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WebConfiguration.class })
@WebAppConfiguration
public class TeacherControllerMockMvcTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesTeacherController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("teacherController"));
    }

    @Test
    public void givenTeachersAllURIWhenMockMVCThenReturnsTeachersListViewNameAndContainsTeachersAttribute() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("teachersList"))
                .andExpect(model().attributeExists("teachers"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }



    @Test
    public void givenTeachersPageableURIWhenMockMVCThenReturnsTeachersListViewNameAndContainsTeachersAttribute() throws Exception {
        MultiValueMap<String, String> requestParams= new LinkedMultiValueMap<>();
        requestParams.add("pageNumber", "1");
        requestParams.add("itemsPerPage", "2");
        MvcResult mvcResult =  this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers/pageable").params(requestParams))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("teachersList"))
                .andExpect(model().attributeExists("teachers"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenTeachersUpdateIdURIWhenMockMVCThenReturnsTeachersWithId() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers/update/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("updateTeacher"))
                .andExpect(model().attributeExists("teacher"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenTeachersUpdateIdURIWhenMockMVCThenRedirectIndex() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers/update/2"))
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andReturn();
    }

    @Test
    public void givenTeachersUpdateURIWithPostWhenMockMVCThenVerifyResponse() throws Exception {
        TeacherDto teacherDto = TeacherDto.builder()
                .withId(1L)
                .withEmail("email@gmail.com")
                .withPassword("12345")
                .withBirthDate(new Date())
                .build();
        this.mockMvc.perform(post("/teachers/update/1").flashAttr("teacher", teacherDto))
                .andDo(print())
                .andExpect(redirectedUrl("/teachers/all"))
                .andReturn();
    }

    @Test
    public void givenTeachersRegistrationURIWithGetWhenMockMVCThenReturnTeacherRegistrationForm() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("teacherRegistration"))
                .andExpect(model().attributeExists("teacher"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenTeachersLoginURIWithGetWhenMockMVCThenReturnTeacherLoginForm() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("teacherLogin"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenTeachersDeleteURIWithGetWhenMockMVCThenRedirectTeachersAll() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers/delete/6"))
                .andDo(print())
                .andExpect(redirectedUrl("/teachers/all"))
                .andReturn();
    }

    @Test
    public void givenTeachersloginURIWithPostWhenMockMVCThenRedirectIndex() throws Exception {
        MultiValueMap<String, String> requestParams= new LinkedMultiValueMap<>();
        requestParams.add("login", "wertuha22@gmail.com");
        requestParams.add("password", "12345");
        this.mockMvc.perform(post("/teachers/login").params(requestParams))
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andReturn();
    }

    @Test
    public void givenTeachersLoginURIWithPostWhenMockMVCThenDontRedirect() throws Exception {
        MultiValueMap<String, String> requestParams= new LinkedMultiValueMap<>();
        requestParams.add("login", "ahdsl@gmail.com");
        requestParams.add("password", "123as");
        this.mockMvc.perform(post("/teachers/login").params(requestParams))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.view().name("teacherLogin"))
                .andExpect(model().attributeExists("message"))
                .andReturn();
    }

    @Test
    public void givenTeachersRegisterURIWithPostWhenMockMVCThenRedirectIndex() throws Exception {
        TeacherDto teacherDto = TeacherDto.builder()
                .withEmail("email@gmail.com")
                .withPassword("12345")
                .withBirthDate(new Date())
                .build();
        this.mockMvc.perform(post("/teachers/register").flashAttr("teacher", teacherDto))
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andReturn();
    }

    @Test
    public void givenTeacherIdURIWhenMockMVCThenReturnsTeacherWithId() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("teacherProfile"))
                .andExpect(model().attributeExists("teacher"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenTeacherIdURIWhenMockMVCThenDoesntRedirect() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers/2"))
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andReturn();
    }
}
