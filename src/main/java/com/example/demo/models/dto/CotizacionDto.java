package com.example.demo.models.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class CotizacionDto implements Serializable {
	
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
	@Getter@Setter
	private  Integer cantidad ;
	@Getter@Setter
	private Double ValorCotizacion;
	@Getter@Setter
	private Date creatAt = new Date();
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
