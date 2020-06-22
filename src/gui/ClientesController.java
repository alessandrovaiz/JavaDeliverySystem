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
import model.entidades.Cliente;
import model.servicos.ClientesServico;

public class ClientesController implements Initializable, MudaDadosListener {

	private ClientesServico servico;

	@FXML
	private TableView<Cliente> tableViewCliente;
	@FXML
	private TableColumn<Cliente, Integer> tableColumnId;
	@FXML
	private TableColumn<Cliente, String> tableColumnNome;
	@FXML
	private TableColumn<Cliente, String> tableColumnEndereco;
	@FXML
	private TableColumn<Cliente, String> tableColumnRank;
	@FXML
	private TableColumn<Cliente, Double> tableColumnTotal;
	@FXML
	private TableColumn<Cliente, Cliente> tableColumnEditar;
	@FXML
	private TableColumn<Cliente, Cliente> tableColumnExcluir;

	@FXML
	private Button btRegCliente;

	@FXML
	public void onBtRegClienteAction(ActionEvent evento) {
		Stage parentStage = Utilitarios.palcoAtual(evento);
		Cliente cliente = new Cliente();

		criarFormularioDialogo(cliente, "/gui/ClientesFormulario.fxml", parentStage);

	}

	private ObservableList<Cliente> obsList;

	public void setClientesServico(ClientesServico servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tableColumnRank.setCellValueFactory(new PropertyValueFactory<>("rankCliente"));
		// tableColumnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

		Stage stage = (Stage) Main.getMainScene().getWindow(); // downcasting
		tableViewCliente.prefHeightProperty().bind(stage.heightProperty()); // deixa a tabela do tamanho da janela
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalStateException("O serviço está nulo"); // evita que o programador esqueça de injetar o
																	// serviço
		}
		List<Cliente> list = servico.findAll();
		for (Cliente p : list) {
			System.out.println(p);
		}
		obsList = FXCollections.observableArrayList(list);
		tableViewCliente.setItems(obsList);
		initEditarBotoes();
		initExcluirBotoes();
	}

	private void criarFormularioDialogo(Cliente cliente, String nomeAbsoluto, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			Pane painel = loader.load();

			ClientesFormController controller = loader.getController();
			controller.setEntidadeCliente(cliente);
			controller.setClientesServico(new ClientesServico());

			controller.inscreveListener(this);
			controller.atualizaDadosForm();

			Stage stageDialogo = new Stage();
			stageDialogo.setTitle("Dados do Cliente");
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
		tableColumnEditar.setCellFactory(param -> new TableCell<Cliente, Cliente>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Cliente obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> criarFormularioDialogo(obj, "/gui/ClientesFormulario.fxml", Utilitarios.palcoAtual(event)));
			}
		});
	}
	
	private void initExcluirBotoes() {
		tableColumnExcluir.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnExcluir.setCellFactory(param -> new TableCell<Cliente, Cliente>() {
			private final Button button = new Button("Excluir");

			@Override
			protected void updateItem(Cliente obj, boolean empty) {
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

	private void removeEntidade(Cliente obj) {
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