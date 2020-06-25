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
		else { // a necessidade do pedido aux é para que o rank não seja reinicializado na hora de atualizar o pedido
			
			
			Pedido aux = dao.findById(obj.getId());
			obj.setStatus(aux.getStatus());
			
			
			dao.update(obj);
		}

	
	}

	public void remove(Pedido obj) {
		
		dao.deleteById(obj.getId());
	}
	
	public void atualizaStatus(Pedido obj) {
		dao.atualizaStatus(obj);
	}
	
	public List<Pedido> findEntregasEmAndamento() {
		return dao.findEntregasEmAndamento();
	}
	
	public Double findCustoEntregadores() {
		return dao.findCustoEntregadores();
	}
}
