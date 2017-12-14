/*
* Projet Slidare
* Sharing anywhere, anytime
*
 */
package controller;

import model.Contact;
import model.iContact;
import model.iContact.ContactStatus;
import model.ObservableContact;
import model.TrackingService;
import model.TrackingServiceStub;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.ContactConverter;

public class ContactTrackingController extends Controller implements Initializable
{

	@FXML
	Button newIssue;
	@FXML
	Button deleteIssue;
	@FXML
	Button saveIssue;
	@FXML
	TableView<ObservableContact> table;
	@FXML
	TableColumn<ObservableContact, String> colName;
	@FXML
	TableColumn<ObservableContact, ContactStatus> colStatus;
	@FXML
	TableColumn<ObservableContact, String> colLastname;
	@FXML
	TableColumn<ObservableContact, String> colSynopsis;
	/**
	 * List on the left
	 */
	@FXML
	ListView<String> list;
	@FXML
	TextField synopsis;

	private String displayedBugId; // the id of the bug displayed in the details section.
	private String displayedBugProject; // the name of the project of the bug displayed in the detailed section.
	/**
	 * Panel editer un contact
	 */
	@FXML
	AnchorPane details;
	AnchorPane groupsView;
	@FXML
	AnchorPane contactsView;
	@FXML
	TextField lastnameField;
	@FXML
	TextArea descriptionValue;

	/**
	 * Panel ajout de contact
	 */
	@FXML
	Button searchContact;
	@FXML
	Button addContact;
	@FXML
	TextField searchContactField;
	@FXML
	ComboBox<Contact> contactsList;

	/**
	 * Liste des projets qui est insérée dans list
	 */
	ObservableList<String> projectsView = FXCollections.observableArrayList();
	TrackingService model = null;
	
	/**
	 * ATTENTION utilisé uniquement pour une methode, le trackingStub s'occupe du reste
	 */
	ContactsBusiness contactBusiness = null;
	private TextField statusValue = new TextField();

	/**
	 * Permet au tableau de fonctionner
	 */
	final ObservableList<ObservableContact> tableContent = FXCollections.observableArrayList();
	@FXML
	private Label lastnameLabel;
	@FXML
	private Label displayedIssueLabel;

	private ObservableList<String> displayedProjectNames;

	private ObservableList<String> displayedIssues;

	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rsrcs
	 */
	@Override
	public void initialize(URL url, ResourceBundle rsrcs)
	{
		assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file.";
		assert colStatus != null : "fx:id=\"colStatus\" was not injected: check your FXML file.";
		assert colLastname != null : "fx:id=\"colLastname\" was not injected: check your FXML file.";
		assert colSynopsis != null : "fx:id=\"colSynopsis\" was not injected: check your FXML file.";
		assert deleteIssue != null : "fx:id=\"deleteIssue\" was not injected: check your FXML file.";

		assert lastnameField != null : "fx:id=\"lastnameField\" was not injected: check your FXML file.";
		assert descriptionValue != null : "fx:id=\"descriptionValue\" was not injected: check your FXML file.";

		assert contactsView != null : "fx:id=\"contactsView\" was not injected: check your FXML file.";
		assert details != null : "fx:id=\"details\" was not injected: check your FXML file.";
		assert newIssue != null : "fx:id=\"newIssue\" was not injected: check your FXML file.";
		assert saveIssue != null : "fx:id=\"saveIssue\" was not injected: check your FXML file.";
		assert synopsis != null : "fx:id=\"synopsis\" was not injected: check your FXML file.";

		//Panel ajout de contact
		assert contactsList != null : "fx:id=\"contactsList\" was not injected: check your FXML file.";

		assert table != null : "fx:id=\"table\" was not injected: check your FXML file.";
		assert list != null : "fx:id=\"list\" was not injected: check your FXML file.";

		Contact ct1 = new Contact();
		Contact ct2 = new Contact();
		Contact ct3 = new Contact();

		ct1.setFirstname("titi1");
		ct1.setLastname("titi2");

		ct2.setFirstname("titi3");
		ct2.setLastname("titi4");

		ct3.setFirstname("titi5");
		ct3.setLastname("titi6");

		contactsList.setConverter(new ContactConverter());

		System.out.println(this.getClass().getSimpleName() + ".initialize");

		//Configuration des boutons sur la droite
		configureButtons();
		//Configuration du panel de modification
		configureDetails();
		//Configuration du tableau
		configureTable();
		//Connexion au service de traitement
		connectToService();

		if (list != null)
		{
			list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			list.getSelectionModel().selectedItemProperty().addListener(projectItemSelected);
			displayedProjectNames.addListener(projectNamesListener);
		}
	}

