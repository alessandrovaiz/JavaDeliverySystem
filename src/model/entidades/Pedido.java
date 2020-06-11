package model.entidades;

public class Pedido {
	private Integer id;
	private Integer qtd;
	private String enderecoEntrega;
	private Cliente cliente;
	private Produto produtos;
	private Enum status;
	
	
	public Pedido() {
		
	}
	
	
	public Pedido(Integer id, Integer qtd, String enderecoEntrega, Cliente cliente, Produto produtos, Enum status) {
		this.id = id;
		this.qtd = qtd;
		this.enderecoEntrega = enderecoEntrega;
		this.cliente = cliente;
		this.produtos = produtos;
		this.status = status;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getQtd() {
		return qtd;
	}
	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}
	public String getEnderecoEntrega() {
		return enderecoEntrega;
	}
	public void setEnderecoEntrega(String enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Produto getProdutos() {
		return produtos;
	}
	public void setProdutos(Produto produtos) {
		this.produtos = produtos;
	}
	public Enum getStatus() {
		return status;
	}
	public void setStatus(Enum status) {
		this.status = status;
	}
	
	
	
	
	
	
}
