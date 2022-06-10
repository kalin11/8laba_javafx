package collection;

import entity.Movie;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import static lab5.command.tasksCommands.ParseFromConsole.resRandDate;

public class CollectionWriter {
//    данный класс должен записывать элемент коллекции в файл в формета csv

    Movie movie;

    public CollectionWriter(Movie movie){
        this.movie = movie;
    }

    /**
     * This method convert all Movie object fields and add this to String with all Fields
     * @return String with all Fields with ','
     */

    public StringBuilder convertingFieldsToString() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        return new StringBuilder().
                append(movie.getId()).append(",").
                append(movie.getMovieName()).append(",").
                append(movie.getCoordinates()).append(",").
                append(movie.getCreationDate()).append(",").
                append(movie.getOscarsCount()).append(",").
                append(movie.getLength()).append(",").
                append(movie.getGenre()).append(",").
                append(movie.getMpaaRating()).append(",").
                append(movie.getOperator());
    }

    /**
     * This method write all Movie object Fields to File
     * @param path is way where you can find your File
     * @throws FileNotFoundException  - because there's cannot be a file
     */

    public void writeInFile(String path) throws FileNotFoundException{
        File file = new File(path);
        if (!file.exists()){
            System.out.println("файл не существует");
        }
        else if (!file.canRead() || !file.canWrite()) {
            System.out.println("файл не доступен для чтения/записи");;
        }
        else if (file.isDirectory()) {
            System.out.println("это не файл, а директория");
        }
        else {
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path, true), StandardCharsets.UTF_8)) {
                writer.write(convertingFieldsToString() + "\n");

            } catch (FileNotFoundException e) {
                System.out.println("файл не найден");
            } catch (IOException e) {
                System.out.println("что-то пошло не так");
            }
        }

    }

}
