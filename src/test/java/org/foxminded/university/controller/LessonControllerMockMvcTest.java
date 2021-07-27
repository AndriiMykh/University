package org.foxminded.university.controller;

import junit.framework.Assert;
import org.foxminded.university.config.web.WebConfiguration;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WebConfiguration.class })
@WebAppConfiguration
public class LessonControllerMockMvcTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesLessonController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("lessonController"));
    }

    @Test
    public void givenLessonsAllURIWhenMockMVCThenReturnsLessonsListViewNameAndContainsGroupsAttribute() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/lessons/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("lessonsList"))
                .andExpect(model().attributeExists("lessons"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenLessonsPageableURIWhenMockMVCThenReturnsLessonsListViewNameAndContainsLessonsAttribute() throws Exception {
        MultiValueMap<String, String> requestParams= new LinkedMultiValueMap<>();
        requestParams.add("pageNumber", "1");
        requestParams.add("itemsPerPage", "2");
        MvcResult mvcResult =  this.mockMvc.perform(MockMvcRequestBuilders.get("/lessons/pageable").params(requestParams))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("lessonsList"))
                .andExpect(model().attributeExists("lessons"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenLessonsIdURIWhenMockMVCThenReturnsLessonsWithId() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/lessons/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("lessonProfile"))
                .andExpect(model().attributeExists("lesson"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenLessonsUpdateIdURIWhenMockMVCThenReturnsLessonWithId() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/lessons/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("updateLesson"))
                .andExpect(model().attributeExists("lesson"))
                .andReturn();
        assertEquals("text/html;charset=ISO-8859-1", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenLessonsUpdateIdThatDoesntExistURIWhenMockMVCThenRedirect() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/lessons/update/11154867611"))
                .andDo(print())
                .andExpect(redirectedUrl("/"))
                .andReturn();
    }

    @Test
    public void givenLessonsUpdateURIWithPostWhenMockMVCThenVerifyResponse() throws Exception {
        this.mockMvc.perform(post("/lessons/update/1"))
                .andDo(print())
                .andExpect(redirectedUrl("/lessons/all"))
                .andReturn();
    }
}