	/**
	 * Called when the NewContact button is fired.
	 *
	 * @param event the action event.
	 */
	@FXML
	public void searchContactFired(ActionEvent event)
	{
		String searchfield = nonNull(searchContactField.getText());
//		System.out.println("controller.ContactTrackingController.searchContactFired()" + searchContactField.getText());
		List<Contact> tmp = this.contactBusiness.findOneOnApiByEmail(searchfield);
		System.out.println("controller.ContactTrackingController.searchContactFired()" + tmp);

		contactsList.getItems().removeAll();
//		System.out.println("===>" + contactsList.getItems());
		contactsList.getItems().addAll(tmp);
		addContact.setDisable(false);
	}

	/**
	 * Called when the AddContact button is fired.
	 *
	 * @param event the action event.
	 */
	@FXML
	public void addContactFired(ActionEvent event)
	{
		System.out.println("addContactFired()");
		Contact tmp = contactsList.getSelectionModel().getSelectedItem();
		final String selectedProject = getSelectedProject();
		if (model != null && selectedProject != null)
		{
			this.contactBusiness.addContactFor(selectedProject, tmp.getComment());
			ObservableContact issue = model.addContactFor(selectedProject, tmp);
			if (table != null)
			{
				System.out.println("addContactFired() 2" + issue);
				// Select the newly created contact.
				table.getSelectionModel().clearSelection();
				table.getSelectionModel().select(issue);
				saveIssue.setDisable(false);
			}
		}
	}

	/**
	 * Called when the NewContact button is fired.
	 *
	 * @param event the action event.
	 */
	@FXML
	public void newContactFired(ActionEvent event)
	{
		final String selectedProject = getSelectedProject();
		if (model != null && selectedProject != null)
		{
			ObservableContact issue = model.createContactFor(selectedProject);
			if (table != null)
			{
				// Select the newly created contact.
				table.getSelectionModel().clearSelection();
				table.getSelectionModel().select(issue);
			}
		}
	}

	/**
	 * Called when the NewContact button is fired.
	 *
	 * @param event the action event.
	 */
	@FXML
	public void showAddContactFired(ActionEvent event)
	{
		System.out.println("controller.ContactTrackingController.showAddContactFired()");
		hideAll();
		showPane("add");
//		final String selectedProject = getSelectedProject();
//		if (model != null && selectedProject != null)
//		{
//			ObservableContact issue = model.createContactFor(selectedProject);
//			if (table != null)
//			{
//				// Select the newly created contact.
//				table.getSelectionModel().clearSelection();
//				table.getSelectionModel().select(issue);
//			}
//		}
	}

	/**
	 * Called when the DeleteContact button is fired.
	 *
	 * @param event the action event.
	 */
	@FXML
	public void deleteContactFired(ActionEvent event)
	{
		final String selectedProject = getSelectedProject();
		if (model != null && selectedProject != null && table != null)
		{
			final List<?> selectedContact = new ArrayList<Object>(table.getSelectionModel().getSelectedItems());
			for (Object o : selectedContact)
			{
				if (o instanceof ObservableContact)
				{
					model.deleteContact(((ObservableContact) o).getId());
				}
			}
			table.getSelectionModel().clearSelection();
		}
	}

