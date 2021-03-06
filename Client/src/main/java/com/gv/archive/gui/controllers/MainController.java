package com.gv.archive.gui.controllers;

import com.gv.archive.gui.start.Main;
import com.gv.archive.logging.AppLogger;
import com.gv.archive.models.Dossier;
import com.gv.archive.models.Role;
import com.gv.archive.services.implementations.BasicDossierService;
import com.gv.archive.services.interfaces.DossierService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane dossierPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label roleInfo;

    @FXML
    private Label countryInfo;

    @FXML
    private Label cityInfo;

    @FXML
    private Label addressInfo;

    @FXML
    private Label mobileInfo;

    @FXML
    private Label skypeInfo;

    @FXML
    private Label experienceInfo;

    @FXML
    private Label currentUserLabel;

    @FXML
    private ChoiceBox parserSelectorBox;

    @FXML
    private Label dossierActionsLabel;

    @FXML
    private Button addDossierButton;

    @FXML
    private Button updateDossierButton;

    @FXML
    private Button deleteDossierButton;

    @FXML
    private Separator controlPanelSeparator;

    @FXML
    private Label chooseParserLabel;

    @FXML
    private ListView<String> dossierHeaderList;

    private ObservableMap<String, Dossier> dossierMap;

    private DossierService service = new BasicDossierService();

    private final static String DOM_PARSER = "DOM";

    private static Dossier cash = null;

    private static final Dossier EMPTY_DOSSIER = null;

    @FXML
    /**
     * initializes entered users name and visible elements according users role,
     * also method initialize dossiers from server
     */
    private void initialize(){
        dossierMap = service.getAllDossiers(DOM_PARSER);
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(dossierMap.keySet());
        dossierHeaderList.setItems(items);

        dossierPane.setVisible(false);
        dossierHeaderList.setItems(items);

        currentUserLabel.setText(FormController.currentUser.getName());     // init of entered user
        initVisibleGuiElements(FormController.currentUser.getRole());       // init visible elements according user role

        dossierHeaderList.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dossierPane.setVisible(true);
                String selectedDossierHeader = dossierHeaderList.getSelectionModel().getSelectedItem();
                Dossier dossier = dossierMap.get(selectedDossierHeader);
                nameLabel.setText(dossier.getName());
                roleInfo.setText(dossier.getRole().toString().toLowerCase());
                countryInfo.setText(dossier.getAddress().getCountry());
                cityInfo.setText(dossier.getAddress().getCity());
                addressInfo.setText(dossier.getAddress().getStreet());
                mobileInfo.setText(dossier.getMobile());
                skypeInfo.setText(dossier.getSkype());
                experienceInfo.setText(dossier.getExperience());
                experienceInfo.setWrapText(true);
            }
        });

        initiateContentUpdater();
    }

    private void initiateContentUpdater() {
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                int i = 0;
                while (true) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            dossierMap = service.getAllDossiers(DOM_PARSER);
                            dossierHeaderList.getItems().clear();
                            for(String key : dossierMap.keySet()){
                                dossierHeaderList.getItems().add(key);
                            }
                        }
                    });
                    Thread.sleep(2000);
                }
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    public void updateCurrentDossier(ActionEvent actionEvent) {
        String oldDossierHeader = dossierHeaderList.getSelectionModel().getSelectedItem();
        if(oldDossierHeader == null){
            return;
        }
        setDossierCash(dossierMap.get(oldDossierHeader));
        callEditWindow(actionEvent);
    }

    public void createDossier(ActionEvent actionEvent) {
        setDossierCash(EMPTY_DOSSIER);
        callEditWindow(actionEvent);
    }

    private void callEditWindow(ActionEvent actionEvent) {
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/edit.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.getIcons().add(new Image(getClass().getClassLoader()
                    .getResource("pictures/icons/favicon.jpg").toExternalForm()));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e){
            AppLogger.getLogger().error(e);
        }
    }

    public void deleteCurrentDossier(ActionEvent actionEvent) {
        String selectedDossierHeader = dossierHeaderList.getSelectionModel().getSelectedItem();
        String login = dossierMap.get(selectedDossierHeader).getLogin();
        service.deleteDossier(login, DOM_PARSER);
    }

    public static Dossier getDossierCash(){
        return cash;
    }

    public static void setDossierCash(Dossier dossier){
        cash = dossier;
    }

    public void logout(ActionEvent actionEvent) {
        try {
            Stage stage = Main.getMainStage();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/form.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(true);
            stage.getIcons().add(new Image(getClass().getClassLoader()
                    .getResource("pictures/icons/favicon.jpg").toExternalForm()));
            stage.setMinHeight(Main.MIN_HEIGHT_OF_FORM_WINDOW);
            stage.setMinWidth(Main.MIN_WIDTH_OF_FORM_WINDOW);
            stage.setMaxHeight(Main.MIN_HEIGHT_OF_FORM_WINDOW);
            stage.setMaxWidth(Main.MIN_WIDTH_OF_FORM_WINDOW);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            AppLogger.getLogger().error(e.getMessage());
        }
    }

    private void initVisibleGuiElements(Role role){
        switch (role){
            case ADMIN:
               parserSelectorBox.setVisible(true);
               dossierActionsLabel.setVisible(true);
               addDossierButton.setVisible(true);
               updateDossierButton.setVisible(true);
               deleteDossierButton.setVisible(true);
               controlPanelSeparator.setVisible(true);
               chooseParserLabel.setVisible(true);
               break;
            case USER:
                parserSelectorBox.setVisible(false);
                dossierActionsLabel.setVisible(true);
                addDossierButton.setVisible(false);
                updateDossierButton.setVisible(true);
                deleteDossierButton.setVisible(false);
                controlPanelSeparator.setVisible(false);
                chooseParserLabel.setVisible(false);
                break;
            case GUEST:
                parserSelectorBox.setVisible(false);
                dossierActionsLabel.setVisible(false);
                addDossierButton.setVisible(false);
                updateDossierButton.setVisible(false);
                deleteDossierButton.setVisible(false);
                controlPanelSeparator.setVisible(false);
                chooseParserLabel.setVisible(false);
                break;
        }
    }
}
