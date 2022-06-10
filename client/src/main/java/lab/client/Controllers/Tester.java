package lab.client.Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//--module-path
//        "C:\Users\kal1n\.jdks\azul-1.8.0_3121\lib"
//        --add-modules
//        javafx.controls,javafx.fxml

public class Tester{
//    @Override
//    public void start(Stage stage) throws Exception{
//        System.out.println("уй");
//        Label l = new Label("ЗДАРОВА ХУЕСОСИЩЕ)))))");
//        Scene scene = new Scene(new StackPane(l), 640, 480);
//        stage.setScene(scene);
//        stage.show();
//        URL url = new File("C:\\Users\\kal1n\\IdeaProjects\\lab7TestThread\\client\\src\\main\\java\\lab\\client\\").toURI().toURL();
//        Parent root = FXMLLoader.load(url);
//        stage.setTitle("hey");
//        stage.setScene(new Scene(root, 600, 400));
//        stage.show();
//        Button button = new Button();
//        button.setText("соснешь?");
//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                button.setText("соснул)");
//            }
//        });
//        Group root = new Group(button);
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.setTitle("хуй");
//        stage.setWidth(250);
//        stage.setHeight(200);
//        stage.show();

//        URL url = new File("login.fxml").toURI().toURL();
//        Parent root = FXMLLoader.load(url);
//
//        stage.setTitle("Hello world");
//        stage.setScene(new Scene(root, 600, 400));
//        stage.show();
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScene.fxml"));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//    }

    public static int value = 0;

    public String toString(){
        return null;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        System.out.println(Integer.toHexString("wfdf33".hashCode()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(new Locale("LV"));
//        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("EN"));
        LocalDate localDate = LocalDate.parse("2022-10-29");

        String format = formatter.format(localDate);
        System.out.println(format);
        System.out.println(localDate);
        //race
        /*Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                int oldValue = value;
                int newValue = ++value;
                if (oldValue + 1 != newValue) {
                    throw new IllegalStateException(oldValue + " + 1 = " + newValue);
                }
            }
        };
        new Thread(task).start();
        new Thread(task).start();
        new Thread(task).start();*/


        //deadlock
//        Object lock1 = new Object();
//        Object lock2 = new Object();
//
//        Thread thread1 = new Thread(() -> {
//            try {
//                synchronized (lock1) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    synchronized (lock2) {
//
//                    }
//                }
//            }finally {
//
//            }
//        });
//        Thread thread2 = new Thread(() -> {
//            synchronized (lock2){
//                synchronized (lock1){
//
//                }
//            }
//        });
//        thread1.start();
//        thread2.start();


        //livelock

//        Lock lock1 = new ReentrantLock();
//        Lock lock2 = new ReentrantLock();
//        Thread thread1 = new Thread(() -> {
//                if (lock1.tryLock()) {
//                    lock1.lock();
//                    System.out.println(Thread.currentThread().getName() + " locked 1");
//                    if (lock2.tryLock()) {
//                        lock2.lock();
//                        System.out.println(Thread.currentThread().getName() + " locked 2");
//                        lock2.unlock();
//                    }
//                }
//
//        });
//        Thread thread2 = new Thread(() -> {
//
//                if (lock2.tryLock()) {
//                    lock2.lock();
//                    System.out.println(Thread.currentThread().getName() + " locked2");
//                    if (lock1.tryLock()) {
//                        lock1.lock();
//                        System.out.println(Thread.currentThread().getName() + " locked1");
//                        lock1.unlock();
//                    }
//                }
//            });
//
//        thread1.start();
//        thread2.start();
//        Callable<String> task = () -> Thread.currentThread().getName();
//        ExecutorService service = Executors.newFixedThreadPool(2);
//        for (int i = 0; i < 5; i++) {
//            Future result = service.submit(task);
//            System.out.println(result.get());
//        }
//        service.shutdown();
    }
}
