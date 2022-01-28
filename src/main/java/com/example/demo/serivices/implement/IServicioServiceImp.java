package com.example.demo.serivices.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.models.dto.CotizacionDto;
import com.example.demo.models.dto.ServicioDto;
import com.example.demo.models.entity.CotizacionProducto;
import com.example.demo.models.entity.CotizacionServicio;
import com.example.demo.models.entity.Producto;
import com.example.demo.models.entity.Servicio;
import com.example.demo.models.repository.ICotizacionServiceRepositoty;
import com.example.demo.models.repository.IServicioRepository;
import com.example.demo.serivices.IServicioService;

@Service
public class IServicioServiceImp implements IServicioService {
	
	@Autowired
	
	private IServicioRepository repo;
	
	@Autowired
	
	private ICotizacionServiceRepositoty repoCotizacion;
	
	@Override
	public List<ServicioDto> ServiciosAll() {
		List<ServicioDto> list = new ArrayList<ServicioDto>();
		
		List<Servicio> resultado = repo.findAll();
		for(Servicio p:resultado) {
			ServicioDto d =new ServicioDto();
			d.setId(p.getId());
			d.setDescripcion(p.getDescripcion());
			d.setUnidad(p.getUnidad());
			d.setPrecio(p.getPrecio());
			d.setPrecioFijo(p.getPrecioFijo());
			d.setEtiqueta(p.getEtiqueta());
			list.add(d);
		}
		return list;
	}

	@Override
	public Servicio finById(Long id) {
		
		return repo.findById(id).get();
		
	}

	@Override
	public CotizacionDto CrearCotizacion(Integer cantidad, Long id) {
		Servicio servicio =  new Servicio ();
		servicio=repo.findById(id).get();
		CotizacionServicio cotizacion =  new CotizacionServicio();
		cotizacion.setCantidadCotizar(cantidad);
		cotizacion.setServicio(servicio);	
		
		cotizacion =repoCotizacion.save(cotizacion);
		CotizacionDto cot = new CotizacionDto();
		cot.setId(cotizacion.getId());
		cot.setCantidad(cantidad);
		cot.setDescripcion(servicio.getDescripcion());
		cot.setEtiqueta(servicio.getEtiqueta());
		cot.setPrecio(servicio.getPrecio());
		cot.setUnidad(servicio.getUnidad());
		cot.setValorCotizacion(cotizacion.getTotal());
		
		return cot;
	}

	@Override
	public List<String> ListaUnidades() {
		List<String> unidades = new ArrayList<String>();
		unidades.add("Centímetro");
		unidades.add("Metro");
		unidades.add("Metro 2");
		unidades.add("Gramo");
		unidades.add("Kilogramo");
		unidades.add("Litro");
		unidades.add("Galón");
		unidades.add("Hora");
		unidades.add("Día");
		unidades.add("Mes");
		unidades.add("Vez");		
		return unidades;
	}

	@Override
	public void eliminar(Long id) {
		repo.deleteById(id);
		
	}

	@Override
	public Servicio crear(Servicio servicio) {
		 return repo.save(servicio);
	}

	@Override
	public Servicio editar(Servicio entidad) {
		return repo.save(entidad);
	}

	@Override
	public Page<Servicio> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public List<Servicio> findByEtiquetaContainingIgnoreCase(String ter) {
		
		return repo.findByEtiquetaContainingIgnoreCase(ter);
	}

}
