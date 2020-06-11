package model.entidades.enums;

public enum StatusPedido {
	
	AGUARDANDO_INICIO(1), EM_PREPARO(2), A_CAMINHO(3), ENTREGUE(4);

	
	private final int valor;
	StatusPedido(int valorOpcao) {
		valor = valorOpcao;
	}
	public int getValor() {
		return valor;
	}
}
