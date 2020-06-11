package model.entidades;

public class Cliente {
	
	private String nome;
	private Integer id;
	private String endereco;
	private Enum rank;
	
	
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
	
	public Enum getRank() {
		return rank;
	}
	public void setRank(Enum rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return nome;
	}
	
	
	
	
	
}
