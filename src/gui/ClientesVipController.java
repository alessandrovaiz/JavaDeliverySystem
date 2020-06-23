package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.MudaDadosListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entidades.Cliente;

import model.servicos.ClientesServico;

public class ClientesVipController implements Initializable, MudaDadosListener {

	private ClientesServico servico;

	@FXML
	private TableView<Cliente> tableViewClientesVip;
	@FXML
	private TableColumn<Cliente, Integer> tableColumnId;
	@FXML
	private TableColumn<Cliente, String> tableColumnNome;



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
		
		

		Stage stage = (Stage) Main.getMainScene().getWindow(); // downcasting
		tableViewClientesVip.prefHeightProperty().bind(stage.heightProperty()); // deixa a tabela do tamanho da janela
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalStateException("O serviço está nulo"); // evita que o programador esqueça de injetar o
																	// serviço
		}
		List<Cliente> list = servico.findClientesVip();
		for (Cliente p : list) {
			System.out.println(p);
		}
		obsList = FXCollections.observableArrayList(list);
		tableViewClientesVip.setItems(obsList);

	}

	

	@Override
	public void atualizaDados() {
		updateTableView();

	}

	
}