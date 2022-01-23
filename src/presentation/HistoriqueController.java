package presentation;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import metier.Historique;
import metier.IMetier;
import metier.MetierImpl;
import metier.Page;

import java.net.URL;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class HistoriqueController implements Initializable {
    @FXML private Label idDateHistorique;
    @FXML private DatePicker idRechercheByDateHistorique=new DatePicker();
    @FXML private TextField idRechercheHistorique ;
    @FXML private TableView<Page> idTableHistorique=new TableView<>();

    @FXML private TableColumn<Historique, DateTimeException> idTimePage=new TableColumn<>();
    @FXML private TableColumn<Historique, Page> idPageName=new TableColumn<>();
    @FXML private TableColumn<Historique, Page> idPageUrl=new TableColumn<>();

    @FXML private TableColumn<Historique, Button> idActionSupprimer=new TableColumn<>();

    ObservableList<Page> historiques= FXCollections.observableArrayList();
    IMetier metier= new MetierImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idRechercheHistorique.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                List<Page> change = new ArrayList<>();
                List<Page> change2 = new ArrayList<>();
                change = metier.searchPage(idRechercheHistorique.getText());
                for (Page p : change) {
                    if (!p.getHistorique().getIdHistorique().toString().equals("0001-01-01"))
                        change2.add(p);
                }
                historiques.setAll(change2);
            }
        });

        idTimePage.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        idPageName.setCellValueFactory(new PropertyValueFactory<>("namePage"));
        idPageUrl.setCellValueFactory(new PropertyValueFactory<>("urlPage"));
        idActionSupprimer.setCellValueFactory(new PropertyValueFactory<>("supBtn"));
        for (Page p : metier.getAllPage()) {
            if (!p.getHistorique().getIdHistorique().toString().equals("0001-01-01"))
                historiques.add(p);
        }

        idTableHistorique.setItems(historiques);
    }

    @FXML private void  historiqueFilter(){
        historiques.clear();
        historiques.addAll(metier.getPageByIdHistorique(idRechercheByDateHistorique.getValue()));
        System.out.println(idRechercheByDateHistorique.getValue());
        idTableHistorique.setItems(historiques);
    }


    @FXML
    private void DeleteBtn(){
        for (int i=0;i<historiques.size();i++)
        {
            int fin=i;
            idActionSupprimer.getCellData(i).setOnAction(event -> {
                metier.supprimerPageInHistorique(historiques.get(fin));
                historiques.clear();
                for(Page p:metier.getAllPage()){
                    if(!p.getHistorique().getIdHistorique().toString().equals("0001-01-01"))
                        historiques.add(p);
                }
                idTableHistorique.setItems(historiques);
            });
        }
    }
}
