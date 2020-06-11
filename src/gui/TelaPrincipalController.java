package gui;

import java.awt.ScrollPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class TelaPrincipalController implements Initializable {

	@FXML
	private MenuItem menuIAcompanharEntregas;
	@FXML
	private MenuItem menuIVendedoresDisponiveis;
	@FXML
	private MenuItem menuIClientesVip;
	@FXML
	private MenuItem menuIEstoque;
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
		System.out.println("teste");
	}
	
	@FXML
	public void onMenuIVendedoresDisponiveisAction() {
		System.out.println("teste");
	}
	
	@FXML
	public void onMenuIClientesVipAction() {
		System.out.println("teste");
	}
	
	@FXML
	public void onMenuIEstoqueAction() {
		System.out.println("teste");
	}
	
	@FXML
	public void onMenuIProducaoAction() {
		System.out.println("teste");
	}
	
	
	@FXML
	public void onMenuIPedidosAction() {
		carregaView("/gui/Pedidos.fxml");
	}
	
	@FXML
	public void onMenuIClientesAction() {
		System.out.println("teste");
	}
	
	@FXML
	public void onMenuIProdutosAction() {
		System.out.println("teste");
	}
	
	
	@FXML
	public void onMenuIEntregadoresAction() {
		System.out.println("teste");
	}
	
	
	@FXML
	public void onMenuISobreAction() {
		Alertas.showAlert("Sobre","Coronery","Desenvolvido por Alessandro Vaiz e Gabriel Pedó\nv1.0",AlertType.CONFIRMATION);
	}
	
	
	private void carregaView(String absoluteName) {
		try {
		
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainNavBar = Main.getNavBar();
		
			
			Node mainMenu = mainNavBar.getChildren().get(0);
			mainNavBar.getChildren().clear();
			mainNavBar.getChildren().add(mainMenu);
			mainNavBar.getChildren().addAll(newVBox.getChildren());
			
			
		}catch(IOException e) {
			Alertas.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
