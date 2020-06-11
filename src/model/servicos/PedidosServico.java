package model.servicos;

import java.util.ArrayList;
import java.util.List;

import model.entidades.Cliente;
import model.entidades.Pedido;
import model.entidades.Produto;
import model.entidades.enums.StatusPedido;

public class PedidosServico {
	
	
	public List<Pedido> findAll(){
		//MOCK
		List<Pedido> list = new ArrayList<>();
		list.add(new Pedido(1,1,"Endereço de teste",new Cliente(1,"Alessandro","Marechal Deodoro"),new Produto(),StatusPedido.AGUARDANDO_INICIO));
		list.add(new Pedido(2,1,"Endereço de teste2",new Cliente(1,"ASDASDA","Duque de caxias"),new Produto(),StatusPedido.A_CAMINHO));
		
		return list;
	}

}
