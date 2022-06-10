package entity;

import com.opencsv.bean.CsvBindByPosition;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

public class MovieDTO implements Serializable {
    @CsvBindByPosition(position = 0)
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @CsvBindByPosition(position = 1)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @CsvBindByPosition(position = 2)
    private int x;
    @CsvBindByPosition(position = 3)
    private int y; //Максимальное значение поля: 884
    @CsvBindByPosition(position = 4)
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @CsvBindByPosition(position = 5)
    private Integer oscarsCount; //Значение поля должно быть больше 0, Поле может быть null
    @CsvBindByPosition(position = 6)
    private Long length; //Поле не может быть null, Значение поля должно быть больше 0
    @CsvBindByPosition(position = 7)
    private MovieGenre genre; //Поле может быть null
    @CsvBindByPosition(position = 8)
    private MpaaRating mpaaRating; //Поле не может быть null
    @CsvBindByPosition(position = 9)
    private String personName; //Поле не может быть null, Строка не может быть пустой
    @CsvBindByPosition(position = 10)
    private String birthday; //Поле может быть null
    @CsvBindByPosition(position = 11)
    private float weight; //Значение поля должно быть больше 0
    @CsvBindByPosition(position = 12)
    private Country nationality; //Поле может быть null

    public String getName() {
        return name;
    }

    public Movie toMovie() throws ParseException, NullPointerException {
        String d1 = this.creationDate;
//        System.out.println(d1);
//        Fri Mar 11 17:40:27 MSK 2022
        String year = d1.substring(24);
        String month = d1.substring(4,7);
        if (month.equals("Mar")){
            month = "0"+3;
        }
        if (month.equals("Apr")){
            month = "0"+4;
        }
        String day = d1.substring(8,10);
        String time = d1.substring(11,19);
        String dat = year+"-"+day+"-"+month+" "+time;
        Date date = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").parse(dat);
//        System.out.println(date);
        ZonedDateTime ZDT = ZonedDateTime.parse(this.birthday);

        return new Movie(this.id,
                this.name,
                new Coordinates(this.x, this.y),
                date,
                this.oscarsCount,
                this.length,
                this.genre,
                this.mpaaRating,
                new Person(this.personName,ZDT,this.weight,this.nationality));
    }

    public String toString(){
        return name;
    }
}
