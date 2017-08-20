/*
* Projet Slidare
* Sharing anywhere, anytime
*
*/
package utils;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Dialogs system of the Slidare
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class DialogSys
{
    public static DialogSys ds = new DialogSys();
    
    public void showError(String title, String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        
        alert.showAndWait();
        System.exit(1);
    }
    
    public boolean showPopup(Pane pane, String ressource, String title)
    {
        return (this.configPopup((Stage)pane.getScene().getWindow(), ressource, title));
    }
    
    public boolean showPopup(ButtonBase button, String ressource, String title)
    {
        return (this.configPopup((Stage)button.getScene().getWindow(), ressource, title));
    }
    
    private boolean configPopup(Stage tmpStage, String ressource, String title)
    {
        Stage stage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(ressource));
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tmpStage);
            stage.showAndWait();
            return (true);
        } catch (IOException ex) {
            return (false);
        }
    }
}
