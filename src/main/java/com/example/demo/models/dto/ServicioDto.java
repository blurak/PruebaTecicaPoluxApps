package com.example.demo.models.dto;

import lombok.Getter;
import lombok.Setter;

public class ServicioDto {
	@Getter@Setter
	private Long id;	
	@Getter@Setter
	private String unidad;	
	@Getter@Setter
	private Double precio;
	@Getter@Setter
	private Boolean precioFijo;
	@Getter@Setter
	private String etiqueta;		
	@Getter@Setter
	private String descripcion;
	
}
