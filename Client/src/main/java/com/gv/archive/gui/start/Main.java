package com.gv.archive.gui.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Entry point of whole app
 */
public class Main extends Application{

    /** min size of form window in pixels*/
    public static final int MIN_WIDTH_OF_FORM_WINDOW = 600;

    /** min size of form window in pixels*/
    public static final int MIN_HEIGHT_OF_FORM_WINDOW = 384;

    /** min size of main window in pixels*/
    public static final int MIN_WIDTH_OF_MAIN_WINDOW = 887;

    /** min size of main window in pixels*/
    public static final int MIN_HEIGHT_OF_MAIN_WINDOW = 600;

    /** max size of main window in pixels*/
    public static final int MAX_WIDTH_OF_MAIN_WINDOW = 3000;

    /** max size of main window in pixels*/
    public static final int MAX_HEIGHT_OF_MAIN_WINDOW = 2000;

    /** main stage of application */
    private static Stage mainStage;

    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/form.fxml"));
        primaryStage.setMinWidth(MIN_WIDTH_OF_FORM_WINDOW);
        primaryStage.setMinHeight(MIN_HEIGHT_OF_FORM_WINDOW);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Archive");
        primaryStage.getIcons().add(new Image(getClass().getClassLoader()
                .getResource("pictures/icons/favicon.jpg").toExternalForm()));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @return main stage of javaFx object
     */
    public static Stage getMainStage(){
        return mainStage;
    }

    /**
     * defines entry point of app
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}