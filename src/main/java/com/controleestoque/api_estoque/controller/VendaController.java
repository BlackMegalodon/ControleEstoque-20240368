package com.controleestoque.api_estoque.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.controleestoque.api_estoque.dto.VendaDTO;
import com.controleestoque.api_estoque.model.Venda;
import com.controleestoque.api_estoque.service.VendaService;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<?> registrarVenda(@RequestBody VendaDTO vendaDTO) {
        try {
        Venda venda = vendaService.registrarVenda(vendaDTO);return ResponseEntity.ok(venda);}
        catch (RuntimeException ex) {return ResponseEntity.badRequest().body(ex.getMessage());}
    }
}
