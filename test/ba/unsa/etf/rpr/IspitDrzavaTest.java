/*package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IspitDrzavaTest {
    @Test
    void testDrzava() {
        Grad paris = new Grad(1, "Paris", 2206488, null);
        Grad marseille = new Grad(2, "Marseille", 3000000, null);
        Drzava francuska = new Drzava(0, "Francuska", paris);
        francuska.setNajveciGrad(marseille);
        assertEquals("Marseille", francuska.getNajveciGrad().getNaziv());
    }

    @Test
    void testDrzavaCtor() {
        // Testiramo novi konstruktor klase država
        Grad paris = new Grad(1, "Paris", 2206488, null);
        Grad marseille = new Grad(2, "Marseille", 3000000, null);
        Drzava francuska = new Drzava(0, "Francuska", paris, marseille);
        assertEquals("Marseille", francuska.getNajveciGrad().getNaziv());
    }

    @Test
    void testDrzavaDefault() {
        // Glavni grad po defaultu treba biti i najveći
        Grad paris = new Grad(1, "Paris", 2206488, null);
        Drzava francuska = new Drzava(0, "Francuska", paris);
        assertEquals("Paris", francuska.getNajveciGrad().getNaziv());
    }

}*/