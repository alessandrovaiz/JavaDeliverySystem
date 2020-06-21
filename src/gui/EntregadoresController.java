package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.MudaDadosListener;
import gui.util.Alertas;
import gui.util.Utilitarios;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entidades.Entregador;
import model.servicos.EntregadoresServico;

public class EntregadoresController implements Initializable, MudaDadosListener {

	private EntregadoresServico servico;

	@FXML
	private TableView<Entregador> tableViewEntregador;
	@FXML
	private TableColumn<Entregador, Integer> tableColumnId;
	@FXML
	private TableColumn<Entregador, String> tableColumnNome;
	@FXML
	private TableColumn<Entregador, Double> tableColumnCustoEntrega;
	@FXML
	private TableColumn<Entregador, Integer> tableColumnStatus;
	@FXML
	private TableColumn<Entregador, Entregador> tableColumnEditar;
	@FXML
	private TableColumn<Entregador, Entregador> tableColumnExcluir;

	@FXML
	private Button btRegEntregador;

	@FXML
	public void onBtRegEntregadorAction(ActionEvent evento) {
		Stage parentStage = Utilitarios.palcoAtual(evento);
		Entregador entregador = new Entregador();

		criarFormularioDialogo(entregador, "/gui/EntregadoresFormulario.fxml", parentStage);

	}

	private ObservableList<Entregador> obsList;

	public void setEntregadoresServico(EntregadoresServico servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnCustoEntrega.setCellValueFactory(new PropertyValueFactory<>("valorPorEntrega"));
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

		Stage stage = (Stage) Main.getMainScene().getWindow(); // downcasting
		tableViewEntregador.prefHeightProperty().bind(stage.heightProperty()); // deixa a tabela do tamanho da janela
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalStateException("O serviço está nulo"); // evita que o programador esqueça de injetar o
																	// serviço
		}
		List<Entregador> list = servico.findAll();
		for (Entregador p : list) {
			System.out.println(p);
		}
		obsList = FXCollections.observableArrayList(list);
		tableViewEntregador.setItems(obsList);
		initEditarBotoes();
		initExcluirBotoes();
	}

	private void criarFormularioDialogo(Entregador entregador, String nomeAbsoluto, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			Pane painel = loader.load();

			EntregadoresFormController controller = loader.getController();
			controller.setEntidadeEntregador(entregador);
			controller.setEntregadoresServico(new EntregadoresServico());

			controller.inscreveListener(this);
			controller.atualizaDadosForm();

			Stage stageDialogo = new Stage();
			stageDialogo.setTitle("Dados do Entregador");
			stageDialogo.setScene(new Scene(painel));
			stageDialogo.setResizable(false);
			stageDialogo.initOwner(parentStage);
			stageDialogo.initModality(Modality.WINDOW_MODAL);
			stageDialogo.showAndWait();

		} catch (IOException e) {
			Alertas.showAlert("IOException", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void atualizaDados() {
		updateTableView();

	}

	private void initEditarBotoes() {
		tableColumnEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEditar.setCellFactory(param -> new TableCell<Entregador, Entregador>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Entregador obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> criarFormularioDialogo(obj, "/gui/EntregadoresFormulario.fxml",
						Utilitarios.palcoAtual(event)));
			}
		});
	}

	private void initExcluirBotoes() {
		tableColumnExcluir.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnExcluir.setCellFactory(param -> new TableCell<Entregador, Entregador>() {
			private final Button button = new Button("Excluir");

			@Override
			protected void updateItem(Entregador obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(event -> removeEntidade(obj));
			}
		});
	}

	private void removeEntidade(Entregador obj) {
		Optional<ButtonType> resultado = Alertas.showConfirmation("Confirmação", "Você tem certeza que quer excluir?");
		if(resultado.get() == ButtonType.OK) {
			if(servico==null) {
				throw new IllegalStateException("O Serviço está nulo!");
			}
			try{
				servico.remove(obj);
				updateTableView();
			}
			catch(DbIntegrityException e) {
				Alertas.showAlert("Erro removendo o objeto", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

}