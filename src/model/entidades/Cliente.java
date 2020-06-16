package model.entidades;

import model.entidades.enums.RankCliente;

public class Cliente {
	
	private String nome;
	private Integer id;
	private String endereco;
	private int rank;
	private RankCliente rank_cliente;
	
	
	public Cliente() {
		
	}
	
	public Cliente(Integer id, String nome,String endereco) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public int getRank() {
		return rank;
	}
	public void setRank(int i) {
		this.rank = i;
		
		
	}

	@Override
	public String toString() {
		return nome;
	}
	
	
	
	
	
}
