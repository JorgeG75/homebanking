package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
@DataJpaTest
//@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CardRepositoryTest {
    @Autowired
    CardRepository cardRepository;

    @Test
    void findByNumber() {
        List<Card> accounts = cardRepository.findAll();
        assertThat(accounts, is(not(empty())));
    }

    @Test
    public void numberNotNull() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("number", notNullValue())));
    }
}