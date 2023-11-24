package it.polito.se2.g04.officequeuemanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polito.se2.g04.officequeuemanagement.Counters.Counter;
import it.polito.se2.g04.officequeuemanagement.Counters.CounterRepository;
import it.polito.se2.g04.officequeuemanagement.Services.Service;
import it.polito.se2.g04.officequeuemanagement.Services.ServiceRepository;

import it.polito.se2.g04.officequeuemanagement.Tickets.TicketDTO;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.UUID;


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
    private ServiceRepository serviceRepository;
    @Autowired
    private CounterRepository counterRepository;
    private Service defaultService;
    private Counter defaultCounter;

    // You can still mock your service if needed

    @BeforeAll
    public void setup() {
        defaultService=new Service("first service", Duration.ofSeconds(10));
        defaultCounter = new Counter("first counter", List.of(defaultService));

        serviceRepository.save(defaultService);
        counterRepository.save(defaultCounter);
    }


    @Test
    @Rollback
    public void testCreateTicket() throws Exception {



        //Test API Call
        mockMvc.perform(MockMvcRequestBuilders.post("/API/tickets/createTicket/{id}",defaultService.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.estimated_time").exists());
        mockMvc.perform(MockMvcRequestBuilders.post("/API/tickets/createTicket/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/API/tickets/createTicket/a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/API/tickets/createTicket/"+ UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Rollback
    public void testGetNextTicket() throws Exception {
        //POPULATE COUNTER / SERVICES
        //ADDED A SECOND SERVICE
        Service service2 = new Service("second service", Duration.ofSeconds(5));
        //COUNTER 1 ADDED
        Counter test_counter = new Counter("Counter1", List.of(defaultService,service2));
        serviceRepository.save(service2);
        counterRepository.save(test_counter);
        //CREATE 2 TICKET FOR SERVICE 1 AND 1 TICKET FOR SERVICE 2
        MvcResult res=mockMvc.perform(MockMvcRequestBuilders.post("/API/tickets/createTicket/{id}",defaultService.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post("/API/tickets/createTicket/{id}",defaultService.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        MvcResult res2=mockMvc.perform(MockMvcRequestBuilders.post("/API/tickets/createTicket/{id}",service2.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        
        //PARSE RESULTS
        JSONObject jsonResponse = new JSONObject(res.getResponse().getContentAsString());
        Long id_1 = jsonResponse.getLong("id");
        JSONObject jsonResponse2 = new JSONObject(res2.getResponse().getContentAsString());
        Long id_2 = jsonResponse2.getLong("id");
        System.out.println("/API/tickets/serveNextTicket/"+test_counter.getId().toString());

        //Test API Call
        mockMvc.perform(MockMvcRequestBuilders.get("/API/tickets/serveNextTicket/"+test_counter.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticket_id").value(id_1));;

        mockMvc.perform(MockMvcRequestBuilders.get("/API/tickets/serveNextTicket/{counterId}",test_counter.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticket_id").value(id_2));

        mockMvc.perform(MockMvcRequestBuilders.get("/API/tickets/serveNextTicket/a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.get("/API/tickets/serveNextTicket/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        mockMvc.perform(MockMvcRequestBuilders.get("/API/tickets/serveNextTicket/"+ UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @Rollback
    public void testWaitingTime() throws Exception {
        Service s1 = new Service("S1", Duration.ofSeconds(60));
        Service s2 = new Service("S2", Duration.ofSeconds(120));
        Counter c1 = new Counter("C1", List.of(s1, s2));
        Counter c2 = new Counter("C2", List.of(s1));
        serviceRepository.saveAll(List.of(s1, s2));
        counterRepository.saveAll(List.of(c1, c2));

        mockMvc.perform(MockMvcRequestBuilders.post("/API/tickets/createTicket/{id}",s1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.estimated_time").value(Duration.ofSeconds(30).toString()));

        mockMvc.perform(MockMvcRequestBuilders.post("/API/tickets/createTicket/{id}",s1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.estimated_time").value(Duration.ofSeconds(70).toString()));

        mockMvc.perform(MockMvcRequestBuilders.post("/API/tickets/createTicket/{id}",s2.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.estimated_time").value(Duration.ofSeconds(60).toString()));
    }

    @Test
    @Rollback
    public void testWaitingTimeWithNoCounter() throws Exception {
        Service s1 = new Service("S1", Duration.ofSeconds(60));
        serviceRepository.save(s1);

        mockMvc.perform(MockMvcRequestBuilders.post("/API/tickets/createTicket/{id}",s1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

