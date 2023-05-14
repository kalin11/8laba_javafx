package command.tasksCommands;

import entity.Country;
import entity.MovieGenre;
import entity.MpaaRating;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

public class ParseFromFile {

    /**
     * This method parse ID Field from File
     * @param element - String value of movie's id
     * @return - returns id of movie
     */

    public long parseID(String element){
        return Long.parseLong(element.trim());
    }

    /**
     * This method parse String Fields from File
     * @param element - some String Field
     * @return - returns String Field
     */

    public String parseString(String element){
        if (element.trim().isEmpty()){
            System.out.println("some field is empty");
        }
        return element;
    }

    /**
     * This method parse X Field from File
     * @param element - String value of int X Field
     * @return - return int X value
     */

    public int parseX(String element){
        return Integer.parseInt(element.trim());
    }
    /**
     * This method parse Y Field from File
     * @param element - String value of int Y Field
     * @return - return int Y value
     */


    public int parseY(String element){
        return Integer.parseInt(element.trim());
    }

    /**
     * This method parse Date Field from File
     * @param element - String value of date Field
     * @return - Date date Field
     * @throws ParseException - if something went from with parsing
     */

    public Date parseDate(String element) throws ParseException {
//        String year = element.substring()
        element = element.trim();
        String year = element.substring(24);
        String month = element.substring(4,7);
        if (month.equals("Mar")){
            month = "0"+String.valueOf(3);
        }
        if (month.equals("Apr")){
            month = "0"+String.valueOf(4);
        }
        if (month.equals("May")){
            month = "0"+String.valueOf(5);
        }
        if (month.equals("Jun")){
            month = "0"+6;
        }
        if (month.equals("Jul")){
            month = "0"+7;
        }
        if (month.equals("Aug")){
            month = "0"+8;
        }
        if (month.equals("Sep")){
            month = "0"+9;
        }
        if (month.equals("Oct")){
            month = "10";
        }
        if (month.equals("Nov")){
            month = "11";
        }
        if (month.equals("Dec")){
            month = "12";
        }
        if (month.equals("Jan")){
            month = "01";
        }
        if (month.equals("Feb")){
            month = "02";
        }
        String day = element.substring(8,10);
        String time = element.substring(11,19);
        String dat = year+"-"+day+"-"+month+" "+time;

//        Thu Mar 10 18:11:38 MSK 2022
        return new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").parse(dat);
    }

    /**
     * This method parse Integer Oscars count from File
     * @param element - String value of Integer count Field
     * @return - Oscars Count value
     */

    public Integer parseOscarsCount(String element){
        return Integer.parseInt(element.trim());
    }

    /**
     * This method parse movie Length from File
     * @param element - String value of length Field
     * @return - Long movie's length
     */

    public Long parseLength(String element){
        return Long.parseLong(element.trim());
    }

    /**
     * This method parse Movie Genre from File
     * @param element - String value of MovieGenre Field
     * @return - MovieGenre value
     */

    public MovieGenre parseMovieGenre(String element){
        switch (element.toUpperCase(Locale.ROOT).trim()){
            case "ACTION":
                return MovieGenre.ACTION;
            case "HORROR":
                return MovieGenre.HORROR;
            case "DRAMA":
                return MovieGenre.DRAMA;
        }
        return MovieGenre.SCIENCE_FICTION;
    }
    /**
     * This method parse MpaaRating from File
     * @param element - String value of MpaaRating Field
     * @return - MpaaRating value
     */

    public MpaaRating parseMpaaRating(String element){
        switch (element.toUpperCase(Locale.ROOT).trim()){
            case "G":
                return MpaaRating.G;
            case "R":
                return MpaaRating.R;
            case "PG":
                return MpaaRating.PG;
        }
        return MpaaRating.NC_17;
    }

    /**
     * This method parse Country from File
     * @param element - String value of Country Field
     * @return - Country value
     */
    public Country parseCountry(String element){
        switch (element.toUpperCase(Locale.ROOT).trim()){
            case "INDIA":
                return Country.INDIA;
            case "JAPAN":
                return Country.JAPAN;
        }
        return Country.ITALY;
    }

    /**
     * This method parse Operator's weight
     * @param element - String value of weight
     * @return - Float value
     */

    public float parseWeight(String element){
        return Float.parseFloat(element.trim());
    }

    /**
     * This method parse Operator's birthday
     * @param element - String value of birthday
     * @return - ZonedDateTime value
     */

    public ZonedDateTime parseZND(String element){
        return ZonedDateTime.parse(element.trim());

    }



}
