package com.example.demo.serivices;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.models.dto.CotizacionDto;
import com.example.demo.models.dto.ServicioDto;
import com.example.demo.models.entity.Servicio;

public interface IServicioService {
	public List<ServicioDto> ServiciosAll();
	public List<Servicio>  findByEtiquetaContainingIgnoreCase(String ter);
	public Page<Servicio> findAll(Pageable pageable);
	public Servicio finById(Long id);
	public CotizacionDto CrearCotizacion(Integer cantidad, Long id);	
	public Servicio crear(Servicio servicio);
	public Servicio editar(Servicio entidad);
	public List<String> ListaUnidades ();
	public void eliminar (Long id);
}
