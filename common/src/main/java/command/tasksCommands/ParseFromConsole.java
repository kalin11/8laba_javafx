package command.tasksCommands;

import entity.Country;
import entity.MovieGenre;
import entity.MpaaRating;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

//import java.lang.Integer.parseInt;

import static java.lang.Integer.parseInt;

public class ParseFromConsole {

    /**
     * This method sets Movie id
     * @return - movie id
     */

    public Long randomID(){
        return (long) (Math.random()*10000000);

    }

    /**
     * This method parse Integer var from Console, its for Oscars count
     * @param msg - message to user, what he has to enter
     * @param in - enter some to Console
     * @return - return some Integer value
     * @throws IOException - if there will be some problems with I/O
     */

    public Integer processInt(String msg, BufferedReader in) throws IOException {
        System.out.println(msg + ", большее 0");
        for (; ;) {

            String read = in.readLine().trim().replaceAll("\\s+", " ").trim();
            int x;
            if (!read.equals("")){
                try{
                    x = parseInt(read);
                    if (x > 0 && x < 12) {
                        return x;
                    }
                    else if (x == 0){
                        System.out.println("я же попросил ввести число, которое больше 0 -_-");
                    }
                    else if (x < 0){
                        System.out.println("отрицательное число наград? типа в долг взяли? введите заново");
                    }
                    else if (x >= 12){
                        System.out.println("в мире максимум 11 оскаров за фильм, вы думаете, что вы лучше..?");
                    }
                    else if (x > 2147483647){
                        System.out.println("вы ввели число, которое уходит за границы (int)");
                    }
                }catch (NumberFormatException e){
                    System.out.println("вы ввели не число, попробуйте снова/неверный формат введеных данных");
                }
            }else {
                System.out.println("пустая строка, попробуйте снова");
            }
        }
    }

    /**
     * This method parse Integer var from Console, its for X
     * @param msg - message to user, what he has to enter
     * @param in - enter some to Console
     * @return - return some Integer value
     * @throws IOException - if there will be some problems with I/O
     */

    public Integer processIntWithoutLimits(String msg, BufferedReader in) throws IOException {
        System.out.println(msg);
        for (; ;){

            String read = in.readLine().trim().replaceAll("\\s+", " ").trim();
            int x;
            if (!read.equals("")){
                try{
                    x = parseInt(read);
                    if (x > 2147483647){
                        System.out.println("вы ввели число, которое уходит за границы (int)");
                    }
                    else {
                        return x;
                    }

                }catch (NumberFormatException e){
                    System.out.println("вы ввели не число, попробуйте снова/неверный формат введеных данных");
                }
            }
            else {
                System.out.println("пустая строка, попробуйте снова");
            }
        }
    }
    /**
     * This method parse Integer var from Console, its for Y
     * @param msg - message to user, what he has to enter
     * @param in - enter some to Console
     * @return - return some Integer value
     * @throws IOException - if there will be some problems with I/O
     */

