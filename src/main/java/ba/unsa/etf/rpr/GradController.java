package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javafx.application.Platform;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GradController {
    public TextField fieldNaziv;
    public TextField fieldBrojStanovnika;
    public ChoiceBox<Drzava> choiceDrzava;
    public ObservableList<Drzava> listDrzave;
    private Grad grad;
    public TextField fieldPostanskiBroj;
    private GeografijaDAO dao;

    private ObservableList<Znamenitosti> znamenitosti;
    public ListView<Znamenitosti> listViewZnamenitosti;

    public GradController(Grad grad, ArrayList<Drzava> drzave) {
        this.grad = grad;
        listDrzave = FXCollections.observableArrayList(drzave);
    }

    public GradController(Grad grad, ArrayList<Drzava> drzave, ObservableList<Znamenitosti> listaZnamenitosti) {
        this.grad = grad;
        listDrzave = FXCollections.observableArrayList(drzave);
        znamenitosti = listaZnamenitosti;
        dao = GeografijaDAO.getInstance();
    }

    @FXML
    public void initialize() {
        choiceDrzava.setItems(listDrzave);
        if (grad != null) {
            fieldNaziv.setText(grad.getNaziv());
            fieldBrojStanovnika.setText(Integer.toString(grad.getBrojStanovnika()));
            fieldPostanskiBroj.setText(Integer.toString(grad.getPostanskiBroj()));
            // choiceDrzava.getSelectionModel().select(grad.getDrzava());
            // ovo ne radi jer grad.getDrzava() nije identički jednak objekat kao član listDrzave
            for (Drzava drzava : listDrzave)
                if (drzava.getId() == grad.getDrzava().getId())
                    choiceDrzava.getSelectionModel().select(drzava);
        } else {
            choiceDrzava.getSelectionModel().selectFirst();
        }
        listViewZnamenitosti.setItems(znamenitosti);
    }

    public Grad getGrad() {
        return grad;
    }

    public void clickCancel(ActionEvent actionEvent) {
        grad = null;
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean sveOk = true;

        if (fieldNaziv.getText().trim().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
        }
        int brojStanovnika = 0;
        try {
            brojStanovnika = Integer.parseInt(fieldBrojStanovnika.getText());
        } catch (NumberFormatException e) {
            System.out.println("Greska u polju za broj stanovnika!");
        }
        if (brojStanovnika <= 0) {
            fieldBrojStanovnika.getStyleClass().removeAll("poljeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldBrojStanovnika.getStyleClass().removeAll("poljeNijeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeIspravno");
        }

        if (!sveOk) return;

        String adresa = "http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=" + fieldPostanskiBroj.getText();

        Thread thread = new Thread(() -> {
            int postBroj = Integer.parseInt(fieldPostanskiBroj.getText());
            try {
                URL provjera = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj="+postBroj);

                BufferedReader buffer = new BufferedReader(new InputStreamReader(provjera.openStream(), StandardCharsets.UTF_8));
                String rez = "", line = null;
                while ((line = buffer.readLine()) != null)
                    rez = rez + line;
                buffer.close();
                if(rez.equals("OK")) {
                    fieldPostanskiBroj.getStyleClass().removeAll("poljeNijeIspravno");
                    fieldPostanskiBroj.getStyleClass().add("poljeIspravno");
                } else if(rez.equals("NOT OK")){
                    fieldPostanskiBroj.getStyleClass().removeAll("poljeIspravno");
                    fieldPostanskiBroj.getStyleClass().add("poljeNijeIspravno");

                    Platform.runLater(() -> {
                        if (grad == null) grad = new Grad();
                        grad.setNaziv(fieldNaziv.getText());
                        grad.setBrojStanovnika(Integer.parseInt(fieldBrojStanovnika.getText()));
                        grad.setDrzava(choiceDrzava.getValue());
                        grad.setPostanskiBroj(postBroj);
                        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
                        stage.close();
                    });
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }

    public void clickDodajZnamenitosti(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/znamenitosti.fxml"));
            ZnamenitostiController znamenitostiController = new ZnamenitostiController(grad, znamenitosti);
            loader.setController(znamenitostiController);
            root = loader.load();
            stage.setTitle("Znamenitosti grada");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding(event -> {
                Znamenitosti znamenitost = znamenitostiController.getZnamenitost();
                if (znamenitost != null) {
                    dao.dodajZnamenitost(znamenitost);
                    znamenitosti.setAll(dao.nadjiZnamenitosti(grad));
                    listViewZnamenitosti.refresh();
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prikaziOdabranuZnamenitost() {
        Stage stage = new Stage();
        Parent root = null;
        try {
            Znamenitosti znamenitost = new Znamenitosti();
            znamenitost = dao.nadjiZnamenitostPoNazivu(listViewZnamenitosti.getSelectionModel().getSelectedItem().toString(), grad);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/znamenitosti.fxml"));
            ZnamenitostiController znamenitostiController = new ZnamenitostiController(grad, znamenitosti);
            loader.setController(znamenitostiController);
            root = loader.load();
            stage.setTitle("Znamenitost " + listViewZnamenitosti.getSelectionModel().getSelectedItem().toString());
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();
            znamenitostiController.initialize(znamenitost);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
