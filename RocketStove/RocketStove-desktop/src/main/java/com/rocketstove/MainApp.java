package com.rocketstove;

import com.sun.javafx.PlatformUtil;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class MainApp extends Application {
    public static ConfigurableApplicationContext springContext;
    private Parent root;
    public static HostServices hs;
    public static String[] args;
     public static Stage stage;
    @Override
    public void init() throws Exception {
        if (PlatformUtil.isWindows()){
            System.setProperty("spring.config.location", "file:///D:/RocketStove/rocketstove/application.properties");
        }else{
            System.setProperty("spring.config.location", "file:////home/bibek/Desktop/rocketstove/application.properties");
        }
        springContext = SpringApplication.run(MainApp.class);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        root = fxmlLoader.load();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        hs = getHostServices();
        Scene scene = new Scene(root);
        stage.setMaximized(false);
        stage.setResizable(false);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Rocket Stove");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        springContext.stop();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         MainApp.args=args;
        launch(args);
    }
    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
        springContext.stop();
        SpringApplication.run(MainApp.class, args).close();
        System.exit(-1);
    }
}
