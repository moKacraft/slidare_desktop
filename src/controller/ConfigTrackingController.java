/*
* Projet Slidare
* Sharing anywhere, anytime
*
*/
package controller;

import model.Config;
import model.Group;
import model.ObservableGroup;
import model.TrackingService;
import model.TrackingServiceStub;
import model.iGroup;
import model.iConfig;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import service.ConfigManager;
import service.ServiceFactory;

/**
 * FXML Controller class
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class ConfigTrackingController extends Controller implements Initializable
{
    @FXML
    private Label widthLabel;
    @FXML
    private TextField widthField;
    @FXML
    private Label heightLabel;
    @FXML
    private TextField heightField;
    @FXML
    private AnchorPane details;
    @FXML
    private CheckBox Maximized;
    @FXML
    private CheckBox autoConnect;
    @FXML // fx:id="langCombo"
    private ComboBox<String> langCombo;
    @FXML
    private Button saveConfig;
//	@FXML
//	private Button deleteGroup;
    
//	private TextField statusValue = new TextField();
    
//	private String displayedBugId;
//	private String displayedBugProject;
    
    /**
     * Permet au tableau de fonctionner
     */
    final ObservableList<ObservableGroup> tableContent = FXCollections.observableArrayList();
    
    //Externaliser
//	ObservableList<String> projectsView = FXCollections.observableArrayList();
    ConfigManager serviceModel = null;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //Configuration des boutons sur la droite
        configureButtons();
        //Connexion au service de traitement
        connectToService();
        //Configuration du panel de modification
        configureDetails();
        //Configuration du tableau
//		configureTable();


//Chargement des fields
        updateBugDetails();

//		if (list != null)
//		{
//			list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//			list.getSelectionModel().selectedItemProperty().addListener(projectItemSelected);
//			displayedProjectNames.addListener(projectNamesListener);
//		}

//		loadTable();
    }
    
    /**
     * Configuration des boutons droit
     */
    private void configureButtons()
    {
        if (saveConfig != null) {
            saveConfig.setDisable(true);
        }
//		if (saveGroup != null)
//		{
//			saveGroup.setDisable(true);
//		}
//		if (deleteGroup != null)
//		{
//			deleteGroup.setDisable(true);
//		}
    }
    
//	private void loadTable()
//	{
////		System.out.println("contact_sys.GroupTrackingController.loadTable()");
////			ObservableMap<String, Group> group = model.getGroups();
////			for (ObservableMap.Entry<String, Group> grouppair : group.entrySet())
////			{
////				final ObservableGroup issue = grouppair.getValue();
////				System.out.println(""+grouppair.getValue().getId());
//////				final ObservableContact issue = model.getContact("");
//////				final ObservableContact issue = model.getContact("");
//////				final ObservableContact issue = model.getContact(3);
//////				table.getItems().add(issue);
////			}
//	}
    
    /**
     * Configuration du tableau
     */
//	private void configureTable()
//	{
////		colId.setCellValueFactory(new PropertyValueFactory<ObservableGroup, String>("id"));
////		colStatus.setCellValueFactory(new PropertyValueFactory<ObservableGroup, String>("status"));
////		colName.setCellValueFactory(new PropertyValueFactory<ObservableGroup, String>("name"));
////
////		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
////
////		table.setItems(tableContent);
////		assert table.getItems() == tableContent;
//
////		final ObservableList<ObservableGroup> tableSelection = table.getSelectionModel().getSelectedItems();
//
////		tableSelection.addListener(tableSelectionChanged);
//	}
    
