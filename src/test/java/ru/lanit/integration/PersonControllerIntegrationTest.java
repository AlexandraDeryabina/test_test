package ru.lanit.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.PropertyValueException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import ru.lanit.config.AppConfig;
import ru.lanit.dto.request.PersonDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class PersonControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private long personId = 1;
    private String personName = "PersonName";
    private LocalDate birthDate = LocalDate.now().minusYears(19);

    ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.mapper = Jackson2ObjectMapperBuilder.json().build();
    }

    @Test
    public void createPerson_whenMockMVC_thenResponseOK() throws Exception {
        PersonDto personRequestDto = new PersonDto(
                personId,
                personName,
                birthDate
        );

        ResultActions resultActions = this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions.andExpect(status().isOk());

        //Проверяем, что такая запись, действительно, есть в БД
        this.mockMvc.perform(get("/personwithcars")
                .param("personid", String.valueOf(personId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(personId))
                .andExpect(jsonPath("$.name").value(personName))
                .andExpect(jsonPath("$.birthdate")
                        .value(birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
    }

    @Test
    public void createPerson_whenFutureBirthDate_thenResponseFail() throws Exception {
        PersonDto personRequestDto = new PersonDto(
                personId,
                personName,
                birthDate.plusYears(100)
        );

        ResultActions resultActions = this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions.andExpect(status().isBadRequest());

        //Проверяем, что такая запись, действительно, НЕ записана в БД
        this.mockMvc.perform(get("/personwithcars")
                .param("personid", String.valueOf(personId)))
                .andExpect(status().isNotFound());
    }

//    @Test
//    public void getPersonWithCars() {
//        PersonDto personRequestDto = new PersonDto(
//                personId,
//                personName,
//                birthDate
//        );
//    }
}