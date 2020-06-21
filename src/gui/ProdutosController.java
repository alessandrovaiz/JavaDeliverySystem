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
import model.entidades.Produto;
import model.servicos.ProdutosServico;

public class ProdutosController implements Initializable, MudaDadosListener {

	private ProdutosServico servico;

	@FXML
	private TableView<Produto> tableViewProduto;
	@FXML
	private TableColumn<Produto, Integer> tableColumnId;
	@FXML
	private TableColumn<Produto, String> tableColumnNome;
	@FXML
	private TableColumn<Produto, Double> tableColumnValor;
	@FXML
	private TableColumn<Produto,Integer> tableColumnQtd;
	@FXML
	private TableColumn<Produto, Produto> tableColumnEditar;
	@FXML
	private TableColumn<Produto, Produto> tableColumnExcluir;

	@FXML
	private Button btRegProduto;

	@FXML
	public void onBtRegProdutoAction(ActionEvent evento) {
		Stage parentStage = Utilitarios.palcoAtual(evento);
		Produto produto = new Produto();

		criarFormularioDialogo(produto, "/gui/ProdutosFormulario.fxml", parentStage);

	}

	private ObservableList<Produto> obsList;

	public void setProdutosServico(ProdutosServico servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		tableColumnQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
	

		Stage stage = (Stage) Main.getMainScene().getWindow(); // downcasting
		tableViewProduto.prefHeightProperty().bind(stage.heightProperty()); // deixa a tabela do tamanho da janela
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalStateException("O serviço está nulo"); // evita que o programador esqueça de injetar o
																	// serviço
		}
		List<Produto> list = servico.findAll();
		for (Produto p : list) {
			System.out.println(p);
		}
		obsList = FXCollections.observableArrayList(list);
		tableViewProduto.setItems(obsList);
		initEditarBotoes();
		initExcluirBotoes();
	}

	private void criarFormularioDialogo(Produto produto, String nomeAbsoluto, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			Pane painel = loader.load();

			ProdutosFormController controller = loader.getController();
			controller.setEntidadeProduto(produto);
			controller.setProdutosServico(new ProdutosServico());

			controller.inscreveListener(this);
			controller.atualizaDadosForm();

			Stage stageDialogo = new Stage();
			stageDialogo.setTitle("Dados do Produto");
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
		tableColumnEditar.setCellFactory(param -> new TableCell<Produto, Produto>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Produto obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				
				
				
				
				button.setOnAction(
						event -> criarFormularioDialogo(obj, "/gui/ProdutosFormulario.fxml", Utilitarios.palcoAtual(event)));
			}
			
		});
	}
	
	private void initExcluirBotoes() {
		tableColumnExcluir.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnExcluir.setCellFactory(param -> new TableCell<Produto, Produto>() {
			private final Button button = new Button("Excluir");

			@Override
			protected void updateItem(Produto obj, boolean empty) {
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

	private void removeEntidade(Produto obj) {
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