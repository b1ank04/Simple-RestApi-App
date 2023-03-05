package com.blank.testtask;

import com.blank.testtask.model.PersonDto;
import com.blank.testtask.service.PeopleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_table.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.yaml")
public class EndpointTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    PeopleService peopleService;

    @Test
    public void givenValidPersonId_whenGetPersonById_thenStatus200AndPersonReturned()
            throws Exception {
        long personId = 1L;
        PersonDto expectedPerson = new PersonDto("John", "Doe", 23);
        mvc.perform(MockMvcRequestBuilders.get("/api/getPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(personId)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(expectedPerson.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedPerson.getLastName())))
                .andExpect(jsonPath("$.age", is(expectedPerson.getAge())));
    }

    @Test
    public void givenNonExistentPersonId_whenGetPersonById_thenStatus204AndNoContentReturned()
            throws Exception {
        long nonExistentId = 999L;
        mvc.perform(MockMvcRequestBuilders.get("/api/getPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Long.toString(nonExistentId)))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Person with such ID isn't presented"));
    }
    @Test
    public void givenInvalidPersonId_whenGetPersonById_thenStatus400AndErrorMessageReturned()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/getPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf("invalid")))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Wrong format of provided ID"));
    }
}

