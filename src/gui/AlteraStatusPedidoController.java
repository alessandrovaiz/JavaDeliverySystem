package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.MudaDadosListener;
import gui.util.Alertas;
import gui.util.Utilitarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import model.entidades.Pedido;
import model.entidades.enums.StatusPedido;
import model.servicos.PedidosServico;

public class AlteraStatusPedidoController implements Initializable {

	
	private Pedido entidadePedido;

	private PedidosServico servicoPedidos;
	

	
	private List<MudaDadosListener> mudaDadosListeners = new ArrayList<>();
	
	@FXML
	private Button btSalvar;
	@FXML
	private Button btCancelar;

	
	@FXML
	private ComboBox<StatusPedido> cbxStatus;
	
	private ObservableList<StatusPedido> obsList = FXCollections.observableArrayList();
	

	ArrayList<Pedido> pedidos;

	@FXML
	private void onBtAdicionarAcao(ActionEvent evento) {
		
		if (entidadePedido == null) {
			throw new IllegalStateException("Pedido n�o pode ser nulo!");
		}
		
		if (cbxStatus.getValue() == null) {
			Alertas.showAlert("Erro", "O campo não pode ser vazio! ", null, AlertType.ERROR);
		} else {
			entidadePedido.setStatus(cbxStatus.getValue().getValor());
			servicoPedidos.atualizaStatus(entidadePedido);
			System.out.println(cbxStatus.getValue().getValor());
			notificaListeners();
			Utilitarios.palcoAtual(evento).close();
		}

		

	}

	private void notificaListeners() {
		for(MudaDadosListener listener: mudaDadosListeners) {
			listener.atualizaDados();
		}
		
	}


	

	

	

	@FXML
	private void onBtCancelarAcao(ActionEvent evento) {
		Utilitarios.palcoAtual(evento).close();
	}
	


	private void inicializarTabelaPedidos() {
				
	}

	public void atualizaDadosForm() {
		if (entidadePedido == null) {
			throw new IllegalStateException("Entidade está nula");
		}
		
		obsList.add(StatusPedido.ACEITO);
		obsList.add(StatusPedido.EM_PREPARO);
		obsList.add(StatusPedido.A_CAMINHO);
		obsList.add(StatusPedido.ENTREGUE);
		obsList.add(StatusPedido.CANCELADO);
		cbxStatus.setItems(obsList);
		

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.inicializarTabelaPedidos();

	}
	
	
	public void inscreveListener(MudaDadosListener listener) {
		mudaDadosListeners.add(listener);
	}

	public void setEntidadePedido(Pedido entidade) {
		this.entidadePedido = entidade;
	}

	

	public void setPedidosServico(PedidosServico servicoPedidos) {
		this.servicoPedidos = servicoPedidos;
	}

	

}
