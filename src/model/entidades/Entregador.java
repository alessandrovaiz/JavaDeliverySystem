package model.entidades;

import model.entidades.enums.StatusEntregador;

public class Entregador {
	private Integer id;
	private String nome;
	private Double valorPorEntrega;
	private Integer status;
	private StatusEntregador statusEntregador;
	
	
	public Entregador() {
		
	}
	
	public Entregador(String nome) {
		this.nome=nome;
	}
	
	public Entregador(Integer id, String nome, Double valorPorEntrega) {
		this.id = id;
		this.nome = nome;
		this.valorPorEntrega= valorPorEntrega;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValorPorEntrega() {
		return valorPorEntrega;
	}

	public void setValorPorEntrega(Double valorPorEntrega) {
		this.valorPorEntrega = valorPorEntrega;
	
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public StatusEntregador getStatusEntregador() {
		return statusEntregador;
	}

	public void setStatusEntregador() {
		switch(this.status) {
		case 1: { this.statusEntregador = StatusEntregador.LIVRE; break; } 
		case 2: { this.statusEntregador = StatusEntregador.OCUPADO; break; } 
		
		}
	}
	
	
}
