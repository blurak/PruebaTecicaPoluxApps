package com.example.demo.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.models.entity.Producto;


public interface IProductoRepository extends JpaRepository<Producto, Long> {
	
	public List<Producto>  findByEtiquetaContainingIgnoreCase(String ter);
	
}
