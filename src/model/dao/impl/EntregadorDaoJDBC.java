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
import model.dao.EntregadorDao;
import model.entidades.Entregador;

public class EntregadorDaoJDBC implements EntregadorDao{

	private Connection conn;
	
	public EntregadorDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Entregador obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO Entregador " +
				"(nome,custo_p_entrega,`status`) " +
				"VALUES " +
				"(?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setDouble(2, obj.getValorPorEntrega());
			st.setInt(3, obj.getStatus());
			
			

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
	public void update(Entregador obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE entregador " +
				"SET nome = ?, custo_p_entrega=?,`status`=?" +
				" WHERE id = ?");
			
			
			st.setString(1, obj.getNome());
			st.setDouble(2, obj.getValorPorEntrega());
			st.setInt(3, obj.getStatus());
			
			
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
				"DELETE FROM entregador WHERE id = ?");

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
	public Entregador findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM entregador WHERE id = ?;");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
			
				Entregador obj = new Entregador();
				
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
				obj.setValorPorEntrega(rs.getDouble("custo_p_entrega"));
				obj.setStatus(rs.getInt("`status`"));
				
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
	public List<Entregador> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
							"SELECT * FROM entregador; " );
			
			rs = st.executeQuery();

			List<Entregador> list = new ArrayList<>();

			while (rs.next()) {
				
				Entregador obj = new Entregador();
				
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
				obj.setValorPorEntrega(rs.getDouble("custo_p_entrega"));
				obj.setStatus(rs.getInt("status"));
				
				
				
				
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
	public List<Entregador> findEntregadoresDisp() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
							"SELECT * FROM entregador WHERE status = 1; " );
			
			rs = st.executeQuery();

			List<Entregador> list = new ArrayList<>();

			while (rs.next()) {
				
				Entregador obj = new Entregador();
				
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
				obj.setValorPorEntrega(rs.getDouble("custo_p_entrega"));
				obj.setStatus(rs.getInt("status"));
				
				
				
				
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
	public Entregador findByName(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM entregador WHERE nome = ?;");
			st.setString(1, nome);
			rs = st.executeQuery();
			if(rs.next()) {
				
				Entregador obj = new Entregador();
				
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
				obj.setValorPorEntrega(rs.getDouble("custo_p_entrega"));
				obj.setStatus(rs.getInt("status"));
				
				
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
