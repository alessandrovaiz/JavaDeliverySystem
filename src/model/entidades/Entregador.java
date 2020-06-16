package model.entidades;

public class Entregador {
	private Integer id;
	private String nome;
	private Double valorPorEntrega;
	
	public Entregador() {
		
	}

	
	public Entregador(Integer id, String nome, Double valorPorEntrega) {
		this.id = id;
		this.nome = nome;
		this.valorPorEntrega = valorPorEntrega;
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
	
	
}
