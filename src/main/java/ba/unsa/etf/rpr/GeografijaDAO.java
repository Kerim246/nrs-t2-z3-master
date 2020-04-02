package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GeografijaDAO {
    private static GeografijaDAO instance;
    private Connection conn;

    private PreparedStatement glavniGradUpit, dajDrzavuUpit, dajZnamenitostiUpit, obrisiDrzavuUpit, obrisiGradoveZaDrzavuUpit, nadjiDrzavuUpit,
            dajGradoveUpit, dodajGradUpit, odrediIdGradaUpit, dodajDrzavuUpit, odrediIdDrzaveUpit, promijeniGradUpit, dajGradUpit,
            nadjiGradUpit, obrisiGradUpit, dajDrzaveUpit, dajOdredjenuZnamenitostUpit, dodajZnamenitostUpit, odrediIdZnamenitostiUpit,
            odrediVrijednostForeignKeyaGrada, nadjiZnamenitostUpit, nadjiZnamenitostPoNazivuUpit;

    public static GeografijaDAO getInstance() {
        if (instance == null) instance = new GeografijaDAO();
        return instance;
    }

    public Connection getConn() { return conn; }
    private GeografijaDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            glavniGradUpit = conn.prepareStatement("SELECT grad.id, grad.naziv, grad.broj_stanovnika, grad.drzava, grad.postanski_broj FROM grad, drzava WHERE grad.drzava=drzava.id AND drzava.naziv=?");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                glavniGradUpit = conn.prepareStatement("SELECT grad.id, grad.naziv, grad.broj_stanovnika, grad.drzava, grad.postanski_broj FROM grad, drzava WHERE grad.drzava=drzava.id AND drzava.naziv=?");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            dajDrzavuUpit = conn.prepareStatement("SELECT * FROM drzava WHERE id=?");
            dajGradUpit = conn.prepareStatement("SELECT * FROM grad WHERE id=?");
            dajOdredjenuZnamenitostUpit = conn.prepareStatement("SELECT * FROM znamenitosti WHERE grad_id = ?");
            obrisiGradoveZaDrzavuUpit = conn.prepareStatement("DELETE FROM grad WHERE drzava=?");
            obrisiDrzavuUpit = conn.prepareStatement("DELETE FROM drzava WHERE id=?");
            obrisiGradUpit = conn.prepareStatement("DELETE FROM grad WHERE id=?");
            nadjiDrzavuUpit = conn.prepareStatement("SELECT * FROM drzava WHERE naziv=?");
            nadjiGradUpit = conn.prepareStatement("SELECT * FROM grad WHERE naziv=?");
            nadjiZnamenitostUpit = conn.prepareStatement("SELECT * FROM znamenitosti WHERE grad_id=?");
            dajGradoveUpit = conn.prepareStatement("SELECT * FROM grad ORDER BY broj_stanovnika DESC");
            dajDrzaveUpit = conn.prepareStatement("SELECT * FROM drzava ORDER BY naziv");
            dajZnamenitostiUpit = conn.prepareStatement("SELECT * FROM znamenitosti ORDER BY naziv");

            dodajGradUpit = conn.prepareStatement("INSERT INTO grad VALUES(?,?,?,?,?)");
            odrediIdGradaUpit = conn.prepareStatement("SELECT MAX(id)+1 FROM grad");
            dodajDrzavuUpit = conn.prepareStatement("INSERT INTO drzava VALUES(?,?,?)");
            dodajZnamenitostUpit = conn.prepareStatement("INSERT INTO znamenitosti VALUES (?,?,?,?)");
            odrediIdDrzaveUpit = conn.prepareStatement("SELECT MAX(id)+1 FROM drzava");
            odrediIdZnamenitostiUpit = conn.prepareStatement("SELECT  MAX(id) + 1 FROM znamenitosti");

            nadjiZnamenitostPoNazivuUpit = conn.prepareStatement("SELECT * FROM znamenitosti WHERE naziv=?");


            promijeniGradUpit = conn.prepareStatement("UPDATE grad SET naziv=?, broj_stanovnika=?, drzava=?, postanski_broj=? WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Grad glavniGrad(String drzava) {
        try {
            Drzava d = nadjiDrzavu(drzava);
            glavniGradUpit.setString(1, drzava);
            ResultSet rs = glavniGradUpit.executeQuery();
            if (!rs.next()) return null;
            return dajGradIzResultSeta(rs, d);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Grad dajGradIzResultSeta(ResultSet rs, Drzava d) throws SQLException {
        return new Grad(rs.getInt(1), rs.getString(2), rs.getInt(3), d, rs.getInt(5));
    }

    private Drzava dajDrzavu(int id) {
        try {
            dajDrzavuUpit.setInt(1, id);
            ResultSet rs = dajDrzavuUpit.executeQuery();
            if (!rs.next()) return null;
            return dajDrzavuIzResultSeta(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Grad dajGrad(int id, Drzava d) {
        try {
            dajGradUpit.setInt(1, id);
            ResultSet rs = dajGradUpit.executeQuery();
            if (!rs.next()) return null;
            return dajGradIzResultSeta(rs, d);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Znamenitosti dajZnamenitost (int id, Grad grad) {
        try {
            dajOdredjenuZnamenitostUpit.setInt(1, id);
            ResultSet resultSet = dajOdredjenuZnamenitostUpit.executeQuery();
            if (!resultSet.next()) return null;
            return dajZnamenitostIzResultSeta(resultSet, grad);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Znamenitosti dajZnamenitostIzResultSeta(ResultSet rs, Grad grad) throws SQLException {
        return new Znamenitosti(rs.getInt(1), rs.getString(2), rs.getString(3), grad);
    }

    private Drzava dajDrzavuIzResultSeta(ResultSet rs) throws SQLException {
        Drzava d = new Drzava(rs.getInt(1), rs.getString(2), null);
        d.setGlavniGrad( dajGrad(rs.getInt(3), d ));
        return d;
    }

    public void obrisiDrzavu(String nazivDrzave) {
        try {
            nadjiDrzavuUpit.setString(1, nazivDrzave);
            ResultSet rs = nadjiDrzavuUpit.executeQuery();
            if (!rs.next()) return;
            Drzava drzava = dajDrzavuIzResultSeta(rs);

            obrisiGradoveZaDrzavuUpit.setInt(1, drzava.getId());
            obrisiGradoveZaDrzavuUpit.executeUpdate();

            obrisiDrzavuUpit.setInt(1, drzava.getId());
            obrisiDrzavuUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Grad> gradovi() {
        ArrayList<Grad> rezultat = new ArrayList();
        try {
            ResultSet rs = dajGradoveUpit.executeQuery();
            while (rs.next()) {
                Drzava d = dajDrzavu(rs.getInt(4));
                Grad grad = dajGradIzResultSeta(rs, d);
                rezultat.add(grad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public ArrayList<Znamenitosti> znamenitosti() {             // Ovdje moze biti greska...ne znam
        ArrayList<Znamenitosti> rezultat = new ArrayList();
        try {
            ResultSet rs = dajZnamenitostiUpit.executeQuery();
            ResultSet rs2 = dajGradoveUpit.executeQuery();
            while (rs.next()) {
                Drzava d = dajDrzavu(rs2.getInt(4));
                Grad grad = dajGradIzResultSeta(rs2, d);
                Znamenitosti znamenitost = dajZnamenitostIzResultSeta(rs, grad);
                rezultat.add(znamenitost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public ArrayList<Drzava> drzave() {
        ArrayList<Drzava> rezultat = new ArrayList();
        try {
            ResultSet rs = dajDrzaveUpit.executeQuery();
            while (rs.next()) {
                Drzava drzava = dajDrzavuIzResultSeta(rs);
                rezultat.add(drzava);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void dodajGrad(Grad grad) {
        try {
            ResultSet rs = odrediIdGradaUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            dodajGradUpit.setInt(1, id);
            dodajGradUpit.setString(2, grad.getNaziv());
            dodajGradUpit.setInt(3, grad.getBrojStanovnika());
            dodajGradUpit.setInt(4, grad.getDrzava().getId());
            dodajGradUpit.setInt(5, grad.getPostanskiBroj());
            dodajGradUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajZnamenitost(Znamenitosti znamenitost) {
        try {
            ResultSet rs = odrediIdZnamenitostiUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            dodajZnamenitostUpit.setInt(1, id);
            dodajZnamenitostUpit.setString(2, znamenitost.getNaziv());
            dodajZnamenitostUpit.setString(3, znamenitost.getSlika());
            dodajZnamenitostUpit.setInt(4, znamenitost.getGradId().getId());
            dodajZnamenitostUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajDrzavu(Drzava drzava) {
        try {
            ResultSet rs = odrediIdDrzaveUpit.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            dodajDrzavuUpit.setInt(1, id);
            dodajDrzavuUpit.setString(2, drzava.getNaziv());
            dodajDrzavuUpit.setInt(3, drzava.getGlavniGrad().getId());
            dodajDrzavuUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniGrad(Grad grad) {
        try {
            promijeniGradUpit.setString(1, grad.getNaziv());
            promijeniGradUpit.setInt(2, grad.getBrojStanovnika());
            promijeniGradUpit.setInt(3, grad.getDrzava().getId());
            promijeniGradUpit.setInt(4, grad.getPostanskiBroj());
            promijeniGradUpit.setInt(5, grad.getId());
            promijeniGradUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Drzava nadjiDrzavu(String nazivDrzave) {
        try {
            nadjiDrzavuUpit.setString(1, nazivDrzave);
            ResultSet rs = nadjiDrzavuUpit.executeQuery();
            if (!rs.next()) return null;
            return dajDrzavuIzResultSeta(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Grad nadjiGrad(String nazivGrada) {
        try {
            nadjiGradUpit.setString(1, nazivGrada);
            ResultSet rs = nadjiGradUpit.executeQuery();
            if (!rs.next()) return null;
            Drzava d = dajDrzavu(rs.getInt(4));
            return dajGradIzResultSeta(rs, d);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Znamenitosti> nadjiZnamenitosti(Grad grad) {
        ArrayList<Znamenitosti> vratiSveSaIstimID = new ArrayList<>();
        try {
            nadjiZnamenitostUpit.setInt(1, grad.getId());
            ResultSet rs = nadjiZnamenitostUpit.executeQuery();
            while (rs.next()) {
                Znamenitosti znamenitost = dajZnamenitostIzResultSeta(rs, grad);
                vratiSveSaIstimID.add(znamenitost);
            }
            return vratiSveSaIstimID;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Znamenitosti nadjiZnamenitostPoNazivu(String ime, Grad grad) {
        Znamenitosti znamenitost = new Znamenitosti();
        try {
            nadjiZnamenitostPoNazivuUpit.setString(1, ime);
            ResultSet rs = nadjiZnamenitostPoNazivuUpit.executeQuery();
            if (rs.next()) {
                znamenitost = dajZnamenitostIzResultSeta(rs, ime, grad);
            }
            return znamenitost;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Znamenitosti dajZnamenitostIzResultSeta(ResultSet rs, String naziv, Grad grad) throws SQLException {
        return new Znamenitosti(rs.getInt(1), naziv, rs.getString(3), grad);
    }

    public void obrisiGrad(Grad grad) {
        try {
            obrisiGradUpit.setInt(1, grad.getId());
            obrisiGradUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
