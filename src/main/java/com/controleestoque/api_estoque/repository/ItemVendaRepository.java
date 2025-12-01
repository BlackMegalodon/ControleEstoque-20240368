package com.controleestoque.api_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.controleestoque.api_estoque.model.ItemVenda;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
}
