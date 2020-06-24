package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.MudaDadosListener;
import gui.util.Alertas;
import gui.util.Utilitarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import model.entidades.Cliente;
import model.entidades.enums.RankCliente;
import model.servicos.ClientesServico;

public class AlteraRankClienteController implements Initializable {

	
	private Cliente entidadeCliente;

	private ClientesServico servicoClientes;
	

	
	private List<MudaDadosListener> mudaDadosListeners = new ArrayList<>();
	
	@FXML
	private Button btSalvar;
	@FXML
	private Button btCancelar;

	
	@FXML
	private ComboBox<RankCliente> cbxStatus;
	
	private ObservableList<RankCliente> obsList = FXCollections.observableArrayList();
	

	ArrayList<Cliente> Clientes;

	@FXML
	private void onBtAdicionarAcao(ActionEvent evento) {
		
		if (entidadeCliente == null) {
			throw new IllegalStateException("Cliente não pode ser nulo!");
		}
		
		if (cbxStatus.getValue() == null) {
			Alertas.showAlert("Erro", "O campo não pode ser vazio! ", null, AlertType.ERROR);
		} else {
			entidadeCliente.setRank(cbxStatus.getValue().getValor());
			servicoClientes.atualizaRank(entidadeCliente);
			System.out.println(cbxStatus.getValue().getValor());
			notificaListeners();
			Utilitarios.palcoAtual(evento).close();
		}

		

	}

	private void notificaListeners() {
		for(MudaDadosListener listener: mudaDadosListeners) {
			listener.atualizaDados();
		}
		
	}


	

	

	

	@FXML
	private void onBtCancelarAcao(ActionEvent evento) {
		Utilitarios.palcoAtual(evento).close();
	}
	


	private void inicializarTabelaClientes() {
				
	}

	public void atualizaDadosForm() {
		if (entidadeCliente == null) {
			throw new IllegalStateException("Entidade está nula");
		}
		
		obsList.add(RankCliente.CLIENTE_NOVO);
		obsList.add(RankCliente.CLIENTE_FREQUENTE);
		obsList.add(RankCliente.CLIENTE_VIP);
		
		cbxStatus.setItems(obsList);
		

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
