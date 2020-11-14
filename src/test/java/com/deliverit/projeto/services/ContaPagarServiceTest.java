package com.deliverit.projeto.services;

import com.deliverit.projeto.models.ContaPagar;
import com.deliverit.projeto.repositories.ContaPagarRepository;
import com.github.javafaker.Faker;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ContaPagarServiceTest {

    static Faker faker = new Faker(new Locale("pt-BR"));

    @InjectMocks
    ContaPagarService service;

    @Mock
    ContaPagarRepository repository;

    @Test
    @DisplayName("Buscar todas as contas")
    public void findAll(){
        ContaPagar conta = criarContaFaker();
        ContaPagar conta2 = criarContaFaker();

        List<ContaPagar> lista = Lists.newArrayList(conta, conta2);
        when( repository.findAll() )
                .thenReturn(lista);

        //execucao
        List<ContaPagar> result = service.findAll();

        //verificacoes
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).isEqualTo(lista);
    }

    @Test
    @DisplayName("Criar conta")
    public void criarConta() {
        ContaPagar conta = criarContaFaker();

        when(repository.save(conta)).thenReturn(
                conta
        );

        ContaPagar contaSalva = service.insert(conta);

        assertThat(contaSalva.getNome()).isNotNull();
        assertThat(contaSalva.getNome()).isEqualTo(conta.getNome());
    }

    private ContaPagar criarContaFaker() {
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

        return conta;
    }

}
