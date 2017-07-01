/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import model.ObservableAccount;
import model.TrackingService;
import model.TrackingServiceStub;
import model.iAccount;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Account;

/**
 * FXML Controller class pour le compte
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class AccountTrackingController extends Controller implements Initializable
{

	@FXML
	private ListView<String> list;
	@FXML
	private AnchorPane contactsView;
	@FXML
	private AnchorPane details;
	@FXML
	private Label universityLabel;
	@FXML
	private TextField universityField;
	@FXML
	private Label jobLabel;
	@FXML
	private TextField jobField;
	@FXML
	private AnchorPane details1;
	@FXML
	private Label firstnameLabel;
	@FXML
	private TextField firstnameField;
	@FXML
	private Label lastnameLabel;
	@FXML
	private TextField lastnameField;
	@FXML
	private Label phonelabel;
	@FXML
	private TextField phoneField;
	@FXML
	private Label phonelabel1;
	@FXML
	private TextField cityField;
	@FXML
	private Label phonelabel11;
	@FXML
	private TextField birthField;
	@FXML
	private Button saveIssue;

	/**
	 * Liste des projets qui est insérée dans list
	 */
	ObservableList<String> projectsView = FXCollections.observableArrayList();
	TrackingService model = null;
	
	private ObservableList<String> displayedProjectNames;
	
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		//Configuration des boutons sur la droite
		if (saveIssue != null)
		{
			saveIssue.setDisable(true);
		}
		//Configuration du panel de modification
		configureDetails();
		//Connexion au service de traitement
		connectToService();
		
		
		updateBugDetails();
		if (list != null)
		{
			list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			list.getSelectionModel().selectedItemProperty().addListener(projectItemSelected);
			displayedProjectNames.addListener(projectNamesListener);
		}
	}	
	
	/**
	 * Inferior panel
	 * Charge le contenu
	 */
	private void updateBugDetails()
	{
		final ObservableAccount selectedAccount = model.getAccount();
		
		if (details != null && selectedAccount != null)
		{
			if (firstnameField != null)
			{
				firstnameField.setText(selectedAccount.getFirstname());
			}
			if (lastnameField != null)
			{
				lastnameField.setText(selectedAccount.getLastname());
			}
			if (universityField != null)
			{
				universityField.setText(nonNull(selectedAccount.getUniversity()));
			}
			if (jobField != null)
			{
				jobField.setText(nonNull(selectedAccount.getJob()));
			}
			if (phoneField != null)
			{
				phoneField.setText(selectedAccount.getPhone());
			}
			if (cityField != null)
			{
				cityField.setText(selectedAccount.getCity());
			}
			if (birthField != null)
			{
				birthField.setText(selectedAccount.getBirth());
			}
		} else
		{
//			displayedBugId = null;
//			displayedBugProject = null;
		}
	}
	
	/**
	 * Connexion au service
	 */
	private void connectToService()
	{
		if (model == null)
		{
			model = new TrackingServiceStub();
			//Récupération de la liste des groupes
			displayedProjectNames = model.getAccountMenu();
		}
		projectsView.clear();
		List<String> sortedProjects = new ArrayList<String>(displayedProjectNames);
		Collections.sort(sortedProjects);
		projectsView.addAll(sortedProjects);
		list.setItems(projectsView);
	}
	
	private final ListChangeListener<String> projectNamesListener = new ListChangeListener<String>()
	{

		@Override
		public void onChanged(ListChangeListener.Change<? extends String> c)
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
	
	private final void hideAllPane()
	{
		if (contactsView.getChildren().size() > 0)
		{
			for (Node anchorpane: contactsView.getChildren())
			{
				AnchorPane panel = (AnchorPane) anchorpane;
				panel.setVisible(false);
			}
		}
//		contactsView.getChildren().get(0);
	}
	
	private final void showPane(String panename)
	{
		int index = model.getAccountMenu().indexOf(panename);
		if (contactsView.getChildren().size() >= index)
			contactsView.getChildren().get(index).setVisible(true);
	}
	
	/**
	 * Listener sur la liste
	 */
	private final ChangeListener<String> projectItemSelected = new ChangeListener<String>()
	{
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
		{
			System.out.println("account .changed()");
			hideAllPane();
			showPane(newValue);
		}
	};
	
	private Account hydratedEntity()
	{
		Account edited = new Account();
		edited.setId(model.getAccount().getId());
		if (firstnameField != null && !isEmpty(firstnameField.getText()))
			edited.setFirstname(firstnameField.getText());
		if (lastnameField != null && !isEmpty(lastnameField.getText()))
			edited.setLastname(lastnameField.getText());
		if (universityField != null && !isEmpty(universityField.getText()))
			edited.setUniversity(universityField.getText());
		if (jobField != null && !isEmpty(jobField.getText()))
			edited.setJob(jobField.getText());
		if (phoneField != null && !isEmpty(phoneField.getText()))
			edited.setPhone(phoneField.getText());
		if (cityField != null && !isEmpty(cityField.getText()))
			edited.setCity(cityField.getText());
		if (birthField != null && !isEmpty(birthField.getText()))
			edited.setBirth(birthField.getText());
		return (edited);
	}
	
	/**
	 * Check if modified
	 * @param edited
	 * @param issue
	 * @return 
	 */
	private boolean saveState(iAccount edited, iAccount issue)
	{
		try
		{
			System.out.println("ComputeSave");
			System.out.println("issue = " + issue.getUniversity());
			System.out.println("edited = " + edited.getUniversity());
			System.out.println(""+edited.getUniversity() + " " + issue.getUniversity());
			if (!equal(edited.getLastname(), issue.getLastname()))
			{
				return false;
			}
			if (!equal(edited.getFirstname(), issue.getFirstname()))
			{
				return false;
			}
			if (!equal(edited.getUniversity(), issue.getUniversity()))
			{
				return false;
			}
		}
		catch (Exception x)
		{
			// If there's an exception, some fields are invalid.
			return true;
		}
		// No field is invalid, no field needs saving.
		return (true);
	}
	
	private void updateSaveAccountButtonState()
	{
		System.out.println("updateSaveAccountButtonState()");
		boolean disable = true;
		if (disable == true)
		{
			System.out.println("disable = "+disable);
			disable = saveState(hydratedEntity(), model.getAccount());
			System.out.println("disable = "+disable);
		}
		if (saveIssue != null)
		{
			saveIssue.setDisable(disable);
		}
	}
	
	/**
	 * Configuration des détails
	 */
	private void configureDetails()
	{
		if (details != null)
		{
			//Gestion des modifications Field
			details.addEventFilter(EventType.ROOT, new EventHandler<Event>()
			{

				@Override
				public void handle(Event event)
				{
					if (event.getEventType() == MouseEvent.MOUSE_RELEASED
							|| event.getEventType() == KeyEvent.KEY_RELEASED)
					{
						updateSaveAccountButtonState();
					}
				}
			});
		}
	}
	
	@FXML
	private void saveAccountFired(ActionEvent event)
	{
		System.out.println("contact_sys.AccountTrackingController.saveAccountFired()");
		final ObservableAccount ref = model.getAccount();
		final Account edited = hydratedEntity();
		boolean isSaved = saveState(edited, ref);
		if (isSaved == false)
		{
			model.saveAccount(edited);
		}
	}
	
}
