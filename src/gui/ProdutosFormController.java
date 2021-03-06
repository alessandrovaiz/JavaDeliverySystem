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
import model.entidades.Produto;
import model.servicos.ProdutosServico;

public class ProdutosFormController implements Initializable {

	private Produto entidadeProduto;

	private ProdutosServico servicoProdutos;

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
	private TextField textFieldValor;
	@FXML
	private TextField textFieldQtd;

	@FXML
	Label lblErro;

	ArrayList<Produto> Produtos;

	@FXML
	private void onBtAdicionarAcao(ActionEvent evento) {

		if (entidadeProduto == null) {
			throw new IllegalStateException("Produto n�o pode ser nulo!");
		}

		if (textFieldNome.getText() == null) {
			Alertas.showAlert("Erro", "O campo nome n�o pode ser vazio! ", null, AlertType.CONFIRMATION);
			return;
		}

		if (textFieldValor.getText().length() < 1) {
			Alertas.showAlert("Erro", "O campo Valor n�o pode ser vazio! ", null, AlertType.CONFIRMATION);
			return;
		}
		if (textFieldQtd.getText().length() < 1) {
			Alertas.showAlert("Erro", "O campo Estoque n�o pode ser vazio! ", null, AlertType.ERROR);
			return;
		}

		entidadeProduto = getProdutoDados();
		servicoProdutos.salvaOuAtualiza(entidadeProduto);
		notificaListeners();
		Utilitarios.palcoAtual(evento).close();

	}

	private void notificaListeners() {
		for (MudaDadosListener listener : mudaDadosListeners) {
			listener.atualizaDados();
		}

	}

	private Produto getProdutoDados() {

		Produto obj = new Produto();
		try {
			obj.setId(Utilitarios.tentaAttParaInteiro(textFieldId.getText()));
			obj.setNome(textFieldNome.getText());
			obj.setQtd(Utilitarios.tentaAttParaInteiro(textFieldQtd.getText()));
			obj.setValor(Double.parseDouble(textFieldValor.getText()));

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

	private void inicializarTabelaProdutos() {

		Restricoes.setTextFieldInteger(textFieldId);
		Restricoes.setTextFieldDouble(textFieldValor);
		Restricoes.setTextFieldInteger(textFieldQtd);
		Restricoes.setTextFieldMaxLength(textFieldNome, 50);

	}

	public void atualizaDadosForm() {
		if (entidadeProduto == null) {
			throw new IllegalStateException("Entidade est� nula");
		}

		textFieldId.setText(String.valueOf(entidadeProduto.getId()));
		textFieldNome.setText(entidadeProduto.getNome());
		textFieldValor.setText(String.valueOf(entidadeProduto.getValor()));
		textFieldQtd.setText(String.valueOf(entidadeProduto.getQtd()));

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.inicializarTabelaProdutos();

	}

	public void inscreveListener(MudaDadosListener listener) {
		mudaDadosListeners.add(listener);
	}

	public void setEntidadeProduto(Produto entidade) {
		this.entidadeProduto = entidade;
	}

	public void setProdutosServico(ProdutosServico servicoProdutos) {
		this.servicoProdutos = servicoProdutos;
	}

}
