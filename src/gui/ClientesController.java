package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.MudaDadosListener;
import gui.util.Alertas;
import gui.util.Utilitarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
	private TableColumn<Cliente, Integer> tableColumnRank;
	@FXML
	private TableColumn<Cliente, Double> tableColumnTotal;
	
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
		tableColumnRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
		//tableColumnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		

		Stage stage = (Stage) Main.getMainScene().getWindow(); // downcasting
		tableViewCliente.prefHeightProperty().bind(stage.heightProperty()); // deixa a tabela do tamanho da janela
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalStateException("O serviço está nulo"); // evita que o programador esqueça de injetar o
																	// serviço
		}
		List<Cliente> list = servico.findAll();
		for(Cliente p: list) {
			System.out.println(p);
		}
		obsList = FXCollections.observableArrayList(list);
		tableViewCliente.setItems(obsList);
	}

	private void criarFormularioDialogo(Cliente cliente,String nomeAbsoluto, Stage parentStage) {
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

}