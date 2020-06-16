package model.entidades.enums;

public enum RankCliente {
	
	CLIENTE_NOVO(1), CLIENTE_FREQUENTE(2), CLIENTE_VIP(3);

	
	private int valor;
	RankCliente(int valorOpcao) {
		valor = valorOpcao;
	}
	
	public void setValor(int valorOpcao) {
		valor = valorOpcao;
	}
	public int getValor() {
		return valor;
	}
}
