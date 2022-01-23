package presentation;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import metier.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable
{


    @FXML private BorderPane idBorderPane;
    @FXML private VBox idVBox;
    @FXML private HBox idHBox;
    @FXML private TextField idTextField;
    @FXML private Button idLoad;
    @FXML private WebView idWebView=new WebView();

    @FXML private ProgressIndicator progress;
    @FXML private Button idLeft = new Button();
    @FXML Label statusL;

    @FXML private FontAwesomeIconView reload = new FontAwesomeIconView();
    @FXML private Button idRefreshPage = new Button();


    public static WebEngine webEngine = new WebEngine();
    private String homePage;
    private double webZoom = 1;
    private WebHistory history;
    @FXML Button idFavoriStart=new Button();
    @FXML HBox idBarFavori=new HBox();

    public int recherche(Page page,List<Page> list){
        int c=0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getUrlPage().equals(page.getUrlPage())) c++;
        }
        return c;
    }
    List<Page> favoris=new ArrayList<>();
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        webEngine = idWebView.getEngine();
        homePage = "www.google.com";
        idTextField.setText(homePage);
        String search=idTextField.getText();
        zoomIn();
        loadPage(search);
        idTextField.clear();
        for(Page p:metier.getAllPage()) {
            int c = 0;
            if (p.getFavori().getIdFavori() != 1) {
                c = recherche(p, favoris);

                if (c == 0) favoris.add(p);
            }
        }

        for(int i=0;i<favoris.size();i++){
            if (favoris.get(i)!=null){
                final int  c=i;
                Button button=new Button(favoris.get(i).getNamePage());
                button.getStyleClass().add("buttonFavori");
                button.setOnAction(event -> {
                    idWebView.getEngine().load(favoris.get(c).getUrlPage());
                });
                idBarFavori.getChildren().add(button);

            }
        }

    }
IMetier metier=new MetierImpl();
    private void loadPage(String search)
    {
        Page p=new Page();
        idWebView.getEngine().load("http://"+search);
        progress.setStyle("-fx-accent: orange");
        idWebView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue)
            {
                statusL.setText(" Chargement en cours... " + idWebView.getEngine().getLocation());
                reload.setGlyphName("CLOSE");
                reload.setSize("25");
                reload.fillProperty().setValue(Paint.valueOf("white"));
                idRefreshPage.setGraphic(reload);
                progress.setVisible(true);
                if(newValue == Worker.State.SUCCEEDED) {
                    statusL.setText(" Chargée");
                    progress.setVisible(false);
                    reload.setGlyphName("ROTATE_RIGHT");
                    reload.setSize("25");
                    reload.fillProperty().setValue(Paint.valueOf("white"));
                    idRefreshPage.setGraphic(reload);
                    if(idBorderPane.getParent() != null) {
                        TabPane tp = (TabPane)idBorderPane.getParent().getParent();
                        for(Tab tab : tp.getTabs()) {
                            if(tab.getContent() == idBorderPane) {
                                tab.setText(idWebView.getEngine().getTitle());

                                p.setUrlPage(idWebView.getEngine().getLocation());
                                p.setNamePage(idWebView.getEngine().getTitle());
                                p.setPhoto(null);
                                p.setDateTime(LocalTime.now());
                                p.setHistorique(new Historique(Date.valueOf("2022-01-02").toLocalDate()));
                                for (Page page:metier.getAllPage()){
                                    if(page.getUrlPage().equals(p.getUrlPage()))
                                    {
                                        if(page.getFavori().getIdFavori()==2)
                                        {p.setFavori(new Favori(2));
                                            FontAwesomeIconView fontAwesomeIconView=new FontAwesomeIconView();
                                            fontAwesomeIconView.setGlyphName("STAR");
                                            fontAwesomeIconView.setSize("25");
                                            fontAwesomeIconView.fillProperty().setValue(Paint.valueOf("#FFFF00"));
                                            idFavoriStart.setGraphic(fontAwesomeIconView);
                                        }
                                        else  p.setFavori(new Favori(1));
                                    }
                                }
                                if(metier.getAllPage().size()==0) p.setFavori(new Favori(1));
                                if(p.getNamePage()!=null)
                                metier.addPage(p);
                                break;
                            }
                        }
                    }
                }
            }
        });
        idWebView.getEngine().locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1)
            {
                idTextField.clear();
            }
        });
    }

