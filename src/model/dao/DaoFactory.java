package model.dao;

import db.DB;
import model.dao.impl.ClienteDaoJDBC;
import model.dao.impl.EntregadorDaoJDBC;
import model.dao.impl.PedidoDaoJDBC;
import model.dao.impl.ProdutoDaoJDBC;

public class DaoFactory {
	
	public static PedidoDao criaPedidoDao() {
		return new PedidoDaoJDBC(DB.getConnection());
	}
	
	public static ClienteDao criaClienteDao() {
		return new ClienteDaoJDBC(DB.getConnection());
	}
	
	public static ProdutoDao criaProdutoDao() {
		return new ProdutoDaoJDBC(DB.getConnection());
	}
	
	public static EntregadorDao criaEntregadorDao() {
		return new EntregadorDaoJDBC(DB.getConnection());
	}
	
}
