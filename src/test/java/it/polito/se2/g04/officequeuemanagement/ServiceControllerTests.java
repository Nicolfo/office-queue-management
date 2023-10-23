package it.polito.se2.g04.officequeuemanagement;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.polito.se2.g04.officequeuemanagement.Services.Service;
import it.polito.se2.g04.officequeuemanagement.Services.ServiceRepository;
import it.polito.se2.g04.officequeuemanagement.Tickets.Ticket;
import it.polito.se2.g04.officequeuemanagement.Tickets.TicketRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ServiceControllerTests{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    private List<Service> defaultServices;

    // You can still mock your service if needed

    @BeforeAll
    public void setup() {
        defaultServices=new ArrayList<>();
        defaultServices.add(new Service("first service", Duration.ofSeconds(10)));
        defaultServices.add(new Service("second service", Duration.ofSeconds(10)));
        defaultServices.forEach( it->serviceRepository.save(it));
    }



    @Test
    @DisplayName("Should get 2 services, with correct values")
    public void getAllServices() throws Exception {
        MvcResult res=mockMvc.perform(MockMvcRequestBuilders.get("/API/services/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String json = res.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Service[] services = mapper.readValue(json, Service[].class);
        assertEquals(2,services.length,"getAll should just return 2 values");
        Assert.isTrue(Arrays.stream(services).toList().containsAll(defaultServices),"getAll does not return the correct values");
    }
}

