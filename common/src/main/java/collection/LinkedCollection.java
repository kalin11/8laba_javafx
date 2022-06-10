package collection;

//import lab5.entity.*;
import DB.DataBase.ConnectToDB;
import cmd.Cmd;
import command.parsing.ObjectParser;
import entity.Movie;
import entity.MovieGenre;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Collections.*;

public class LinkedCollection implements Serializable {

    private List<Movie> list;
    private final Instant initializationTime = Instant.now();

    public void setList(List<Movie> list) {
        this.list = Collections.synchronizedList(list);
    }

    public LinkedCollection(){
        this.list = Collections.synchronizedList(new LinkedList<>());
    }

    private Movie movie = null;

    public List<Movie> getList() {
        return list;
    }

    public Movie getMovie(){
        return movie;
    }

    /**
     * this method add Movie object to list
     * @param movie - object which will be added to list
     */

    public Object add(Movie movie, Cmd cmd){
        ConnectToDB connect = new ConnectToDB();
        Object o = connect.sendToDB(cmd);
        if (o.getClass().toString().equals("class java.util.Collections$SynchronizedList")){
            list = Collections.synchronizedList((List<Movie>)o);
            return list;
        }
        else return o;
    }

    public void add(Movie movie){
        list.add(movie);
    }

    public String toString(){
        return list.toString();
    }

    public boolean isEmpty(){
        return list.size() == 0;
    }

    /**
     * Get all Movie object Fields and make String from them
     * @return String with all Movie Fields
     */

    public synchronized String print(){
        return list.size() == 0 ? "коллекция пустая" : list.stream().map(Movie::allInfo).collect(Collectors.joining());
    }

    /**
     * This method is printing first element of this list
     */

    public synchronized Object printFirstElement(){
        if (list.size() == 0){
            return "коллекция пустая";
        }
        else {
            System.out.println("исполняю команду 'head' ");
            return list.get(0);
        }
    }

    /**
     * This method clear list
     */

    public Object clear(Cmd cmd){
        ConnectToDB connect = new ConnectToDB();
//        if ((list = (List<Movie>) connect.sendToDB(cmd)) instanceof LinkedList) {
//            return list;
//        }
//        else return connect.sendToDB(cmd);
        Object o = connect.sendToDB(cmd);
        if (o.getClass().toString().equals("class java.util.Collections$SynchronizedList")){
            list = Collections.synchronizedList((List<Movie>)o);
            return list;
        }
        else return o;

    }

    /**
     * This method save list to File
     * @param path - way to File
     */

    public void save(String path)  {
        if (list.size() == 0){
            try {
                PrintWriter writer = new PrintWriter(path);
                writer.print("");
                writer.close();
            }catch (FileNotFoundException e){
                System.out.println("файла не существует");
                return;
            }
        }
        else {
            try{
                PrintWriter writer = new PrintWriter(path);
                writer.print("");
                writer.close();
            }catch (FileNotFoundException e) {
                System.out.println("у файла нет прав для записи");
                System.out.print("");
                return;
            }
            try{
                for (Movie movie : list) {
                    CollectionWriter collectionWriter = new CollectionWriter(movie);
                    collectionWriter.writeInFile(path);
                }
            }catch (FileNotFoundException e){
                System.out.println("файл не найден");
            }


        }
        System.out.println("коллекция была сохранена в указаный файл");
    }

    /**
     * This method write list info
     */

    public String info(){
        return "Type : " + list.getClass().toString() + "\n"+
                "Size : " + list.size() + "\n" +
                "Initialization time : " + initializationTime.toString() + "\n";

    }

    /**
     * This method count average of length of all movies in list
     */

    public Object averageOfLength(){
        if (list.size() == 0){
            return "коллекция пустая, добавьте объекты";
        }
        else{
            return list.stream().mapToDouble(Movie::getLength).average().orElse(0);
        }
    }

    /**
     * This method print Movies with Unique Oscars Count
     */

    public Object printMoviesWithUniqueOscarsCount(){
        if (list.size() == 0){
            return "коллекция пустая, добавьте объекты";
        }
        else {
            return list.stream().map(Movie::getOscarsCount).collect(Collectors.toSet());
        }
    }

    /**
     * This method update existed movie in list to a new movie
     * @param id - id of movie which you need to update
     * @param movie - Movie object that user has enter
     */

    public synchronized Object updateElement(long id, Movie movie, Cmd cmd) {
        ConnectToDB connect = new ConnectToDB();
        Object o = connect.sendToDB(cmd);
        if (o.getClass().toString().equals("class java.util.Collections$SynchronizedList")){
            list = Collections.synchronizedList((List<Movie>)o);
            return list;
        }
        else return o;
//        int index = -1;
//        for (Movie m : list){
//            index++;
//            if (m.getId() == id){
//                m.setId(id);
//                m.setName(movie.getMovieName());
//                m.setCoordinates(movie.getCoordinates());
////                    m.setCreationDate(movie.getCreationDate());
//                m.setOscarsCount(movie.getOscarsCount());
//                m.setLength(movie.getLength());
//                m.setGenre(movie.getGenre());
//                m.setMpaaRating(movie.getMpaaRating());
//                m.setOperator(movie.getOperator());
//                list.set(index,m);
//                return "коллекция была обновлена \n";
//            }
//
//        }
//        return "нет объекта с таким id";

    }

