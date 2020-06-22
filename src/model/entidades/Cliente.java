package model.entidades;

import model.entidades.enums.RankCliente;

public class Cliente {
	
	private String nome;
	private Integer id;
	private String endereco;
	private int rank;
	private RankCliente rankCliente;
	
	
	public Cliente() {
		
	}
	
	public Cliente(String nome) {
		this.nome = nome;
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
		setRankCliente();
		
	}
	public void setRankCliente() {
		switch(rank) {
		case 1: {	rankCliente = RankCliente.CLIENTE_NOVO; break; }
		case 2: { 	rankCliente = RankCliente.CLIENTE_FREQUENTE;break;}
		case 3: {	rankCliente = RankCliente.CLIENTE_VIP;break;}
		
		}
	}
	public String getRankCliente() {
		return rankCliente.getStatusStr();
	}
	@Override
	public String toString() {
		return nome;
	}
	
	
	
	
	
}
