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
import model.dao.PedidoDao;
import model.entidades.Cliente;
import model.entidades.Entregador;
import model.entidades.Pedido;
import model.entidades.Produto;

public class PedidoDaoJDBC implements PedidoDao{

	private Connection conn;
	
	public PedidoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Pedido obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO pedido " +
				"(qtd,`status`,valor,cliente_id,entregador_id,produto_id) " +
				"VALUES " +
				"(?,?,?,?,?,?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, obj.getQtd());
			st.setInt(2, obj.getStatus());
			st.setDouble(3, obj.getTotal());
			st.setInt(4, obj.getCliente().getId());
			st.setInt(5, obj.getEntregador().getId());
			st.setInt(6,obj.getProdutos().getId());
			

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
	public void update(Pedido obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE pedido " +
				"SET qtd = ?,`status`=?,valor=?,cliente_id=?,entregador_id=?,produto_id=? " +
				"WHERE id = ?");

			st.setInt(1, obj.getQtd());
			st.setInt(2, obj.getStatus());
			st.setDouble(3, obj.getTotal());
			st.setInt(4, obj.getCliente().getId());
			st.setInt(5, obj.getEntregador().getId());
			st.setInt(6,obj.getProdutos().getId());
			
			st.setInt(7, obj.getId());

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
				"DELETE FROM pedido WHERE Id = ?");

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
	public Pedido findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT pedido.*,cliente.endereco as enderecoCliente, cliente.nome as nomeCliente,\r\n" + 
					"cliente.rank as rankCliente, \r\n" + 
					"produto.nome as nomeProduto, produto.valor as valorProduto,produto.quantidade as qtdProduto,\r\n" + 
					"entregador.nome as nomeEntregador, entregador.custo_p_entrega as custoEntregador\r\n" + 
					"FROM pedido \r\n" + 
					"INNER JOIN cliente ON pedido.cliente_id = cliente_id\r\n" + 
					"INNER JOIN produto ON pedido.produto_id = produto_id\r\n" + 
					"INNER JOIN entregador ON pedido.entregador_id = entregador_id WHERE pedido.id = ?;");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Produto prod = new Produto();
				prod.setId(rs.getInt("produto_id"));
				prod.setNome(rs.getString("nomeProduto"));
				prod.setQtd(rs.getInt("qtdProduto"));
				prod.setValor(rs.getDouble("valorProduto"));
				
				Cliente cliente = new Cliente();
				
				cliente.setEndereco(rs.getString("enderecoCliente"));
				cliente.setNome(rs.getString("nomeCliente"));
				cliente.setId(rs.getInt("cliente_id"));
				cliente.setRank(rs.getInt("rankCliente"));
				
				Entregador entregador = new Entregador();
				
				entregador.setId(rs.getInt("entregador_id"));
				entregador.setNome(rs.getString("nomeEntregador"));
				entregador.setValorPorEntrega(rs.getDouble("custoEntregador"));
				
				
				Pedido obj = new Pedido();
				
				obj.setId(rs.getInt("id"));
				obj.setCliente(cliente);
				obj.setEnderecoEntrega(rs.getString("enderecoCliente"));
				obj.setProdutos(prod);
				obj.setQtd(rs.getInt("qtd"));
				obj.setStatus(rs.getInt("status"));
				obj.setTotal(rs.getDouble("valor"));
				
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
	public List<Pedido> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
							"SELECT pedido.*,cliente.endereco as enderecoCliente, cliente.nome as nomeCliente," + 
							"							cliente.rank as rankCliente," + 
							"							produto.nome as nomeProduto, produto.valor as valorProduto,produto.quantidade as qtdProduto," + 
							"							entregador.nome as nomeEntregador, entregador.custo_p_entrega as custoEntregador \r\n" + 
							"							FROM pedido " + 
							"							INNER JOIN cliente ON pedido.cliente_id = cliente.id " + 
							"							INNER JOIN produto ON pedido.produto_id = produto.id " + 
							"							INNER JOIN entregador ON pedido.entregador_id = entregador.id" );
			
			rs = st.executeQuery();

			List<Pedido> list = new ArrayList<>();

			while (rs.next()) {
				
				Produto prod = new Produto();
				
				prod.setId(rs.getInt("produto_id"));
				prod.setNome(rs.getString("nomeProduto"));
				prod.setQtd(rs.getInt("qtdProduto"));
				prod.setValor(rs.getDouble("valorProduto"));
				
				Cliente cliente = new Cliente();
				
				cliente.setEndereco(rs.getString("enderecoCliente"));
				cliente.setNome(rs.getString("nomeCliente"));
				cliente.setId(rs.getInt("cliente_id"));
				cliente.setRank(rs.getInt("rankCliente"));
				
				Entregador entregador = new Entregador();
				
				entregador.setId(rs.getInt("entregador_id"));
				entregador.setNome(rs.getString("nomeEntregador"));
				entregador.setValorPorEntrega(rs.getDouble("custoEntregador"));
				
				
				Pedido obj = new Pedido();
				
				
				
				
				obj.setId(rs.getInt("id"));
				obj.setCliente(cliente);
				obj.setEnderecoEntrega(rs.getString("enderecoCliente"));
				obj.setProdutos(prod);
				obj.setEntregador(entregador);
				obj.setQtd(rs.getInt("qtd"));
				obj.setStatus(rs.getInt("status"));
				obj.setTotal(rs.getDouble("valor"));
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
	public List<Pedido> findEntregasEmAndamento() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
							"SELECT pedido.*,cliente.endereco as enderecoCliente, cliente.nome as nomeCliente," + 
							"							cliente.rank as rankCliente," + 
							"							produto.nome as nomeProduto, produto.valor as valorProduto,produto.quantidade as qtdProduto," + 
							"							entregador.nome as nomeEntregador, entregador.custo_p_entrega as custoEntregador \r\n" + 
							"							FROM pedido " + 
							"							INNER JOIN cliente ON pedido.cliente_id = cliente.id " + 
							"							INNER JOIN produto ON pedido.produto_id = produto.id " + 
							"							INNER JOIN entregador ON pedido.entregador_id = entregador.id WHERE pedido.status = 3;" );
			
			rs = st.executeQuery();

			List<Pedido> list = new ArrayList<>();

			while (rs.next()) {
				
				
				
				Cliente cliente = new Cliente();
				
				cliente.setEndereco(rs.getString("enderecoCliente"));
				cliente.setNome(rs.getString("nomeCliente"));
				cliente.setId(rs.getInt("cliente_id"));
				cliente.setRank(rs.getInt("rankCliente"));
				
				Entregador entregador = new Entregador();
				
				entregador.setId(rs.getInt("entregador_id"));
				entregador.setNome(rs.getString("nomeEntregador"));
				entregador.setValorPorEntrega(rs.getDouble("custoEntregador"));
				
				
				Pedido obj = new Pedido();
				
				
				
				
				obj.setId(rs.getInt("id"));
				obj.setCliente(cliente);
				obj.setEnderecoEntrega(rs.getString("enderecoCliente"));
			
				obj.setEntregador(entregador);
				obj.setQtd(rs.getInt("qtd"));
				obj.setStatus(rs.getInt("status"));
				obj.setTotal(rs.getDouble("valor"));
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
	public Double findCustoEntregadores() {
		PreparedStatement st = null;
		ResultSet rs = null;
		Double custoEntregadores = (double) 0;
		
		try {
			st = conn.prepareStatement(
							"SELECT  pedido.*," + 
							"entregador.nome as nomeEntregador, entregador.custo_p_entrega as custoEntregador " + 
							"FROM pedido " + 
							"INNER JOIN entregador ON pedido.entregador_id = entregador.id ;" );
			
			rs = st.executeQuery();

			

			while (rs.next()) {
				
				
				custoEntregadores+=rs.getDouble("custoEntregador");
				
			}
			return custoEntregadores;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	
	}
	
	public void atualizaStatus(Pedido obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE pedido " +
				"SET `status`=? " +
				"WHERE id = ?");

			st.setInt(1, obj.getStatus());
			
			st.setInt(2, obj.getId());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}	
	}
		

