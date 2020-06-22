package model.entidades;

import model.entidades.enums.StatusPedido;

public class Pedido {
	private Integer id;
	private Integer qtd;
	private Double total;
	private String enderecoEntrega;
	private Cliente cliente;
	private Produto produtos;
	private Integer status;
	private StatusPedido statusPedido;
	private Entregador entregador;
	
	
	public Pedido() {
		
	}
	
	
	public Pedido(Integer id, Integer qtd, String enderecoEntrega, Cliente cliente, Produto produtos, int status, Double total, Entregador entregador) {
		this.id = id;
		this.qtd = qtd;
		this.enderecoEntrega = enderecoEntrega;
		this.cliente = cliente;
		this.produtos = produtos;
		this.status = status;
		this.total = total;
		this.entregador = entregador;
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


	public Double getTotal() {
		return this.total;
	}


	public void setTotal(Double total) {
		this.total = total;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		
		
		setStatusPedido();
	}

	private void setStatusPedido() {
		
		switch(this.status) {
		case 1: { this.statusPedido = StatusPedido.ACEITO; break; } 
		case 2: { this.statusPedido = StatusPedido.EM_PREPARO; break; } 
		case 3: { this.statusPedido = StatusPedido.A_CAMINHO; break; } 
		case 4: { this.statusPedido = StatusPedido.ENTREGUE; break; } 
		
		}
		
	}

	public String getStatusPedido () {
		return statusPedido.getStatusStr();
	}
	public Entregador getEntregador() {
		return entregador;
	}
	

	public void setEntregador(Entregador entregador) {
		this.entregador = entregador;
	}


	@Override
	public String toString() {
		return "Pedido [id=" + id + ", qtd=" + qtd + ", total=" + total + ", enderecoEntrega=" + enderecoEntrega
				+ ", cliente=" + cliente + ", produtos=" + produtos + ", status=" + status + ", statusPedido="
				+ statusPedido + ", entregador=" + entregador + "]";
	}


	
	
	
	
	
	
	
}