    /**
     * This method remove movie from list by id
     * @param id - id of movie which you wanna remove
     */

    public synchronized Object remove(long id, Cmd cmd){
        ConnectToDB connect = new ConnectToDB();
        Object o = connect.sendToDB(cmd);
        if (o.getClass().toString().equals("class java.util.Collections$SynchronizedList")){
            list = Collections.synchronizedList((List<Movie>)o);
            return list;
        }
        else return o;
//        }
//        if (list.size() > 0){
//            int count = 0;
//            try{
//                for (Movie movie : list) {
//                    if (movie.getId() == id) {
//                        list.remove(movie);
//                        count += 1;
//                    }
//                }
//            }
//            catch (ConcurrentModificationException e){
//                System.out.print("");
//            }
//            if (count == 0){
//                return "кино с таким id нет";
//            }
//            else{
//                return "объекты были удалены \n" ;
//            }
//        }
//        return "done";
    }

    /**
     * This method counts Movies in list with entered MovieGenre
     * @param genre - Genre of movie
     */

    public String countByGenre(MovieGenre genre) {
        if (list.size() > 0) {
            try {
                return "фильмов с жанром " + genre + " - " + (int) list.stream().filter(movie1 -> (movie1.getGenre() == genre)).count();
            }catch (IllegalArgumentException e){
                return "ошибка в указании жанра";
            }
        }
        else{
            return "коллекция пустая";
        }
    }

    /**
     * This method remove all movies which oscars count is greater than entered
     * @param movie - entered movie
     */

//    public Object removeGreater(Movie movie, Cmd cmd){
//    }

    /**
     * This method remove all movies which oscars count is lower than entered
     * @param movie - entered movie
     */

    public LinkedList<Movie> removeLower(Movie movie){
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in =  new BufferedReader(isr);
        ObjectParser parser = new ObjectParser();
        try {
            Movie mov = parser.parseObject(in);
            list.removeIf(m -> mov.compareTo(m) > 0);
            System.out.println("объекты были удалены");
            return (LinkedList<Movie>) list;
        }catch (IOException e){
            System.out.println("не удалось чот");
        }
        return null;


    }

    public synchronized Object removeG(Movie movie, Cmd cmd){
        ConnectToDB connect = new ConnectToDB();
        Object o = connect.sendToDB(cmd);
        if (o.getClass().toString().equals("class java.util.Collections$SynchronizedList")){
            list = Collections.synchronizedList((List<Movie>)o);
            return list;
        }
        else return o;
    }

    public synchronized Object removeL(Movie movie, Cmd cmd){
        ConnectToDB connect = new ConnectToDB();
        Object o = connect.sendToDB(cmd);
        if (o.getClass().toString().equals("class java.util.Collections$SynchronizedList")){
            list = Collections.synchronizedList((List<Movie>)o);
            return list;
        }
        else return o;
    }
    public void updateMov(long id, Movie movie) throws ConcurrentModificationException{
//        int index = -1;
//            for (Movie m : list){
//                index++;
//                if (m.getId() == id){
//                    m.setId(id);
//                    m.setName(movie.getMovieName());
//                    m.setCoordinates(movie.getCoordinates());
////                    m.setCreationDate(movie.getCreationDate());
//                    m.setOscarsCount(movie.getOscarsCount());
//                    m.setLength(movie.getLength());
//                    m.setGenre(movie.getGenre());
//                    m.setMpaaRating(movie.getMpaaRating());
//                    m.setOperator(movie.getOperator());
//                    list.set(index,m);
//                }
//            }
            if (list.stream().filter(movie1 -> movie1.getId()==id).collect(Collectors.toList()).size() == 0){
                System.out.println("нет фильмов с таким id");
            }
            else {
                list.stream().filter(m -> {
                    if (m.getId() == id) {
                        m.setId(id);
                        m.setName(movie.getMovieName());
                        m.setCoordinates(movie.getCoordinates());
//                    m.setCreationDate(movie.getCreationDate());
                        m.setOscarsCount(movie.getOscarsCount());
                        m.setLength(movie.getLength());
                        m.setGenre(movie.getGenre());
                        m.setMpaaRating(movie.getMpaaRating());
                        m.setOperator(movie.getOperator());
                        list.set(list.indexOf(m), m);
                    }
                    return true;
                }).collect(Collectors.toList());
                System.out.println("коллекция была успешна обновлена");
            }
    }
}
