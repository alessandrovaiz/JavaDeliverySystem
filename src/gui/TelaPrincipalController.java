package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import model.servicos.ClientesServico;
import model.servicos.EntregadoresServico;
import model.servicos.PedidosServico;
import model.servicos.ProdutosServico;

public class TelaPrincipalController implements Initializable {

	@FXML
	private MenuItem menuIAcompanharEntregas;
	@FXML
	private MenuItem menuIEntregadoresDisponiveis;
	@FXML
	private MenuItem menuIClientesVip;
	@FXML
	private MenuItem menuIProducao;
	
	
	@FXML
	private MenuItem menuIPedidos;
	@FXML
	private MenuItem menuIClientes;
	@FXML
	private MenuItem menuIProdutos;
	@FXML
	private MenuItem menuIEntregadores;
	
	@FXML
	private MenuItem menuISobre;
	
	
	
	
	@FXML
	public void onMenuIAcompanharEntregasAction() {
		carregaView("/gui/AcompanharEntregas.fxml", (AcompanharEntregasController controller) -> {
			controller.setPedidosServico(new PedidosServico());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuIEntregadoresDisponiveisAction() {
		carregaView("/gui/EntregadoresDisponiveis.fxml", (EntregadoresDisponiveisController controller) -> {
			controller.setEntregadoresServico(new EntregadoresServico());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuIClientesVipAction() {
		carregaView("/gui/ClientesVip.fxml", (ClientesVipController controller) -> {
			controller.setClientesServico(new ClientesServico());
			controller.updateTableView();
		});
	}
	
	
	
	@FXML
	public void onMenuIProducaoAction() {
		carregaView("/gui/Producao.fxml", (ProducaoController controller) -> {
			controller.setPedidosServico(new PedidosServico());
			controller.updateTableView();
		});
	}
	
	
	@FXML
	public void onMenuIPedidosAction() {
		carregaView("/gui/Pedidos.fxml", (PedidosController controller) -> {
			controller.setPedidosServico(new PedidosServico());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuIClientesAction() {
		carregaView("/gui/Clientes.fxml", (ClientesController controller) -> {
			controller.setClientesServico(new ClientesServico());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuIProdutosAction() {
		carregaView("/gui/Produtos.fxml", (ProdutosController controller) -> {
			controller.setProdutosServico(new ProdutosServico());
			controller.updateTableView();
		});
	}
	
	
	@FXML
	public void onMenuIEntregadoresAction() {
		carregaView("/gui/Entregadores.fxml", (EntregadoresController controller) -> {
			controller.setEntregadoresServico(new EntregadoresServico());
			controller.updateTableView();
		});
	}
	
	
	@FXML
	public void onMenuISobreAction() {
		Alertas.showAlert("Sobre","Coronery","Desenvolvido por Alessandro Vaiz e Gabriel Pedó\nv1.0",AlertType.CONFIRMATION);
	}
	
	
	private <T> void carregaView(String nomeAbsoluto, Consumer<T> acaoDeInicio) { //funcao generica
		try {
		
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nomeAbsoluto));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainNavBar = Main.getNavBar();
		
			
			Node mainMenu = mainNavBar.getChildren().get(0);
			mainNavBar.getChildren().clear();
			mainNavBar.getChildren().add(mainMenu);
			mainNavBar.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			acaoDeInicio.accept(controller); // executa a funcao na tela de acao
			
		}catch(IOException e) {
			Alertas.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
