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
import model.entidades.Entregador;
import model.entidades.enums.StatusEntregador;
import model.servicos.EntregadoresServico;

public class AlteraStatusEntregadorController implements Initializable {

	
	private Entregador entidadeEntregador;

	private EntregadoresServico servicoEntregadores;
	

	
	private List<MudaDadosListener> mudaDadosListeners = new ArrayList<>();
	
	@FXML
	private Button btSalvar;
	@FXML
	private Button btCancelar;

	
	@FXML
	private ComboBox<StatusEntregador> cbxStatus;
	
	private ObservableList<StatusEntregador> obsList = FXCollections.observableArrayList();
	

	ArrayList<Entregador> Entregadors;

	@FXML
	private void onBtAdicionarAcao(ActionEvent evento) {
		
		if (entidadeEntregador == null) {
			throw new IllegalStateException("Entregador não pode ser nulo!");
		}
		
		if (cbxStatus.getValue() == null) {
			Alertas.showAlert("Erro", "O campo não pode ser vazio! ", null, AlertType.ERROR);
		} else {
			entidadeEntregador.setStatus(cbxStatus.getValue().getValor());
			servicoEntregadores.atualizaStatus(entidadeEntregador);
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
	


	private void inicializarTabelaEntregadors() {
				
	}

	public void atualizaDadosForm() {
		if (entidadeEntregador == null) {
			throw new IllegalStateException("Entidade está nula");
		}
		
		obsList.add(StatusEntregador.LIVRE);
		obsList.add(StatusEntregador.OCUPADO);
		
		
		cbxStatus.setItems(obsList);
		

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.inicializarTabelaEntregadors();

	}
	
	
	public void inscreveListener(MudaDadosListener listener) {
		mudaDadosListeners.add(listener);
	}

	public void setEntidadeEntregador(Entregador entidade) {
		this.entidadeEntregador = entidade;
	}

	

	public void setEntregadoresServico(EntregadoresServico servicoEntregadors) {
		this.servicoEntregadores = servicoEntregadors;
	}

	

}