    public   Integer processIntY(String msg, BufferedReader in) throws IOException {
        System.out.println(msg + ", максимальное значение - 884");
        for (; ;){

            String read = in.readLine().trim().replaceAll("\\s+", " ").trim();
            int x;
            if (!read.equals("")) {
                try {
                    x = parseInt(read);
                    if (x < 885) {
                        return x;
                    } else if (x <= 2147483647) {
                        System.out.println("вы ввели что-то большее, чем 884");
                    }
                    else if (x > 2147483647){
                        System.out.println("вы ввели число, которое уходит за границы (int)");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("вы ввели не число, попробуйте снова/неверный формат введеных данных");
                }
            }
            else {
                System.out.println("пустая строка, попробуйте снова");
            }
        }
    }

    /**
     * This method parse Long var from Console, its for Movie Length
     * @param msg - message to user, what he has to enter
     * @param in - enter some to Console
     * @return - return some Long value
     * @throws IOException - if there will be some problems with I/O
     */

    public Long processLong(String msg, BufferedReader in) throws IOException {
        System.out.println(msg + ", вы должны ввести что-то большее 0");
        for (; ;){

            String read = in.readLine().trim().replaceAll("\\s+", " ");
            Long x;
            if (!read.equals("")){
            try {
                x = Long.parseLong(read);
                if (x > 0 && x < 2880){
                    return x;
                }
                else if (x == 0){
                    System.out.println("я же попросил ввести что-то большее 0 -_-");
                }
                else if (x > 2880){
                    System.out.println("самый длиннный фильм длился 48 часов, то есть 2880 минут, вы сняли что-то длиннее? у вас бы не хватило нервов, вы на ВТ, хахах)))");
                }
                else {
                    System.out.println("вы ввели что-то меньшее 0");
                }
            }catch (NumberFormatException e){
                System.out.println("вы ввели не число, попробуйте снова/неверный формат введеных данных");
            }
            }
            else {
                System.out.println("пустая строка, попробуйте снова");
            }
        }
    }

    /**
     * This method parse String var from Console, its for Movie title
     * @param msg - message to user, what he has to enter
     * @param in - enter some to Console
     * @return - return some String value
     * @throws IOException - if there will be some problems with I/O
     */

    public String processString(String msg, BufferedReader in) throws IOException {
        System.out.println(msg + "это не может быть null/пустой строкой");
        for (; ;){
            String read = in.readLine().trim().replaceAll("\\s+", " ").trim();
            if ((read != null) && (!read.isEmpty())){
                return read;
            }
            else{
                System.out.println("пустая строка, попробуйте снова");
            }
        }
    }
    /**
     * This method parse String var from Console, its for Operator Name
     * @param msg - message to user, what he has to enter
     * @param in - enter some to Console
     * @return - return some String value
     * @throws IOException - if there will be some problems with I/O
     */

    public String processStringForName(String msg, BufferedReader in) throws IOException {
        System.out.println(msg + "это не может быть null или пустой строкой, первая буква должна быть заглавной, пример - Sasha");
        for (; ;) {

            String read = in.readLine().trim().replaceAll("\\s+", " ");
            if ((!read.equals("")) && (read.matches("^([A-Z]{1}[a-z]{1,50})$"))) {
                return read;
            }else if ((!read.equals("")) && !(read.matches("^([A-Z]{1}[a-z]{1,50})$"))){
                System.out.println("прочитайте пример...");
            }
            else {
                System.out.println("пустая строка, попробуйте снова");
            }
        }
    }

    /**
     * This method parse Country var from Console
     * @param msg - message to user, what he has to enter
     * @param in - enter some to Console
     * @return - return some Country value
     * @throws IOException - if there will be some problems with I/O
     */

    public Country processCountry(String msg, BufferedReader in) throws IOException {
        System.out.println(msg + "это не может быть null, вы можете выбрать: 'Japan, Italy or India'");
        for (; ;){
            String read = in.readLine().trim().replaceAll("\\s+", " ").toUpperCase(Locale.ROOT);
            if (!read.equals("")) {
                switch (read) {
                    case "INDIA":
                        return Country.INDIA;

                    case "ITALY":
                        return Country.ITALY;

                    case "JAPAN":
                        return Country.JAPAN;

                    default:
                        System.out.println("вы ввели что-то не то, либо же эта страна не в списке, вы можете выбрать только :  Japan, Italy or India, попробуйте снова");
                }
            }else {
                System.out.println("пустая строка, попробуйте снова");
            }
        }
    }

    /**
     * This method parse MpaaRating var from Console
     * @param msg - message to user, what he has to enter
     * @param in - enter some to Console
     * @return - return some MpaaRating value
     * @throws IOException - if there will be some problems with I/O
     */


    public MpaaRating processRating(String msg, BufferedReader in) throws IOException {
        System.out.println(msg + "вы можете выбрать : 'G', 'PG', 'R', 'NC_17'");
        for (; ;) {
            String read = in.readLine().trim().replaceAll("\\s+", " ").toUpperCase(Locale.ROOT);
            if (!read.equals("")) {

                switch (read) {
                    case "G":
                        return MpaaRating.G;
                    case "PG":
                        return MpaaRating.PG;
                    case "R":
                        return MpaaRating.R;
                    case "NC_17":
                        return MpaaRating.NC_17;
                    default:
                        System.out.println("вы ввели что-то не то, либо же этот рейтинг не в списке, вы можете выбрать только 'G', 'PG', 'R', 'NC_17'");
                }
            }else {
                System.out.println("пустая строка, попробуйте снова");
            }
        }

    }
    /**
     * This method parse Genre var from Console
     * @param msg - message to user, what he has to enter
     * @param in - enter some to Console
     * @return - return some Genre value
     * @throws IOException - if there will be some problems with I/O
     */

    public   MovieGenre processGenre(String msg, BufferedReader in) throws IOException {
        System.out.println(msg + "вы можете выбрать :  'HORROR', 'DRAMA', 'ACTION', 'SCIENCE FICTION'");

        for (; ;){
            String read = in.readLine().trim().replaceAll("\\s+", " ").toUpperCase(Locale.ROOT);
            if (!read.equals("")){

                switch (read){
                    case "SCIENCE FICTION":
                        return MovieGenre.SCIENCE_FICTION;
                    case "HORROR":
                        return MovieGenre.HORROR;
                    case "DRAMA":
                        return MovieGenre.DRAMA;
                    case "ACTION":
                        return MovieGenre.ACTION;
                    default:
                        System.out.println("вы ввели что-то не то, либо же этот жжанр не в списке, вы можете выбрать : 'HORROR', 'DRAMA', 'ACTION', 'SCIENCE FICTION'");
                }
            }else {
                System.out.println("пустая строка, попробуйте снова");
            }
        }
    }

    /**
     * This method parse Float var from Console, its for Operator's weigth
     * @param msg - message to user, what he has to enter
     * @param in - enter some to Console
     * @return - return some Float value
     * @throws IOException - if there will be some problems with I/O
     */

    public   Float processFloat(String msg, BufferedReader in) throws IOException {
        System.out.println(msg + "это должно быть больше 0, пусть вес среднестатистического человека - 50");
        for (; ;){
            String read = in.readLine().trim().replaceAll("\\s+", " ");
            Float x;
            if (!read.equals("")) {
                try {
                    x = Float.parseFloat(read);
                    if (x > 50 && x < 150) {
                        return x;
                    }else if (x >= 150) {
                        System.out.println("что-то слишком много для среднестатистического человека");
                    }
                    else if (x > 0 && x <= 50){
                        System.out.println("ну чот прям худенький, надо откормить пацана, потом садить фильмы снимать");
                    }

                    else {
                        System.out.println("вы ввели что-то меньшее 0, попробуйте снова");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("вы ввели не число, попробуйте снова/неверный формат введеных данных");
                }
            }else {
                System.out.println("пустая строка, попробуйте снова");
            }
        }
    }
    /**
     * This method parse ZonedDateTime var from Console, its for Operator's birthday
     * @param msg - message to user, what he has to enter
     * @param in - enter some to Console
     * @return - return some ZonedDateTime value
     * @throws IOException - if there will be some problems with I/O
     */

    public ZonedDateTime processZND(String msg, BufferedReader in) throws IOException{
        System.out.println(msg + "пример даты - 'MM/dd/yyyy'");
        for (; ;) {
            String read = in.readLine().trim().replaceAll("\\s+", " ");

            if (!read.equals("")) {

                String H = "";
                String M = "";
                String S = "";

                int h = (int) (Math.random()*24);
                if (h < 10){
                    H = "0"+h;
                }
                else {
                    H = String.valueOf(h);
                }
                int m = (int) (Math.random()*60);
                if (m < 10){
                    M = "0"+m;
                }
                else {
                    M = String.valueOf(m);
                }
                int s = (int) (Math.random()*60);
                if (s < 10){
                    S = "0"+s;
                }
                else {
                    S = String.valueOf(s);
                }
                String hms = String.valueOf(H+":" + M+":" + S);

                read+= " "+ hms + ".900000";

                try {
                    String pattern = "MM/dd/yyyy HH:mm:ss.SSSSSS";
                    DateTimeFormatter Parser = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
                    ZonedDateTime a = ZonedDateTime.parse(read, Parser);
                    return a;

                }catch (DateTimeParseException e){
                    System.out.println("вы совершили ошибку в шаблоне даты, проверьте себя. пример - '01/01/1900'");
                }

            }else {
                System.out.println("пустая строка, попробуйте снова");
            }

        }



    }


}
