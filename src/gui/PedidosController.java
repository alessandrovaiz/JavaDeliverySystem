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
import model.entidades.Entregador;
import model.entidades.Pedido;
import model.entidades.Produto;
import model.servicos.ClientesServico;
import model.servicos.EntregadoresServico;
import model.servicos.PedidosServico;
import model.servicos.ProdutosServico;

public class PedidosController implements Initializable, MudaDadosListener {

	private PedidosServico servico;
	
	
	private Cliente cliente;
	private Produto produto;
	private Entregador entregador;
	
	
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
	private TableColumn<Pedido, String> tableColumnEntregador;
	@FXML
	private TableColumn<Pedido, String> tableColumnProduto;
	@FXML
	private TableColumn<Pedido, Double> tableColumnTotal;
	@FXML
	private TableColumn<Pedido, Pedido> tableColumnEditar;
	@FXML
	private TableColumn<Pedido, Pedido> tableColumnExcluir;
	
	
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
		tableColumnEntregador.setCellValueFactory(new PropertyValueFactory<>("entregador"));
		tableColumnProduto.setCellValueFactory(new PropertyValueFactory<>("produtos"));
		tableColumnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		

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
		initEditarBotoes();
		initExcluirBotoes();
		
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
	
	private void initEditarBotoes() {
		tableColumnEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEditar.setCellFactory(param -> new TableCell<Pedido, Pedido>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Pedido obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				
				
				
				
				button.setOnAction(
						event -> criarFormularioDialogo(obj,obj.getCliente(),obj.getProdutos(),obj.getEntregador(), "/gui/PedidosFormulario.fxml", Utilitarios.palcoAtual(event)));
			}
			
		});
	}
	
	
	private void initExcluirBotoes() {
		tableColumnExcluir.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnExcluir.setCellFactory(param -> new TableCell<Pedido, Pedido>() {
			private final Button button = new Button("Excluir");

			@Override
			protected void updateItem(Pedido obj, boolean empty) {
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

	private void removeEntidade(Pedido obj) {
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