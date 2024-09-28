package be.pxl.microservices;

import be.pxl.microservices.domain.Notification;
import be.pxl.microservices.repository.NotificationRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class NotificationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificationRepository notificationRepository;

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
        notificationRepository.deleteAll();
    }

    @Test
    public void testGetAllNotifications() throws Exception {
        Notification notification1 = Notification.builder()
                .from("Organization")
                .to("Department")
                .subject("Update department")
                .message("json")
                .build();

        Notification notification2 = Notification.builder()
                .from("Organization2")
                .to("Department2")
                .subject("Update department2")
                .message("json2")
                .build();

        notificationRepository.save(notification1);
        notificationRepository.save(notification2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/notification")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(1)));
    }

    @Test
    public void testGetNotificationById() throws Exception {
        Notification notification = Notification.builder()
                .from("Organization")
                .to("Department")
                .subject("Update department")
                .message("json")
                .build();

        Notification notificationEntity = notificationRepository.save(notification);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/notification/" + notificationEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(Matchers.greaterThan(0)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(notificationEntity)));

    }

    @Test
    public void testCreateNotification() throws Exception {
        Notification notification = Notification.builder()
                .from("Organization")
                .to("Department")
                .subject("Update department")
                .message("json")
                .build();

        String notificationString = objectMapper.writeValueAsString(notification);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(notificationString))
                .andExpect(status().isCreated());

        assertEquals(1,notificationRepository.findAll().size());
    }

    @Test
    public void testUpdateNotification() throws Exception {
        Notification notification = Notification.builder()
                .from("Organization")
                .to("Department")
                .subject("Update department")
                .message("json")
                .build();

        Notification notificationEntity = notificationRepository.save(notification);

        Notification notificationUpdated = Notification.builder()
                .from("Organization2")
                .to("Department2")
                .subject("Update department2")
                .message("json2")
                .build();

        String notificationUpdatedString = objectMapper.writeValueAsString(notificationUpdated);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/notification/" + notificationEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(notificationUpdatedString))
                .andExpect(status().isOk());

        assertEquals("Organization2",notificationRepository.findById(notificationEntity.getId())
                .get().getFrom());
        assertEquals("Department2",notificationRepository.findById(notificationEntity.getId())
                .get().getTo());
        assertEquals("Update department2",notificationRepository.findById(notificationEntity.getId())
                .get().getSubject());
        assertEquals("json2",notificationRepository.findById(notificationEntity.getId())
                .get().getMessage());

    }

    @Test
    public void testDeleteNotification() throws Exception {
        Notification notification = Notification.builder()
                .from("Organization")
                .to("Department")
                .subject("Update department")
                .message("json")
                .build();

        Notification notificationEntity = notificationRepository.save(notification);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/notification/" + notificationEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(0,notificationRepository.findAll().size());
    }
}
