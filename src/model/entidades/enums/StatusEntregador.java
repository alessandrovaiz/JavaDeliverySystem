package model.entidades.enums;

public enum StatusEntregador {
	
	LIVRE(1), OCUPADO(2);

	
	private int valor;
	StatusEntregador(int valorOpcao) {
		valor = valorOpcao;
	}
	
	public void setValor(int valorOpcao) {
		valor = valorOpcao;
	}
	public int getValor() {
		return valor;
	}
}
