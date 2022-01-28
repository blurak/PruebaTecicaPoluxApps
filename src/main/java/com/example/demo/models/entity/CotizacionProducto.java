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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="cotizacionproductos")
public class CotizacionProducto implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter@Setter
	private Long id;
	
	@NotNull(message = "el cantidad es obligatorio")
	@Min(value = 1)
	@Getter@Setter
	private Integer cantidadCotizar;
	
	@JsonIgnoreProperties(value={"Productos","hibernateLazyInitializer","handler"},allowSetters = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@Getter@Setter
	private Producto producto;
	
	
	public Double getTotal() {
		Double total = 0.00;
		total=cantidadCotizar*producto.getPrecio();
		return total;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
