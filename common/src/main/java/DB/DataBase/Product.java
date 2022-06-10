package DB.DataBase;

import DB.DataBase.GetCollectionFromDB;
import entity.Country;
import entity.MovieGenre;
import entity.MpaaRating;
import entity.Person;
import javafx.beans.property.*;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Product {
    private Long movie_id;
    private String title;
    private Integer x;
    private Integer y;
    private String creation_date;
    private Integer oscars_count;
    private Long length;
    private String genre;
    private String rating;
    private String person_name;
    private String birthday;
    private Float weight;
    private String country;
    private String owner;


    public Product(long movie_id, String title, int x, int y, Date creation_date, int oscars_count, long l, MovieGenre genre, MpaaRating rating, String person_name, ZonedDateTime bithday, float weight, Country country, String owner){
        this.movie_id = movie_id;
        this.title = title;
        this.x = x;
        this.y = y;
        this.creation_date = new SimpleDateFormat("yyyy-MM-dd").format(creation_date);
        this.oscars_count = oscars_count;
        this.length = l;
        this.genre = genre.name();
        this.rating = rating.name();
        this.person_name = person_name;
        this.birthday = bithday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z"));
        this.weight = (weight);
        this.country = (country.name());
        this.owner = (owner);
    }

    public Long getmovie_id() {
        return movie_id;
    }

    public Long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public Integer getOscars_count() {
        return oscars_count;
    }

    public void setOscars_count(Integer oscars_count) {
        this.oscars_count = oscars_count;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setmovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getDate() {
        return creation_date;
    }

    public void setDate(String creation_date) {
        this.creation_date = creation_date;
    }

    public Integer getOscarsCount() {
        return oscars_count;
    }

    public void setOscarsCount(Integer oscars_count) {
        this.oscars_count = oscars_count;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getMovieGenre() {
        return genre;
    }

    public void setMovieGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPerson() {
        return person_name;
    }

    public void setPerson(String person_name) {
        this.person_name = person_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setWeigth(Float weight) {
        this.weight = weight;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void allInfo(){
        System.out.println( movie_id + " " + title + " " + x + " " + y + "\n"
        + creation_date + " " + oscars_count + " " + length + " " + genre + "\n" +
                rating + " " + person_name + " " + birthday + " " + weight + " \n"
        + country + " " + owner + "\n" + "\n");
    }



}