//	/**
//	 * Listener sur le tableau
//	 */
//	private final ListChangeListener<ObservableGroup> tableSelectionChanged
//			= new ListChangeListener<ObservableGroup>()
//	{
//
//		@Override
//		public void onChanged(Change<? extends ObservableGroup> c)
//		{
//			updateDeleteGroupButtonState();
//			updateBugDetails();
//			updateSaveGroupButtonState();
//		}
//	};
    
    /**
     * Inferior panel
     * Charge le contenu
     */
    private void updateBugDetails()
    {
//		final ObservableGroup selectedGroup = getSelectedGroup();
        final Config config = serviceModel.getConfig();
        if (details != null /*&& selectedGroup != null*/) {
//			displayedBugId = selectedGroup.getId();
////			displayedBugProject = selectedGroup.getGroup();
            if (widthField != null) {
                widthField.setText(String.valueOf(config.getWidth()));
            }
            if (heightField != null) {
                heightField.setText(String.valueOf(config.getHeight()));
            }
            
            langCombo.getSelectionModel().select(getLangNameFromCode(serviceModel.getConfig().getLang()));
            
//			autoConnect.se

//			if (lastnameField != null)
//			{
////				lastnameField.setText(nonNull(selectedGroup.getLastname()));
////			}
//			if (statusValue != null)
//			{
//				statusValue.setText(selectedGroup.getStatus().toString());
//			}
//			if (descriptionValue != null)
//			{
//				descriptionValue.selectAll();
//				descriptionValue.cut();
//				descriptionValue.setText(selectedGroup.getComment());
//			}
        } else {
//			displayedBugId = null;
//			displayedBugProject = null;
        }
//		if (details != null)
//		{
//			details.setVisible(selectedGroup != null);
//		}
    }
    
    /**
     * get the object selected in the table
     * @return retourne le groupe selectionné dans le tableau
     */
    public ObservableGroup getSelectedGroup()
    {
//		if (model != null /*&& table != null*/)
//		{
//			List<ObservableGroup> selectedGroups = table.getSelectionModel().getSelectedItems();
//			if (selectedGroups.size() == 1)
//			{
//				final ObservableGroup selectedIssue = selectedGroups.get(0);
//				return selectedIssue;
//			}
//		}
        return null;
    }
    
    //Externaliser
    private void connectToService()
    {
        if (serviceModel == null) {
            serviceModel = (ConfigManager) ServiceFactory.getConfigManager();
//			displayedProjectNames = serviceModel.getProjectNames();
        }
//		projectsView.clear();
//		List<String> sortedProjects = new ArrayList<String>(displayedProjectNames);
//		Collections.sort(sortedProjects);
//		projectsView.addAll(sortedProjects);
//		list.setItems(projectsView);
    }
    
    //Externaliser
    private final ChangeListener<String> projectItemSelected = new ChangeListener<String>()
    {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
        {
            projectUnselected(oldValue);
            projectSelected(newValue);
        }
    };
    
    //Externaliser
    private final ListChangeListener<String> projectNamesListener = new ListChangeListener<String>()
    {
        
        @Override
        public void onChanged(Change<? extends String> c)
        {
//			if (projectsView == null)
//			{
//				return;
//			}
//			while (c.next())
//			{
//				if (c.wasAdded() || c.wasReplaced())
//				{
//					for (String p : c.getAddedSubList())
//					{
//						projectsView.add(p);
//					}
//				}
//				if (c.wasRemoved() || c.wasReplaced())
//				{
//					for (String p : c.getRemoved())
//					{
//						projectsView.remove(p);
//					}
//				}
//			}
//			FXCollections.sort(projectsView);
        }
    };
    
    
    // Called when a project is unselected.
    //Externaliser
    private void projectUnselected(String oldProjectName)
    {
        if (oldProjectName != null) {
//			displayedIssues.removeListener(projectContactsListener);
//			displayedIssues = null;
//			table.getSelectionModel().clearSelection();
//			table.getItems().clear();
//			if (newIssue != null)
//			{
//				newIssue.setDisable(true);
//			}
//			if (deleteIssue != null)
//			{
//				deleteIssue.setDisable(true);
//			}
        }
    }
    private ObservableList<String> displayedProjectNames;
    
