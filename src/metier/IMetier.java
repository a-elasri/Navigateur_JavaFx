package metier;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface IMetier {

    void addPage(Page page);

    List<Page> getAllPage();
//    List<Historique> getAllHistorique();
    List<Page> searchPage(String cle);
    void deletePage(Page page);
    void affectPageTofavorite(Favori favori,Page page) throws SQLException;
    void affectPageToHistorical(Historique historique,Page page) throws SQLException;
    void affectPageToDownload(Telechargement telechargement,Page page) throws SQLException;
    List<Page> getPageByIdHistorique(LocalDate date);
    void supprimerPageInHistorique(Page page);
    void supprimerPageInFavoris (Page page);
    void ajouterPagetoFavoris(Page page);

}
