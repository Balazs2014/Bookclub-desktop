package com.example.bookclubdesktop;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HelloController {

    @FXML
    private TableColumn<Member, String> colName;
    @FXML
    private TableColumn<Member, String> colGender;
    @FXML
    private TableColumn<Member, LocalDate> colBirth_date;
    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> colBanned;

    public MemberDb db;

    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("stringGender"));
        colBirth_date.setCellValueFactory(new PropertyValueFactory<>("birth_date"));
        colBanned.setCellValueFactory(new PropertyValueFactory<>("banned"));

        feltoltes();
    }

    public void feltoltes() {
        try {
            db = new MemberDb();
            List<Member> members = new ArrayList<>();
            members = db.getMembers();
            tableView.getItems().clear();
            for (Member m : members) {
                tableView.getItems().add(m);
            }
        } catch (SQLException e) {
            Platform.runLater(() -> {
                error("Nem sikerült kapcsolódni az adatbázishoz");
                System.exit(0);
            });
        }
    }

    public void error(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(uzenet);
        alert.showAndWait();
    }

    public void alert(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(uzenet);
        alert.showAndWait();
    }

    public boolean confirm(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(uzenet);
        Optional<ButtonType> optional = alert.showAndWait();
        return optional.isPresent() && optional.get() == ButtonType.OK;
    }

    @FXML
    public void onTiltasClick(ActionEvent actionEvent) {
        int index = tableView.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            error("Tiltás módosításához előbb válasszon ki klubtagot");
            return;
        }

        Member m = tableView.getSelectionModel().getSelectedItem();
        if (!confirm(m.isBanned() ? "Biztos szeretné visszavonni a kiválasztott klubtag tiltását?" : "Biztos szeretné kitiltani a kiválasztott klubtagot?")) {
            return;
        }

        try {
            db = new MemberDb();
            boolean result = db.banMember(m);
            if (result) {
                alert("Sikeres művelet");
                feltoltes();
            } else {
                alert("Sikertelen művelet");
            }
        } catch (SQLException e) {
            error("Hiba");
        }
    }
}