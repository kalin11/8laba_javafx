package entity;

import command.tasksCommands.ParseFromFile;

import java.io.Serializable;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

public class Movie implements Comparable<Movie>, Serializable {

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer oscarsCount; //Значение поля должно быть больше 0, Поле может быть null
    private Long length; //Поле не может быть null, Значение поля должно быть больше 0
    private MovieGenre genre; //Поле может быть null
    private MpaaRating mpaaRating; //Поле не может быть null
    private Person operator; //Поле не может быть null

    public Movie(long id,
                 String name,
                 Coordinates coordinates,
                 java.util.Date creationDate,
                 Integer oscarsCount,
                 Long length,
                 MovieGenre genre,
                 MpaaRating mpaaRating,
                 Person operator) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.length = length;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.operator = operator;
    }

    public Movie(long id,
                       String name,
                       int x,
                       int y,
                       Date creationDate,
                       Integer oscarsCount,
                       Long length,
                       MovieGenre genre,
                       MpaaRating rating,
                       Person person){
        this.id = id;
        this.name = name;
        this.coordinates = new Coordinates(x, y);
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.length = length;
        this.genre = genre;
        this.mpaaRating = rating;
        this.operator = person;
    }

    public long getId(){
        return id;
    }

    public String getMovieName(){
        return name;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public Date getCreationDate(){
        return creationDate;
    }

    public Integer getOscarsCount(){
        return oscarsCount;
    }

    public Long getLength(){
        return length;
    }

    public MovieGenre getGenre(){
        return genre;
    }

    public MpaaRating getMpaaRating(){
        return mpaaRating;
    }

    public Person getOperator(){
        return operator;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setOscarsCount(Integer oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public void setOperator(Person operator) {
        this.operator = operator;
    }

    //    public String toString(){
//        return getId() + ", " +
//                getMovieName() + ", " +
//                getCoordinates() + ", " +
//                getCreationDate() + ", " +
//                getOscarsCount() + ", " +
//                getLength()+ ", " +
//                getGenre() + ", " +
//                getMpaaRating()+ ", " +
//                getOperator();
//    }
    public String toString(){
        return getMovieName();
    }
    public String allInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append(getId()).append(",")
                    .append(getMovieName()).append(",")
                    .append(getCoordinates()).append(",")
                    .append(getCreationDate()).append(",")
                    .append(getOscarsCount()).append(",")
                    .append(getLength()).append(",")
                    .append(getGenre()).append(",")
                    .append(getMpaaRating()).append(",")
                    .append(getOperator());
        sb.append("\n");
        return sb.toString();
    }

//    public Movie getDefault(){
//        return new Movie(1, "name", new Coordinates(1,2), new Date(), 1, 12L, MovieGenre.ACTION, MpaaRating.G,
//                new Person("1", ZonedDateTime.now(), 1.5F, Country.INDIA));
//    }



        public static Movie Default = new Movie(
            1,
            "Name",
            new Coordinates(1, 1),
            new Date(),
            1,
            12L,
            MovieGenre.ACTION,
            MpaaRating.G,
            new Person("Oleg", ZonedDateTime.now(), 1.5F, Country.INDIA)

    );

    public void setCoordinates(int parseX, int parseY) {
        this.coordinates = new Coordinates(parseX, parseY);
    }

    
    public int compareTo(Movie o) {
        return this.getOscarsCount() - o.getOscarsCount();
    }

    public Movie getMoveFromString(String f){
        String[] fields = f.split(",");
        if (fields.length != 13) {
            System.out.print("");
        }
        else {
            ParseFromFile parse = new ParseFromFile();
            Person person = new Person("Jora", ZonedDateTime.now(), 98.9F, Country.INDIA);
            Movie movie = new Movie(1, "Vasya", new Coordinates(1,-1000000000), new Date(), 4, 99L, MovieGenre.ACTION, MpaaRating.R, person);
            try {
                movie.setId(parse.parseID(fields[0]));
                movie.setName(parse.parseString(fields[1]));
                if (parse.parseY(fields[3]) > 884) {
                    System.out.print("");
                } else {
                    movie.setCoordinates(parse.parseX(fields[2]), parse.parseY(fields[3]));
                }
                movie.setCreationDate(parse.parseDate(fields[4]));
                movie.setOscarsCount(parse.parseOscarsCount(fields[5]));
                movie.setLength(parse.parseLength(fields[6]));
                movie.setGenre(MovieGenre.valueOf(fields[7]));
                movie.setMpaaRating(MpaaRating.valueOf(fields[8]));
                Person p = new Person(parse.parseString(fields[9]),
                        parse.parseZND(fields[10]),
                        parse.parseWeight(fields[11]),
                        Country.valueOf(fields[12]));
                movie.setOperator(p);
                return movie;
            } catch (ParseException e) {
                System.out.println("не удалось спарсить");
            }

        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id && name.equals(movie.name) && coordinates.equals(movie.coordinates) && creationDate.equals(movie.creationDate) && oscarsCount.equals(movie.oscarsCount) && length.equals(movie.length) && genre == movie.genre && mpaaRating == movie.mpaaRating && operator.equals(movie.operator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, oscarsCount, length, genre, mpaaRating, operator);
    }
}

