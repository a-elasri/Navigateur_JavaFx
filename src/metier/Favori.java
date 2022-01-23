package metier;

import java.util.*;

public class Favori {
   private int idFavori;
   
   public java.util.List<Page> page;


   public Favori() {
   }

   public Favori(int idFavori, List<Page> page) {
      this.idFavori = idFavori;
      this.page = page;
   }

   public java.util.List<Page> getPage() {
      if (page == null)
         page = new java.util.ArrayList<Page>();
      return page;
   }
   
   public java.util.Iterator getIteratorPage() {
      if (page == null)
         page = new java.util.ArrayList<Page>();
      return page.iterator();
   }

   public void setPage(java.util.List<Page> newPage) {
      removeAllPage();
      for (java.util.Iterator iter = newPage.iterator(); iter.hasNext();)
         addPage((Page)iter.next());
   }

   public void addPage(Page newPage) {
      if (newPage == null)
         return;
      if (this.page == null)
         this.page = new java.util.ArrayList<Page>();
      if (!this.page.contains(newPage))
      {
         this.page.add(newPage);
         newPage.setFavori(this);      
      }
   }
   

   public void removePage(Page oldPage) {
      if (oldPage == null)
         return;
      if (this.page != null)
         if (this.page.contains(oldPage))
         {
            this.page.remove(oldPage);
            oldPage.setFavori((Favori)null);
         }
   }
   
   public void removeAllPage() {
      if (page != null)
      {
         Page oldPage;
         for (java.util.Iterator iter = getIteratorPage(); iter.hasNext();)
         {
            oldPage = (Page)iter.next();
            iter.remove();
            oldPage.setFavori((Favori)null);
         }
      }
   }

   public Favori(int idFavori) {
      this.idFavori = idFavori;
   }


   public int getIdFavori() {
      return idFavori;
   }

   public void setIdFavori(int idFavori) {
      this.idFavori = idFavori;
   }

   @Override
   public String toString() {
      return "Favori{" +
              "idFavori=" + idFavori +
              ", page=" + page +
              '}';
   }
}