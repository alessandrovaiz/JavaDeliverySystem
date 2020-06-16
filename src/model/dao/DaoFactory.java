package model.dao;

import db.DB;
import model.dao.impl.PedidoDaoJDBC;

public class DaoFactory {
	
	public static PedidoDao criaPedidoDao() {
		return new PedidoDaoJDBC(DB.getConnection());
	}
}
