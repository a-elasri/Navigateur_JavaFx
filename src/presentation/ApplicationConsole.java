package presentation;

import metier.IMetier;
import metier.MetierImpl;
import metier.Page;

import java.util.ArrayList;
import java.util.List;

public class ApplicationConsole {
    public static void main(String[] args) {

        IMetier metier=new MetierImpl();
        List<Page> pages=new ArrayList<>();
        pages=metier.getAllPage();
        for (Page d:pages) {
            System.out.println(d);
        }
    }
}
