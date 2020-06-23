package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import db.DbException;
import gui.listeners.MudaDadosListener;
import gui.util.Alertas;
import gui.util.Restricoes;
import gui.util.Utilitarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Cliente;
import model.entidades.Entregador;
import model.entidades.Pedido;
import model.entidades.Produto;
import model.servicos.ClientesServico;
import model.servicos.EntregadoresServico;
import model.servicos.PedidosServico;
import model.servicos.ProdutosServico;

public class PedidosFormController implements Initializable {

	private Pedido entidadePedido;
	private Produto entidadeProduto;
	private Cliente entidadeCliente;
	private Entregador entidadeEntregador;

	private PedidosServico servicoPedidos;
	private ClientesServico servicoClientes;
	private EntregadoresServico servicoEntregadores;
	private ProdutosServico servicoProdutos;

	
	private List<MudaDadosListener> mudaDadosListeners = new ArrayList<>();
	
	@FXML
	private Button btAdicionar;
	@FXML
	private Button btCancelar;

	@FXML
	private TextField textFieldId;
	@FXML
	private TextField textFieldCliente;
	@FXML
	private TextField textFieldEntregador;
	@FXML
	private TextField textFieldProduto;
	@FXML
	private TextField textFieldQuantidade;
	@FXML
	private TextField textFieldEndereco;

	@FXML
	Label lblErro;

	ArrayList<Pedido> pedidos;

	@FXML
	private void onBtAdicionarAcao(ActionEvent evento) {


		if (entidadeCliente == null) {
			throw new IllegalStateException("Cliente n�o pode ser nulo!");
		}
		if (entidadePedido == null) {
			throw new IllegalStateException("Pedido n�o pode ser nulo!");
		}
		if (entidadeEntregador == null) {
			throw new IllegalStateException("Entregador n�o pode ser nulo!");
		}
		if (entidadeProduto == null) {
			throw new IllegalStateException("Produto n�o pode ser nulo!");
		}
		
		if(textFieldCliente.getText()==null) {
			Alertas.showAlert("Erro", "O campo cliente n�o pode ser vazio! ", null, AlertType.ERROR);
			
			return;
		}
		
		if(textFieldEntregador.getText()==null) {
			Alertas.showAlert("Erro", "O campo entregador n�o pode ser vazio! ", null, AlertType.ERROR);
			return;
		}
		
		if (textFieldEndereco.getText() == null) {
			Alertas.showAlert("Erro", "O campo endere�o n�o pode ser vazio! ", null, AlertType.ERROR);
			return;
			
		}
		
		if(textFieldProduto.getText()==null) {
			Alertas.showAlert("Erro", "O campo produto n�o pode ser vazio! ", null, AlertType.ERROR);
			return;
		}
		if(textFieldQuantidade.getText().length()<1) {
			Alertas.showAlert("Erro", "O campo quantidade n�o pode ser vazio! ", null, AlertType.ERROR);
			return;
		} 
		
		
		entidadePedido = getPedidoDados();
		
		servicoPedidos.salvaOuAtualiza(entidadePedido);
		notificaListeners();
		Utilitarios.palcoAtual(evento).close();

	}

	private void notificaListeners() {
		for(MudaDadosListener listener: mudaDadosListeners) {
			listener.atualizaDados();
		}
		
	}

	private Entregador getEntregadorDados() {
		Entregador entregador = new Entregador();
		String valor;
		try {

			if (servicoEntregadores.findByName(textFieldEntregador.getText()) != null) {
				entregador = servicoEntregadores.findByName(textFieldEntregador.getText());
				
				return entregador;
			} else {
				entregador.setNome(textFieldEntregador.getText());
				entregador.setStatus(1);
				entregador.setValorPorEntrega(4.0);
				
				valor = JOptionPane.showInputDialog("Insira o valor de entrega deste entregador!");
				entregador.setValorPorEntrega(Double.parseDouble(valor));
				servicoEntregadores.insert(entregador);
				this.entidadeEntregador = entregador;
				return entregador;
			}

		} catch (DbException e) {
			Alertas.showAlert("Erro ao inserir entregador", null, e.getMessage(), AlertType.ERROR);
		}

		return null;
	}

	private Cliente getClienteDados() {
		Cliente cliente = new Cliente();
		
		try {
			if (servicoClientes.findByName(textFieldCliente.getText()) != null) {
				cliente = servicoClientes.findByName(textFieldCliente.getText());
				return cliente;
			} else  {
				cliente.setNome(textFieldCliente.getText());
				cliente.setRank(1);
				cliente.setEndereco(textFieldEndereco.getText());

				servicoClientes.insert(cliente);
				this.entidadeCliente = cliente;
				return cliente;
			} 
		} catch (DbException e) {
			Alertas.showAlert("Erro ao inserir cliente", null, e.getMessage(), AlertType.ERROR);
		}
		return null;
	}

