package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()){
            String query = "CREATE TABLE IF NOT EXISTS users(" +
                    "id BIGSERIAL primary key," +
                    "name varchar(255)," +
                    "lastname varchar(255)," +
                    "age int)";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()){
            String query = "DROP TABLE IF EXISTS Users";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()){
            tx = session.beginTransaction();
            session.persist(new User(name,lastName,age));
            tx.commit();
        }   catch (Exception ex){
            if(tx != null){tx.rollback();}
            ex.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()){
            tx = session.beginTransaction();
            User user= session.find(User.class, id);
            if(user != null){
                session.remove(user);
            }
            tx.commit();
        }   catch (Exception ex){
            if(tx != null){tx.rollback();}
            ex.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {

            List<User> users = session.createQuery("from User", User.class).getResultList();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()){
            String query = "Delete from users";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
