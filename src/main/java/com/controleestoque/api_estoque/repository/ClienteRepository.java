package com.controleestoque.api_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.controleestoque.api_estoque.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}