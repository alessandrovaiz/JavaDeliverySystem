package model.servicos;

import java.util.List;

import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.entidades.Cliente;

public class ClientesServico {
	
	private ClienteDao dao =  DaoFactory.criaClienteDao();
	public List<Cliente> findAll(){
		
		return dao.findAll();
	}
	
	public void salvaOuAtualiza(Cliente obj) {
		if (obj.getId()==null) {
			dao.insert(obj);
		}
		else { // a necessidade do cliente aux é para que o rank não seja reinicializado na hora de atualizar o cliente
			Cliente aux = dao.findById(obj.getId());
			obj.setRank(aux.getRank());
			dao.update(obj);
		}
	}
	
	public void remove(Cliente obj) {
		dao.deleteById(obj.getId());
	}
	
	public Cliente findByName(String nome) {
		Cliente obj = dao.findByName(nome);
		return obj;
		
	}
	
	public Cliente findById(Integer id) {
		Cliente obj = dao.findById(id);
		return obj;
		
	}
	
	public void insert(Cliente obj) {
		dao.insert(obj);
	}

	public List<Cliente> findClientesVip() {
		return dao.findClientesVip();
	}
	
	public void atualizaRank(Cliente obj) {
		dao.atualizaRank(obj);
	}
	
	
}
