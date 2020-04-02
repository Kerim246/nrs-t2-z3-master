/*package ba.unsa.etf.rpr;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class IspitGlavnaTest {
    Stage theStage;
    GlavnaController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"));
        ctrl = new GlavnaController();
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();

        stage.toFront();

        theStage = stage;
    }

    @Test
    public void testDodajDrzava(FxRobot robot) {
        ctrl.resetujBazu();

        // Otvaranje forme za dodavanje
        robot.clickOn("#btnDodajDrzavu");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#fieldNaziv").tryQuery().isPresent();

        // Ovo moramo jer robot klika po glavnoj formi umjesto po ovoj
        Platform.runLater(() -> theStage.hide());

        // Postoji li fieldNaziv
        robot.clickOn("#fieldNaziv");
        robot.write("Bosna i Hercegovina");

        robot.clickOn("#choiceGrad");
        robot.clickOn("London");
        // Default je da je najveći grad isti kao glavni

        // Klik na dugme Ok
        robot.clickOn("#btnOk");

        // Da li je Bosna i Hercegovina dodana u bazu?
        GeografijaDAO dao = GeografijaDAO.getInstance();
        assertEquals(4, dao.drzave().size());

        Drzava bih = null;
        for(Drzava drzava : dao.drzave())
            if (drzava.getNaziv().equals("Bosna i Hercegovina"))
                bih = drzava;
        assertNotNull(bih);
        assertEquals(2, bih.getGlavniGrad().getId());
        assertEquals("London", bih.getGlavniGrad().getNaziv());
        assertEquals(2, bih.getNajveciGrad().getId());
        assertEquals("London", bih.getNajveciGrad().getNaziv());

        // Ponovo prikazujemo glavnu formu
        Platform.runLater(() -> theStage.show());
    }



    @Test
    public void testDodajDrzavaRazlicit(FxRobot robot) {
        // Sličan test samo sa različitim glavnim i najvećim gradom
        ctrl.resetujBazu();

        // Otvaranje forme za dodavanje
        robot.clickOn("#btnDodajDrzavu");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#fieldNaziv").tryQuery().isPresent();

        // Ovo moramo jer robot klika po glavnoj formi umjesto po ovoj
        Platform.runLater(() -> theStage.hide());

        // Postoji li fieldNaziv
        robot.clickOn("#fieldNaziv");
        robot.write("Sao Tome & Principe");

        robot.clickOn("#choiceGrad");
        robot.clickOn("Manchester");

        robot.clickOn("#radioDrugi");

        robot.clickOn("#choiceGradNajveci");
        robot.clickOn("Beč");

        // Klik na dugme Ok
        robot.clickOn("#btnOk");

        // Da li je država dodana u bazu?
        GeografijaDAO dao = GeografijaDAO.getInstance();
        assertEquals(4, dao.drzave().size());

        Drzava sao = null;
        for(Drzava drzava : dao.drzave())
            if (drzava.getNaziv().equals("Sao Tome & Principe"))
                sao = drzava;
        assertNotNull(sao);
        assertEquals(4, sao.getGlavniGrad().getId());
        assertEquals("Manchester", sao.getGlavniGrad().getNaziv());
        assertEquals(3, sao.getNajveciGrad().getId());
        assertEquals("Beč", sao.getNajveciGrad().getNaziv());

        Platform.runLater(() -> theStage.show());
    }


}
*/