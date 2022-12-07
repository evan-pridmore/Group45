module Group45Project {
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.web;
	
	opens application to javafx.graphics, javafx.fxml;
}
