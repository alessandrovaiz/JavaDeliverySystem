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
import model.entidades.Entregador;
import model.entidades.Pedido;
import model.entidades.Produto;
import model.servicos.ClientesServico;
import model.servicos.EntregadoresServico;
import model.servicos.PedidosServico;
import model.servicos.ProdutosServico;

public class PedidosController implements Initializable, MudaDadosListener {

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
	private Button btMeusPedidos;

	@FXML
	public void onBtMeusPedidosAction(ActionEvent evento) {
		Stage parentStage = Utilitarios.palcoAtual(evento);
		Pedido pedido = new Pedido();
		Cliente cliente = new Cliente(); 
		Produto produto = new Produto();
		Entregador entregador = new Entregador();
		criarFormularioDialogo(pedido,cliente,produto,entregador, "/gui/PedidosFormulario.fxml", parentStage);
	
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
		for(Pedido p: list) {
			System.out.println(p);
		}
		obsList = FXCollections.observableArrayList(list);
		tableViewPedido.setItems(obsList);
	}

	private void criarFormularioDialogo(Pedido pedido,Cliente cliente,Produto produto,Entregador entregador,String nomeAbsoluto, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			Pane painel = loader.load();
			
			PedidosFormController controller = loader.getController();
			controller.setEntidadePedido(pedido);
			controller.setEntidadeCliente(cliente);
			controller.setEntidadeEntregador(entregador);
			controller.setEntidadeProduto(produto);
			
			controller.setPedidosServico(new PedidosServico());
			controller.setProdutosServico(new ProdutosServico());
			controller.setClientesServico(new ClientesServico());
			controller.setEntregadoresServico(new EntregadoresServico());
			
			controller.inscreveListener(this);
			controller.atualizaDadosForm();
			
			
			Stage stageDialogo = new Stage();
			stageDialogo.setTitle("Dados do Pedido");
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