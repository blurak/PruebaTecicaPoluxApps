package com.example.demo.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="productos")
public class Producto implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter@Setter
	private Long id;
	
	@NotNull(message = "La unidad  es campo obligatorio")	
	@Getter@Setter
	private String unidad;	
	@NotNull(message = "el precio es obligatorio")
	@Min(value = 1)
	@Getter@Setter
	private Double precio;
	@NotNull(message = "etiqueta es campo obligatorio")	
	@Size(min = 5,  max = 30, message = "etiqueta entre 5 y 30 carácteres")
	@Getter@Setter
	private String etiqueta;	
	@Size(min = 0,  max = 100, message = "etiqueta entre 0 y 100 carácteres")
	@Getter@Setter
	private String descripcion;
	@Getter@Setter
	@JsonIgnoreProperties({"producto","hibernateLazyInitializer","handler"})
	@OneToMany(fetch =FetchType.LAZY, mappedBy = "producto",cascade = CascadeType.ALL)
	private List<CotizacionProducto> cotizaciones;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
