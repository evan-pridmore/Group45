package application;

import java.io.FileInputStream;
import java.time.ZonedDateTime;

import application.TimeUnits.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**A class that manages each of the controllers for each FXML file present of the application.
 *This manages the references to instances relevant to the creation of controllers, the current logged in user, etc.
 *
 *<p>Important:
 *--> When creating a new controller/FXML file, create a new 'initialize' method and a new static controller variable,
 *specific to the type of controller class managing the FXML file. <br>
 *--> Any new controller should have an INDEPENDENT controller class, which should EXTEND application controller. <br>
 *--> In each initialize method, check to see if the controller is already defined as a static variable in this class. If 
 *it is null, create a new controller specific to that FXML file. If it is not null, do NOT create a new controller; rather,
 *assign it to the FXML loader created in that initialize method. <br>
 *--> Do not pass around any references to each controller. If absolutely necessary, create a static variable in this class. <br>
 * 
 * @author evan-pridmore
 */
public class ApplicationController {

// Define static 'class variables'.
	// private static ApplicationController appController;
	private static CalendarViewController calendarController;
	private static EventMakerController eventMakerController;
	private static EventManagerController eventManagerController;
	private static EventViewerController eventViewerController;
	private static LoginViewController loginController; 
	
	private static Stage appStage;
	private static Stage makerStage;
	private static Stage managerStage;
	private static Stage viewerStage;
	
	private static Scene managerScene;
	private static Scene viewerScene;
	
	private static User currentUser; 
	
	private static ZonedDateTime selectedDate;
	
// Define static 'getter' and 'setter' methods.
	// Define static controllers for each view to standardize across instances. 
	protected static void setLoginController(LoginViewController inputController) {
		loginController = inputController;
	}
	
	protected static void setCalendarController(CalendarViewController inputController) {
		calendarController = inputController;
	}
	
	protected static void setEventViewerController(EventViewerController inputController) {
		eventViewerController = inputController;
	}

	// Define components of GUI to standardize across instances.
	protected static void setAppStage(Stage inputStage) {
		// This is the general application window, used by CalendarView and LoginView
		appStage = inputStage;
	}
	
	protected static Stage getManagementStage() {
		// This stage is specific to the 'event management' windows.
		return viewerStage;
	}
	
	protected static Stage getMakerStage() {
		return makerStage;
	}
	
	protected static ZonedDateTime getSelectedDate() {
		return selectedDate;
	}
	
	protected static void setSelectedDate(ZonedDateTime inputDate) {
		selectedDate = inputDate;
	}
	
	// Define current logged in user across application and provide access to currentUser across controllers.
	protected static void setCurrentUser(User inputUser) {
		currentUser = inputUser;
	}
	
	public static User getCurrentUser() {
		// This 'getter' likely should be public, just in case non-controllers require access to the reference of 
		// currentUser.
		return currentUser;
	}
	
// Define 'initialize' methods for each distinct FXML view.
	/** A static method that triggers the initialization of LoginView.fxml. <p>
	 * 
	 * Creates a new FXMLLoader, checks for whether there is a pre-existing loginViewController instance stored as a static
	 * variable in ApplicationController, and sets/creates a new instance of loginViewController as appropriate (i.e., if 
	 * null, create new instance and store in ApplicationController).
	 */
	protected static void initializeLoginView() {
    	System.out.println("initializeLoginView: Attempting to initialize LoginView...");
		try {
			FXMLLoader loginLoader = new FXMLLoader();
			Parent rootScene = loginLoader.load(new FileInputStream("src/application/FXML/LoginView.fxml"));
			
			if (loginController == null) {
				System.out.println("loginController null");
				loginController = loginLoader.getController();
			} else {
				System.out.println("loginController not null");
				loginLoader.setController(loginController);
			}
			
			appStage.setTitle("Login");
			appStage.setScene(new Scene(rootScene));
			appStage.centerOnScreen();
			appStage.show();
  				  							
		} catch (Exception e) { e.printStackTrace(); }
		
		System.out.println("initializeLoginView: LoginView successfully initialized.");
		}
	