	/**
	 * Called when the SaveContact button is fired.
	 *
	 * @param event the action event.
	 */
	@FXML
	public void saveContactFired(ActionEvent event)
	{
		final ObservableContact ref = getSelectedContact();
		final iContact edited = new DetailsData();
		SaveState saveState = computeSaveState(edited, ref);
		if (saveState == SaveState.UNSAVED)
		{
			model.saveContact(ref.getId(), edited);
		}

		int selectedRowIndex = table.getSelectionModel().getSelectedIndex();
		table.getItems().clear();
		displayedIssues = model.getContactIds(getSelectedProject());
		for (String id : displayedIssues)
		{
			final ObservableContact issue = model.getContact(id);
			table.getItems().add(issue);
		}
		table.getSelectionModel().select(selectedRowIndex);

		updateSaveContactButtonState();
	}

	/**
	 * Configuration des boutons droit
	 */
	private void configureButtons()
	{
		if (newIssue != null)
		{
			newIssue.setDisable(true);
		}
		if (saveIssue != null)
		{
			saveIssue.setDisable(true);
		}
		if (deleteIssue != null)
		{
			deleteIssue.setDisable(true);
		}
		if (addContact != null)
		{
			addContact.setDisable(true);
		}
	}

	private final ListChangeListener<String> projectNamesListener = new ListChangeListener<String>()
	{

		@Override
		public void onChanged(Change<? extends String> c)
		{
			System.out.println("projectNamesListener change");
			if (projectsView == null)
			{
				return;
			}
			while (c.next())
			{
				if (c.wasAdded() || c.wasReplaced())
				{
					for (String p : c.getAddedSubList())
					{
						projectsView.add(p);
					}
				}
				if (c.wasRemoved() || c.wasReplaced())
				{
					for (String p : c.getRemoved())
					{
						projectsView.remove(p);
					}
				}
			}
			FXCollections.sort(projectsView);
		}
	};

	private final ListChangeListener<String> projectContactsListener = new ListChangeListener<String>()
	{

		@Override
		public void onChanged(Change<? extends String> c)
		{
			System.out.println("projectContactsListener change");
			if (table == null)
			{
				return;
			}
			while (c.next())
			{
				if (c.wasAdded() || c.wasReplaced())
				{
					for (String p : c.getAddedSubList())
					{
						table.getItems().add(model.getContact(p));
					}
				}
				if (c.wasRemoved() || c.wasReplaced())
				{
					for (String p : c.getRemoved())
					{
						ObservableContact removed = null;

						for (ObservableContact t : table.getItems())
						{
							if (t.getId().equals(p))
							{
								removed = t;
								break;
							}
						}
						if (removed != null)
						{
							table.getItems().remove(removed);
						}
					}
				}
			}
		}
	};

	/**
	 * Connexion au service
	 */
	private void connectToService()
	{
		if (model == null)
		{
			model = new TrackingServiceStub();
			//Récupération de la liste des groupes
			displayedProjectNames = model.getProjectNames();
		}
		if (contactBusiness == null)
		{
			if (model.getContactsBusiness() == null)
				contactBusiness = new ContactsBusiness();
			else
				contactBusiness = model.getContactsBusiness();
		}
		projectsView.clear();
		List<String> sortedProjects = new ArrayList<String>(displayedProjectNames);
		Collections.sort(sortedProjects);
		projectsView.addAll(sortedProjects);
		list.setItems(projectsView);
	}

	/**
	 * Listener sur le tableau
	 */
	private final ListChangeListener<ObservableContact> tableSelectionChanged
			= new ListChangeListener<ObservableContact>()
	{

		@Override
		public void onChanged(Change<? extends ObservableContact> c)
		{
			hideAll();
			showPane("edit");
			updateDeleteContactButtonState();
			updateBugDetails();
			updateSaveContactButtonState();
		}
	};

	private final void hideAll()
	{
		if (details.getChildren().size() > 0)
		{
			for (Node anchorpane : details.getChildren())
			{
				AnchorPane panel = (AnchorPane) anchorpane;
				panel.setVisible(false);
			}
		}
	}

	private final void showPane(String anchorpaneid)
	{
		System.out.println("controller.ContactTrackingController.showPane()" + anchorpaneid);
		details.lookup("#" + anchorpaneid).setVisible(true);
	}