@FXML private void actionLoad()
{
    idWebView.getEngine().load("http://www.google.com/search?q="+idTextField.getText()+"&ie=UTF-8&oe=UTF-8");
    idWebView.getEngine().locationProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> ov, String t, String t1)
        {
            idTextField.setText(t1);
        }
    });
}

    //fonction qui recupere l'url de la page courante et l'affiche au textField
    // fonctionnel par le boutton <<history>> et <<back>> et <<forward>>
    public void setTextField(){
        history=webEngine.getHistory();
        String URL;
        WebHistory.Entry urlpage =history.getEntries().get(history.getCurrentIndex());
        URL=urlpage.getUrl();
        idTextField.setText(URL);
    }
    //fonction qui recupere la liste des pages visitée
    public void getHistoryPages(){
        history=webEngine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
    }

    //page presedente
    @FXML private void back(){
        history=idWebView.getEngine().getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        history.go(entries.size()>1 && history.getCurrentIndex() > 0 ? -1 : 0);
        setTextField();
    }
    //page suivante
    @FXML private void forward()
    {
        history=idWebView.getEngine().getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        history.go(entries.size()>1 && history.getCurrentIndex() < (entries.size() - 1) ? 1 : 0);
        setTextField();
    }

    //fonction qui actualise la page
    @FXML private void refreshPage()
    {
        webZoom=1.25;
        idWebView.setZoom(webZoom);
        if(idWebView.getEngine().getLoadWorker().isRunning()) {
            idWebView.getEngine().getLoadWorker().cancel();
            statusL.setText(" Chargée");
            progress.setVisible(false);
            reload.setGlyphName("ROTATE_RIGHT");
            reload.setSize("25");
            reload.fillProperty().setValue(Paint.valueOf("white"));
            idRefreshPage.setGraphic(reload);
        } else {
            idWebView.getEngine().reload();
            reload.setGlyphName("CLOSE");
            reload.setSize("25");
            reload.fillProperty().setValue(Paint.valueOf("white"));
            idRefreshPage.setGraphic(reload);
        }
    }

    //foncton qui charge la page d'acceuil
    @FXML private void goToHomePage()
    {
        homePage="www.google.com";
        idTextField.setText(homePage);
        loadPage(homePage);
        idTextField.clear();
    }


    @FXML private void download() {

    }

    //imprimer une page (a revoir : car elle envoie directement la page a imprimer a l'imprimante
    //sans afficher une apercu)
    @FXML private void imprimerPage()
    {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            idWebView.getEngine().print(job);
            job.endJob();
        }
    }

    //fonction qui incremente le zoom de la page
    @FXML private void zoomIn()
    {
        if(webZoom<=2) {
            webZoom += 0.25;
            idWebView.setZoom(webZoom);
        }
    }

    //fonction qui decremente le zoom la page
    @FXML void zoomOut()
    {
        if (webZoom >0.5) {
            webZoom -= 0.25;
            idWebView.setZoom(webZoom);
            System.out.println(webZoom);
        }
    }


    @FXML void BtnAjouteFavori(){
        List<Page> pageList= new ArrayList<>();
        pageList=metier.getAllPage();
        for (Page p:pageList){
          //  System.out.println(idWebView.getEngine().getLocation());
           // System.out.println(p.getUrlPage());
        if(idWebView.getEngine().getLocation().equals(p.getUrlPage()))
        {
            if(p.getFavori().getIdFavori()==1) {
                FontAwesomeIconView fontAwesomeIconView=new FontAwesomeIconView();
                fontAwesomeIconView.setGlyphName("STAR");
                fontAwesomeIconView.setSize("25");
                fontAwesomeIconView.fillProperty().setValue(Paint.valueOf("#FFFF00"));
                idFavoriStart.setGraphic(fontAwesomeIconView);
                metier.ajouterPagetoFavoris(p);
            }
            else{
                FontAwesomeIconView fontAwesomeIconView=new FontAwesomeIconView();
                fontAwesomeIconView.setGlyphName("STAR");
                fontAwesomeIconView.setSize("25");
                fontAwesomeIconView.fillProperty().setValue(Paint.valueOf("#FFFF00"));
                idFavoriStart.setGraphic(fontAwesomeIconView);
            }
        }
        }
    }
//    @FXML private void option() {
//
//    }




    Stage window=new Stage();
    @FXML public void ActionHistorique() throws IOException {
        BorderPane root= FXMLLoader.load(getClass().getResource("Historique.fxml"));
        Scene scene=new Scene(root);
        window3.setScene(scene);
        window3.show();
    }
        Stage window2=new Stage();
        @FXML public void ActionFavoris() throws IOException {
        BorderPane root= FXMLLoader.load(getClass().getResource("Favoris.fxml"));
        Scene scene=new Scene(root);
        window2.setScene(scene);
        window2.show();
    }
    Stage window3=new Stage();
    @FXML public void ActionTelechargement() throws IOException {
        BorderPane root= FXMLLoader.load(getClass().getResource("Download.fxml"));
        Scene scene=new Scene(root);
        window3.setScene(scene);
        window3.show();
    }
}
