package ru.lanit.integration.mock;

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

    private long personId = 100;
    private String personName = "PersonName";
    private LocalDate birthDate = LocalDate.now().minusYears(19);

    private long carId = 1;
    private String model = "VW-Polo";
    private int horsepower = 101;

    ObjectMapper mapper;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.mapper = Jackson2ObjectMapperBuilder.json().build();
    }

    @Test
    public void createCar_thenResponseOK() throws Exception {
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

    @Test
    public void createCar_whenIdNull_thenResponseFail() throws Exception {
        PersonDto personRequestDto = new PersonDto(
                personId + 1,
                personName,
                birthDate
        );

        this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        CarDto carRequestDto = new CarDto();
        carRequestDto.setModel(model);
        carRequestDto.setHorsepower(horsepower);
        carRequestDto.setOwnerId(personId + 1);

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCar_whenModelNull_thenResponseFail() throws Exception {
        PersonDto personRequestDto = new PersonDto(
                personId + 2,
                personName,
                birthDate
        );

        this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        CarDto carRequestDto = new CarDto();
        carRequestDto.setId(carId + 2);
        carRequestDto.setHorsepower(horsepower);
        carRequestDto.setOwnerId(personId + 2);

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCar_whenHorsepowerNull_thenResponseFail() throws Exception {
        PersonDto personRequestDto = new PersonDto(
                personId + 3,
                personName,
                birthDate
        );

        this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        CarDto carRequestDto = new CarDto();
        carRequestDto.setId(carId + 3);
        carRequestDto.setModel(model);
        carRequestDto.setOwnerId(personId + 3);

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCar_whenOwnerIdNull_thenResponseFail() throws Exception {
        PersonDto personRequestDto = new PersonDto(
                personId + 4,
                personName,
                birthDate
        );

        this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        CarDto carRequestDto = new CarDto();
        carRequestDto.setId(carId + 4);
        carRequestDto.setModel(model);
        carRequestDto.setHorsepower(horsepower);

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCar_whenHorsePowerNegative_thenResponseFail() throws Exception {
        PersonDto personRequestDto = new PersonDto(
                personId + 5,
                personName,
                birthDate
        );

        this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        CarDto carRequestDto = new CarDto();
        carRequestDto.setId(carId + 5);
        carRequestDto.setModel(model);
        carRequestDto.setHorsepower(horsepower * (-1));
        carRequestDto.setOwnerId(personId + 5);

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCar_carNotUnique_thenResponseFail() throws Exception {
        PersonDto personRequestDto = new PersonDto(
                personId + 6,
                personName,
                birthDate
        );

        this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        CarDto carRequestDto = new CarDto();
        carRequestDto.setId(carId + 6);
        carRequestDto.setModel(model);
        carRequestDto.setHorsepower(horsepower);
        carRequestDto.setOwnerId(personId + 6);

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCar_ownerUnder18_thenResponseFail() throws Exception {
        PersonDto personRequestDto = new PersonDto(
                personId + 7,
                personName,
                birthDate.plusYears(10)
        );

        this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        CarDto carRequestDto = new CarDto();
        carRequestDto.setId(carId + 7);
        carRequestDto.setModel(model);
        carRequestDto.setHorsepower(horsepower);
        carRequestDto.setOwnerId(personId + 7);

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCar_modelIncorrectFormat_thenResponseFail() throws Exception {
        PersonDto personRequestDto = new PersonDto(
                personId + 8,
                personName,
                birthDate
        );

        this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        CarDto carRequestDto = new CarDto();
        carRequestDto.setId(carId + 8);
        carRequestDto.setModel("incorrectModel");
        carRequestDto.setHorsepower(horsepower);
        carRequestDto.setOwnerId(personId + 8);

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createCar_modelCorrectFormat_thenResponseOK() throws Exception {
        PersonDto personRequestDto = new PersonDto(
                personId + 9,
                personName,
                birthDate
        );

        this.mockMvc.perform(post("/person")
                .content(mapper.writeValueAsString(personRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        CarDto carRequestDto = new CarDto();
        carRequestDto.setId(carId + 9);
        carRequestDto.setModel(model + "-sedan");
        carRequestDto.setHorsepower(horsepower);
        carRequestDto.setOwnerId(personId + 9);

        this.mockMvc.perform(post("/car")
                .content(mapper.writeValueAsString(carRequestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}