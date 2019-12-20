package ru.lanit.integration.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class CommonControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private String personName = "PersonName";
    private LocalDate birthDate = LocalDate.now().minusYears(19);

    private String personName2 = "PersonName2";
    private LocalDate birthDate2 = LocalDate.now().minusYears(20);

    private String model = "vw-Polo";
    private int horsepower = 101;

    private String model2 = "VW-Tiguan";
    private int horsepower2 = 150;

    private String model3 = "Lada-Granta";
    private int horsepower3 = 90;

    ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.mapper = Jackson2ObjectMapperBuilder.json().build();
    }
    @Test
    public void getStatistics() throws Exception {
        // Очищаем перед снятием статистики
        this.mockMvc.perform(get("/clear")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        prepare(1);

        this.mockMvc.perform(get("/statistics")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personcount").value(2))
                .andExpect(jsonPath("$.carcount").value(3))
                .andExpect(jsonPath("$.uniquevendorcount").value(2));
    }

    @Test
    public void clearAll() throws Exception {
        prepare(10);
        this.mockMvc.perform(get("/clear")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        // Проверяем, что подготовленные данные затерты
        this.mockMvc.perform(get("/personwithcars")
                .param("personid", String.valueOf(10)))
                .andExpect(status().isNotFound());
    }

    // Готовим данные (2 владельца, 3 автомобиля, 2 различных производителя)
    private void prepare(long id) throws Exception {
        PersonDto personRequestDto = new PersonDto(id, personName, birthDate);
        PersonDto personRequestDto2 = new PersonDto(id + 1, personName2, birthDate2);

        this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto2))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        CarDto carRequestDto = new CarDto(id, model, horsepower, id);
        CarDto carRequestDto2 = new CarDto(id + 1, model2, horsepower2, id);
        CarDto carRequestDto3 = new CarDto(id + 2, model3, horsepower3, id + 1);

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto2))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto3))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}