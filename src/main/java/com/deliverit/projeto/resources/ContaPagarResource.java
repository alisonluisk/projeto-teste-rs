package com.deliverit.projeto.resources;

import com.deliverit.projeto.dto.ContaPagarDTO;
import com.deliverit.projeto.models.ContaPagar;
import com.deliverit.projeto.services.ContaPagarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/conta_pagar")
public class ContaPagarResource {

    @Autowired
    private ContaPagarService contaPagarService;

    @GetMapping
    public ResponseEntity<List<ContaPagarDTO>> findAll() {
        List<ContaPagar> list = contaPagarService.findAll();
        return ResponseEntity.ok().body(list.stream().map(contaPagar -> contaPagarService.toDTO(contaPagar)).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<ContaPagarDTO> insert(@Valid @RequestBody ContaPagarDTO objDto) {
        ContaPagar obj = contaPagarService.fromDTO(new ContaPagar(), objDto);
        contaPagarService.insert(obj);
        return ResponseEntity.created(null).body(contaPagarService.toDTO(obj));
    }


}
