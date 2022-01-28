package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.models.dto.CotizacionDto;
import com.example.demo.models.dto.ProductoDto;
import com.example.demo.models.entity.CotizacionProducto;
import com.example.demo.models.entity.Producto;

import com.example.demo.serivices.IProductoService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/productos")
public class ProductoController {

	@Autowired
	private IProductoService service;

	@GetMapping("/consultarProductos")
	public ResponseEntity<List<ProductoDto>> consultarTodasPoductos() {
		return new ResponseEntity<List<ProductoDto>>(service.ProductosAll(), HttpStatus.OK);
	}
	@CrossOrigin(origins = "*")
	@GetMapping("/consultar/page/{page}/{cant}")
	public ResponseEntity<Page<Producto>> consultarPage(@PathVariable Integer page, @PathVariable Integer cant) {
		return new ResponseEntity<Page<Producto>>(service.findAll(PageRequest.of(page, cant)), HttpStatus.OK);
	}

	@PostMapping("/crear")
	public ResponseEntity<?> crearProducto(@Valid @RequestBody Producto producto, BindingResult result) {
		Map<String, Object> response = new HashMap<>();

		if (service.ListaUnidades().contains(producto.getUnidad().toString())) {
			if (result.hasErrors()) {
				List<String> errors = result.getFieldErrors().stream().map(err -> {
					return "El Campo " + err.getField() + " " + err.getDefaultMessage();
				}).collect(Collectors.toList());

				response.put("error", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

			}

			try {
				Producto p = service.crear(producto);
				if (p != null) {
					response.put("Mensaje", "El Producto fue creado con exito");
					response.put("Producto", p);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

				} else {
					response.put("Mensaje", "Error al crear El Producto");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				response.put("Mensaje", "Compruebe Datos no diligenciados");
				response.put("error", e.getMessage());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
		response.put("Mensaje",
				"Tiene Que tener una unidad de las definidas Ejemplo: Centímetro, Metro, Metro 2, Gramo, Kilogramo, Litro, Galón, Hora, Día, Mes, Vez");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/editar")
	public ResponseEntity<?> editarPst(@Valid @RequestBody Producto producto, BindingResult result) {
		Producto p = null;
		Map<String, Object> response = new HashMap<>();

		if (service.ListaUnidades().contains(producto.getUnidad().toString())) {

			if (result.hasErrors()) {
				List<String> errors = result.getFieldErrors().stream().map(err -> {
					return "El Campo " + err.getField() + " " + err.getDefaultMessage();
				}).collect(Collectors.toList());

				response.put("error", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

			}

			try {
				p = service.finById(producto.getId());
			} catch (Exception e) {
				response.put("Mensaje", "El Producto no existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			if (p == null) {
				response.put("Mensaje", "El Producto no existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			try {

				p = service.editar(producto);
				return new ResponseEntity<Producto>(p, HttpStatus.OK);

			} catch (TransactionSystemException e) {
				response.put("Mensaje", "Error al actualizar");
				response.put("error", e.getMostSpecificCause().getMessage());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		response.put("Mensaje","Tiene Que tener una unidad de las definidas Ejemplo: Centímetro, Metro, Metro 2, Gramo, Kilogramo, Litro, Galón, Hora, Día, Mes, Vez");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

	}
	
	@GetMapping("/producto/{id}")
	public ResponseEntity<?> productoId(@PathVariable Long id) {
		Producto producto = null;
		Map<String, Object> response = new HashMap<>();
		try {
			producto = service.finById(id);
		} catch (Exception e) {
			response.put("Mensaje", "El Producto no existe");
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (producto == null) {
			response.put("Mensaje", "el Producto ID: ".concat(id.toString().concat(" No existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}
	
	@GetMapping("/productosEtiquetas/{etiqueta}")
	public ResponseEntity<?> productoId(@PathVariable String etiqueta) {
		List<Producto> producto = null;
		Map<String, Object> response = new HashMap<>();
		try {
			producto = service.findByEtiquetaContainingIgnoreCase(etiqueta);
		} catch (Exception e) {
			response.put("Mensaje", "El Producto no existe");
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (producto == null) {
			response.put("Mensaje", "el Producto ID: ");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity <List<Producto>>(producto, HttpStatus.OK);
	}
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Producto producto = service.finById(id);
			if (producto == null) {
				response.put("Mensaje", "Error al realizar la consulta");
				response.put("error", "El producto No Existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				
				service.eliminar(id);
			}
		} catch (Exception e) {
			response.put("Mensaje", "El Producto no existe");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Mensaje", "El Producto Fue eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	

	@PostMapping("/CotizarProductos/{cant}/{id}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<?> crear(@PathVariable Integer cant, @PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Producto producto = new Producto();
		CotizacionProducto cotizacion = new CotizacionProducto();
		try {
			producto = service.finById(id);

		} catch (Exception e) {
			response.put("Mensaje", "el producto que ingreso no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		CotizacionDto cotDto = new CotizacionDto();
		cotDto = service.CrearCotizacion(cant, id);

		response.put("Mensaje", "Contizacion con exito");
		response.put("Cotizacion", cotDto);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