	/**
	 * Inferior panel Charge le contenu
	 */
	private void updateBugDetails()
	{
		final ObservableContact selectedContact = getSelectedContact();
		if (details != null && selectedContact != null)
		{
			displayedBugId = selectedContact.getId();
			displayedBugProject = selectedContact.getGroup();
			if (synopsis != null)
			{
				synopsis.setText(nonNull(selectedContact.getFirstname()));
			}

			if (lastnameField != null)
			{
				lastnameField.setText(nonNull(selectedContact.getLastname()));
			}
			if (statusValue != null)
			{
				statusValue.setText(selectedContact.getStatus().toString());
			}
			if (descriptionValue != null)
			{
				descriptionValue.selectAll();
				descriptionValue.cut();
				descriptionValue.setText(selectedContact.getComment());
			}
		}
		else
		{
			displayedBugId = null;
			displayedBugProject = null;
		}
		if (details != null)
		{
			if (selectedContact == null)
			{
				hideAll();
//			details.setVisible(selectedContact != null);
			}
		}
	}

	private static enum SaveState
	{
		INVALID, UNSAVED, UNCHANGED
	}

	private final class DetailsData implements iContact
	{

		@Override
		public String getId()
		{
			if (displayedBugId == null)
			{
				return null;
			}
			return displayedBugId;
		}

		@Override
		public ContactStatus getStatus()
		{
			if (statusValue == null || isEmpty(statusValue.getText()))
			{
				return null;
			}
			return ContactStatus.valueOf(statusValue.getText().trim());
		}

		@Override
		public String getGroup()
		{
			if (displayedBugProject == null)
			{
				return null;
			}
			return displayedBugProject;
		}

		@Override
		public String getFirstname()
		{
			if (synopsis == null || isEmpty(synopsis.getText()))
			{
				return "";
			}
			return synopsis.getText();
		}

		@Override
		public String getLastname()
		{
			if (lastnameField == null || isEmpty(lastnameField.getText()))
			{
				return "";
			}
			return lastnameField.getText();
		}

		@Override
		public String getComment()
		{
			if (descriptionValue == null || isEmpty(descriptionValue.getText()))
			{
				return "";
			}
			return descriptionValue.getText();
		}
	}

