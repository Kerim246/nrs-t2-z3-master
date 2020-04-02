package ba.unsa.etf.rpr;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PretragaController implements Initializable {
    public TextField txtFieldTraziPoUzorku;
    public ListView<String> listViewPretrage;
    public ArrayList<String> filesToAddToList;
    public ObservableList<String> listOfFiles;
    private File slika;
    private Thread pretraga;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pretraga = new Thread();
        filesToAddToList = new ArrayList<>();
        listOfFiles = FXCollections.observableArrayList(filesToAddToList);
        listViewPretrage.setItems(listOfFiles);
    }

    public File getSlika() {
        return slika;
    }

    public void pretrazi() {
        pretraga = new Thread(() -> {
            traziPoUzorku(new File(System.getProperty("user.home")), txtFieldTraziPoUzorku.getText());
        });
        pretraga.start();
    }


    public void traziPoUzorku(File file, String uzorak) {
        File[] files = file.listFiles();

        if (files == null) return;
        for ( File aFile : files ) {
            if ( aFile.isDirectory() ) {
                traziPoUzorku(aFile, uzorak);
            }
            else {
                if (aFile.getAbsolutePath().toLowerCase().contains(uzorak.toLowerCase()))
                    Platform.runLater(() -> {
                        listOfFiles.add(aFile.getAbsolutePath());
                    });
            }
        }
    }

    public void izaberiSliku() {
        listViewPretrage.setItems(listOfFiles);
        pretraga.interrupt();
        if (!listViewPretrage.getSelectionModel().getSelectedItem().isEmpty())
            slika = new File(listViewPretrage.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) listViewPretrage.getScene().getWindow();
        stage.close();
    }

}
