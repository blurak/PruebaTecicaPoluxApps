package com.example.demo.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.entity.CotizacionServicio;

public interface ICotizacionServiceRepositoty extends JpaRepository<CotizacionServicio, Long> {

}