//	private ObservableList<String> displayedIssues;
    
    // Called when a project is selected.
    //Externaliser
    private void projectSelected(String newProjectName)
    {
//		System.out.println("contact_sys.GroupTrackingController.projectSelected()");
        if (newProjectName != null) {
//			table.getItems().clear();
//			displayedIssues = model.getContactIds(newProjectName);
//			for (String id : displayedIssues)
//			{
//				final ObservableContact issue = model.getContact(id);
//				table.getItems().add(issue);
//			}
//			displayedIssues.addListener(projectContactsListener);
//			if (newIssue != null)
//			{
//				newIssue.setDisable(false);
//			}
//			updateDeleteContactButtonState();
//			updateSaveContactButtonState();
        }
    }
    
    private String getLangCodeFromName(String langName)
    {
        if (langName != null) {
            switch(langName)
            {
                case "Français":
                    return ("fr");
                case "English":
                    return ("en");
            }
        }
        return ("");
    }
    
    private String getLangNameFromCode(String langCode)
    {
        if (langCode != null) {
            switch(langCode)
            {
                case "fr":
                    return ("Français");
                case "en":
                    return ("English");
            }
        }
        return ("");
    }
    
    private final class DetailsData implements iConfig
    {
        @Override
        public String getLang()
        {
            String langSelected = langCombo.getSelectionModel().getSelectedItem();
            
            return (getLangCodeFromName(langSelected));
        }
        
        @Override
        public boolean getMaximized()
        {
            if (Maximized.isSelected()) {
                return (true);
            } else {
                return (false);
            }
        }
        
        @Override
        public int getWidth()
        {
            if (widthField == null || isEmpty(widthField.getText())) {
                return (0);
            }
            return (Integer.parseInt(widthField.getText()));
        }
        
        @Override
        public int getHeight()
        {
            if (heightField == null || isEmpty(heightField.getText())) {
                return (0);
            }
            return (Integer.parseInt(heightField.getText()));
        }
        
        @Override
        public boolean getAutoConnect()
        {
            if (autoConnect.isSelected()) {
                return (true);
            }
            return (false);
        }
        
        @Override
        public String getUsername()
        {
            return ("");
        }
        
        @Override
        public String getPassword()
        {
            return ("");
        }
        
        @Override
        public String getToken()
        {
            return ("");
        }
        
        @Override
        public String getId()
        {
            return ("");
        }
        
        @Override
        public boolean isEqual(Object obj)
        {
            return false;
        }
    }
    
    private static enum SaveState
    {
        INVALID, UNSAVED, UNCHANGED
    }
    
    private void updateSaveGroupButtonState()
    {
        boolean disable = true;
        if (saveConfig != null) {
            disable = false;
        }
        if (disable == false) {
            disable = computeSaveState(new ConfigTrackingController.DetailsData(), serviceModel.getConfig()) != SaveState.UNSAVED;
        }
        if (saveConfig != null) {
            saveConfig.setDisable(disable);
        }
    }
    
    /**
     * Check if modified
     * @param edited
     * @param issue
     * @return
     */
    private SaveState computeSaveState(iConfig edited, iConfig issue)
    {
        try {
            if (issue.isEqual(edited) == false) {
                return (SaveState.UNSAVED);
            }
        } catch (Exception x) {
            return SaveState.INVALID;
        }
        return SaveState.UNCHANGED;
    }
    
    private void configureDetails()
    {
        if (details != null) {
            //init of the combobox
            langCombo.getItems().setAll("Français", "English");
            
            details.addEventFilter(EventType.ROOT, new EventHandler<Event>()
            {
                
                @Override
                public void handle(Event event)
                {
                    if (event.getEventType() == MouseEvent.MOUSE_RELEASED
                            || event.getEventType() == KeyEvent.KEY_RELEASED) {
                        updateSaveGroupButtonState();
                    }
                }
            });
        }
    }
    
    /**
     * Called when the SaveConfig button is fired.
     *
     * @param event the action event.
     */
    @FXML
    private void saveConfigFired(ActionEvent event)
    {
        System.out.println("contact_sys.GroupTrackingController.saveGroupFired()");
        final iConfig ref = serviceModel.getConfig();
        final iConfig edited = new DetailsData();
        SaveState saveState = computeSaveState(edited, ref);
        if (saveState == SaveState.UNSAVED) {
            serviceModel.saveConfig(edited);
        }
        updateSaveGroupButtonState();
    }
}