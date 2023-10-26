package it.polito.se2.g04.officequeuemanagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.polito.se2.g04.officequeuemanagement.Counters.Counter;
import it.polito.se2.g04.officequeuemanagement.Counters.CounterRepository;
import it.polito.se2.g04.officequeuemanagement.Services.Service;
import it.polito.se2.g04.officequeuemanagement.Services.ServiceRepository;
import it.polito.se2.g04.officequeuemanagement.Tickets.Ticket;
import it.polito.se2.g04.officequeuemanagement.Tickets.TicketRepository;
import org.json.JSONArray;
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
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CostumerTurnTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private CounterRepository counterRepository;
    private List<Ticket> initialTickets;

    private Counter counter1;
    private Counter counter2;
    private Counter counter3;

    // You can still mock your service if needed

    @BeforeAll
    public void setup() {
        // create and save services
        Service service1 = new Service("first service", Duration.ofSeconds(10));
        serviceRepository.save(service1);
        Service service2 = new Service("second service", Duration.ofSeconds(5));
        serviceRepository.save(service2);
        Service service3 = new Service("third service", Duration.ofSeconds(150));
        serviceRepository.save(service3);

        // create and save new tickets
        initialTickets = new ArrayList<>();
        initialTickets.add(new Ticket(service1));
        initialTickets.add(new Ticket(service2));
        initialTickets.add(new Ticket(service1));
        initialTickets.add(new Ticket(service1));
        initialTickets.add(new Ticket(service1));
        initialTickets.add(new Ticket(service2));
        initialTickets.add(new Ticket(service3));
        initialTickets.add(new Ticket(service2));
        ticketRepository.saveAll(initialTickets);

        // initialize counters and set served service types
        ArrayList<Service> servicesCounter = new ArrayList<>();
        servicesCounter.add(service1);
        counter1 = new Counter();
        counter1.setAssociated_services(servicesCounter);
        counterRepository.save(counter1);

        servicesCounter.add(service2);
        counter2 = new Counter();
        counter2.setAssociated_services(servicesCounter);
        counterRepository.save(counter2);

        servicesCounter.add(service3);
        counter3 = new Counter();
        counter3.setAssociated_services(servicesCounter);
        counterRepository.save(counter3);
    }
    @AfterAll
    public void CleanUo(){
        ticketRepository.deleteAll();
        counterRepository.deleteAll();
        serviceRepository.deleteAll();
    }




    @Test
    @DisplayName("Should get 0 tickets")
    @Rollback
    public void getAllServingTicketsEmpty() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/API/tickets/ticketsServing")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String json = res.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Ticket[] tickets = mapper.readValue(json, Ticket[].class);
        assertEquals(0, tickets.length, "getAll should just return 0 values");
        //Assert.isTrue(Arrays.stream(tickets).toList().containsAll(initialTickets),"getAll does not return the correct values");
    }

    @Test
    @Rollback
    @DisplayName("Should get correct amount of tickets tickets, with correct service types")
    public void getAllServingTicketsNotEmpty() throws Exception {

        initialTickets.get(0).setCounter(counter2);
        initialTickets.get(0).setServed_timestamp(new Timestamp(1000));
        initialTickets.get(1).setCounter(counter2);
        initialTickets.get(1).setServed_timestamp(new Timestamp(1200));


        ticketRepository.saveAll(initialTickets);

        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/API/tickets/ticketsServing")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String json = res.getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(json);
        assertEquals(1, jsonArray.length(), "getAll should just return 1 value");
        assertEquals("" + initialTickets.get(1).getId(), jsonArray.getJSONObject(0).getString("ticket_id"));
        assert initialTickets.get(1).getCounter() != null;
        assertEquals("" + initialTickets.get(1).getCounter().getId(), jsonArray.getJSONObject(0).getString("counter_id"));

        initialTickets.get(3).setCounter(counter2);
        initialTickets.get(3).setServed_timestamp(new Timestamp(1500));
        initialTickets.get(4).setCounter(counter1);
        initialTickets.get(4).setServed_timestamp(new Timestamp(1800));
        initialTickets.get(5).setCounter(counter2);
        initialTickets.get(5).setServed_timestamp(new Timestamp(1900));
        initialTickets.get(6).setCounter(counter3);
        initialTickets.get(6).setServed_timestamp(new Timestamp(1900));

        ticketRepository.saveAll(initialTickets);

        res = mockMvc.perform(MockMvcRequestBuilders.get("/API/tickets/ticketsServing")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        json = res.getResponse().getContentAsString();
        jsonArray = new JSONArray(json);

        assertEquals(3, jsonArray.length(), "getAll should return 3 values, one for each counter. Instead " + jsonArray.length() + "tickets returned.");

        String ticketId;
        String counterId;
        for (int i = 0; i < jsonArray.length(); i++) {
            ticketId = jsonArray.getJSONObject(i).getString("ticket_id");
            counterId = jsonArray.getJSONObject(i).getString("counter_id");
            assertTrue(Arrays.asList("" + initialTickets.get(6).getId(), "" + initialTickets.get(5).getId(), "" + initialTickets.get(4).getId()).contains(ticketId));

            if (("" + initialTickets.get(6).getId()).equals(ticketId))
                assertEquals(initialTickets.get(6).getCounter().getId() + "", counterId);
            else if (("" + initialTickets.get(5).getId()).equals(ticketId))
                assertEquals(initialTickets.get(5).getCounter().getId() + "", counterId);
            else if (("" + initialTickets.get(4).getId()).equals(ticketId))
                assertEquals(initialTickets.get(4).getCounter().getId() + "", counterId);

        }


    }

}
