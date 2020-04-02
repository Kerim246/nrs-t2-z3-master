/*package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IspitDAOTest {

    @Test
    void testIzmijeniNajveci() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();

        GeografijaDAO dao = GeografijaDAO.getInstance();
        Drzava austrija = dao.nadjiDrzavu("Austrija");
        Grad graz = dao.nadjiGrad("Graz");
        austrija.setNajveciGrad(graz);

        assertEquals("Graz", austrija.getNajveciGrad().getNaziv());
    }

    @Test
    void testDefaultNajveci() {
        // Najveći grad po defaultu treba biti glavni
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();

        GeografijaDAO dao = GeografijaDAO.getInstance();
        // Najprije kreiramo grad Sarajevo
        Drzava vb = dao.nadjiDrzavu("Velika Britanija");
        Grad sarajevo = new Grad(0, "Sarajevo", 350000, vb);
        dao.dodajGrad(sarajevo);

        // Da li je grad dodat u bazu?
        Grad s2 = dao.nadjiGrad("Sarajevo");
        assertNotNull(s2);

        // Zatim kreiramo državu BiH
        Drzava bih = new Drzava(0, "Bosna i Hercegovina", s2);
        dao.dodajDrzavu(bih);

        // Da li je drzava dodata u bazu?
        Drzava bih2 = dao.nadjiDrzavu("Bosna i Hercegovina");
        assertNotNull(bih2);
        assertEquals("Sarajevo", bih2.getNajveciGrad().getNaziv());
        assertEquals(s2.getId(), bih2.getNajveciGrad().getId());
    }

    @Test
    void testNajveciDodaj() {
        // Dodavanje nove države gdje se glavni i najveći grad razlikuju
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();

        GeografijaDAO dao = GeografijaDAO.getInstance();
        // Najprije kreiramo gradove Washington i New York
        Drzava vb = dao.nadjiDrzavu("Velika Britanija");
        Grad dc = new Grad(0, "Washington, D.C.", 633427, vb);
        dao.dodajGrad(dc);
        Grad ny = new Grad(0, "New York City", 8623000, vb);
        dao.dodajGrad(ny);

        // Da li su gradovi dodati u bazu?
        Grad dc2 = dao.nadjiGrad("Washington, D.C.");
        assertNotNull(dc2);
        Grad ny2 = dao.nadjiGrad("New York City");
        assertNotNull(ny2);

        // Zatim kreiramo državu USA sa glavnim i najvećim gradom
        Drzava usa = new Drzava(0, "United States of America", dc2, ny2);
        dao.dodajDrzavu(usa);

        // Da li je drzava dodata u bazu?
        Drzava usa2 = dao.nadjiDrzavu("United States of America");
        assertNotNull(usa2);
        assertEquals("Washington, D.C.", usa2.getGlavniGrad().getNaziv());
        assertEquals(dc2.getId(), usa2.getGlavniGrad().getId());
        assertEquals("New York City", usa2.getNajveciGrad().getNaziv());
        assertEquals(ny2.getId(), usa2.getNajveciGrad().getId());
    }


    @Test
    void testBazaDirekt() {
        // Test koji direktno pristupa bazi zaobilazeći DAO klasu

        // Regenerišemo bazu ako je promijenjena prethodnim testovima
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        GeografijaDAO dao = GeografijaDAO.getInstance();

        // Sad ćemo se opet diskonektovati jer radimo sa bazom direktno
        GeografijaDAO.removeInstance();

        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
            try {
                PreparedStatement najveciGradUpit = conn.prepareStatement("SELECT najveci_grad FROM drzava WHERE id=1");
                najveciGradUpit.execute();
                conn.close();
            } catch (SQLException e) {
                fail("Tabela drzava ne sadrži kolonu najveci_grad");
            }
        } catch (SQLException e) {
            fail("Datoteka sa bazom ne postoji ili je nedostupna");
        }
    }
}
*/