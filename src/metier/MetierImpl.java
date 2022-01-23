package metier;

import javafx.util.converter.LocalDateTimeStringConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MetierImpl implements IMetier{

    private Connection connection;

    public MetierImpl() {
        connection= SignletonConnexionDB.getConnection();
    }


    @Override
    public void addPage(Page page) {
        try {
            PreparedStatement pstm=connection.prepareStatement("insert into PAGE(IDHISTORIQUE,IDFAVORI,NAMEPAGE,URLPAGE,PHOTO,DATEPAGE) values(?,?,?,?,?,?)");
            pstm.setDate(1,Date.valueOf(page.getHistorique().getIdHistorique()));
            pstm.setInt(2,page.getFavori().getIdFavori());
            pstm.setString(3,page.getNamePage());
            pstm.setString(4,page.getUrlPage());
            pstm.setString(5,page.getPhoto());
            pstm.setTime(6,Time.valueOf(page.getDateTime()));
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Page> getAllPage() {
        Connection conn=SignletonConnexionDB.getConnection();
        List<Page> pages=new ArrayList<>();
        try {
            PreparedStatement pstm=conn.prepareStatement("select * from PAGE");
            ResultSet rs= pstm.executeQuery();
            while (rs.next()){
                Page p=new Page(rs.getInt("IDPAGE"),rs.getString("NAMEPAGE"), rs.getString("URLPAGE"),rs.getString("PHOTO"),rs.getTime("DATEPAGE").toLocalTime());
                p.setHistorique(new Historique(rs.getDate("IDHISTORIQUE").toLocalDate()));
                p.setFavori(new Favori(rs.getInt("IDFAVORI")));
                //Page p=new Page();
                //System.out.println(rs.getDate("DATEPAGE").toLocalDate().atTime(rs.getTime("DATEPAGE").toLocalTime()));
                pages.add(p);
                ////////////interet de 'historique/////////////////
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  pages;
    }

    @Override
    public List<Page> searchPage(String cle) {
        List<Page> pages=new ArrayList<>();
        try {
            PreparedStatement pstm=connection.prepareStatement("select * from PAGE where NAMEPAGE like ?");
            pstm.setString(1,"%"+cle+"%");
            pstm.executeQuery();
            ResultSet rs= pstm.getResultSet();
            while (rs.next()){
                Page p=new Page();
                p.setId(rs.getInt("IDPAGE"));
                p.setHistorique(new Historique(rs.getDate("IDHISTORIQUE").toLocalDate()));
                p.setFavori(new Favori(rs.getInt("IDFAVORI")));
                p.setNamePage(rs.getString("NAMEPAGE"));
                p.setUrlPage(rs.getString("URLPAGE"));
                p.setPhoto(rs.getString("PHOTO"));
                p.setDateTime(rs.getTime("DATEPAGE").toLocalTime());
                pages.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pages;
    }


    @Override
    public void deletePage(Page page) {
        Connection conn=SignletonConnexionDB.getConnection();
        try {
            PreparedStatement pstm=conn.prepareStatement("DELETE FROM PAGE WHERE IDPAGE=?");
            pstm.setInt(1,page.getId());
            pstm.executeUpdate();
            System.out.println("La page a été supprimer avec succès...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void affectPageTofavorite(Favori favori, Page page) throws SQLException {
        page.setFavori(favori);
        Connection conn=SignletonConnexionDB.getConnection();
        try {
            PreparedStatement pstm=conn.prepareStatement("update PAGE SET IDFAVORI=? WHERE IDPAGE=?");
            pstm.setInt(1,favori.getIdFavori());
            pstm.setInt(2,page.getId());
            pstm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void affectPageToHistorical(Historique historique, Page page) throws SQLException {
        page.setHistorique(historique);
        Connection conn=SignletonConnexionDB.getConnection();
        try {
            PreparedStatement pstm=conn.prepareStatement("update PAGE SET IDHISTORIQUE=? WHERE IDPAGE=?");
            pstm.setDate(1,Date.valueOf(historique.getIdHistorique()));
            pstm.setInt(2,page.getId());
            pstm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void affectPageToDownload(Telechargement telechargement, Page page) throws SQLException {
        Connection conn=SignletonConnexionDB.getConnection();
        try {
            PreparedStatement pstm=conn.prepareStatement("update PAGE SET IDTELECHARGEMENT=? WHERE IDPAGE=?");
            pstm.setInt(1,telechargement.getIdTelechargement());
            pstm.setInt(2,page.getId());
            pstm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Page> getPageByIdHistorique(LocalDate date) {
        Connection conn=SignletonConnexionDB.getConnection();
        List<Page> pages=new ArrayList<>();
        try {
            PreparedStatement pstm=conn.prepareStatement("select * from PAGE WHERE IDHISTORIQUE=?");
            pstm.setDate(1,Date.valueOf(date));
            ResultSet rs= pstm.executeQuery();
            while (rs.next()){
                Page p=new Page(rs.getInt("IDPAGE"),rs.getString("NAMEPAGE"), rs.getString("URLPAGE"),rs.getString("PHOTO"),rs.getTime("DATEPAGE").toLocalTime());
                p.setHistorique(new Historique(rs.getDate("IDHISTORIQUE").toLocalDate()));
                p.setFavori(new Favori(rs.getInt("IDFAVORI")));
                pages.add(p);
                ////////////interet de 'historique/////////////////
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  pages;
    }


    @Override
    public void supprimerPageInHistorique(Page page) {
        Connection conn=SignletonConnexionDB.getConnection();
        try {
            PreparedStatement pstm=conn.prepareStatement("update PAGE SET IDHISTORIQUE=? WHERE IDPAGE=?");
            pstm.setDate(1,Date.valueOf("0001-01-01"));
            pstm.setInt(2,page.getId());
            pstm.executeUpdate();
            System.out.println("l'historique a été supprimer avec succès...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void supprimerPageInFavoris(Page page){
        Connection conn=SignletonConnexionDB.getConnection();
        try {
            PreparedStatement pstm=conn.prepareStatement("update PAGE SET IDFAVORI=? WHERE IDPAGE=?");
            pstm.setInt(1,1);
            pstm.setInt(2,page.getId());
            pstm.executeUpdate();
            System.out.println("Favori a été supprimer avec succès...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterPagetoFavoris(Page page){
        Connection conn=SignletonConnexionDB.getConnection();
        try {
            PreparedStatement pstm=conn.prepareStatement("update PAGE SET IDFAVORI=? WHERE IDPAGE=?");
            pstm.setInt(1,2);
            pstm.setInt(2,page.getId());
            pstm.executeUpdate();
            System.out.println("La page a été ajouter au favori avec succès...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}