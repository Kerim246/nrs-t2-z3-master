package ba.unsa.etf.rpr;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ZnamenitostiController {
    public TextField txtNazivZnamenitosti;
    public ImageView imageZnamenitost;
    private Grad grad;
    private File slika;
    private Znamenitosti znamenitost;
    private ObservableList<Znamenitosti> listaZnamenitosti;

    public ZnamenitostiController(Grad grad, ObservableList<Znamenitosti> znamenitosti) {
        this.grad = grad;
        listaZnamenitosti = znamenitosti;
        slika = new File("");
    }

    public void initialize(Znamenitosti znamenitost) {
        txtNazivZnamenitosti.setText(znamenitost.getNaziv());
        Image image = new Image(znamenitost.getSlika());
        imageZnamenitost.setImage(image);
    }

    public void actionOdaberiSliku(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pretraga.fxml"));
            PretragaController pretragaController = new PretragaController();
            loader.setController(pretragaController);
            root = loader.load();
            stage.setTitle("Pretraga datoteka");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                slika = pretragaController.getSlika();
                imageZnamenitost.setImage(new Image(slika.toURI().toString()));
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Unesite ime slike");
        Optional<String> result = td.showAndWait();
        imeSlike = result.get();

        imageFile = new File(System.getProperty("user.home"), "/Desktop/slikeProba/" + imeSlike + ".jpg");
        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            imageZnamenitost.setImage(image);
        }*/
    }

    public void actionSpasiZnamenitost() {
        if (znamenitost == null) znamenitost = new Znamenitosti();
        znamenitost.setNaziv(txtNazivZnamenitosti.getText());
        znamenitost.setSlika(slika.toURI().toString());
        znamenitost.setGradId(grad);
        Stage stage = (Stage) txtNazivZnamenitosti.getScene().getWindow();
        stage.close();
    }

    public Znamenitosti getZnamenitost() {
        return znamenitost;
    }

}
