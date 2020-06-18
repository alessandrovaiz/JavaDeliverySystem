package model.entidades;

public class Produto {
	private Integer id;
	private String nome;
	private Double valor;
	private Integer qtd;
	public Produto() {
		
	}
	
	public Produto (String nome) {
		this.nome = nome;
	}
	public Produto(Integer id, String nome, Double valor, Integer qtd) {
		this.id = id;
		this.nome = nome;
		this.valor = valor;
		this.qtd = qtd;
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
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Integer getQtd() {
		return qtd;
	}
	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}
	
	
	
}
