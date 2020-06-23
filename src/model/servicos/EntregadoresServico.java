package model.servicos;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.EntregadorDao;
import model.entidades.Entregador;

public class EntregadoresServico {
	
	private EntregadorDao dao =  DaoFactory.criaEntregadorDao();
	public List<Entregador> findAll(){
		
		return dao.findAll();
	}
	public List<Entregador> findEntregadoresDisp(){
		
		return dao.findEntregadoresDisp();
	}
	
	public void salvaOuAtualiza(Entregador obj) {
		if (obj.getId()==null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Entregador obj) {
		dao.deleteById(obj.getId());
	}
	
	public Entregador findByName(String nome) {
		Entregador obj = dao.findByName(nome);
		return obj;
		
	}
	
	public Entregador findById(Integer id) {
		Entregador obj = dao.findById(id);
		return obj;
		
	}
	public void insert(Entregador obj) {
		dao.insert(obj);
	}


}
