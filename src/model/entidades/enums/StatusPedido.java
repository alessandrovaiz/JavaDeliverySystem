package model.entidades.enums;

public enum StatusPedido {
	
	AGUARDANDO_ACEITACAO(1), EM_PREPARO(2), A_CAMINHO(3), ENTREGUE(4);

	
	private int valor;
	StatusPedido(int valorOpcao) {
		valor = valorOpcao;
	}
	
	public int getValor() {
		return valor;
	}
}
