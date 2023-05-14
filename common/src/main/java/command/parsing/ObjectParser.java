package command.parsing;

import command.tasksCommands.ParseFromConsole;
import entity.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class ObjectParser {

    public Movie parseObject(BufferedReader in) throws IOException {
        ParseFromConsole process = new ParseFromConsole();

        Long id = process.randomID();

        String name = process.processString("Введите название фильма ", in);

        int x = process.processIntWithoutLimits("Введите Х координату (int) ", in);

        int y = process.processIntY("Введите Y координату (int) ", in);

        Date date = Calendar.getInstance().getTime();

        int oscarCount = process.processInt("Введите число оскаров (int) ", in);

        Long length = process.processLong("Введите длину (в минутах) ", in);

        Country country = process.processCountry("Введите страну ", in);

        MpaaRating mpaaRating = process.processRating("Введите рейтинг ", in);

        MovieGenre movieGenre = process.processGenre("Введите жанр ", in);

        String personName = process.processStringForName("Введите имя оператора ", in);

        Float personWeight = process.processFloat("Введите вес операвтора (float) ", in);

        ZonedDateTime zonedDateTime1 = process.processZND("Введите дату ", in);

        Coordinates coordinates = new Coordinates(x, y);

        Person operator = new Person(personName, zonedDateTime1, personWeight, country);

        return new Movie(id,
                        name,
                        coordinates,
                        date,
                        oscarCount,
                        length,
                        movieGenre,
                        mpaaRating,
                        operator);
    }

}
