package gui.util;

import javafx.scene.control.TextField;

public class Restricoes {

	public static void setTextFieldInteger(TextField txt) {
		txt.textProperty().addListener((obs, valorVelho, novoValor) -> {
	        if (novoValor != null && !novoValor.matches("\\d*")) {
	        	txt.setText(valorVelho);
	        }
	    });
	}

	public static void setTextFieldMaxLength(TextField txt, int max) {
		txt.textProperty().addListener((obs, valorVelho, novoValor) -> {
	        if (novoValor != null && novoValor.length() > max) {
	        	txt.setText(valorVelho);
	        }
	    });
	}

	public static void setTextFieldDouble(TextField txt) {
		txt.textProperty().addListener((obs, valorVelho, novoValor) -> {
		    	if (novoValor != null && !novoValor.matches("\\d*([\\.]\\d*)?")) {
                    txt.setText(valorVelho);
                }
		    });
	}
}