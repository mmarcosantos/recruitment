package com.tech.test.strategic.controller;

import com.tech.test.strategic.UserRegistrationStrategicApplication;
import com.tech.test.strategic.dto.Client;
import com.tech.test.strategic.service.ClientService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes = UserRegistrationStrategicApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();


    @Test
    void getClients() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Client[]> response = restTemplate.exchange(
                createURLWithPort("/api/v1/clients"),
                HttpMethod.GET, entity, Client[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Client client1 = Arrays.stream(response.getBody()).toList().get(0);

        assertEquals(1, client1.getId());
        assertEquals("John", client1.getName());
        assertEquals("Doe", client1.getLastName());
        assertEquals("john.doe@example.com", client1.getEmail());
        assertEquals("1234567890", client1.getPhone());

    }


    @Test
    void getClient() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Client> response = restTemplate.exchange(
                createURLWithPort("/api/v1/clients/1"),
                HttpMethod.GET, entity, Client.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Client client1 = response.getBody();

        assertEquals(1, client1.getId());
        assertEquals("John", client1.getName());
        assertEquals("Doe", client1.getLastName());
        assertEquals("john.doe@example.com", client1.getEmail());
        assertEquals("1234567890", client1.getPhone());
    }

    @Test
    void createClient() {
        Client client = new Client();
        client.setId(6L);
        client.setName("Mark");
        client.setLastName("Cuban");
        client.setEmail("mark.cuban@example.com");
        client.setPhone("987987987");

        HttpEntity<Client> entity = new HttpEntity<>(client, headers);
        ResponseEntity<Client> response = restTemplate.exchange(
                createURLWithPort("/api/v1/clients"),
                HttpMethod.POST, entity, Client.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Mark", response.getBody().getName());
        assertEquals("Cuban", response.getBody().getLastName());
        String actualHeader = response.getHeaders()
                .get(HttpHeaders.LOCATION)
                .get(0);

        assertTrue(actualHeader.contains("/api/v1/clients"));
        restTemplate.exchange(
                createURLWithPort("/api/v1/clients" + "/" + response.getBody().getId()),
                HttpMethod.DELETE, entity, Client.class);
    }

    @Test
    void updateClient() {
        Client client = new Client(null, "Mark", "Cuban", "mark.cuban@example.com", "987987987");
        HttpEntity<Client> entity = new HttpEntity<>(client, headers);
        ResponseEntity<Client> response = restTemplate.exchange(
                createURLWithPort("/api/v1/clients/2"),
                HttpMethod.PUT, entity, Client.class);
        assertEquals("Mark", response.getBody().getName());
        assertEquals("Cuban", response.getBody().getLastName());
    }

    @Test
    void createClientShouldReturnBadRequest() {
        Client client = new Client(1L, "Mark", "Cuban", "","");
        HttpEntity<Client> entity = new HttpEntity<>(client, headers);
        ResponseEntity<Client> response = restTemplate.exchange(
                createURLWithPort("/api/v1/clients"),
                HttpMethod.POST, entity, Client.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email is mandatory", response.getBody().getEmail());
        assertEquals("Phone is mandatory", response.getBody().getPhone());

    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
    
    @BeforeEach
    public void setUp() {
        Client client1 = new Client(1L,"John", "Doe", "john.doe@example.com", "1234567890");
        Client client2 = new Client(2L,"Jane", "Smith", "jane.smith@example.com", "9876543210");
          HttpEntity<Client> entity = new HttpEntity<>(client1, headers);
        ResponseEntity<Client> response = restTemplate.exchange(
                createURLWithPort("/api/v1/clients"),
                HttpMethod.POST, entity, Client.class);
        entity = new HttpEntity<>(client2, headers);
        restTemplate.exchange(
                createURLWithPort("/api/v1/clients"),
                HttpMethod.POST, entity, Client.class);
    }

}