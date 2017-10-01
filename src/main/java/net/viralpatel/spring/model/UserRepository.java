package net.viralpatel.spring.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Component
public class UserRepository implements UserDAO{
//    private List<User> All_users;

    //Автосвязывание бина
    @Autowired
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource){
        this.dataSource=dataSource;
    }

    public UserRepository(){}

    /**
     * Получает определенного юзера с БД с помощью id
     * @param id Идентификатор юзера в БД
     * @return User Возвращет User
     */
    public User getUserFromDb(int id){
        String sql = "select * from USER_TBL WHERE ID = ?";
        Connection conn=null;
        Boolean isOnline = false;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            User user = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt("STATUS")==1) isOnline=true;
                user = new User(
                        rs.getInt("ID"),
                        rs.getString("FIRSTNAME"),
                        rs.getString("LASTNAME"),
                        rs.getDate("BIRTHDATE").toLocalDate(),
                        isOnline
                );
            }
            rs.close();
            ps.close();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    /**
     * Проставляет статус юзера оффлайн или онлайн
     * @param userId Идентификатор юзера в БД
     * @param isOnline Онлайн или оффлайн
     */
    public void setUserStatus(int userId, boolean isOnline){
        String sql="UPDATE USER_TBL" +
                " SET STATUS = ?" +
                " WHERE ID = ?";
        Connection conn = null;
        int status = 0;
        if (isOnline) status=1;
        try{
            conn=dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,status);
            ps.setInt(2,userId);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }


    /**
     * Добавляет юзера
     * @param firstName Фамилия юзера
     * @param lastName Имя юзера
     * @param birthDate Дата рождения
     * @return User Возвращет User
     */
    public User insert(String firstName, String lastName, LocalDate birthDate){
        String sql = "INSERT INTO USER_TBL( FIRSTNAME, LASTNAME, BIRTHDATE)" +
                " VALUES( ? , ? , ?)";
        Connection conn = null;
        User user = null;
        try {
            System.out.println(dataSource);
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setDate(3, java.sql.Date.valueOf(birthDate));
            ps.executeUpdate();
            //int id = ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
//                    user.setId();
                    user = new User((int)generatedKeys.getLong(1), firstName, lastName, birthDate, false);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            ps.close();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    /**
     * Получение списка юзера
     * @param status Статус юзера-онлайн или оффлайн
     * @param id Идентификатор юзера
     * @return List<User> Возвращет список юзеров
     */
    public List<User> getUserList(boolean status, Integer id){
        List<User> userList = new ArrayList<>();
        int status_id = 0;
        if (status) status_id = 1;
        String sql = "SELECT * FROM USER_TBL" +
                " WHERE STATUS = ?" +
                " AND ID = IFNULL(?, ID)";
        Connection conn = null;
        try{
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,status_id);
            if (id == null) {
                ps.setNull(2, java.sql.Types.INTEGER);
            }else{
                ps.setInt(2, id);
            }
            ps.executeQuery();
            try (ResultSet rs = ps.getResultSet()){
                while(rs.next()){
                    Boolean isOnline = true;
                    if (rs.getInt("STATUS")==0) isOnline=false;
                    User user = new User(rs.getInt("ID"), rs.getString("FIRSTNAME"),
                            rs.getString("LASTNAME"), rs.getDate("BIRTHDATE").toLocalDate(), isOnline);
                    userList.add(user);
                }
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return userList;
    }



}