	private Produto getProdutoDados() {
		String valor;
		Produto produto = new Produto();
		
		try {
			if (servicoProdutos.findByName(textFieldProduto.getText()) != null) {
				produto = servicoProdutos.findByName(textFieldProduto.getText());
				
				return produto;
			} else {
				produto.setNome(textFieldProduto.getText());
				valor = JOptionPane.showInputDialog("Insira o valor do produto!");
				produto.setValor(Double.parseDouble(valor));
				produto.setQtd(Integer.parseInt(textFieldQuantidade.getText()));
	
				servicoProdutos.insert(produto);
				this.entidadeProduto = produto;
				return produto;
			} 
		} catch(DbException e) {
			Alertas.showAlert("Erro ao inserir produto", null, e.getMessage(), AlertType.ERROR);
		}
		return null;
	}

	private Pedido getPedidoDados() {
		Pedido obj = new Pedido();

		try {
			

			
			Produto produto = new Produto();
			Entregador entregador = new Entregador();
			Cliente cliente = new Cliente();
			
			if((getClienteDados()!= null) && (getProdutoDados()!= null) && (getEntregadorDados()!= null)) {
				cliente = getClienteDados();
				produto = getProdutoDados();
				entregador = getEntregadorDados();
			} else {
				Alertas.showAlert("Erro!!", null, "Existem entidades vazias!!", AlertType.ERROR);
				return null;
			}
			
			
	
			
		
	
			obj.setId(Utilitarios.tentaAttParaInteiro(textFieldId.getText()));
			obj.setCliente(servicoClientes.findByName(cliente.getNome()));
			obj.setEntregador(servicoEntregadores.findByName(entregador.getNome()));
			obj.setProdutos(servicoProdutos.findByName(produto.getNome()));
			obj.setTotal(produto.getValor() * Utilitarios.tentaAttParaInteiro(textFieldQuantidade.getText()) + entregador.getValorPorEntrega());
			obj.setEnderecoEntrega(textFieldEndereco.getText());
			obj.setQtd(Utilitarios.tentaAttParaInteiro(textFieldQuantidade.getText()));
			
			
			obj.setStatus(1);
			
			return obj;
		} catch(DbException e) {
			Alertas.showAlert("Erro ao inserir pedido", null, e.getMessage(), AlertType.ERROR);
		}
		return null;
	}

	@FXML
	private void onBtCancelarAcao(ActionEvent evento) {
		Utilitarios.palcoAtual(evento).close();
	}

	private void inicializarTabelaPedidos() {

		Restricoes.setTextFieldInteger(textFieldId);
		Restricoes.setTextFieldInteger(textFieldQuantidade);
		Restricoes.setTextFieldMaxLength(textFieldCliente, 50);

	}

	public void atualizaDadosForm() {
		if ((entidadePedido == null) || (entidadeProduto == null) || (entidadeCliente == null)
				|| (entidadeEntregador == null)) {
			throw new IllegalStateException("Entidade est� nula");
		}

		textFieldId.setText(String.valueOf(entidadePedido.getId()));
		textFieldCliente.setText(entidadeCliente.getNome());
		textFieldEndereco.setText(entidadeCliente.getEndereco());
		textFieldEntregador.setText(entidadeEntregador.getNome());
		textFieldProduto.setText(entidadeProduto.getNome());

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.inicializarTabelaPedidos();

	}
	
	
	public void inscreveListener(MudaDadosListener listener) {
		mudaDadosListeners.add(listener);
	}

	public void setEntidadePedido(Pedido entidade) {
		this.entidadePedido = entidade;
	}

	public void setEntidadeCliente(Cliente entidade) {
		this.entidadeCliente = entidade;
	}

	public void setEntidadeEntregador(Entregador entidade) {
		this.entidadeEntregador = entidade;
	}

	public void setEntidadeProduto(Produto entidade) {
		this.entidadeProduto = entidade;
	}

	public void setPedidosServico(PedidosServico servicoPedidos) {
		this.servicoPedidos = servicoPedidos;
	}

	public void setClientesServico(ClientesServico servicoClientes) {
		this.servicoClientes = servicoClientes;
	}

	public void setProdutosServico(ProdutosServico servicoProdutos) {
		this.servicoProdutos = servicoProdutos;
	}

	public void setEntregadoresServico(EntregadoresServico servicoEntregadores) {
		this.servicoEntregadores = servicoEntregadores;
	}

}
