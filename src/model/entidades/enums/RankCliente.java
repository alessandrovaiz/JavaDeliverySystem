package model.entidades.enums;

public enum RankCliente {
	
	CLIENTE_NOVO(1,"Bronze"), CLIENTE_FREQUENTE(2,"Ouro"), CLIENTE_VIP(3,"Diamante");

	
	private int valor;
	private String statusStr;
	RankCliente(int valorOpcao, String str) {
		valor = valorOpcao;
		statusStr = str;
		
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
