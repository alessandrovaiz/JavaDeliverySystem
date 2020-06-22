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
	
	public void salvaOuAtualiza(Pedido obj) {
		if (obj.getId()==null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}

	public void remove(Pedido obj) {
		dao.deleteById(obj.getId());
	}

	public List<Pedido> findEntregasEmAndamento() {
		return dao.findEntregasEmAndamento();
	}

}
