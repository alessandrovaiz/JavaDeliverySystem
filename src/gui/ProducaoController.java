package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.entidades.Pedido;
import model.servicos.PedidosServico;

public class ProducaoController implements Initializable {

	private PedidosServico servico;

	@FXML
	private Label totalBruto;
	@FXML
	private Label valorEntregadores;
	@FXML
	private Label qtdPedidosEntregues;
	@FXML
	private Label totalLimpo;

	private Double somaTotBruto=(double) 0;
	private Double custoEntregadores;
	private int qtdPedEntregues;
	private Double totLimpo=(double) 0;
	
	
	public void setPedidosServico(PedidosServico servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
		
	    
	}
	
	private void initializeNodes() {
		
		

		//Stage stage = (Stage) Main.getMainScene().getWindow(); // downcasting
		//tableViewEntregadoresDisp.prefHeightProperty().bind(stage.heightProperty()); // deixa a tabela do tamanho da janela
	}
	private void setCampos() {
		List<Pedido> list = servico.findAll();
		for (Pedido p : list) {
			somaTotBruto+= p.getTotal()   ;
		}
		totalBruto.setText(": R$ " + somaTotBruto);
		
	    custoEntregadores = servico.findCustoEntregadores();
	    
	    valorEntregadores.setText(": R$ " + custoEntregadores);
	    
	    for(Pedido z: list) {
	    	if(z.getStatus()==4) {
	    		qtdPedEntregues++;
	    	}
	    }
	    
	    qtdPedidosEntregues.setText(": " + qtdPedEntregues);
	    
	    totLimpo = somaTotBruto - custoEntregadores;
	    
	    totalLimpo.setText(": R$ " + totLimpo);
	}
	public void updateTableView() {
		if (servico == null) {
			throw new IllegalStateException("O serviço está nulo"); // evita que o programador esqueça de injetar o
																	// serviço
		}
		setCampos();
		
		
	    

	}

	

	

	
}