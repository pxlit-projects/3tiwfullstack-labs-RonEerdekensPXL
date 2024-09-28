package be.pxl.microservices;

import be.pxl.microservices.api.dto.request.OrganizationRequest;
import be.pxl.microservices.api.dto.response.OrganizationResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithDepartmentsAndEmployeesResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithDepartmentsResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithEmployeesResponse;
import be.pxl.microservices.domain.Organization;
import be.pxl.microservices.repository.OrganizationRepository;
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
public class OrganisationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrganizationRepository organizationRepository;

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
        organizationRepository.deleteAll();
    }

    private OrganizationResponse mapToOrganizationResponse(Organization organization) {
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .build();
    }

    private OrganizationWithDepartmentsAndEmployeesResponse mapToOrganizationWithDepartmentsAndEmployees(Organization organization) {
        return OrganizationWithDepartmentsAndEmployeesResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .departments(organization.getDepartments())
                .employees(organization.getEmployees())
                .build();
    }
    private OrganizationWithDepartmentsResponse mapToOrganizationWithDepartments(Organization organization) {
        return OrganizationWithDepartmentsResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .departments(organization.getDepartments())
                .build();
    }
    private OrganizationWithEmployeesResponse mapToOrganizationWithEmployees(Organization organization) {
        return OrganizationWithEmployeesResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .employees(organization.getEmployees())
                .build();
    }

    @Test
    public void testGetAllOrganisations() throws Exception {
        Organization organization1 = Organization.builder()
                .name("PXL")
                .address("Hasselt")
                .build();
        Organization organization2 = Organization.builder()
                .name("UHasselt")
                .address("Hasselt")
                .build();

        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization1);
        organizations.add(organization2);
        organizationRepository.saveAll(organizations);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(1)));
    }

    @Test
    public void testFindOrganizationById() throws Exception {
        Organization organization = Organization.builder()
                .name("PXL")
                .address("Hasselt")
                .build();

        Organization organizationEntity = organizationRepository.save(organization);
        OrganizationResponse organizationResponse = mapToOrganizationResponse(organization);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + organizationEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(organizationResponse)));

    }

    @Test
    public void testFindOrganizationByIdShouldNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testFindOrganizationByIdWithDepartments() throws Exception {
        Organization organization = Organization.builder()
                .name("PXL")
                .address("Hasselt")
                .build();

        Organization organizationEntity = organizationRepository.save(organization);
        OrganizationWithDepartmentsResponse organizationResponse = mapToOrganizationWithDepartments(organization);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + organizationEntity.getId() + "/with-departments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(organizationResponse)));

    }
    @Test
    public void testFindOrganizationByIdWithDepartmentsShouldNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + 1 + "/with-departments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testFindOrganizationByIdWithDepartmentsAndEmployees() throws Exception {
        Organization organization = Organization.builder()
                .name("PXL")
                .address("Hasselt")
                .build();

        Organization organizationEntity = organizationRepository.save(organization);
        OrganizationWithDepartmentsAndEmployeesResponse organizationResponse = mapToOrganizationWithDepartmentsAndEmployees(organization);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + organizationEntity.getId() + "/with-departments-and-employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(organizationResponse)));

    }
    @Test
    public void testFindOrganizationByIdWithDepartmentsAndEmployeesShouldNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + 1 + "/with-departments-and-employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindOrganizationByIdWithEmployees() throws Exception {
        Organization organization = Organization.builder()
                .name("PXL")
                .address("Hasselt")
                .build();

        Organization organizationEntity = organizationRepository.save(organization);
        OrganizationWithEmployeesResponse organizationResponse = mapToOrganizationWithEmployees(organization);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + organizationEntity.getId() + "/with-employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(organizationResponse)));

    }
    @Test
    public void testFindOrganizationByIdWithEmployeesShouldNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + 1+ "/with-employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOrganization() throws Exception {
        OrganizationRequest organization = OrganizationRequest.builder()
                .name("PXL")
                .address("Hasselt")
                .build();

        String organizationString = objectMapper.writeValueAsString(organization);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/organization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(organizationString))
                .andExpect(status().isCreated());

        assertEquals(1,organizationRepository.findAll().size());
    }

    @Test
    public void testUpdateOrganization() throws Exception {
        Organization organization = Organization.builder()
                .name("PXL")
                .address("Hasselt")
                .build();

        Organization organizationEntity = organizationRepository.save(organization);

        OrganizationRequest organizationUpdated = OrganizationRequest.builder()
                .name("PXL2")
                .address("Hasselt2")
                .build();

        String organizationString = objectMapper.writeValueAsString(organizationUpdated);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/organization/" + organizationEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(organizationString))
                .andExpect(status().isOk());

        assertEquals("PXL2",organizationRepository.findById(organizationEntity.getId()).get().getName());
        assertEquals("Hasselt2",organizationRepository.findById(organizationEntity.getId()).get().getAddress());

    }

    @Test
    public void testUpdateOrganizationShouldNotFound() throws Exception {
        OrganizationRequest organizationUpdated = OrganizationRequest.builder()
                .name("PXL2")
                .address("Hasselt2")
                .build();

        String organizationString = objectMapper.writeValueAsString(organizationUpdated);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/organization/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(organizationString))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteOrganization() throws Exception {
        Organization organization = Organization.builder()
                .name("PXL")
                .address("Hasselt")
                .build();

        Organization organizationEntity = organizationRepository.save(organization);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/organization/" + organizationEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(0,organizationRepository.findAll().size());
    }
    @Test
    public void  testDeleteOrganizationShouldNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/organization/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
