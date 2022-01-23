package presentation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import metier.Historique;
import metier.IMetier;
import metier.MetierImpl;
import metier.Page;

import java.net.URL;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FavoriController implements Initializable {

    @FXML private TextField idRechercheFavori ;
    @FXML private TableView<Page> idTableFavori=new TableView<>();

    @FXML private TableColumn<Historique, Page> idPageFavori=new TableColumn<>();

    @FXML private TableColumn<Historique, Button> idActionSupprimerFavori=new TableColumn<>();

    ObservableList<Page> favoris= FXCollections.observableArrayList();
    IMetier metier= new MetierImpl();

    public int recherche(Page page,List<Page> list){
        int c=0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getUrlPage().equals(page.getUrlPage())) c++;
        }
        return c;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idRechercheFavori.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                List<Page> change=new ArrayList<>();
                List<Page> change2=new ArrayList<>();
                change=metier.searchPage(idRechercheFavori.getText());
                for(Page p:change){
                    int c=0;
                    if(p.getFavori().getIdFavori()!=1){
                        c = recherche(p, change2);

                        if (c == 0) change2.add(p);
                    }
                }
                favoris.setAll(change2) ;
            }
        });

        idPageFavori.setCellValueFactory(new PropertyValueFactory<>("namePage"));
        idActionSupprimerFavori.setCellValueFactory(new PropertyValueFactory<>("supBtn"));

        for(Page p:metier.getAllPage()) {
            int c = 0;
            if (p.getFavori().getIdFavori() != 1) {
            c = recherche(p, favoris);

            if (c == 0) favoris.add(p);
            }
        }
        idTableFavori.setItems(favoris);
    }

    @FXML
    private void DeleteBtnFavori(){
        for (int i=0;i<favoris.size();i++)
        {
            int fin=i;
            idActionSupprimerFavori.getCellData(i).setOnAction(event -> {
                for(Page p:metier.getAllPage()){
                    if(favoris.get(fin).equals(p)){
                        metier.supprimerPageInFavoris(p);
                    }
                    metier.supprimerPageInFavoris(favoris.get(fin));
                }
                favoris.clear();
                for(Page p:metier.getAllPage()){
                    int c = 0;
                    if (p.getFavori().getIdFavori()!= 1)
                    {
                        c = recherche(p, favoris);
                        if (c == 0) favoris.add(p);
                    }
                }
                idTableFavori.setItems(favoris);
            });
        }
    }
}
