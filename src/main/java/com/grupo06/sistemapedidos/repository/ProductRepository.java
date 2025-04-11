package com.grupo06.sistemapedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo06.sistemapedidos.model.Producto;

@Repository
public interface ProductRepository extends JpaRepository<Producto, Integer> {
}