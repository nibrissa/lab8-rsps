package com.example.labs8;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.prefs.Preferences;

public class MainController {
    @FXML
    private TableColumn<Agreement, String> column_client, column_dateclose, column_dateopen, column_number, column_status, column_type;

    private Stage dialog;
    @FXML
    private TableView<Agreement> table_view;
    @FXML
    private Label l_client, l_dateclose, l_dateopen, l_number, l_status, l_type;
    ArrayList<Agreement> agreements = new ArrayList<Agreement>();


    @FXML
    void add(ActionEvent event) throws IOException {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("addview.fxml"));
        dialog.setScene(new Scene(loader.load(), 600, 400));
        dialog.getIcons().add(new Image(Objects.requireNonNull(MainApplication.class.getResourceAsStream("/com/example/labs8/log.png"))));
        dialog.setTitle("Добавление");
        AddController controller = loader.getController();
        controller.getdialog(dialog);
        controller.getagreements(agreements);
        dialog.showAndWait();
        table_view.setItems(FXCollections.observableList(agreements));
    }

    @FXML
    void delete(ActionEvent event) {
        if (table_view.getSelectionModel().getSelectedItem() != null) {
            agreements.remove(table_view.getSelectionModel().getSelectedItem());
            table_view.setItems(FXCollections.observableList(agreements));
            l_number.setText("Договор №");
            l_client.setText("Клиент: ");
            l_type.setText("Тип: ");
            l_status.setText("Статус договора: ");
            l_dateopen.setText("Дата заключения договора: ");
            l_dateclose.setText("Дата закрытия договора: ");
        }
    }

    @FXML
    void edit(ActionEvent event) throws IOException {
        if (table_view.getSelectionModel().getSelectedItem() != null) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("editview.fxml"));
            stage.setScene(new Scene(loader.load(), 600, 400));
            stage.getIcons().add(new Image(Objects.requireNonNull(MainApplication.class.getResourceAsStream("/com/example/labs8/log.png"))));
            stage.setTitle("Редактирование");
            EditController controller = loader.getController();
            controller.getdialog(stage);
            int id = agreements.indexOf(table_view.getSelectionModel().getSelectedItem());
            controller.getagreeemet(agreements.get(id));
            stage.showAndWait();
            table_view.setItems(FXCollections.observableList(agreements));
            table_view.getSelectionModel().clearSelection();
            table_view.getSelectionModel().select(id);
            table_view.refresh();
        }
    }

    @FXML
    void newfile(ActionEvent event) throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        File file = fileChooser.showSaveDialog(dialog);
        if (file != null) {
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            setPersonFilePath(file);
            agreements.clear();
            saveToFile(file);
            table_view.refresh();
            table_view.getSelectionModel().clearSelection();
            dialog.setTitle("Договора - " + file.getName());
        }
    }

    @FXML
    private void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Договора");
        alert.setHeaderText("О приложении");
        alert.setContentText("Разработчик: Акинтьева Полина \nИСТ-332");
        alert.showAndWait();
    }

    @FXML
    void openfile(ActionEvent event) throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        File file = fileChooser.showOpenDialog(dialog);
        if (file != null) {
            setPersonFilePath(file);
            agreements.clear();
            agreements.addAll(loadFromFile(file));
            table_view.refresh();
            table_view.getSelectionModel().clearSelection();
            dialog.setTitle("Договора - " + file.getName());
        }
    }

    @FXML
    void exit(ActionEvent event) {System.exit(0);}

    @FXML
    void savefileas(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        File file = fileChooser.showSaveDialog(dialog);
        if (file != null) {
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            saveToFile(file);
        }
    }

    @FXML
    void savefile(ActionEvent event) {
        File personFile = getPersonFilePath();
        if (personFile != null) saveToFile(personFile);
        else savefileas(event);
    }

    public void saveToFile(File file) {
        try {
            Marshaller m = JAXBContext.newInstance(Wrapper.class).createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Wrapper wrapper = new Wrapper();

            wrapper.setAgreements(agreements);
            m.marshal(wrapper, file);
            System.out.println(wrapper);
            setPersonFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Данные не сохранены");
            alert.setContentText("Ошибка при попытке сохранить файл:\n" + file.getPath());
            alert.showAndWait();
        }
    }

    public static ArrayList<Agreement> loadFromFile(File file) throws JAXBException {
        try {
            Unmarshaller um = JAXBContext.newInstance(Wrapper.class).createUnmarshaller();
            Wrapper wrapper = (Wrapper) um.unmarshal(file);
            if (wrapper.getAgreements() == null) return new ArrayList<Agreement>();
            else return wrapper.getAgreements();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApplication.class);
        String filePath = prefs.get("filePath", null);
        try {
            return new File(filePath);
        } catch (Exception e) {
            return null;
        }
    }

    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApplication.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            dialog.setTitle("Договора - " + file.getName());
        } else {
            prefs.remove("filePath");
            dialog.setTitle("Договора - ");
        }
    }

    public void getstage(Stage dialog) {
        this.dialog = dialog;
        column_number.setCellValueFactory(new PropertyValueFactory<Agreement, String>("number"));
        column_client.setCellValueFactory(new PropertyValueFactory<Agreement, String>("client"));
        column_type.setCellValueFactory(new PropertyValueFactory<Agreement, String>("type"));
        column_status.setCellValueFactory(new PropertyValueFactory<Agreement, String>("status"));
        column_dateopen.setCellValueFactory(new PropertyValueFactory<Agreement, String>("dateopen"));
        column_dateclose.setCellValueFactory(new PropertyValueFactory<Agreement, String>("dateclose"));
        table_view.setItems(FXCollections.observableList(agreements));

        dialog.setTitle("Договора");
        try {
            agreements.addAll(loadFromFile(getPersonFilePath()));
            if (getPersonFilePath()!=null) dialog.setTitle("Договора - " + getPersonFilePath().getName());
            System.out.println(agreements);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        table_view.setItems(FXCollections.observableList(agreements));

        table_view.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Agreement agreement = table_view.getSelectionModel().getSelectedItem();
                l_number.setText("Договор №" + agreement.getNumber());
                l_client.setText("Клиент: " + agreement.getClient());
                l_type.setText("Тип: " + agreement.getType());
                l_status.setText("Статус договора: " + agreement.getStatus());
                l_dateopen.setText("Дата заключения договора: " + agreement.getDateopen());
                l_dateclose.setText("Дата закрытия договора: " + agreement.getDateclose());
            }
        });
    }
}
