package com.solar;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration
public class MainApp extends Application {

    public static ConfigurableApplicationContext springContext;
    private Parent root;
    public static HostServices hs;
    public static Stage stage;
    public static String[] args;

    @Override
    public void init() throws Exception {
        log.info("Starting UI......");
        if (PlatformUtil.isWindows()) {
            log.info("Window......");
            System.setProperty("spring.config.location", "file:///D:/Solar/solar/application.properties");
        } else {
            log.info("Linux.....");
            System.setProperty("spring.config.location", "file:///home/bibek/Desktop/solar/application.properties");
        }
        log.info("loading dashboard");
        springContext = SpringApplication.run(MainApp.class);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
        log.info("ok");
        fxmlLoader.setControllerFactory(springContext::getBean);
        log.info("ok");
        root = fxmlLoader.load();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        hs = getHostServices();
        stage = primaryStage;
        Scene scene = new Scene(root);
        stage.setMaximized(false);
        stage.setResizable(false);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Solar");
        stage.setScene(scene);

        stage.show();
        log.info("stage are showing");
    }

    @Override
    public void stop() throws Exception {
        log.info("stopping");
        springContext.stop();
        System.exit(-1);
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
        log.info("stopping");
        Platform.exit();
        springContext.stop();
        SpringApplication.run(MainApp.class, args).close();
        System.exit(-1);
    }
}
