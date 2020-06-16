package model.servicos;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.PedidoDao;
import model.entidades.Pedido;

public class PedidosServico {
	
	private PedidoDao dao = DaoFactory.criaPedidoDao();
	public List<Pedido> findAll(){
		
		return dao.findAll();
	}

}
