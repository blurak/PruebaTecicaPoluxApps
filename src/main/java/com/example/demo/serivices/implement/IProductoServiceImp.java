package com.example.demo.serivices.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.models.dto.CotizacionDto;
import com.example.demo.models.dto.ProductoDto;
import com.example.demo.models.entity.CotizacionProducto;
import com.example.demo.models.entity.Producto;
import com.example.demo.models.repository.ICotizacionProducto;
import com.example.demo.models.repository.IProductoRepository;
import com.example.demo.serivices.IProductoService;

@Service
public class IProductoServiceImp implements IProductoService {
	
	
	@Autowired
	
	private IProductoRepository repo;
	
	
	@Autowired	
	private ICotizacionProducto repoCotizacion;

	@Override
	public List<ProductoDto> ProductosAll() {
		List<ProductoDto> list = new ArrayList<ProductoDto>();
		
		List<Producto> resultado = repo.findAll();
		for(Producto p:resultado) {
			ProductoDto d =new ProductoDto();
			d.setId(p.getId());
			d.setDescripcion(p.getDescripcion());
			d.setUnidad(p.getUnidad());
			d.setPrecio(p.getPrecio());
			d.setEtiqueta(p.getEtiqueta());
			list.add(d);
		}
		return list;
	}

	@Override
	public CotizacionDto CrearCotizacion(Integer cantidad, Long id) {
		Producto producto =  new Producto ();
		producto=repo.findById(id).get();
		CotizacionProducto cotizacion =  new CotizacionProducto();
		cotizacion.setCantidadCotizar(cantidad);
		cotizacion.setProducto(producto);		
		CotizacionProducto p = new CotizacionProducto();
		p =	repoCotizacion.save(cotizacion);
		CotizacionDto cot = new CotizacionDto();
		cot.setId(p.getId());
		cot.setCantidad(cantidad);
		cot.setDescripcion(producto.getDescripcion());
		cot.setEtiqueta(producto.getEtiqueta());
		cot.setPrecio(producto.getPrecio());
		cot.setUnidad(producto.getUnidad());
		cot.setValorCotizacion(p.getTotal());		
		return cot;
	}

	@Override
	public Producto finById(Long id) {
		Producto producto = new Producto();
		producto=repo.findById(id).get();
		if(producto==null) {
			return null;
		}
		return producto ;
	}

	@Override
	public Producto crear(Producto producto) {
	
		 return repo.save(producto);
	}

	@Override
	public Producto editar(Producto entidad) {
		return repo.save(entidad);
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
	public Page<Producto> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public List<Producto> findByEtiquetaContainingIgnoreCase(String ter) {
		
		return repo.findByEtiquetaContainingIgnoreCase(ter);
	}

	

}
