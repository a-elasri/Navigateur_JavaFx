package metier;

import java.time.LocalDate;
import java.util.*;
public class Historique {
   private LocalDate idHistorique;
   
   public java.util.List<Page> page;

   public Historique() {
   }

   public Historique(LocalDate idHistorique, List<Page> page) {
      this.idHistorique = idHistorique;
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
         newPage.setHistorique(this);      
      }
   }
   

   public void removePage(Page oldPage) {
      if (oldPage == null)
         return;
      if (this.page != null)
         if (this.page.contains(oldPage))
         {
            this.page.remove(oldPage);
            oldPage.setHistorique((Historique)null);
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
            oldPage.setHistorique((Historique)null);
         }
      }
   }


   public Historique(LocalDate idHistorique) {
      this.idHistorique = idHistorique;
   }

   public LocalDate getIdHistorique() {
      return idHistorique;
   }

   public void setIdHistorique(LocalDate idHistorique) {
      this.idHistorique = idHistorique;
   }
}