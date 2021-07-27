package org.foxminded.university.controller;

import junit.framework.Assert;
import org.foxminded.university.config.web.WebConfiguration;
import org.foxminded.university.dto.StudentDto;
import org.foxminded.university.entity.StudiesType;
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
public class StudentControllerMockMvcTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesStudentController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("studentController"));
    }

    @Test
    public void givenStudentsAllURIWhenMockMVCThenReturnsStudentsListViewNameAndContainsStudentsAttribute() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/students/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("studentsList"))
                .andExpect(model().attributeExists("students"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenStudentsPageableURIWhenMockMVCThenReturnsStudentsListViewNameAndContainsStudentsAttribute() throws Exception {
        MultiValueMap<String, String> requestParams= new LinkedMultiValueMap<>();
        requestParams.add("pageNumber", "1");
        requestParams.add("itemsPerPage", "2");
        MvcResult mvcResult =  this.mockMvc.perform(MockMvcRequestBuilders.get("/students/pageable").params(requestParams))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("studentsList"))
                .andExpect(model().attributeExists("students"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenStudentsUpdateIdURIWhenMockMVCThenReturnsStudentsWithId() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/students/update/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("updateStudent"))
                .andExpect(model().attributeExists("student"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenStudentsUpdateIdURIWhenMockMVCThenRedirectIndex() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/students/update/1"))
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andReturn();
    }

    @Test
    public void givenStudentsUpdateURIWithPostWhenMockMVCThenVerifyResponse() throws Exception {
        StudentDto studentDto = StudentDto.builder()
                .withId(1L)
                .withEmail("email@gmail.com")
                .withPassword("12345")
                .withBirthDate(new Date())
                .withStudiesType(StudiesType.FULL_TIME)
                .build();
        this.mockMvc.perform(post("/students/update/1").flashAttr("student", studentDto))
                .andDo(print())
                .andExpect(redirectedUrl("/students/all"))
                .andReturn();
    }

    @Test
    public void givenStudentsRegistrationURIWithGetWhenMockMVCThenReturnStudentRegistrationaForm() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/students/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("studentRegistration"))
                .andExpect(model().attributeExists("student"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenStudentsLoginURIWithGetWhenMockMVCThenReturnStudentLoginForm() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/students/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("studentLogin"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenStudentsDeleteURIWithGetWhenMockMVCThenRedirectStudentsAll() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/students/delete/6"))
                .andDo(print())
                .andExpect(redirectedUrl("/students/all"))
                .andReturn();
    }

    @Test
    public void givenStudentsloginURIWithPostWhenMockMVCThenRedirectIndex() throws Exception {
        MultiValueMap<String, String> requestParams= new LinkedMultiValueMap<>();
        requestParams.add("login", "wertuha3@gmail.com");
        requestParams.add("password", "1234");
        this.mockMvc.perform(post("/students/login").params(requestParams))
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andReturn();
    }

    @Test
    public void givenStudentsloginURIWithPostWhenMockMVCThenDontRedirect() throws Exception {
        MultiValueMap<String, String> requestParams= new LinkedMultiValueMap<>();
        requestParams.add("login", "ahdsl@gmail.com");
        requestParams.add("password", "123as");
        this.mockMvc.perform(post("/students/login").params(requestParams))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.view().name("studentLogin"))
                .andExpect(model().attributeExists("message"))
                .andReturn();
    }

    @Test
    public void givenStudentsRegisterURIWithPostWhenMockMVCThenRedirectIndex() throws Exception {
        StudentDto studentDto = StudentDto.builder()
                .withEmail("email@gmail.com")
                .withPassword("12345")
                .withBirthDate(new Date())
                .withStudiesType(StudiesType.FULL_TIME)
                .build();
        this.mockMvc.perform(post("/students/register").flashAttr("student", studentDto))
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andReturn();
    }

    @Test
    public void givenStudentIdURIWhenMockMVCThenReturnsStudentWithId() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/students/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("studentProfile"))
                .andExpect(model().attributeExists("student"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenStudentIdURIWhenMockMVCThenDoesntRedirect() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/students/1"))
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andReturn();
    }
}
