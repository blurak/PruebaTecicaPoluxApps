package com.example.demo.models.dto;



import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
public class ProductoDto {
	@Getter@Setter
	private Long id;
	
	@Getter@Setter
	private String unidad;	
	@Getter@Setter
	private Double precio;
	@Getter@Setter
	private String etiqueta;		
	@Getter@Setter
	private String descripcion;
	public ProductoDto(Long id, String unidad, Double precio, String etiqueta, String descripcion) {
		super();
		this.id = id;
		this.unidad = unidad;
		this.precio = precio;
		this.etiqueta = etiqueta;
		this.descripcion = descripcion;
	}

	
}
