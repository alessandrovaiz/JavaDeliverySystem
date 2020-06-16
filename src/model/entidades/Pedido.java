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
		return total;
	}


	public void setTotal(Double total) {
		this.total = total*getQtd();
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(Integer i) {
		this.status = i;
		switch(i) {
		case 1: { statusPedido = statusPedido.AGUARDANDO_ACEITACAO; break; } 
		case 2: { statusPedido = statusPedido.EM_PREPARO; break; } 
		case 3: { statusPedido = statusPedido.A_CAMINHO; break; } 
		case 4: { statusPedido = statusPedido.ENTREGUE; break; } 
		}
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
