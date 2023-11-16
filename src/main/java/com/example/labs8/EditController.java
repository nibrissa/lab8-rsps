package com.example.labs8;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class EditController{
        @FXML
        private TextField text_client,text_number,text_type,text_status,text_dateopen,text_dateclose;
        private Stage dialog;
        private Agreement agreement;
        @FXML
        void edit(ActionEvent event) {
                if (!text_client.getText().isEmpty() && !text_number.getText().isEmpty() && !text_dateclose.getText().isEmpty() && !text_dateopen.getText().isEmpty() && !text_type.getText().isEmpty() && !text_status.getText().isEmpty()){
                agreement.setNumber(text_number.getText());
                agreement.setClient(text_client.getText());
                agreement.setType(text_type.getText());
                agreement.setStatus(text_status.getText());
                agreement.setDateopen(text_dateopen.getText());
                agreement.setDateclose(text_dateclose.getText());
                dialog.close();}
                else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initOwner(dialog);
                        alert.setTitle("Пустое поле!");
                        alert.setHeaderText("Одно или несколько полей пусты");
                        alert.showAndWait();
                }
        }
        @FXML
        void cancel(ActionEvent event) {dialog.close();}
        public void getdialog(Stage dialogStage) {this.dialog = dialogStage;}
        public void getagreeemet(Agreement agreement) {
                text_number.setText(agreement.getNumber());
                text_client.setText(agreement.getClient());
                text_type.setText(agreement.getType());
                text_status.setText(agreement.getStatus());
                text_dateopen.setText(agreement.getDateopen());
                text_dateclose.setText(agreement.getDateclose());
                this.agreement = agreement;}
}
