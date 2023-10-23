package it.polito.se2.g04.officequeuemanagement;

import it.polito.se2.g04.officequeuemanagement.Services.Service;
import it.polito.se2.g04.officequeuemanagement.Services.ServiceRepository;
import it.polito.se2.g04.officequeuemanagement.Tickets.Ticket;
import it.polito.se2.g04.officequeuemanagement.Tickets.TicketRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TicketControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    private Service defaultService;

    // You can still mock your service if needed

    @BeforeAll
    public void setup() {
        defaultService=new Service("first service", Duration.ofSeconds(10));
        serviceRepository.save(defaultService);
    }


    @Test
    @Rollback
    public void testCreateTicket() throws Exception {
        //Test API Call

        //Test API Call
        mockMvc.perform(MockMvcRequestBuilders.get("/API/tickets/createTicket/{id}",defaultService.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.estimated_time").exists());
        mockMvc.perform(MockMvcRequestBuilders.get("/API/tickets/createTicket/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        mockMvc.perform(MockMvcRequestBuilders.get("/API/tickets/createTicket/a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}

