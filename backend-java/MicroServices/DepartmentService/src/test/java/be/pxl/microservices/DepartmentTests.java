package be.pxl.microservices;

import be.pxl.microservices.api.dto.response.DepartmentResponse;
import be.pxl.microservices.api.dto.response.DepartmentWithEmployeesResponse;
import be.pxl.microservices.domain.Department;
import be.pxl.microservices.repository.DepartmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class DepartmentTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentRepository departmentRepository;

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
        departmentRepository.deleteAll();
    }

    private DepartmentResponse mapToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .position(department.getPosition())
                .build();
    }
    private DepartmentWithEmployeesResponse mapToDepartmentWithEmployeesResponse(Department department) {
        return DepartmentWithEmployeesResponse.builder()
                .id(department.getId())
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .position(department.getPosition())
                .employees(department.getEmployees())
                .build();
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        Department department1 = Department.builder()
                .name("school")
                .organizationId(2L)
                .position("Test")
                .build();
        Department department2 = Department.builder()
                .name("bedrijf")
                .organizationId(3L)
                .position("Test2")
                .build();

        List<Department> departments = new ArrayList<>();
        departments.add(department1);
        departments.add(department2);

        departmentRepository.saveAll(departments);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(1)));

    }

    @Test
    public void testGetDepartmentById() throws Exception {
        Department department = Department.builder()
                .name("school")
                .organizationId(2L)
                .position("Test")
                .build();

        Department departmentEntity = departmentRepository.save(department);
        DepartmentResponse departmentResponse = mapToDepartmentResponse(departmentEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/" + departmentEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(departmentResponse)));
    }

    @Test
    public void testFindDepartmentByOrganizationId() throws Exception {
        Department department = Department.builder()
                .name("school")
                .organizationId(2L)
                .position("Test")
                .build();

        Department departmentEntity = departmentRepository.save(department);
        DepartmentResponse departmentResponse = mapToDepartmentResponse(departmentEntity);

        List<DepartmentResponse> departmentResponseList = new ArrayList<>();
        departmentResponseList.add(departmentResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/" + departmentEntity.getOrganizationId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(departmentResponseList)));
    }

    @Test
    public void testFindDepartmentByOrganizationIdWithEmployees() throws Exception {
        Department department = Department.builder()
                .name("school")
                .organizationId(2L)
                .position("Test")
                .build();

        Department departmentEntity = departmentRepository.save(department);
        DepartmentWithEmployeesResponse departmentResponse = mapToDepartmentWithEmployeesResponse(departmentEntity);

        List<DepartmentWithEmployeesResponse> departmentResponseList = new ArrayList<>();
        departmentResponseList.add(departmentResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/" + departmentEntity.getOrganizationId()
                        + "/with-employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(departmentResponseList)));
    }

    @Test
    public void testCreateDepartment() throws Exception {
        Department department = Department.builder()
                .name("school")
                .organizationId(2L)
                .position("Test")
                .build();

        String departmentString = objectMapper.writeValueAsString(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentString))
                .andExpect(status().isCreated());

        assertEquals(1,departmentRepository.findAll().size());
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        Department department = Department.builder()
                .name("school")
                .organizationId(2L)
                .position("Test")
                .build();

        Department departmentEntity = departmentRepository.save(department);

        Department departmentUpdated = Department.builder()
                .name("school2")
                .organizationId(3L)
                .position("Test2")
                .build();

        String departmentUpdatedString = objectMapper.writeValueAsString(departmentUpdated);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/department/" + departmentEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentUpdatedString))
                .andExpect(status().isOk());

        assertEquals("school2", departmentRepository.findById(departmentEntity.getId()).get().getName());
        assertEquals(3L, departmentRepository.findById(departmentEntity.getId()).get().getOrganizationId());
        assertEquals("Test2", departmentRepository.findById(departmentEntity.getId()).get().getPosition());
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        Department department = Department.builder()
                .name("school")
                .organizationId(2L)
                .position("Test")
                .build();

        Department departmentEntity = departmentRepository.save(department);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/department/" + departmentEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(0,departmentRepository.findAll().size());
    }
}
