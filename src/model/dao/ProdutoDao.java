package model.dao;

import java.util.List;

import model.entidades.Produto;

public interface ProdutoDao {
	void insert(Produto obj);
	void update(Produto obj);
	void deleteById(Integer id);
	Produto findById(Integer id);
	Produto findByName(String nome);
	List<Produto> findAll();
}
