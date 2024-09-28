package be.pxl.microservices;

import be.pxl.microservices.domain.Employee;
import be.pxl.microservices.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.xmlunit.util.Mapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class EmployeeTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Container
    private static final MySQLContainer sglContainer = new MySQLContainer(
            "mysql:5.7.37"
    );

    @DynamicPropertySource
    static void registerMySqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sglContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sglContainer::getUsername);
        registry.add("spring.datasource.password", sglContainer::getPassword);
    }

    @AfterEach
    public void afterEachtTest() {
        employeeRepository.deleteAll();
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        Employee employee1 = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .organizationId(1L)
                .departmentId(1L)
                .build();
        Employee employee2 = Employee.builder()
                .age(25)
                .name("Tine")
                .position("student")
                .organizationId(1L)
                .departmentId(1L)
                .build();
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employeeRepository.saveAll(employees);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(1)));

    }
    @Test
    public void testGetEmployeeById() throws Exception {
        Employee employee = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .organizationId(1L)
                .departmentId(1L)
                .build();


        Employee emplEntity = employeeRepository.save(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/" + emplEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(emplEntity)));

    }

    @Test
    public void testGetEmployeeByOrganizationId() throws Exception {
        Employee employee = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .organizationId(1L)
                .departmentId(1L)
                .build();

        Employee emplEntity = employeeRepository.save(employee);

        List<Employee> employeesResult = new ArrayList<>();
        employeesResult.add(emplEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/organization/" + emplEntity.getOrganizationId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(employeesResult)));
    }
    @Test
    public void testGetEmployeeByDepartmentId() throws Exception {
        Employee employee = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .organizationId(1L)
                .departmentId(1L)
                .build();

        Employee emplEntity = employeeRepository.save(employee);

        List<Employee> employeesResult = new ArrayList<>();
        employeesResult.add(emplEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department/" + emplEntity.getDepartmentId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(employeesResult)));
    }

    @Test
    public void testCreateEmployee() throws Exception {
        Employee employee = Employee.builder()
                .age(34)
                .name("Jos")
                .position("CEO")
                .organizationId(1L)
                .departmentId(1L)
                .build();

        String employeeString = objectMapper.writeValueAsString(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeString))
                .andExpect(status().isCreated());

        assertEquals(1,employeeRepository.findAll().size());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Employee employee = Employee.builder()
                .age(34)
                .name("Jos")
                .position("CEO")
                .organizationId(1L)
                .departmentId(1L)
                .build();

        Employee emplEntity = employeeRepository.save(employee);

        Employee employeeUpdated = Employee.builder()
                .age(35)
                .name("Jan")
                .position("student")
                .organizationId(2L)
                .departmentId(2L)
                .build();

        String employeeString = objectMapper.writeValueAsString(employeeUpdated);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/employee/" + emplEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeString))
                .andExpect(status().isOk());

        assertEquals(35,employeeRepository.findById(emplEntity.getId()).get().getAge());
        assertEquals("Jan",employeeRepository.findById(emplEntity.getId()).get().getName());
        assertEquals("student",employeeRepository.findById(emplEntity.getId()).get().getPosition());
        assertEquals(2L,employeeRepository.findById(emplEntity.getId()).get().getOrganizationId());
        assertEquals(2L,employeeRepository.findById(emplEntity.getId()).get().getDepartmentId());
    }
}
