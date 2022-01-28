package com.example.demo.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.entity.CotizacionProducto;


public interface ICotizacionProducto  extends JpaRepository<CotizacionProducto, Long>{

}
