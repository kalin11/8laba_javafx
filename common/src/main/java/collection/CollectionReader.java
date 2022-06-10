package collection;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.CsvToBeanFilter;
//import lab5.entity.*;
import entity.Country;
import entity.MovieDTO;
import entity.MovieGenre;
import entity.MpaaRating;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class CollectionReader {

    private final LinkedCollection collection;

    public CollectionReader(LinkedCollection c){
        this.collection = c;
    }

    public LinkedCollection read(String fileName) {
        LinkedCollection result = new LinkedCollection();
        try{
            List<MovieDTO> listik = new CsvToBeanBuilder(new FileReader(fileName))
                    .withType(MovieDTO.class)
                    .withFilter(new CsvToBeanFilter() {
                        
                        public boolean allowLine(String[] strings) {
                            ArrayList<MovieGenre> genres = new ArrayList<>();
                            genres.add(MovieGenre.ACTION);
                            genres.add(MovieGenre.DRAMA);
                            genres.add(MovieGenre.HORROR);
                            genres.add(MovieGenre.SCIENCE_FICTION);


                            ArrayList<MpaaRating> ratings = new ArrayList<>();
                            ratings.add(MpaaRating.R);
                            ratings.add(MpaaRating.G);
                            ratings.add(MpaaRating.PG);
                            ratings.add(MpaaRating.NC_17);


                            ArrayList<Country> countries = new ArrayList<>();
                            countries.add(Country.INDIA);
                            countries.add(Country.ITALY);
                            countries.add(Country.JAPAN);


                            ArrayList<String> list = new ArrayList<>();
                            for (String str : strings) {
                                list.add(str);
                            }
                            try {
                                MovieGenre genre = MovieGenre.valueOf(list.get(7));
                                MpaaRating rating = MpaaRating.valueOf(list.get(8));
                                Country country = Country.valueOf(list.get(12));
                                try {
                                    if ((!genres.contains(genre)) || (!ratings.contains(rating)) || (!countries.contains(country))) {
                                        return false;
                                    } else if ((genres.contains(genre)) && (ratings.contains(rating)) && (countries.contains(country))) {
                                        long id = Long.parseLong(list.get(0));
                                        int x = Integer.parseInt(list.get(2));
                                        int y = Integer.parseInt(list.get(3));
                                        Integer oscarsCount = Integer.parseInt(list.get(5));
                                        Long length = Long.parseLong(list.get(6));
                                        Float weigth = Float.parseFloat(list.get(11));
                                    }

                                } catch (NumberFormatException e) {
                                    System.out.println("не смогли запарсить " + list.get(1));
                                    return false;
                                }

                                if (list.size() == 13) {
                                    if (list.get(1).getClass().equals(String.class) &&
                                            list.get(4).getClass().equals(String.class) &&
                                            list.get(9).getClass().equals(String.class) &&
                                            list.get(10).getClass().equals(String.class) &&
                                            genres.contains(genre) && ratings.contains(rating) && countries.contains(country)) {
//                                        System.out.println("фильм с названием " + list.get(1) + " был успешно добавлен");


                                        return true;

                                    } else {
                                        System.out.println("в каком-то из полей фильма " + list.get(1) + " не тот тип данных");
                                        return false;
                                    }
                                } else {
                                    System.out.println("фильм с названием " + list.get(1) + " не был добавлен, тк в файле недостаточно информации");
                                    return false;
                                }
                            }catch (RuntimeException e){
                                System.out.println(list.get(1) + " не смогли запарсить");
                                return false;
                            }
                        }
                    })
                    .withIgnoreEmptyLine(true)
                    .build()
                    .parse();
            for (MovieDTO movieDTO : listik){
                result.add(movieDTO.toMovie());
            }
        }catch (FileNotFoundException e){
            System.out.println("файл не найден");
        }catch (ParseException e){
            System.out.print("у каких-то объектов неверный формат данных \n");
        }

        return result;
    }
}
