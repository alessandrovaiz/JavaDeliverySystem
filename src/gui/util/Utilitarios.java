package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utilitarios {
	public static Stage palcoAtual(ActionEvent evento) {
		return (Stage)((Node) evento.getSource()).getScene().getWindow(); // para acessar o stage onde o controller que recebeu o evento está
	}
}