	/**
	 * Check if modified
	 *
	 * @param edited
	 * @param issue
	 * @return
	 */
	private SaveState computeSaveState(iContact edited, iContact issue)
	{
//		System.out.println(""+edited.getId());
//		System.out.println(""+issue.getId());
//		System.out.println(""+edited.getGroup());
//		System.out.println(""+issue.getGroup());
//		System.out.println(""+equal(edited.getComment(), issue.getComment()));
		try
		{
			// These fields are not editable - so if they differ they are invalid
			// and we cannot save.
			if (!equal(edited.getId(), issue.getId()))
			{
				return SaveState.INVALID;
			}
			if (!equal(edited.getGroup(), issue.getGroup()))
			{
				return SaveState.INVALID;
			}
			// If these fields differ, the issue needs saving.
			if (!equal(edited.getStatus(), issue.getStatus()))
			{
				return SaveState.UNSAVED;
			}
			if (!equal(edited.getLastname(), issue.getLastname()))
			{
				return SaveState.UNSAVED;
			}
			if (!equal(edited.getFirstname(), issue.getFirstname()))
			{
				return SaveState.UNSAVED;
			}
			if (!equal(edited.getComment(), issue.getComment()))
			{
//				System.out.println("return UNSAVED");
				return SaveState.UNSAVED;
			}
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

	/**
	 * Active ou desactive le bouton de suppression
	 */
	private void updateDeleteContactButtonState()
	{
		boolean disable = true;
		if (deleteIssue != null && table != null)
		{
			final boolean nothingSelected = table.getSelectionModel().getSelectedItems().isEmpty();
			disable = nothingSelected;
		}
		if (deleteIssue != null)
		{
			deleteIssue.setDisable(disable);
		}
	}

	/**
	 * Affiche le bouton de sauvegarde si necessaire
	 */
	private void updateSaveContactButtonState()
	{
		System.out.println("updateSaveContactButtonState()");
		boolean disable = true;
		if (saveIssue != null && table != null)
		{
			final boolean nothingSelected = table.getSelectionModel().getSelectedItems().isEmpty();
			disable = nothingSelected;
		}
		if (disable == false)
		{
			disable = computeSaveState(new DetailsData(), getSelectedContact()) != SaveState.UNSAVED;
			System.out.println("disable = " + disable);
		}
		if (saveIssue != null)
		{
			saveIssue.setDisable(disable);
		}
	}

	/**
	 * Configuration du tableau
	 */
	private void configureTable()
	{
		colName.setCellValueFactory(new PropertyValueFactory<ObservableContact, String>("id"));
		colLastname.setCellValueFactory(new PropertyValueFactory<ObservableContact, String>("lastname"));
		colSynopsis.setCellValueFactory(new PropertyValueFactory<ObservableContact, String>("firstname"));
		colStatus.setCellValueFactory(new PropertyValueFactory<ObservableContact, ContactStatus>("status"));

		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.setItems(tableContent);
		assert table.getItems() == tableContent;

		final ObservableList<ObservableContact> tableSelection = table.getSelectionModel().getSelectedItems();

		tableSelection.addListener(tableSelectionChanged);
	}

	public String getSelectedProject()
	{
		if (model != null && list != null)
		{
			final ObservableList<String> selectedProjectItem = list.getSelectionModel().getSelectedItems();
			final String selectedProject = selectedProjectItem.get(0);
			return selectedProject;
		}
		return null;
	}

	/**
	 * get the object selected in the table
	 *
	 * @return Retourne le contact selectionné dans le tableau
	 */
	public ObservableContact getSelectedContact()
	{
		if (model != null && table != null)
		{
			List<ObservableContact> selectedContacts = table.getSelectionModel().getSelectedItems();
			if (selectedContacts.size() == 1)
			{
				final ObservableContact selectedIssue = selectedContacts.get(0);
				return selectedIssue;
			}
		}
		return null;
	}

	/**
	 * Listener sur la liste
	 */
	private final ChangeListener<String> projectItemSelected = new ChangeListener<String>()
	{
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
		{
			projectUnselected(oldValue);
			projectSelected(newValue);
		}
	};

	// Called when a project is unselected.
	private void projectUnselected(String oldProjectName)
	{
		if (oldProjectName != null)
		{
			displayedIssues.removeListener(projectContactsListener);
			displayedIssues = null;
			table.getSelectionModel().clearSelection();
			table.getItems().clear();

			if (newIssue != null)
			{
				newIssue.setDisable(true);
			}
			if (deleteIssue != null)
			{
				deleteIssue.setDisable(true);
			}
		}
	}

	// Called when a project is selected.
	private void projectSelected(String newProjectName)
	{
		if (newProjectName != null)
		{
			table.getItems().clear();
			displayedIssues = model.getContactIds(newProjectName);
			System.out.println("displayedIssues" + displayedIssues);
			for (String id : displayedIssues)
			{
				final ObservableContact issue = model.getContact(id);
				table.getItems().add(issue);
			}

			displayedIssues.addListener(projectContactsListener);
			if (newIssue != null)
			{
				newIssue.setDisable(false);
			}
			updateDeleteContactButtonState();
			updateSaveContactButtonState();
		}
	}

	/**
	 * Display non sur le détail + event handler
	 */
	private void configureDetails()
	{
		if (details != null)
		{
			hideAll();
//			details.setVisible(false);
		}

		if (details != null)
		{
			details.addEventFilter(EventType.ROOT, new EventHandler<Event>()
			{
				@Override
				public void handle(Event event)
				{
					if (event.getEventType() == MouseEvent.MOUSE_RELEASED
							|| event.getEventType() == KeyEvent.KEY_RELEASED)
					{
						updateSaveContactButtonState();
					}
				}
			});
		}
	}
}
