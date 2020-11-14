package com.deliverit.projeto.services;

import com.deliverit.projeto.dto.ContaPagarDTO;
import com.deliverit.projeto.enums.JurosMultaEnum;
import com.deliverit.projeto.models.ContaPagar;
import com.deliverit.projeto.repositories.ContaPagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ContaPagarService {

    @Autowired
    private ContaPagarRepository repository;

    public List<ContaPagar> findAll(){
        return repository.findAll();
    }

    public ContaPagar insert(ContaPagar contaPagar){
        contaPagar.setId(null);

        return repository.save(contaPagar);
    }

    public ContaPagar fromDTO(ContaPagar conta, ContaPagarDTO dto){
        conta.setNome(dto.getNome());
        conta.setValor(dto.getValor());
        conta.setValorCorrigido(dto.getValor());
        conta.setDataVencimento(dto.getDataVencimento());
        conta.setDataPagamento(dto.getDataPagamento());

        if(conta.getDataPagamento().after(conta.getDataVencimento()))
            calcularDiasAtraso(conta);

        return conta;
    }

    public ContaPagarDTO toDTO(ContaPagar conta){
        return ContaPagarDTO.builder()
                .id(conta.getId())
                .nome(conta.getNome())
                .valor(conta.getValor())
                .dataVencimento(conta.getDataVencimento())
                .dataPagamento(conta.getDataPagamento())
                .valorCorrigido(conta.getValorCorrigido())
                .build();
    }

    private void calcularDiasAtraso(ContaPagar conta){
        Long time = TimeUnit.MILLISECONDS.toDays((conta.getDataPagamento().getTime() - conta.getDataVencimento().getTime()));
        conta.setDiasAtraso(time.intValue());
        if(time > 0){
           calcularJurosConta(conta, time);
        }
    }

    private void calcularJurosConta(ContaPagar conta, Long diasAtraso){
        JurosMultaEnum regra = JurosMultaEnum.getRegraPorDiasAtraso(diasAtraso);
        BigDecimal oneHundred = BigDecimal.valueOf(100);

        conta.setMulta(regra.getMulta());
        conta.setJurosDia(regra.getJurosDias());
        BigDecimal valorJuros = conta.getValor().divide(oneHundred).multiply(conta.getMulta());
        valorJuros = valorJuros.add(conta.getValor().divide(oneHundred).multiply(conta.getJurosDia()).multiply(new BigDecimal(diasAtraso))).setScale(2, RoundingMode.HALF_UP);;
        conta.setValorCorrigido(conta.getValor().add(valorJuros));
    }
}
