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
public class IspitDrzavaControllerSet {
    Stage theStage;
    DrzavaController ctrl;

    @Start
    public void start(Stage stage) throws Exception {
        GeografijaDAO dao = GeografijaDAO.getInstance();
        Grad bech = dao.nadjiGrad("Beč");
        Grad graz = dao.nadjiGrad("Graz");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/drzava.fxml"));
        ctrl = new DrzavaController(new Drzava(12345, "Fejk Austrija", bech, graz), dao.gradovi());
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
    public void testIspravneVrijednosti(FxRobot robot) {
        ChoiceBox<Grad> cb = robot.lookup("#choiceGrad").queryAs(ChoiceBox.class);
        assertEquals("Beč", cb.getSelectionModel().getSelectedItem().getNaziv());
        ChoiceBox<Grad> cb2 = robot.lookup("#choiceGradNajveci").queryAs(ChoiceBox.class);
        assertEquals("Graz", cb2.getSelectionModel().getSelectedItem().getNaziv());
        RadioButton rb1 = robot.lookup("#radioIsti").queryAs(RadioButton.class);
        assertFalse(rb1.isSelected());
        RadioButton rb2 = robot.lookup("#radioDrugi").queryAs(RadioButton.class);
        assertTrue(rb2.isSelected());
    }


    @Test
    public void testPromjenaTipa(FxRobot robot) {
        robot.clickOn("#choiceGrad");
        robot.clickOn("Pariz");
        robot.clickOn("#radioIsti");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        Drzava austrija = ctrl.getDrzava();
        assertEquals(12345, austrija.getId());
        assertEquals("Fejk Austrija", austrija.getNaziv());
        assertEquals("Pariz", austrija.getGlavniGrad().getNaziv());
        assertEquals("Pariz", austrija.getNajveciGrad().getNaziv());
    }

}
*/