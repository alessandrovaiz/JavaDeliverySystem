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
import model.dao.ClienteDao;
import model.entidades.Cliente;

public class ClienteDaoJDBC implements ClienteDao{

	private Connection conn;
	
	public ClienteDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Cliente obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO cliente " +
				"(nome,endereco,`rank`)" +
				"VALUES " +
				"(?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEndereco());
			st.setInt(3,obj.getRank());
			
			

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
	public void update(Cliente obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE cliente " +
				"SET nome = ?,endereco=?,`rank`=?" +
				" WHERE id = ?");
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEndereco());
			st.setInt(3, obj.getRank());
			
			
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
				"DELETE FROM cliente WHERE Id = ?");

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
	public Cliente findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM cliente WHERE id = ?;");
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				
				Cliente obj = new Cliente();
				
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));;
				obj.setEndereco(rs.getString("endereco"));
				obj.setRank(rs.getInt("rank"));
				
				
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

	@Override
	public List<Cliente> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
							"SELECT * FROM cliente;" );
			
			rs = st.executeQuery();

			List<Cliente> list = new ArrayList<>();

			while (rs.next()) {
				
				
				Cliente obj = new Cliente();
				
				obj.setEndereco(rs.getString("endereco"));
				obj.setNome(rs.getString("nome"));
				obj.setId(rs.getInt("id"));
				obj.setRank(rs.getInt("rank"));
				
		
				list.add(obj);
				
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
	public Cliente findByName(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM cliente WHERE nome = ?;");
			st.setString(1, nome);
			rs = st.executeQuery();
			if(rs.next()) {
				
				Cliente obj = new Cliente();
				
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));;
				obj.setEndereco(rs.getString("endereco"));
				obj.setRank(rs.getInt("rank"));
				
				
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
	
	@Override
	public List<Cliente> findClientesVip() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
							"SELECT * FROM cliente WHERE `rank` = 3;" );
			
			rs = st.executeQuery();

			List<Cliente> list = new ArrayList<>();

			while (rs.next()) {
				
				
				Cliente obj = new Cliente();
				
				obj.setEndereco(rs.getString("endereco"));
				obj.setNome(rs.getString("nome"));
				obj.setId(rs.getInt("id"));
				obj.setRank(rs.getInt("rank"));
				
		
				list.add(obj);
				
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
	
	
	
}
