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
import model.entidades.Entregador;
import model.servicos.EntregadoresServico;

public class EntregadoresDisponiveisController implements Initializable, MudaDadosListener {

	private EntregadoresServico servico;

	@FXML
	private TableView<Entregador> tableViewEntregadoresDisp;
	@FXML
	private TableColumn<Entregador, Integer> tableColumnId;
	@FXML
	private TableColumn<Entregador, String> tableColumnNome;
	@FXML
	private TableColumn<Entregador, Double> tableColumnTaxa;


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
		tableColumnTaxa.setCellValueFactory(new PropertyValueFactory<>("valorPorEntrega"));
		
		

		Stage stage = (Stage) Main.getMainScene().getWindow(); // downcasting
		tableViewEntregadoresDisp.prefHeightProperty().bind(stage.heightProperty()); // deixa a tabela do tamanho da janela
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalStateException("O serviço está nulo"); // evita que o programador esqueça de injetar o
																	// serviço
		}
		List<Entregador> list = servico.findEntregadoresDisp();
		for (Entregador p : list) {
			System.out.println(p);
		}
		obsList = FXCollections.observableArrayList(list);
		tableViewEntregadoresDisp.setItems(obsList);

	}

	

	@Override
	public void atualizaDados() {
		updateTableView();

	}

	
}