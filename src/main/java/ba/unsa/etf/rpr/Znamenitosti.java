package ba.unsa.etf.rpr;

public class Znamenitosti {
    private int id;
    private String naziv, slika;
    private Grad gradId;

    public Znamenitosti(int id, String naziv, String slika, Grad gradId) {
        this.id = id;
        this.naziv = naziv;
        this.slika = slika;
        this.gradId = gradId;
    }

    public Znamenitosti() {
    }

    public Znamenitosti(String text, String imeSlike, Grad grad) {
        this.naziv = naziv;
        this.slika = slika;
        this.gradId = gradId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public Grad getGradId() {
        return gradId;
    }

    @Override
    public String toString() {
        return naziv;
    }

    public void setGradId(Grad gradId) {
        this.gradId = gradId;
    }
}
