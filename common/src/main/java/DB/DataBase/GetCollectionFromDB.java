package DB.DataBase;

import collection.LinkedCollection;
import entity.*;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class GetCollectionFromDB {
    private static final String url = "url";
    private static final String username = "username";
    private static final String password = "password";
    private static final String driver = "org.postgresql.Driver";
    private LinkedCollection collection = new LinkedCollection();
    private List<Movie> list = Collections.synchronizedList(new LinkedList<>());
    private List<Product> products = Collections.synchronizedList(new LinkedList<>());
    private Connection connection = DriverManager.getConnection(url, username, password);

    public GetCollectionFromDB() throws SQLException {

    }


    public LinkedCollection getCollection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(driver);
            PreparedStatement ps;
            ResultSet resultSet;
            String getOneRow = "select num, title, x, y, creation_date, oscars_count, length,  genre, rating, name, birthday, weight, nationality from \"Movies\" as M inner join \"Persons\" P on P.id = M.person order by num";
            ps = connection.prepareStatement(getOneRow);
            ps.execute();
            resultSet = ps.getResultSet();
            while (resultSet.next()) {
                getColl(resultSet);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return collection;
    }

    public List<Movie> getList() throws SQLException, ClassNotFoundException{
        Class.forName(driver);
        PreparedStatement ps;
        ResultSet resultSet;
        String getOneRow = "select num, title, x, y, creation_date, oscars_count, length,  genre, rating, name, birthday, weight, nationality from \"Movies\" as M inner join \"Persons\" P on P.id = M.person order by num";
        ps = connection.prepareStatement(getOneRow);
        ps.execute();
        resultSet = ps.getResultSet();
        while (resultSet.next()){
            getlist(resultSet);
        }
        return Collections.synchronizedList(list);
    }

    public List<Product> getDataBase(){
        try {
            Class.forName(driver);
            PreparedStatement ps;
            ResultSet resultSet;
            String getOneRow = "select num, title, x, y, creation_date, oscars_count, length,  genre, rating, name, birthday, weight, nationality, owner from \"Movies\" as M inner join \"Persons\" P on P.id = M.person order by num";
            ps = connection.prepareStatement(getOneRow);
            ps.execute();
            resultSet = ps.getResultSet();
            while (resultSet.next()) {
                getlist(resultSet);
                Product product = new Product(1, "1", 1, 1, new Date(), 1, 1L, MovieGenre.ACTION, MpaaRating.G, "1", ZonedDateTime.now(), 99F, Country.INDIA, "1");
                product.setMovie_id(resultSet.getLong(1));
                product.setTitle(resultSet.getString(2));
                product.setX(resultSet.getInt(3));
                product.setY(resultSet.getInt(4));
                product.setCreation_date(resultSet.getString(5));
                product.setOscars_count(resultSet.getInt(6));
                product.setLength(resultSet.getLong(7));
                if (resultSet.getInt(8) == 4){
                    product.setGenre(null);
                }
                else {
                    product.setGenre(MovieGenre.values()[resultSet.getInt(8)].name());
                }
                try {
                    product.setRating(MpaaRating.values()[resultSet.getInt(9)].name());
                }catch (ArrayIndexOutOfBoundsException e){
                    product.setRating(MpaaRating.values()[resultSet.getInt(9) - 1].name());
                }
                product.setPerson_name(resultSet.getString(10));
                if (fromTimestamp(resultSet, 11) == null){
                    product.setBirthday(null);
                }else {
                    product.setBirthday(fromTimestamp(resultSet, 11).toString());
                }
                product.setWeight(resultSet.getFloat(12));
                if (resultSet.getInt(13) == 3){
                    product.setCountry(null);
                }
                else {
                    product.setCountry(Country.values()[resultSet.getInt(13)].name());
                }
                product.setOwner(resultSet.getString(14));

                products.add(product);

                getColl(resultSet);
                getlist(resultSet);

            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("SQLExecption in GetCollectionFromDB");
        }
        catch (NullPointerException e){

        }
        catch (ClassNotFoundException e){

        }
        return products;
    }

    public ZonedDateTime fromTimestamp(ResultSet rs, int column) throws SQLException{
        Timestamp timestamp = rs.getTimestamp(column);
        return getDateTime(timestamp);
    }

    public ZonedDateTime getDateTime(Timestamp timestamp){
        return timestamp != null ? ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), ZoneOffset.UTC) : null;
    }

    public void getColl(ResultSet resultSet) throws SQLException {
        Movie movie = new Movie(1, "name", new Coordinates(1, 2), new Date(), 1, 12L, MovieGenre.ACTION, MpaaRating.G,
                new Person("1", ZonedDateTime.now(), 1.5F, Country.INDIA));
        Person person = new Person("Vasya", ZonedDateTime.now(), 0.1F, Country.INDIA);
        movie.setId(resultSet.getInt(1));
        movie.setName(resultSet.getString(2));
        movie.setCoordinates(resultSet.getInt(3), resultSet.getInt(4));
        movie.setCreationDate(resultSet.getTimestamp(5));
        movie.setOscarsCount(resultSet.getInt(6));
        movie.setLength(resultSet.getLong(7));
        movie.setGenre(MovieGenre.values()[resultSet.getInt(8)]);
        try {
            movie.setMpaaRating(MpaaRating.values()[resultSet.getInt(9)]);
        }catch (ArrayIndexOutOfBoundsException e ){
            movie.setMpaaRating(MpaaRating.values()[resultSet.getInt(9) - 1]);
        }
        person.setName(resultSet.getString(10));
        if (fromTimestamp(resultSet, 11) == null){
            person.setBirthday(Person.zonedDateTime);
        }else {
            person.setBirthday(fromTimestamp(resultSet, 11));
        }
        person.setWeight(resultSet.getFloat(12));
        person.setNationality(Country.values()[resultSet.getInt(13)]);
        movie.setOperator(person);
        collection.add(movie);
    }

    public void getlist(ResultSet resultSet) throws SQLException {
        Movie movie = new Movie(1, "name", new Coordinates(1,2), new Date(), 1, 12L, MovieGenre.ACTION, MpaaRating.G,
                new Person("1", ZonedDateTime.now(), 1.5F, Country.INDIA));
        Person person = new Person("Vasya", ZonedDateTime.now(), 0.1F, Country.INDIA);
        movie.setId(resultSet.getInt(1));
        movie.setName(resultSet.getString(2));
        movie.setCoordinates(resultSet.getInt(3), resultSet.getInt(4));
        movie.setCreationDate(resultSet.getTimestamp(5));
        movie.setOscarsCount(resultSet.getInt(6));
        movie.setLength(resultSet.getLong(7));
        movie.setGenre(MovieGenre.values()[resultSet.getInt(8)]);
        try {
            movie.setMpaaRating(MpaaRating.values()[resultSet.getInt(9)]);
        }catch (ArrayIndexOutOfBoundsException e){
            movie.setMpaaRating(MpaaRating.values()[resultSet.getInt(9) - 1]);
        }
        person.setName(resultSet.getString(10));
        person.setBirthday(fromTimestamp(resultSet, 11));
        person.setWeight(resultSet.getFloat(12));
        person.setNationality(Country.values()[resultSet.getInt(13)]);
        movie.setOperator(person);
        list.add(movie);
        collection.setList(list);
    }
}
