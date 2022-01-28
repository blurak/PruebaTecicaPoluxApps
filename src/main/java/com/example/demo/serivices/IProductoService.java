package com.example.demo.serivices;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.example.demo.models.dto.CotizacionDto;
import com.example.demo.models.dto.ProductoDto;
import com.example.demo.models.entity.Producto;



public interface IProductoService  {
	public List<ProductoDto> ProductosAll();
	public List<Producto>  findByEtiquetaContainingIgnoreCase(String ter);
	public Page<Producto> findAll(Pageable pageable);
	public Producto finById(Long id);
	public CotizacionDto CrearCotizacion(Integer cantidad, Long id);
	public Producto crear(Producto producto);
	public Producto editar(Producto entidad);
	public List<String> ListaUnidades ();
	public void eliminar (Long id);
	
}
