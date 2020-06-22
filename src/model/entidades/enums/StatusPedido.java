package model.entidades.enums;

public enum StatusPedido {
	
	ACEITO(1,"Aceito"), EM_PREPARO(2,"Em preparo"), A_CAMINHO(3,"A caminho"), ENTREGUE(4,"Entregue!"),CANCELADO(5,"Cancelado");

	
	private int valor;
	private String status;
	StatusPedido(int valorOpcao, String status) {
		valor = valorOpcao;
		this.status = status;
	}
	
	
	public void setValor(int valorOpcao) {
		valor = valorOpcao;
	}
	public int getValor() {
		return valor;
	}
	
	public String getStatusStr() {
		return status;
	}
}
