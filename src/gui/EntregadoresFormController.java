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
import model.entidades.Entregador;
import model.servicos.EntregadoresServico;

public class EntregadoresFormController implements Initializable {
	
	private Entregador entidadeEntregador;

	private EntregadoresServico servicoEntregadores;


	
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
	private TextField textFieldValorEntrega;
	
	@FXML
	Label lblErro;

	ArrayList<Entregador> Entregadores;

	@FXML
	private void onBtAdicionarAcao(ActionEvent evento) {

		if (entidadeEntregador == null) {
			throw new IllegalStateException("Entregador não pode ser nulo!");
		}
		
		
		
		if(textFieldNome.getText()==null) {
			Alertas.showAlert("Erro", "O campo nome não pode ser vazio! ", null, AlertType.CONFIRMATION);
			return;
		}
		
		if(textFieldValorEntrega.getText().length()<1) {
			Alertas.showAlert("Erro", "O campo Valor Por Entrega não pode ser vazio! ", null, AlertType.CONFIRMATION);
			return;
		} 
		
		
		entidadeEntregador = getEntregadorDados();
		servicoEntregadores.salvaOuAtualiza(entidadeEntregador);
		notificaListeners();
		Utilitarios.palcoAtual(evento).close();

	}

	private void notificaListeners() {
		for(MudaDadosListener listener: mudaDadosListeners) {
			listener.atualizaDados();
		}
		
	}

	
	private Entregador getEntregadorDados() {
		
		Entregador obj = new Entregador();
		try {
			
			obj.setId(Utilitarios.tentaAttParaInteiro(textFieldId.getText()));
			obj.setNome(textFieldNome.getText());
			obj.setStatus(1);
			obj.setValorPorEntrega(Double.parseDouble(textFieldValorEntrega.getText()));
			
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

	private void inicializarTabelaEntregadores() {

		Restricoes.setTextFieldInteger(textFieldId);
		Restricoes.setTextFieldMaxLength(textFieldNome, 50);
		Restricoes.setTextFieldDouble(textFieldValorEntrega);

	}

	public void atualizaDadosForm() {
		if (entidadeEntregador == null) {
			throw new IllegalStateException("Entidade está nula");
		}

		textFieldId.setText(String.valueOf(entidadeEntregador.getId()));
		textFieldNome.setText(entidadeEntregador.getNome());
		textFieldValorEntrega.setText(String.valueOf(entidadeEntregador.getValorPorEntrega()));
	

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.inicializarTabelaEntregadores();

	}
	
	
	public void inscreveListener(MudaDadosListener listener) {
		mudaDadosListeners.add(listener);
	}

	

	public void setEntidadeEntregador(Entregador entidade) {
		this.entidadeEntregador = entidade;
	}

	

	public void setEntregadoresServico(EntregadoresServico servicoEntregadores) {
		this.servicoEntregadores = servicoEntregadores;
	}

	

}
