package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.ProdutoDao;
import model.entidades.Entregador;
import model.entidades.Produto;

public class ProdutoDaoJDBC implements ProdutoDao{

	private Connection conn;
	
	public ProdutoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Produto obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO Produto " +
				"(nome,valor,quantidade) " +
				"VALUES " +
				"(?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setDouble(2, obj.getValor());
			st.setInt(3, obj.getQtd());
			

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DbException("Erro inesperado, sem linhas afetadas!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Produto obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE produto " +
				"SET nome = ?,valor =?,quantidade=? " +
				"WHERE Id = ?");

			st.setString(1, obj.getNome());
			st.setDouble(2, obj.getValor());
			st.setInt(3, obj.getQtd());
		
			
			st.setInt(4, obj.getId());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}	
		
	

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM produto WHERE id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Produto findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM produto WHERE id = ?;");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Produto prod = new Produto();
				prod.setId(rs.getInt("produto_id"));
				prod.setNome(rs.getString("nomeProduto"));
				prod.setQtd(rs.getInt("qtdProduto"));
				prod.setValor(rs.getDouble("valorProduto"));
				
				
				
				return prod;
				
			}
			return null;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<Produto> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
							"SELECT * FROM produto; " );
			
			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();

			while (rs.next()) {
				
				Produto prod = new Produto();
				
				prod.setId(rs.getInt("produto_id"));
				prod.setNome(rs.getString("nomeProduto"));
				prod.setQtd(rs.getInt("qtdProduto"));
				prod.setValor(rs.getDouble("valorProduto"));
				
			
				list.add(prod);
				
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	
	}
	
	
	@Override
	public Produto findByName(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM produto WHERE nome = ?;");
			st.setString(1, nome);
			rs = st.executeQuery();
			if(rs.next()) {
				
				Produto obj = new Produto();
				
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
				obj.setValor(rs.getDouble("valor"));
				obj.setQtd(rs.getInt("quantidade"));
				
				
				return obj;
				
			}
			return null;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}
	
	
	
}
