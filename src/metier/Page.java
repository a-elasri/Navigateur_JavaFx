package metier;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Page {
   private int id;
   private String namePage;
   private String urlPage;
   private String photo;
   private LocalTime dateTime;
   
   public Historique historique;
   public Favori favori;



   public Button supBtn;

   public Button getSupBtn() {
      return supBtn;
   }

   public void setSupBtn(Button supBtn) {
      this.supBtn = supBtn;
   }

   public Historique getHistorique() {
      return historique;
   }

   public void setHistorique(Historique newHistorique) {
      if (this.historique == null || !this.historique.equals(newHistorique))
      {
         if (this.historique != null)
         {
            Historique oldHistorique = this.historique;
            this.historique = null;
            oldHistorique.removePage(this);
         }
         if (newHistorique != null)
         {
            this.historique = newHistorique;
            this.historique.addPage(this);
         }
      }
   }
   public Favori getFavori() {
      return favori;
   }

   public void setFavori(Favori newFavori) {
      if (this.favori == null || !this.favori.equals(newFavori))
      {
         if (this.favori != null)
         {
            Favori oldFavori = this.favori;
            this.favori = null;
            oldFavori.removePage(this);
         }
         if (newFavori != null)
         {
            this.favori = newFavori;
            this.favori.addPage(this);
         }
      }
   }

   public Page() {
   }

   public Page(String namePage, String urlPage, String photo, LocalTime dateTime) {
      this.namePage = namePage;
      this.urlPage = urlPage;
      this.photo = photo;
      this.dateTime = dateTime;
      this.supBtn=new Button();
      FontAwesomeIconView fontAwesomeIconView=new FontAwesomeIconView();
      fontAwesomeIconView.setGlyphName("CLOSE");
      fontAwesomeIconView.setSize("25");
      fontAwesomeIconView.fillProperty().setValue(Paint.valueOf("red"));
      supBtn.setGraphic(fontAwesomeIconView);
   }

   public Page(int id, String namePage, String urlPage, String photo, LocalTime dateTime) {
      this.id = id;
      this.namePage = namePage;
      this.urlPage = urlPage;
      this.photo = photo;
      this.dateTime = dateTime;
      this.supBtn=new Button();
      FontAwesomeIconView fontAwesomeIconView=new FontAwesomeIconView();
      fontAwesomeIconView.setGlyphName("CLOSE");
      fontAwesomeIconView.setSize("25");
      fontAwesomeIconView.fillProperty().setValue(Paint.valueOf("red"));
      supBtn.setGraphic(fontAwesomeIconView);
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getNamePage() {
      return namePage;
   }

   public void setNamePage(String namePage) {
      this.namePage = namePage;
   }

   public String getUrlPage() {
      return urlPage;
   }

   public void setUrlPage(String urlPage) {
      this.urlPage = urlPage;
   }

   public String getPhoto() {
      return photo;
   }

   public void setPhoto(String photo) {
      this.photo = photo;
   }

   public LocalTime getDateTime() {
      return dateTime;
   }

   public void setDateTime(LocalTime dateTime) {
      this.dateTime = dateTime;
   }

   @Override
   public String toString() {
      return      id +
                  namePage + '\'' +
                  urlPage + '\'' +
                  photo + '\'' +
                  dateTime ;
   }
}