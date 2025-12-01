package com.controleestoque.api_estoque.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.controleestoque.api_estoque.dto.ItemVendaDTO;
import com.controleestoque.api_estoque.dto.VendaDTO;
import com.controleestoque.api_estoque.model.*;
import com.controleestoque.api_estoque.repository.*;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemVendaRepository itemVendaRepository;

    public VendaService(
            VendaRepository vendaRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository,
            ItemVendaRepository itemVendaRepository) {

        this.vendaRepository = vendaRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.itemVendaRepository = itemVendaRepository;
    }

    @Transactional
    public Venda registrarVenda(VendaDTO vendaDTO) {

        Cliente cliente = clienteRepository.findById(vendaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente inexistente"));

        Venda venda = new Venda();
        venda.setCliente(cliente);

        List<ItemVenda> itens = new ArrayList<>();

        for (ItemVendaDTO itemDTO : vendaDTO.getItens()) {

            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("O Produto solicitado não existe"));

            Estoque estoque = produto.getEstoque();
            if (estoque.getQuantidade() < itemDTO.getQuantidade()) {
                throw new RuntimeException("Estoque faltando de: " + produto.getNome());
            }

            estoque.setQuantidade(estoque.getQuantidade() - itemDTO.getQuantidade());

            ItemVenda item = new ItemVenda();
            item.setVenda(venda);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco().doubleValue());

            itens.add(item);
        }

        venda.setItens(itens);
        vendaRepository.save(venda);

        for (ItemVenda item : itens) {
            itemVendaRepository.save(item);
        }

        return venda;
    }
}
