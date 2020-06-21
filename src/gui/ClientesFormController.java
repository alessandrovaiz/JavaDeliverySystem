package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import model.entidades.Produto;
import model.servicos.ClientesServico;

public class ClientesFormController implements Initializable {
	
	private Cliente entidadeCliente;

	private ClientesServico servicoClientes;


	
	private List<MudaDadosListener> mudaDadosListeners = new ArrayList<>();
	
	@FXML
	private Button btAdicionar;
	@FXML
	private Button btCancelar;

	@FXML
	private TextField textFieldId;
	@FXML
	private TextField textFieldNome;
	@FXML
	private TextField textFieldEndereco;
	
	@FXML
	Label lblErro;

	ArrayList<Cliente> clientes;

	@FXML
	private void onBtAdicionarAcao(ActionEvent evento) {

		if (entidadeCliente == null) {
			throw new IllegalStateException("Cliente não pode ser nulo!");
		}
		
		
		
		if(textFieldNome.getText()==null) {
			Alertas.showAlert("Erro", "O campo nome não pode ser vazio! ", null, AlertType.CONFIRMATION);
			return;
		}
		
		if(textFieldEndereco.getText()==null) {
			Alertas.showAlert("Erro", "O campo endereco não pode ser vazio! ", null, AlertType.CONFIRMATION);
			return;
		} 
		
		
		entidadeCliente = getClienteDados();
		servicoClientes.salvaOuAtualiza(entidadeCliente);
		notificaListeners();
		Utilitarios.palcoAtual(evento).close();

	}

	private void notificaListeners() {
		for(MudaDadosListener listener: mudaDadosListeners) {
			listener.atualizaDados();
		}
		
	}

	
	private Cliente getClienteDados() {
		
		Cliente obj = new Cliente();
		try {
			
			obj.setId(Utilitarios.tentaAttParaInteiro(textFieldId.getText()));
			obj.setNome(textFieldNome.getText());
			obj.setRank(1);
			obj.setEndereco(textFieldEndereco.getText());
			return obj;
			
		} catch (DbException e) {
			Alertas.showAlert("Erro ao inserir pedido", null, e.getMessage(), AlertType.ERROR);
		}
		return null;
		
	}

	

	
	@FXML
	private void onBtCancelarAcao(ActionEvent evento) {
		Utilitarios.palcoAtual(evento).close();
	}

	private void inicializarTabelaClientes() {

		Restricoes.setTextFieldInteger(textFieldId);
		Restricoes.setTextFieldMaxLength(textFieldNome, 50);

	}

	public void atualizaDadosForm() {
		if (entidadeCliente == null) {
			throw new IllegalStateException("Entidade está nula");
		}

		textFieldId.setText(String.valueOf(entidadeCliente.getId()));
		textFieldNome.setText(entidadeCliente.getNome());
		textFieldEndereco.setText(entidadeCliente.getEndereco());
	

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.inicializarTabelaClientes();

	}
	
	
	public void inscreveListener(MudaDadosListener listener) {
		mudaDadosListeners.add(listener);
	}

	

	public void setEntidadeCliente(Cliente entidade) {
		this.entidadeCliente = entidade;
	}

	

	public void setClientesServico(ClientesServico servicoClientes) {
		this.servicoClientes = servicoClientes;
	}

	

}
