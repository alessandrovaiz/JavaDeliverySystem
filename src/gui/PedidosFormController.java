package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import gui.util.Restricoes;
import gui.util.Utilitarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
		
		
		if(entidadeCliente == null) {
			throw new IllegalStateException ("Cliente n�o pode ser nulo!");
		}
		if(entidadePedido == null) {
			throw new IllegalStateException ("Pedido n�o pode ser nulo!");
		}
		if(entidadeEntregador == null) {
			throw new IllegalStateException ("Entregador n�o pode ser nulo!");
		}
		if(entidadeProduto == null) {
			throw new IllegalStateException ("Produto n�o pode ser nulo!");
		}
		if(textFieldEndereco.getText() == "") {
			throw new IllegalStateException ("Endere�o n�o pode ser nulo!");
		}
		if(textFieldQuantidade.getText()=="") {
			throw new IllegalStateException("Quantidade n�o pode ser nulo!");
		}
		
		
			entidadePedido = getPedidoDados();
			
			servicoPedidos.salvaOuAtualiza(entidadePedido);
		
		
	}
	
	private Entregador getEntregadorDados() {
		Entregador entregador = new Entregador();
		if (servicoEntregadores.findByName(textFieldEntregador.getText()) != null) {
			entregador = servicoEntregadores.findByName(textFieldEntregador.getText());
		} else {
			entregador.setNome(textFieldEntregador.getText());
			entregador.setStatus(1);
			entregador.setValorPorEntrega(4.0);

			servicoEntregadores.insert(entregador);
		}
		
		return entregador;

	}

	private Cliente getClienteDados() {
		Cliente cliente = new Cliente();

		Cliente teste = servicoClientes.findByName(textFieldCliente.getText());
		if (teste != null) {
			cliente = servicoClientes.findByName(textFieldCliente.getText());
		} else {
			cliente.setNome(textFieldCliente.getText());
			cliente.setRank(1);
			cliente.setEndereco(textFieldEndereco.getText());

			servicoClientes.insert(cliente);

		}
		
		return cliente;
	}
	
	private Produto getProdutoDados() {
		String valor;
		Produto produto = new Produto();
		
		
		if (servicoProdutos.findByName(textFieldProduto.getText()) != null) {
			produto = servicoProdutos.findByName(textFieldProduto.getText());

		} else {
			produto.setNome(textFieldProduto.getText());
			valor = JOptionPane.showInputDialog("Insira o valor do produto!");
			produto.setValor(Double.parseDouble(valor));
			produto.setQtd(Utilitarios.tentaAttParaInteiro(textFieldQuantidade.getText()));
			
			servicoProdutos.insert(produto);
		}
		return produto;
	}
	
	private Pedido getPedidoDados() {
		Pedido obj = new Pedido();
	

		Cliente cliente = getClienteDados();

		Produto produto = getProdutoDados();
		
		Entregador entregador = getEntregadorDados();
		
		

		obj.setId(Utilitarios.tentaAttParaInteiro(textFieldId.getText()));
		obj.setCliente(servicoClientes.findByName(cliente.getNome()));
		obj.setEntregador(servicoEntregadores.findByName(entregador.getNome()));
		obj.setProdutos(servicoProdutos.findByName(produto.getNome()));
		obj.setTotal(produto.getValor()*Utilitarios.tentaAttParaInteiro(textFieldQuantidade.getText()));
		obj.setEnderecoEntrega(textFieldEndereco.getText());
		obj.setQtd(Utilitarios.tentaAttParaInteiro(textFieldQuantidade.getText()));
		obj.setStatus(1);

		return obj;
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

	public void setEntidadePedido(Pedido entidade) {
		this.entidadePedido=entidade;
	}

	public void setEntidadeCliente(Cliente entidade) {
		this.entidadeCliente=entidade;
	}

	public void setEntidadeEntregador(Entregador entidade) {
		this.entidadeEntregador=entidade;
	}

	public void setEntidadeProduto(Produto entidade) {
		this.entidadeProduto=entidade;
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
