/*package ba.unsa.etf.rpr;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class IspitDrzavaControllerTest {
    Stage theStage;
    DrzavaController ctrl;

    @Start
    public void start(Stage stage) throws Exception {
        GeografijaDAO dao = GeografijaDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/drzava.fxml"));
        ctrl = new DrzavaController(null, dao.gradovi());
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Država");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }


    @Test
    public void testPoljaPostoje(FxRobot robot) {
        ChoiceBox cb = robot.lookup("#choiceGradNajveci").queryAs(ChoiceBox.class);
        assertNotNull(cb);
        RadioButton rb1 = robot.lookup("#radioIsti").queryAs(RadioButton.class);
        assertNotNull(rb1);
        RadioButton rb2 = robot.lookup("#radioDrugi").queryAs(RadioButton.class);
        assertNotNull(rb2);
    }

    @Test
    public void testDefaultIsti(FxRobot robot) {
        // Provjera da li je po defaultu izabrano da je najveći grad isti kao glavno
        RadioButton rb1 = robot.lookup("#radioIsti").queryAs(RadioButton.class);
        assertTrue(rb1.isSelected());
        RadioButton rb2 = robot.lookup("#radioDrugi").queryAs(RadioButton.class);
        assertFalse(rb2.isSelected());
        ChoiceBox cb = robot.lookup("#choiceGradNajveci").queryAs(ChoiceBox.class);
        assertTrue(cb.isDisabled());
    }

    @Test
    public void testRadio(FxRobot robot) {
        // Provjera da li se radio dugmad ponašaju kako bi trebalo
        RadioButton rb1 = robot.lookup("#radioIsti").queryAs(RadioButton.class);
        RadioButton rb2 = robot.lookup("#radioDrugi").queryAs(RadioButton.class);
        ChoiceBox cb = robot.lookup("#choiceGradNajveci").queryAs(ChoiceBox.class);

        robot.clickOn("#radioDrugi");
        assertFalse(rb1.isSelected());
        assertTrue(rb2.isSelected());
        assertFalse(cb.isDisabled());

        robot.clickOn("#radioIsti");
        assertTrue(rb1.isSelected());
        assertFalse(rb2.isSelected());
        assertTrue(cb.isDisabled());

        robot.clickOn("#radioDrugi");
        assertFalse(rb1.isSelected());
        assertTrue(rb2.isSelected());
        assertFalse(cb.isDisabled());
    }

    @Test
    public void testVracanjeDrzaveDefault(FxRobot robot) {
        // Upisujemo državu, očekujemo da je najveći jednak glavnom po defaultu
        robot.clickOn("#fieldNaziv");
        robot.write("Bosna i Hercegovina");
        robot.clickOn("#choiceGrad");
        robot.clickOn("Pariz");
        robot.clickOn("#radioIsti"); // Za svaki slučaj

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        Drzava bih = ctrl.getDrzava();
        assertEquals("Bosna i Hercegovina", bih.getNaziv());
        assertEquals("Pariz", bih.getGlavniGrad().getNaziv());
        assertEquals("Pariz", bih.getNajveciGrad().getNaziv());
    }

    @Test
    public void testVracanjeDrzaveRazlicit(FxRobot robot) {
        // Upisujemo državu, unosimo različit glavni i najveći grad
        robot.clickOn("#fieldNaziv");
        robot.write("Fejk Austrija");
        robot.clickOn("#choiceGrad");
        robot.clickOn("Beč");
        robot.clickOn("#radioDrugi");
        robot.clickOn("#choiceGradNajveci");
        robot.clickOn("Graz");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        Drzava austrija = ctrl.getDrzava();
        assertEquals("Fejk Austrija", austrija.getNaziv());
        assertEquals("Beč", austrija.getGlavniGrad().getNaziv());
        assertEquals("Graz", austrija.getNajveciGrad().getNaziv());
    }
}*/