package model.entidades.enums;

public enum StatusEntregador {
	
	LIVRE(1,"Livre"), OCUPADO(2,"Ocupado");

	
	private int valor;
	private String statusStr;
	StatusEntregador(int valorOpcao,String statusStr) {
		this.valor = valorOpcao;
		this.statusStr = statusStr;
	}
	
	public void setValor(int valorOpcao) {
		valor = valorOpcao;
	}
	public int getValor() {
		return valor;
	}
	public String getStatusStr() {
		return statusStr;
	}
}
