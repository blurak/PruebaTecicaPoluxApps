package com.example.demo.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.aspectj.weaver.tools.Trace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="cotizacionservicios")
public class CotizacionServicio implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter@Setter
	private Long id;
	
	@NotNull(message = "el cantidad es obligatorio")
	@Min(value = 1)
	@Getter@Setter
	private Integer cantidadCotizar;
	
	@JsonIgnoreProperties(value={"Servicios","hibernateLazyInitializer","handler"},allowSetters = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@Getter@Setter
	private Servicio servicio;
	
	
	public Double getTotal() {
		Double total = 0.00;
		total=cantidadCotizar*servicio.getPrecio();
		return total;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
