package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static java.util.Locale.lookup;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
public class FurtherCheckoutTest {

    public Stage stage;
//
    @BeforeEach
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(HelloApplication.class);
        FxToolkit.showStage();
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.cleanupStages();
        FxToolkit.cleanupApplication(HelloApplication.class.newInstance());
    }

    @Start
    public void start(Stage primaryStage) throws IOException {
        Stage stage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
        stage.resizableProperty();
        stage.setTitle("Menu Manager");
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void goToNotAdmin(FxRobot robot) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("orders.json");
        JSONObject obj = (JSONObject) jsonParser.parse(reader);

        Label notAdmin = robot.lookup("#notAdmin").queryAs(Label.class);
        robot.clickOn("#notAdmin");
        robot.clickOn("#cartButton");
        robot.clickOn("#Payment");

        Text u = robot.lookup("#thanks").queryAs(Text.class);

        //Setting the newStage to the Stage associated w/ the hello-view.fxml
        Stage newStage = (Stage) u.getScene().getWindow();

        FileWriter file = new FileWriter("orders.json");
        file.write(obj.toJSONString());
        file.flush();



        //Test that the Stages are indeed different
        Assertions.assertNotEquals(stage, newStage);
    }

    @Test
    void goToNotAdmin2(FxRobot robot) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("orders.json");
        JSONObject obj = (JSONObject) jsonParser.parse(reader);


        robot.clickOn("#notAdmin");
        robot.clickOn("#cartButton");
        robot.clickOn("#Payment");

        robot.clickOn("#backButtonImageCompletion");

        robot.clickOn("#username");

        TextField u = robot.lookup("#username").queryAs(TextField.class);

        //Setting the newStage to the Stage associated w/ the hello-view.fxml
        Stage oldStage = (Stage) u.getScene().getWindow();
        Label notAdmin = robot.lookup("#notAdmin").queryAs(Label.class);
        robot.clickOn("#notAdmin");
        robot.clickOn("#goBackInitMenuButton");

        TextField v = robot.lookup("#username").queryAs(TextField.class);

        //Setting the newStage to the Stage associated w/ the hello-view.fxml
        Stage newStage = (Stage) v.getScene().getWindow();

        FileWriter file = new FileWriter("orders.json");
        file.write(obj.toJSONString());
        file.flush();

        //Test that the Stages are not different
        Assertions.assertEquals(oldStage, newStage);
    }

}
