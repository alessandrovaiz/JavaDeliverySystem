package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entidades.Pedido;

public class PedidosController implements Initializable {

	@FXML private TableView<Pedido> tableViewProduto;
	@FXML private TableColumn<Pedido,Integer> tableColumnId;
	@FXML private TableColumn<Pedido,String> tableColumnCliente;
	@FXML private TableColumn<Pedido,String> tableColumnEndereco;
	@FXML private TableColumn<Pedido,Integer> tableColumnStatus;
	
	
	@FXML private Button btRegPedido;
	
	@FXML
	public void onBtRegPedidoAction() {
		System.out.println("Teste");
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
		
		Stage stage = (Stage)Main.getMainScene().getWindow(); // downcasting
		tableViewProduto.prefHeightProperty().bind(stage.heightProperty()); // deixa a tabela do tamanho da janela
	}
	
}
