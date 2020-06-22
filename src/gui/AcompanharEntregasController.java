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
import model.entidades.Pedido;
import model.servicos.PedidosServico;

public class AcompanharEntregasController implements Initializable, MudaDadosListener {

	private PedidosServico servico;

	@FXML
	private TableView<Pedido> tableViewEntregas;
	@FXML
	private TableColumn<Pedido, Integer> tableColumnIdPedido;
	@FXML
	private TableColumn<Pedido, String> tableColumnCliente;
	@FXML
	private TableColumn<Pedido, String> tableColumnEndereco;
	@FXML
	private TableColumn<Pedido, String> tableColumnEntregador;

	

	private ObservableList<Pedido> obsList;

	public void setPedidosServico(PedidosServico servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnIdPedido.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		tableColumnEndereco.setCellValueFactory(new PropertyValueFactory<>("enderecoEntrega"));
		tableColumnEntregador.setCellValueFactory(new PropertyValueFactory<>("entregador"));
		

		Stage stage = (Stage) Main.getMainScene().getWindow(); // downcasting
		tableViewEntregas.prefHeightProperty().bind(stage.heightProperty()); // deixa a tabela do tamanho da janela
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalStateException("O serviço está nulo"); // evita que o programador esqueça de injetar o
																	// serviço
		}
		List<Pedido> list = servico.findEntregasEmAndamento();
		for (Pedido p : list) {
			System.out.println(p);
		}
		obsList = FXCollections.observableArrayList(list);
		tableViewEntregas.setItems(obsList);

	}

	

	@Override
	public void atualizaDados() {
		updateTableView();

	}

	
}