package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entidades.Pedido;
import model.servicos.PedidosServico;

public class PedidosController implements Initializable {

	private PedidosServico servico;

	@FXML
	private TableView<Pedido> tableViewPedido;
	@FXML
	private TableColumn<Pedido, Integer> tableColumnId;
	@FXML
	private TableColumn<Pedido, String> tableColumnCliente;
	@FXML
	private TableColumn<Pedido, String> tableColumnEndereco;
	@FXML
	private TableColumn<Pedido, Integer> tableColumnStatus;

	@FXML
	private Button btRegPedido;

	@FXML
	public void onBtRegPedidoAction() {
		System.out.println("Teste");
	}

	private ObservableList<Pedido> obsList;

	public void setPedidosServico(PedidosServico servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		tableColumnEndereco.setCellValueFactory(new PropertyValueFactory<>("enderecoEntrega"));
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

		Stage stage = (Stage) Main.getMainScene().getWindow(); // downcasting
		tableViewPedido.prefHeightProperty().bind(stage.heightProperty()); // deixa a tabela do tamanho da janela
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalStateException("O serviço está nulo"); // evita que o programador esqueça de injetar o
																	// serviço
		}
		List<Pedido> list = servico.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewPedido.setItems(obsList);
	}

	

}