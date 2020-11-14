package com.deliverit.projeto.repositories;

import com.deliverit.projeto.models.ContaPagar;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ContaPagarRepositoryTest {

    @Autowired
    ContaPagarRepository repository;

    @Autowired
    protected TestEntityManager entityManager;

    static Faker faker = new Faker(new Locale("pt-BR"));

    @Test
    @DisplayName("Criar uma conta")
    public void criarConta(){
        ContaPagar conta = criarConta(false);

        ContaPagar contaSalva = repository.save(conta);

        assertThat(contaSalva.getId()).isNotNull();
    }

   @Test
    @DisplayName("Buscar contas a pagar")
    public void findAll(){
       criarConta(true);
       criarConta(true);

        List<ContaPagar> lista = repository.findAll();

        assertThat(lista.size()).isEqualTo(2);
    }

   private ContaPagar criarConta(boolean persisted) {
        ContaPagar conta = ContaPagar.builder()
                .nome(faker.book().title())
                .valor(BigDecimal.valueOf(faker.number().randomDouble(2,1, 1000)))
                .dataVencimento(new Date())
                .dataPagamento(new Date())
                .valorCorrigido(BigDecimal.valueOf(faker.number().randomDouble(2,1, 1000)))
                .diasAtraso(0)
                .jurosDia(BigDecimal.ZERO)
                .multa(BigDecimal.ZERO)
                .build();

        if(persisted)
            entityManager.persist(conta);

        return conta;
    }


}
