package model.dao;

import java.util.List;

import model.entidades.Cliente;

public interface ClienteDao {

	void insert(Cliente obj);
	void update(Cliente obj);
	void deleteById(Integer id);
	Cliente findById(Integer id);
	Cliente findByName(String nome);
	List<Cliente> findAll();
	List<Cliente> findClientesVip();
	void atualizaRank(Cliente obj);
	
}
