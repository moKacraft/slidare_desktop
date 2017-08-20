/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import model.Group;
import model.ObservableGroup;
import model.TrackingService;
import model.TrackingServiceStub;
import model.iGroup;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class GroupTrackingController extends Controller implements Initializable
{

//	@FXML
//	private ListView<String> list;
    @FXML
    private AnchorPane contactsView;
    @FXML
    private TableView<ObservableGroup> table;
    @FXML
    private TableColumn<ObservableGroup, String> colId;
    @FXML
    private TableColumn<ObservableGroup, String> colName;
    @FXML
    private TableColumn<ObservableGroup, String> colStatus;
    @FXML
    private TableColumn<?, ?> colLastname;
    @FXML
    private AnchorPane details;
    @FXML
    private Label displayedIssueLabel;
    @FXML
    private TextField nameField;
    @FXML
    private Button newGroup;
    @FXML
    private Button saveGroup;
    @FXML
    private Button deleteGroup;
    
    private TextField statusValue = new TextField();
    
    private String displayedBugId;
    private String displayedBugProject;
    
    /**
     * Permet au tableau de fonctionner
     */
    final ObservableList<ObservableGroup> tableContent = FXCollections.observableArrayList();
    
    //Externaliser
    ObservableList<String> projectsView = FXCollections.observableArrayList();
    TrackingService model = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //Configuration des boutons sur la droite
        configureButtons();
        //Configuration du panel de modification
        configureDetails();
        //Configuration du tableau
        configureTable();
        //Connexion au service de traitement
        connectToService();
//		if (list != null)
//		{
//			list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//			list.getSelectionModel().selectedItemProperty().addListener(projectItemSelected);
//			displayedProjectNames.addListener(projectNamesListener);
//		}

        loadTable();
    }
    
    /**
     * Configuration des boutons droit
     */
    private void configureButtons()
    {
//		if (newGroup != null)
//		{
//			newGroup.setDisable(true);
//		}
        if (saveGroup != null) {
            saveGroup.setDisable(true);
        }
        if (deleteGroup != null) {
            deleteGroup.setDisable(true);
        }
    }
    
    private void loadTable()
    {
//		System.out.println("contact_sys.GroupTrackingController.loadTable()");
        ObservableMap<String, Group> group = model.getGroups();
        for (ObservableMap.Entry<String, Group> grouppair : group.entrySet()) {
            final ObservableGroup issue = grouppair.getValue();
            System.out.println(""+grouppair.getValue().getId());
//				final ObservableContact issue = model.getContact("");
//				final ObservableContact issue = model.getContact("");
//				final ObservableContact issue = model.getContact(3);
            table.getItems().add(issue);
        }
    }
    
    /**
     * Configuration du tableau
     */
    private void configureTable()
    {
        colId.setCellValueFactory(new PropertyValueFactory<ObservableGroup, String>("id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<ObservableGroup, String>("status"));
        colName.setCellValueFactory(new PropertyValueFactory<ObservableGroup, String>("name"));
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        table.setItems(tableContent);
        assert table.getItems() == tableContent;
        
        final ObservableList<ObservableGroup> tableSelection = table.getSelectionModel().getSelectedItems();
        
        tableSelection.addListener(tableSelectionChanged);
    }
    
    /**
     * Listener sur le tableau
     */
    private final ListChangeListener<ObservableGroup> tableSelectionChanged
            = new ListChangeListener<ObservableGroup>()
            {
                @Override
                public void onChanged(Change<? extends ObservableGroup> c)
                {
                    updateDeleteGroupButtonState();
                    updateBugDetails();
                    updateSaveGroupButtonState();
                }
            };
    
    /**
     * Inferior panel
     * Charge le contenu
     */
    private void updateBugDetails()
    {
        final ObservableGroup selectedGroup = getSelectedGroup();
        if (details != null && selectedGroup != null) {
            displayedBugId = selectedGroup.getId();
//			displayedBugProject = selectedGroup.getGroup();
            if (nameField != null) {
                nameField.setText(nonNull(selectedGroup.getName()));
            }
//			if (lastnameField != null)
//			{
//				lastnameField.setText(nonNull(selectedGroup.getLastname()));
//			}
            if (statusValue != null) {
                statusValue.setText(selectedGroup.getStatus().toString());
            }
//			if (descriptionValue != null)
//			{
//				descriptionValue.selectAll();
//				descriptionValue.cut();
//				descriptionValue.setText(selectedGroup.getComment());
//			}
        } else {
            displayedBugId = null;
//			displayedBugProject = null;
        }
        if (details != null) {
            details.setVisible(selectedGroup != null);
        }
    }
    
    /**
     * get the object selected in the table
     * @return retourne le groupe selectionné dans le tableau
     */
    public ObservableGroup getSelectedGroup()
    {
        if (model != null && table != null) {
            List<ObservableGroup> selectedGroups = table.getSelectionModel().getSelectedItems();
            if (selectedGroups.size() == 1) {
                final ObservableGroup selectedIssue = selectedGroups.get(0);
                return selectedIssue;
            }
        }
        return null;
    }
    
    //Externaliser
    private void connectToService()
    {
        if (model == null) {
            model = new TrackingServiceStub();
            displayedProjectNames = model.getProjectNames();
        }
        projectsView.clear();
        List<String> sortedProjects = new ArrayList<String>(displayedProjectNames);
        Collections.sort(sortedProjects);
        projectsView.addAll(sortedProjects);
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
            table.getItems().clear();
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
    
    private void updateDeleteGroupButtonState()
    {
        boolean disable = true;
        if (deleteGroup != null && table != null) {
            final boolean nothingSelected = table.getSelectionModel().getSelectedItems().isEmpty();
            disable = nothingSelected;
        }
        if (deleteGroup != null) {
            deleteGroup.setDisable(disable);
        }
    }
    
//	private void configureButtons()
//	{
//		if (newIssue != null)
//		{
//			newIssue.setDisable(true);
//		}
//		if (saveIssue != null)
//		{
//			saveIssue.setDisable(true);
//		}
//		if (deleteIssue != null)
//		{
//			deleteIssue.setDisable(true);
//		}
//	}
    
    
    private final class DetailsData implements iGroup
    {
        
        @Override
        public String getId()
        {
            if (displayedBugId == null) {
                return null;
            }
            return displayedBugId;
        }
        
        @Override
        public iGroup.GroupStatus getStatus()
        {
            if (statusValue == null || isEmpty(statusValue.getText())) {
                return null;
            }
            return iGroup.GroupStatus.valueOf(statusValue.getText().trim());
        }
        
        @Override
        public String getName()
        {
            if (nameField == null || isEmpty(nameField.getText())) {
                return "";
            }
            return nameField.getText();
        }
        
    }
    
    private static enum SaveState
    {
        INVALID, UNSAVED, UNCHANGED
    }
    
    private void updateSaveGroupButtonState()
    {
        boolean disable = true;
        if (saveGroup != null && table != null) {
            final boolean nothingSelected = table.getSelectionModel().getSelectedItems().isEmpty();
            disable = nothingSelected;
        }
        if (disable == false) {
            disable = computeSaveState(new GroupTrackingController.DetailsData(), getSelectedGroup()) != SaveState.UNSAVED;
        }
        if (saveGroup != null) {
            saveGroup.setDisable(disable);
        }
    }
    
    /**
     * Check if modified
     * @param edited
     * @param issue
     * @return
     */
    private SaveState computeSaveState(iGroup edited, iGroup issue)
    {
//		System.out.println(""+edited.getId());
//		System.out.println(""+issue.getId());
//		System.out.println(""+edited.getGroup());
//		System.out.println(""+issue.getGroup());
//		System.out.println(""+equal(edited.getComment(), issue.getComment()));
        try {
            // These fields are not editable - so if they differ they are invalid
            // and we cannot save.
            if (!equal(edited.getId(), issue.getId())) {
                return SaveState.INVALID;
            }
//			if (!equal(edited.getGroup(), issue.getGroup()))
//			{
//				return SaveState.INVALID;
//			}

// If these fields differ, the issue needs saving.
            if (!equal(edited.getStatus(), issue.getStatus())) {
                return SaveState.UNSAVED;
            }
            if (!equal(edited.getName(), issue.getName())) {
                return SaveState.UNSAVED;
            }
//			if (!equal(edited.getFirstname(), issue.getFirstname()))
//			{
//				return SaveState.UNSAVED;
//			}
//			if (!equal(edited.getComment(), issue.getComment()))
//			{
////				System.out.println("return UNSAVED");
//				return SaveState.UNSAVED;
//			}
//			else
//			{
//				System.out.println("return autre");
//			}
        }
        catch (Exception x)
        {
            // If there's an exception, some fields are invalid.
            return SaveState.INVALID;
        }
        // No field is invalid, no field needs saving.
        return SaveState.UNCHANGED;
    }
    
    private void configureDetails()
    {
        if (details != null) {
            details.setVisible(false);
        }
        
        if (details != null) {
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
    
    @FXML
    private void newGroupFired(ActionEvent event)
    {
//		final String selectedProject = getSelectedProject();
        if (model != null) {
            ObservableGroup group = model.createGroup("Nouveau");
            
            System.out.println("groupes"+ model.getProjectNames());
            
            if (table != null) {
                // Select the newly created contact.
//				table.getSelectionModel().clearSelection();
                //Check pk loadtable est necessaire
                table.getItems().clear();
                loadTable();
                
                table.getSelectionModel().select(group);
                System.out.println("contact_sys.GroupTrackingController.newGroupFired() FIN"+group.getId()+" & "+group.getName());
            }
        }
    }
    
    /**
     * Called when the SaveGroup button is fired.
     *
     * @param event the action event.
     */
    @FXML
    private void saveGroupFired(ActionEvent event)
    {
//		System.out.println("contact_sys.GroupTrackingController.saveGroupFired()");
        final ObservableGroup ref = getSelectedGroup();
        final iGroup edited = new DetailsData();
        SaveState saveState = computeSaveState(edited, ref);
        if (saveState == SaveState.UNSAVED) {
            model.saveGroup(ref.getId(), edited);
        }
        
//		int selectedRowIndex = table.getSelectionModel().getSelectedIndex();
        table.getItems().clear();
        loadTable();
//		displayedIssues = model.getGroupIds(getSelectedProject());
//		for (String id : displayedIssues)
//		{
//			final ObservableGroup issue = model.getGroup(id);
//			table.getItems().add(issue);
//		}
//		table.getSelectionModel().select(selectedRowIndex);

        updateSaveGroupButtonState();
    }
    
    @FXML
    private void deleteGroupFired(ActionEvent event)
    {
//		final String selectedProject = getSelectedProject();
        if (model != null && table != null) {
            final List<?> selectedGroup = new ArrayList<Object>(table.getSelectionModel().getSelectedItems());
            for (Object o : selectedGroup) {
                if (o instanceof ObservableGroup) {
                    model.deleteGroup(((ObservableGroup) o).getId());
                }
            }
//			table.getSelectionModel().clearSelection();
            table.getItems().clear();
            loadTable();
        }
    }
    
}
