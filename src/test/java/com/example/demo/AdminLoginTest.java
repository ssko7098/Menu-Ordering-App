package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

@ExtendWith(ApplicationExtension.class)
public class AdminLoginTest {

    public Stage stage;

    @BeforeEach
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(HelloApplication.class);
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
    void testingTestFunction(FxRobot robot) throws IOException {
        Button buttonTester = robot.lookup("#button").queryAs(Button.class);
        Label labelHolder = robot.lookup("#wrongLogin").queryAs(Label.class);
        TextField userLogin = robot.lookup("#username").queryAs(TextField.class);
        assertNotNull(buttonTester);

        //Testing Login Combinations
        robot.clickOn("#button");
        robot.clickOn("#username");
        robot.write("admin1");
        robot.clickOn("#button");
        assertEquals(labelHolder.getText(),"Wrong username or password!");
        robot.clickOn("#password");
        robot.write("1234");

        robot.clickOn("#username");
        robot.eraseText(6);
        robot.clickOn("#button");
        robot.clickOn("#username");
        robot.write("adminnnn");
        robot.clickOn("#button");
        robot.clickOn("#username");
        robot.eraseText(8);

        robot.clickOn("#username");
        robot.write("admin");
        robot.clickOn("#password");
        robot.write("5");
        robot.clickOn("#button");

        robot.clickOn("#password");
        robot.eraseText(1);
        robot.clickOn("#button");
        assertEquals("Success!",labelHolder.getText());
    }

    @Test
    void goToOrderTest(FxRobot robot) throws IOException {

        Label notAdmin = robot.lookup("#notAdmin").queryAs(Label.class);
        robot.clickOn("#notAdmin");
    }
}
