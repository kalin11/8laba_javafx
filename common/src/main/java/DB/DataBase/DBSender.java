package DB.DataBase;

import cmd.Cmd;
import entity.Movie;
import entity.Person;


import java.sql.*;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedList;

public class DBSender {
    public Object send(Connection connection, Cmd cmd) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement("");
            ResultSet resultSet = null;
            GetCollectionFromDB get = new GetCollectionFromDB();
            String getID;
            String sql;
            String delPer;
            int id = -1;
            switch (cmd.getName()) {
                case "clear":
                    if (checkExesting(connection, cmd)){
                        getID = "select person from \"Movies\" where owner = ?";
                        ps = connection.prepareStatement(getID);
                        ps.setString(1, cmd.getLogin());
                        ps.execute();
                        resultSet = ps.getResultSet();
                        while (resultSet.next()) {
                            id = resultSet.getInt(1);
                            System.out.println(id);
                            sql = "delete from \"Movies\" where owner = ?";
                            ps = connection.prepareStatement(sql);
                            ps.setString(1, cmd.getLogin());
                            ps.execute();
                            delPer = "delete from \"Persons\" where id = ?";
                            ps = connection.prepareStatement(delPer);
                            ps.setInt(1, id);
                            ps.execute();
                        }
                        return get.getList();
                    }
                    else return "нет объектов, принадлежащих вам";
                case "remove_by_id":
                    if (checkForRemoveById(connection, cmd)) {
                        getID = "select person from \"Movies\" where owner = ?";
                        ps = connection.prepareStatement(getID);
                        ps.setString(1, cmd.getLogin());
                        ps.execute();
                        resultSet = ps.getResultSet();
                        if (resultSet.next()) {
                            long x = resultSet.getLong(1);
                            System.out.println("я пидор"+x);
                            sql = "delete from \"Movies\" where owner = ? and num = ?";
                            ps = connection.prepareStatement(sql);
                            ps.setString(1, cmd.getLogin());
                            ps.setLong(2, Long.parseLong(cmd.getArguments()));
                            ps.execute();
                            delPer = "delete from \"Persons\" where id = ?";
                            ps = connection.prepareStatement(delPer);
                            ps.setLong(1, x);
                            ps.execute();
                        }
                        return get.getList();
                    }
                    else {
                        return "нет объектов с таким id, принадлежащим вам";
                    }
                case "add":
                    String add = "insert into \"Persons\" (name, birthday, weight, nationality) VALUES (?, ?, ?, ?) returning id";
                    ps = connection.prepareStatement(add);
                    ps.setString(1,cmd.getMovie().getOperator().getPersonsName());
                    int hour = (int) (Math.random() * 24);
                    int minute = (int) (Math.random() * 60);
                    int seconds = (int) (Math.random() * 60);
                    if (String.valueOf(cmd.getMovie().getOperator().getBirthday()).equals(String.valueOf(Person.zonedDateTime))){
                        ps.setTimestamp(2, null);
                    }
                    else {
                        ps.setTimestamp(2, Timestamp.valueOf(cmd.getMovie().getOperator().getBirthday().toLocalDate().atTime(hour, minute, seconds)));
                    }
                    ps.setFloat(3, cmd.getMovie().getOperator().getWeight());
                    int xx = cmd.getMovie().getOperator().getNationality().ordinal();
                    if (xx <= 0){
                        ps.setInt(4, 0);
                    }
                    else {
                        ps.setInt(4, cmd.getMovie().getOperator().getNationality().ordinal());
                    }

                    ps.execute();
                    resultSet = ps.getResultSet();
                    if (resultSet.next()){
                        int person = resultSet.getInt(1);
                        sql = "insert into \"Movies\" (title, x, y, creation_date, oscars_count, length, genre, rating, person, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        ps = connection.prepareStatement(sql);
                        ps.setString(1, cmd.getMovie().getMovieName());
                        ps.setInt(2, cmd.getMovie().getCoordinates().getX());
                        ps.setInt(3, cmd.getMovie().getCoordinates().getY());
                        ps.setDate(4, new java.sql.Date(cmd.getMovie().getCreationDate().getTime()));
                        ps.setInt(5, cmd.getMovie().getOscarsCount());
                        ps.setLong(6, cmd.getMovie().getLength());
                        int yy, zz;
                        yy = cmd.getMovie().getGenre().ordinal();
                        zz = cmd.getMovie().getMpaaRating().ordinal();
                        if (yy <= 0){
                            ps.setInt(7, 0);
                        }
                        else {
                            ps.setInt(7, cmd.getMovie().getGenre().ordinal());
                        }
                        if (zz <= 0){
                            ps.setInt(8, 0);
                        }
                        else {
                            ps.setInt(8, cmd.getMovie().getGenre().ordinal());
                        }

                        ps.setInt(9, person);
                        ps.setString(10, cmd.getLogin());
                        ps.execute();
                    }
                    return get.getList();
                case "remove_lower":
                    if (checkExesting(connection, cmd)){
                        getID = "select person from \"Movies\" where owner = ?";
                        ps = connection.prepareStatement(getID);
                        ps.setString(1, cmd.getLogin());
                        ps.execute();
                        resultSet = ps.getResultSet();
                        while (resultSet.next()) {
                            try {
                                id = resultSet.getInt(1);
                                long x = resultSet.getLong(1);
                                sql = "delete from \"Movies\" where owner = ? and oscars_count < ?";
                                ps = connection.prepareStatement(sql);
                                ps.setString(1, cmd.getLogin());
                                ps.setInt(2, cmd.getMovie().getOscarsCount());
                                ps.execute();
                                delPer = "delete from \"Persons\" where id = ?";
                                ps = connection.prepareStatement(delPer);
                                ps.setLong(1, x);
                                ps.execute();
                            }catch (SQLException e){
                                return "нет объектов, удовлетворяющих условию";
                            }
                        }
                        return get.getList();
                    }
                    else return "нет объектов, принадлежащих вам";

                case "remove_greater":
                    if (checkExesting(connection, cmd)){
                        getID = "select person from \"Movies\" where owner = ?";
                        ps = connection.prepareStatement(getID);
                        ps.setString(1, cmd.getLogin());
                        ps.execute();
                        resultSet = ps.getResultSet();
                        while (resultSet.next()) {
                            try {
                                id = resultSet.getInt(1);
                                long x = resultSet.getLong(1);
                                sql = "delete from \"Movies\" where owner = ? and oscars_count > ?";
                                ps = connection.prepareStatement(sql);
                                ps.setString(1, cmd.getLogin());
                                ps.setInt(2, cmd.getMovie().getOscarsCount());
                                ps.execute();
                                delPer = "delete from \"Persons\" where id = ?";
                                ps = connection.prepareStatement(delPer);
                                ps.setLong(1, x);
                                ps.execute();
                            }catch (SQLException e){
                                return "нет объектов, удовлетворяющих условию";
                            }
                        }
                        return get.getList();
                    }
                    else return "нет объектов, принадлежащих вам";
                case "update":
                    if (checkExesting(connection, cmd)){
                        getID = "select person from \"Movies\" where owner = ?";
                        ps = connection.prepareStatement(getID);
                        ps.setString(1, cmd.getLogin());
                        ps.execute();
                        resultSet = ps.getResultSet();
                        if (resultSet.next()){
                            long x = resultSet.getLong(1);
                            System.out.println(x);
                            sql = "select count(*) from \"Movies\" where num = ? and owner = ?";
                            ps = connection.prepareStatement(sql);
                            ps.setLong(1,cmd.getMovie().getId());
                            ps.setString(2, cmd.getLogin());
                            ps.execute();
                            ResultSet reslt = ps.getResultSet();
                            int count = 0;
                            while (reslt.next()){
                                count = reslt.getInt(1);
                            }
                            if (count > 0) {
                                sql = "update \"Movies\" set title = ?, x = ?, y = ?, creation_date = ?, oscars_count = ?, length = ?, genre = ?, rating = ? where num = ? and owner = ? returning person";
                                ps = connection.prepareStatement(sql);
                                int x1, y1, z1;
                                ps.setString(1, cmd.getMovie().getMovieName());
                                ps.setInt(2, cmd.getMovie().getCoordinates().getX());
                                ps.setInt(3, cmd.getMovie().getCoordinates().getY());
                                ps.setDate(4, new java.sql.Date(cmd.getMovie().getCreationDate().getTime()));
                                ps.setInt(5, cmd.getMovie().getOscarsCount());
                                ps.setLong(6, cmd.getMovie().getLength());
                                x1 = cmd.getMovie().getGenre().ordinal();
                                if (x1 <= 0){
                                    ps.setInt(7, 0);
                                }
                                else {
                                    ps.setInt(7, cmd.getMovie().getGenre().ordinal());
                                }
                                y1 = cmd.getMovie().getMpaaRating().ordinal();
                                if (y1 <= 0){
                                    ps.setInt(8, 0);
                                }
                                else {
                                    ps.setInt(8, cmd.getMovie().getMpaaRating().ordinal());
                                }
                                ps.setLong(9, Long.parseLong(cmd.getArguments()));
                                ps.setString(10, cmd.getLogin());
                                ps.execute();
                                resultSet = ps.getResultSet();
                                long bum = 0;
                                if (resultSet.next()){
                                    bum = resultSet.getLong(1);
                                }
                                System.out.println(bum);
                                String person = "update \"Persons\" set name = ?, birthday = ?, weight = ?, nationality = ? where id = ?";
                                ps = connection.prepareStatement(person);
                                ps.setString(1, cmd.getMovie().getOperator().getPersonsName());
                                if (String.valueOf(cmd.getMovie().getOperator().getBirthday()).equals(String.valueOf(Person.zonedDateTime))){
                                    ps.setTimestamp(2, null);
                                }
                                else {
                                    System.out.println("dbsender - " + cmd.getMovie().getOperator().getBirthday().getHour());
                                    ZonedDateTime zonedDateTime = cmd.getMovie().getOperator().getBirthday();
                                    int hh = zonedDateTime.getHour();
                                    int mm = zonedDateTime.getMinute();
                                    int ss = zonedDateTime.getSecond();
                                    if (hh == 21) {
                                        hh = 0;
                                    } else if (hh == 22) {
                                        hh = 1;
                                    } else if (hh == 23) {
                                        hh = 2;
                                    } else {
                                        hh += 3;
                                    }
                                    ps.setTimestamp(2, Timestamp.valueOf(cmd.getMovie().getOperator().getBirthday().toLocalDate().atTime(hh, mm, ss)));
                                }
                                ps.setFloat(3, cmd.getMovie().getOperator().getWeight());
                                z1 = cmd.getMovie().getOperator().getNationality().ordinal();
                                if (z1 <= 0){
                                    ps.setInt(4, 0);
                                }
                                else {
                                    ps.setInt(4, cmd.getMovie().getOperator().getNationality().ordinal());
                                }
                                ps.setLong(5, bum);
                                ps.execute();
                                return get.getList();
                            }
                            else{
                                return "коллекция не была обновлена";
                            }

                        }
                        else return "нет объектов, принадлежащих вам";
                    }


            }
        }catch (ClassNotFoundException e){
            System.out.println("ClassNotFound");
        }
        return new LinkedList<Movie>();
    }

    public boolean checkExesting(Connection connection, Cmd cmd){
        try{
            int c = -1;
            String check = "select count(*) from \"Movies\" where owner = ?";
            PreparedStatement ps = connection.prepareStatement(check);
            ps.setString(1, cmd.getLogin());
            ps.execute();
            ResultSet resultSet = ps.getResultSet();
            if (resultSet.next()){
                c = resultSet.getInt(1);
            }
            return (c>0);
        }catch (SQLException e){
            return false;
        }
    }

    public boolean checkForRemoveById(Connection connection, Cmd cmd){
        try{
            int c = -1;
            String check = "select count(*) from \"Movies\" where owner = ? and num = ?";
            PreparedStatement ps = connection.prepareStatement(check);
            ps.setString(1, cmd.getLogin());
            ps.setLong(2, Long.parseLong(cmd.getArguments()));
            ps.execute();
            ResultSet resultSet = ps.getResultSet();
            if (resultSet.next()){
                c = resultSet.getInt(1);
            }
            return c > 0;
        }catch (SQLException e){
            return false;
        }

    }

}