	/** A static method that triggers the initialization of CalendarView.fxml. <p>
	 * 
	 * Creates a new FXMLLoader, checks for whether there is a pre-existing calendarViewController instance stored as a static
	 * variable in ApplicationController, and sets/creates a new instance of calendarViewController as appropriate (i.e., if 
	 * null, create new instance and store in ApplicationController).
	 * 
	 * Opens a new application window depending on the user that has logged-in from the loginView.
	 */
	protected static void initializeCalendarView() {
    	System.out.println("initializeCalendarView: Attempting to initialize CalendarView...");
		try {
			FXMLLoader calendarLoader = new FXMLLoader();
			Parent rootScene = calendarLoader.load(new FileInputStream("src/application/FXML/CalendarView.fxml"));
			
			if (calendarController == null) {
				System.out.println("calendarController null");
				calendarController = calendarLoader.getController();
			} else {
				System.out.println("calendarController not null");
				calendarLoader.setController(calendarController);
			}
			
			calendarController.updateGUI();
			
			appStage.setTitle(String.format("%s's Calendar", currentUser.getUsername()));
			appStage.setScene(new Scene(rootScene));
			appStage.centerOnScreen();
			appStage.show();
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/** A static method that triggers the initialization of EventViewerView.fxml. <p>
	 * 
	 * Creates a new FXMLLoader, checks for whether there is a pre-existing eventViewerController instance stored as a static
	 * variable in ApplicationController, and sets/creates a new instance of eventViewerController as appropriate (i.e., if 
	 * null, create new instance and store in ApplicationController).
	 */
	protected static void initializeEventViewerView() {
    	System.out.println("initializeEventViewerView: Attempting to initialize EventViewerView...");
    	
    	try {
    		// Create new stage (window) for EventViewerView
    		if (viewerStage == null) {
    			viewerStage = new Stage();
    			viewerStage.initModality(Modality.APPLICATION_MODAL);
    		}
			
			if (viewerScene == null) {
				System.out.print("viewerScene null");
				
				FXMLLoader eventManagementLoader = new FXMLLoader();
	    		Parent rootScene = eventManagementLoader.load(new FileInputStream("src/application/FXML/EventsViewerView.fxml"));
				
				// Setting appropriate static controller to prevent the creation of additional instances.
				if (eventViewerController == null) {
					System.out.println("eventViewerController null");
					eventViewerController = eventManagementLoader.getController();
					viewerScene = new Scene(rootScene);
	    		} else {
					System.out.println("eventViewerController not null");
					eventManagementLoader.setController(eventViewerController);
	    		}

			}
			viewerStage.setTitle(currentUser.getUsername() + "'s Events");
			viewerStage.setScene(viewerScene);
			
			eventViewerController.makeTree();
			viewerStage.show();
			    		
    	} catch (Exception e) { e.printStackTrace(); }
	}
	
	/** A static method that triggers the initialization of EventMakerView.fxml. <p>
	 * 
	 * Creates a new FXMLLoader, checks for whether there is a pre-existing eventManagementController instance stored as a static
	 * variable in ApplicationController, and sets/creates a new instance of eventManagementController as appropriate (i.e., if 
	 * null, create new instance and store in ApplicationController).
	 */
	protected static void initializeEventMakerView() {
    	System.out.println("intializeEventMakerView: Attempting to initialize EventMakerView...");
		
		try {
			
			if (makerStage == null) {
    			System.out.println("makerStage null");
    			makerStage = new Stage();
    			makerStage.initModality(Modality.APPLICATION_MODAL);
    			makerStage.setTitle("New Event");
    			makerStage.setOnCloseRequest(eventMakerClose -> eventMakerController.closeEventMaker());
			}
    		
			FXMLLoader eventMakerLoader = new FXMLLoader();
			Parent root = eventMakerLoader.load(new FileInputStream("src/application/FXML/EventMakerView.fxml"));
			
    		// Setting appropriate static controller to prevent the creation of additional instances.
    		if (eventMakerController == null) {
				System.out.println("eventMakerController null");
				eventMakerController = eventMakerLoader.getController();
    		} else {
				System.out.println("eventMakerController not null");
				eventMakerLoader.setController(eventMakerController);
    		}
    		
    		Scene scene = new Scene(root);
    		makerStage.setScene(scene);
    		makerStage.show();
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	protected static void initializeEventManagerView(Event selectedEvent) {
		System.out.println("initializeEventManagerView: Attemptng to intialize EventManagerView...");
		
		try {
    		// Create new stage (window) for EventManagerView
    		if (managerStage == null) {
    			managerStage = new Stage();
    			managerStage.initModality(Modality.APPLICATION_MODAL);
    		}
			
			if (managerScene == null) {
				System.out.print("managerScene null");
				
				FXMLLoader eventManagerLoader = new FXMLLoader();
	    		Parent root = eventManagerLoader.load(new FileInputStream("src/application/FXML/EventManagerView.fxml"));
				
				// Setting appropriate static controller to prevent the creation of additional instances.
				if (eventManagerController == null) {
					System.out.println("eventManagementController null");
					eventManagerController = eventManagerLoader.getController();
					managerScene = new Scene(root);
	    		} else {
					System.out.println("eventManagementController not null");
					eventManagerLoader.setController(eventManagerController);
	    		}

			}
			managerStage.setTitle(selectedEvent.getName());
			managerStage.setScene(managerScene);
			
			managerStage.show();
			    		
    	} catch (Exception e) { e.printStackTrace(); }
	}
}
