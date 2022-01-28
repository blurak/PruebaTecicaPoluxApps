package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.models.dto.ServicioDto;
import com.example.demo.models.entity.CotizacionProducto;
import com.example.demo.models.entity.CotizacionServicio;
import com.example.demo.models.entity.Producto;
import com.example.demo.models.entity.Servicio;
import com.example.demo.serivices.IServicioService;


@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/servicios")
public class ServicioController {
	
	@Autowired
	private IServicioService service;
	
	
	@CrossOrigin(origins = "*")
	@GetMapping("/consultar/page/{page}/{cant}")
	public ResponseEntity<Page<Servicio>> consultarPage(@PathVariable Integer page, @PathVariable Integer cant) {
		return new ResponseEntity<Page<Servicio>>(service.findAll(PageRequest.of(page, cant)), HttpStatus.OK);
	}
	@PostMapping("/crear")
	public ResponseEntity<?> crearProducto(@Valid @RequestBody Servicio servicio, BindingResult result) {
		Map<String, Object> response = new HashMap<>();

		if (service.ListaUnidades().contains(servicio.getUnidad().toString())) {
			if (result.hasErrors()) {
				List<String> errors = result.getFieldErrors().stream().map(err -> {
					return "El Campo " + err.getField() + " " + err.getDefaultMessage();
				}).collect(Collectors.toList());

				response.put("error", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

			}

			try {
				Servicio p = service.crear(servicio);
				if (p != null) {
					response.put("Mensaje", "El Servicio fue creado con exito");
					response.put("Producto", p);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

				} else {
					response.put("Mensaje", "Error al crear El Servicio");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				response.put("Mensaje", "Compruebe Datos no diligenciados");
				response.put("error", e.getMessage());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
		response.put("Mensaje","Tiene Que tener una unidad de las definidas Ejemplo: Centímetro, Metro, Metro 2, Gramo, Kilogramo, Litro, Galón, Hora, Día, Mes, Vez");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/editar")
	public ResponseEntity<?> editarPst(@Valid @RequestBody Servicio servicio, BindingResult result) {
		Servicio p = null;
		Map<String, Object> response = new HashMap<>();

		if (service.ListaUnidades().contains(servicio.getUnidad().toString())) {

			if (result.hasErrors()) {
				List<String> errors = result.getFieldErrors().stream().map(err -> {
					return "El Campo " + err.getField() + " " + err.getDefaultMessage();
				}).collect(Collectors.toList());

				response.put("error", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

			}
			try {
				p = service.finById(servicio.getId());
			} catch (Exception e) {
				response.put("Mensaje", "El Producto no existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			if (p == null) {
				response.put("Mensaje", "El Producto no existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			try {

				p = service.editar(servicio);
				return new ResponseEntity<Servicio>(p, HttpStatus.OK);

			} catch (TransactionSystemException e) {
				response.put("Mensaje", "Error al actualizar");
				response.put("error", e.getMostSpecificCause().getMessage());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		response.put("Mensaje","Tiene Que tener una unidad de las definidas Ejemplo: Centímetro, Metro, Metro 2, Gramo, Kilogramo, Litro, Galón, Hora, Día, Mes, Vez");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

	}
	@GetMapping("/servicio/{id}")
	public ResponseEntity<?> productoId(@PathVariable Long id) {
		Servicio servicio = null;
		Map<String, Object> response = new HashMap<>();
		try {
			servicio = service.finById(id);
		} catch (Exception e) {
			response.put("Mensaje", "El Producto no existe");
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (servicio == null) {
			response.put("Mensaje", "el Producto ID: ".concat(id.toString().concat(" No existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Servicio>(servicio, HttpStatus.OK);
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Servicio servicio = service.finById(id);
			if (servicio == null) {
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
	@GetMapping("/serviciosEtiquetas/{etiqueta}")
	public ResponseEntity<?> productoId(@PathVariable String etiqueta) {
		List<Servicio> servicio = null;
		Map<String, Object> response = new HashMap<>();
		try {
			servicio = service.findByEtiquetaContainingIgnoreCase(etiqueta);
		} catch (Exception e) {
			response.put("Mensaje", "El Producto no existe");
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (servicio == null) {
			response.put("Mensaje", "el Producto ID: ");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity <List<Servicio>>(servicio, HttpStatus.OK);
	}
	
	@GetMapping("/consultarServicios")
	public ResponseEntity<List<ServicioDto>> consultarTodasPoductos() {
		return new ResponseEntity<List<ServicioDto>>(service.ServiciosAll(), HttpStatus.OK);
	}
	
	@PostMapping("/CotizarServicio/{cant}/{id}")
	@ResponseStatus(code =HttpStatus.CREATED)
	public ResponseEntity<?> crear ( @PathVariable Integer cant , @PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Servicio servicio = new Servicio();	
		try {
			servicio = service.finById(id);	
		} catch (Exception e) {
			response.put("Mensaje", "el Servicio que ingreso no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		
		if(servicio != null) {
			
			if(servicio.getPrecioFijo()==true && cant>1) {
				response.put("Mensaje", "el Servicio tiene precio Fijo No puede ingresar mas de 1 en cantidad");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			CotizacionDto cotDto = new CotizacionDto();
			cotDto = service.CrearCotizacion(cant, id);
			
			
			
			response.put("Mensaje", "Contizacion con exito");
			response.put("Cotizacion", cotDto);
			
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			
		}
		response.put("Mensaje", "el Servicio que ingreso no existe");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		
		
	}

}
