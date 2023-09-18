package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
//@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void emailNotNull() {
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("mail", notNullValue())));
    }

    @Test
    @Transactional
    void findByMail() {

        Client client = new Client();
        client.setMail("test@example.com");
        clientRepository.save(client);

        Client foundClient = clientRepository.findByMail("test@example.com");
        assertEquals("test@example.com", foundClient.getMail());
    }
}
