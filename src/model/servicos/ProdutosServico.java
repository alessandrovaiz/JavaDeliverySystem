package model.servicos;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProdutoDao;
import model.entidades.Produto;

public class ProdutosServico {
	
	private ProdutoDao dao = DaoFactory.criaProdutoDao();
	public List<Produto> findAll(){
		
		return dao.findAll();
	}
	
	public void salvaOuAtualiza(Produto obj) {
		if (obj.getId()==null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}

	public void remove(Produto obj) {
		dao.deleteById(obj.getId());
	}

	public Produto findById (Integer id) {
		Produto obj = dao.findById(id);
		return obj;
	}
	
	public Produto findByName(String nome) {
		Produto obj = dao.findByName(nome);
		return obj;
		
	}
	
	public void insert(Produto obj) {
		dao.insert(obj);
	}

}
