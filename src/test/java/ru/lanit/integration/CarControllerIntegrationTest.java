package ru.lanit.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.lanit.config.AppConfig;
import ru.lanit.dto.request.CarDto;
import ru.lanit.dto.request.PersonDto;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class CarControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private long personId = 2;
    private String personName = "PersonName";
    private LocalDate birthDate = LocalDate.now().minusYears(19);

    private long carId = 1;
    private String model = "VW-Polo";
    private int horsepower = 101;

    ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.mapper = Jackson2ObjectMapperBuilder.json().build();
    }

    @Test
    public void createCar_whenMockMVC_thenResponseOK() throws Exception {
        PersonDto personRequestDto = new PersonDto(
                personId,
                personName,
                birthDate
        );

        this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        CarDto carRequestDto = new CarDto(
                carId,
                model,
                horsepower,
                personId
        );

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        //Проверяем, что такая запись, действительно, есть в БД
        this.mockMvc.perform(get("/personwithcars")
                .param("personid", String.valueOf(personId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(personId))
                .andExpect(jsonPath("$.cars.size()").value(1))
                .andExpect(jsonPath("$.cars[0].id").value(carId))
                .andExpect(jsonPath("$.cars[0].model").value(model))
                .andExpect(jsonPath("$.cars[0].horsepower").value(horsepower))
                .andExpect(jsonPath("$.cars[0].ownerId").value(personId));
    }
}