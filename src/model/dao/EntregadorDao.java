package model.dao;

import java.util.List;

import model.entidades.Entregador;


public interface EntregadorDao {
	void insert(Entregador obj);
	void update(Entregador obj);
	void deleteById(Integer id);
	Entregador findById(Integer id);
	Entregador findByName(String nome);
	List<Entregador> findAll();
	List<Entregador> findEntregadoresDisp();
	void atualizaStatus(Entregador obj);
}
