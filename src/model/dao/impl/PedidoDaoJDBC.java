package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
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
		
		
	}

	@Override
	public void update(Pedido obj) {
		
		
	}

	@Override
	public void deleteById(Integer id) {
		
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
							"cliente.rank as rankCliente, " + 
							"produto.nome as nomeProduto, produto.valor as valorProduto,produto.quantidade as qtdProduto, " + 
							"entregador.nome as nomeEntregador, entregador.custo_p_entrega as custoEntregador " + 
							"FROM pedido " + 
							"INNER JOIN cliente ON pedido.cliente_id = cliente_id " + 
							"INNER JOIN produto ON pedido.produto_id = produto_id " + 
							"INNER JOIN entregador ON pedido.entregador_id " );
			
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
	
	
	
}
