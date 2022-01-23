package metier;

import java.util.*;

public class Telechargement {
   private int idTelechargement;
   private String nameTelechargement;
   private String urlTelechargement;
   private String location;
   private String statu;
   private Date dateTelechargement;

   public Telechargement( String nameTelechargement, String urlTelechargement, String location, String statu, Date dateTelechargement) {
      this.nameTelechargement = nameTelechargement;
      this.urlTelechargement = urlTelechargement;
      this.location = location;
      this.statu = statu;
      this.dateTelechargement = dateTelechargement;
   }

   public Telechargement(int idTelechargement, String nameTelechargement, String urlTelechargement, String location, String statu, Date dateTelechargement) {
      this.idTelechargement = idTelechargement;
      this.nameTelechargement = nameTelechargement;
      this.urlTelechargement = urlTelechargement;
      this.location = location;
      this.statu = statu;
      this.dateTelechargement = dateTelechargement;
   }

   public Telechargement() {
   }

   public int getIdTelechargement() {
      return idTelechargement;
   }

   public void setIdTelechargement(int idTelechargement) {
      this.idTelechargement = idTelechargement;
   }

   public String getNameTelechargement() {
      return nameTelechargement;
   }

   public void setNameTelechargement(String nameTelechargement) {
      this.nameTelechargement = nameTelechargement;
   }

   public String getUrlTelechargement() {
      return urlTelechargement;
   }

   public void setUrlTelechargement(String urlTelechargement) {
      this.urlTelechargement = urlTelechargement;
   }

   public String getLocation() {
      return location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public String getStatu() {
      return statu;
   }

   public void setStatu(String statu) {
      this.statu = statu;
   }

   public Date getDateTelechargement() {
      return dateTelechargement;
   }

   public void setDateTelechargement(Date dateTelechargement) {
      this.dateTelechargement = dateTelechargement;
   }

   @Override
   public String toString() {
      return "Telechargement{" +
              "idTelechargement=" + idTelechargement +
              ", nameTelechargement='" + nameTelechargement + '\'' +
              ", urlTelechargement='" + urlTelechargement + '\'' +
              ", location='" + location + '\'' +
              ", statu='" + statu + '\'' +
              ", dateTelechargement=" + dateTelechargement +
              '}';
   }
}