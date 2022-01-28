package com.example.demo.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.models.entity.Servicio;

public interface IServicioRepository extends JpaRepository<Servicio, Long> {
	public List<Servicio>  findByEtiquetaContainingIgnoreCase(String ter);
}
