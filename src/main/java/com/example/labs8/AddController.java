package com.example.labs8;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddController{
        @FXML
        private TextField text_client,text_number,text_type,text_status,text_dateopen,text_dateclose;
        private Stage dialog;
        private ArrayList<Agreement> agreements = new ArrayList<>();
        @FXML
        void add(ActionEvent event) {
                if(!text_client.getText().isEmpty() && !text_number.getText().isEmpty() && !text_dateclose.getText().isEmpty() && !text_dateopen.getText().isEmpty() && !text_type.getText().isEmpty() && !text_status.getText().isEmpty()){
                        agreements.add(new Agreement(text_number.getText(),text_client.getText(),text_type.getText(),text_status.getText(),text_dateopen.getText(),text_dateclose.getText()));
                        dialog.close();}
                else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initOwner(dialog);
                        alert.setTitle("Пустое поле!");
                        alert.setHeaderText("Одно или несколько полей пусты!");
                        alert.showAndWait();
                }
        }
        @FXML
        void cancel(ActionEvent event) {dialog.close();}
        public void getdialog(Stage dialog)
        {this.dialog = dialog;}
        public void getagreements(ArrayList<Agreement> agreements) {this.agreements = agreements;}
}



