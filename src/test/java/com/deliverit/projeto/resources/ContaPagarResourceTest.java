package com.deliverit.projeto.resources;

import com.deliverit.projeto.dto.ContaPagarDTO;
import com.deliverit.projeto.models.ContaPagar;
import com.deliverit.projeto.repositories.ContaPagarRepository;
import com.deliverit.projeto.services.ContaPagarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = ContaPagarResource.class)
@AutoConfigureMockMvc(addFilters = false)
public class ContaPagarResourceTest {

    static String CONTAPAGAR_URL = "/api/conta_pagar";

    @MockBean
    ContaPagarService service;

    @Autowired
    protected MockMvc mvc;

    static Faker faker = new Faker(new Locale("pt-BR"));

    @Test
    @DisplayName("Inserir novaConta")
    public void inserirConta() throws Exception {
        ContaPagar conta = criarContaFaker();
        ContaPagarDTO contaDTO = ContaPagarDTO.builder()
                .nome(conta.getNome())
                .valor(conta.getValor())
                .dataVencimento(conta.getDataVencimento())
                .dataPagamento(conta.getDataPagamento())
                .valorCorrigido(conta.getValorCorrigido())
                .build();

        BDDMockito.given(service.insert(Mockito.any(ContaPagar.class))).willReturn(conta);
        BDDMockito.given(service.fromDTO(Mockito.any(ContaPagar.class), Mockito.any(ContaPagarDTO.class))).willReturn(conta);
        BDDMockito.given(service.toDTO(Mockito.any(ContaPagar.class))).willReturn(contaDTO);

        String json = getStringValueJsonFromObject(contaDTO);
        MockHttpServletRequestBuilder request = getMockHttpRequestBuilderPOST(CONTAPAGAR_URL, json);

        mvc.perform(request)
                .andExpect( status().isCreated() )
                .andExpect( jsonPath("nome").isNotEmpty())
                .andExpect( jsonPath("nome").value(contaDTO.getNome()));
    }

   @Test
    @DisplayName("Buscar todas as contas")
    public void findAll() throws Exception{
        ContaPagar conta = criarContaFaker();
        ContaPagar conta2 = criarContaFaker();

        BDDMockito.given( service.findAll() )
                .willReturn( Lists.newArrayList(conta, conta2) );

        MockHttpServletRequestBuilder request = getMockHttpRequestBuilderGET(CONTAPAGAR_URL);

        mvc.perform(request)
                .andExpect( status().is2xxSuccessful())
                .andExpect( jsonPath("$.*", Matchers.hasSize(2)));
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

    protected <T> String getStringValueJsonFromObject(T object) throws Exception {
        return new ObjectMapper().writeValueAsString(object);
    }

    protected MockHttpServletRequestBuilder getMockHttpRequestBuilderPOST(String URL, String json) {
        return MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json);
    }

    protected MockHttpServletRequestBuilder getMockHttpRequestBuilderGET(String URL) {
        return MockMvcRequestBuilders.get(URL)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
    }
}


