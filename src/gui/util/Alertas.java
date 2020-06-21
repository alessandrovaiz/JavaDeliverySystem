package gui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alertas {

	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alerta = new Alert(type);
		alerta.setTitle(title);
		alerta.setHeaderText(header);
		alerta.setContentText(content);
		alerta.show();
	}

	public static Optional<ButtonType> showConfirmation(String title, String content) {
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle(title);
		alerta.setHeaderText(null);
		alerta.setContentText(content);
		return alerta.showAndWait();
	}

}