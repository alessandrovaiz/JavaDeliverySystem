package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.SpringLayout.Constraints;

import application.Main;
import gui.util.Restricoes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entidades.Cliente;
import model.entidades.Entregador;
import model.entidades.Pedido;
import model.entidades.Produto;
import model.servicos.PedidosServico;

public class PedidosFormController implements Initializable {


	
	private Pedido entidade;
	private PedidosServico servico;
	

	
	@FXML
	private Button btAdicionar;
	@FXML
	private Button btCancelar;

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
	
	
	@FXML Label lblErro;
	
	ArrayList<Pedido> pedidos;

	
	
	

	@FXML
	private void onBtAdicionarAcao(ActionEvent evento) {
		System.out.println("teste");
	}
	
	
	@FXML
	private void onBtCancelarAcao() {
		System.out.println("teste");
	}

	private void inicializarTabelaPedidos() {
		
		Restricoes.setTextFieldInteger(textFieldQuantidade);
		Restricoes.setTextFieldMaxLength(textFieldCliente, 50);

	}

	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		this.inicializarTabelaPedidos();
	
		
	
		
	}



}
