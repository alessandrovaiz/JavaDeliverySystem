package model.dao;

import java.util.List;

import model.entidades.Pedido;


public interface PedidoDao {
	void insert(Pedido obj);
	void update(Pedido obj);
	void deleteById(Integer id);
	Pedido findById(Integer id);
	List<Pedido> findAll();
	List<Pedido> findEntregasEmAndamento();
	Double findCustoEntregadores();
	
}
