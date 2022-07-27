package peaksoft.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            Session session = Util.getSession().openSession();
            session.beginTransaction();
            session.createSQLQuery("create table users " +
                    "( id bigint not null primary key," +
                    "age smallint, " +
                    "last_name varchar(255)," +
                    "name varchar(255))").executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("Table created");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            System.out.println("!!!!!");
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = Util.getSession().openSession();
            session.beginTransaction();
            session.createSQLQuery("drop table users").executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("droped table");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            System.out.println("!!!!");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
       try {
           User user = new User(name, lastName, age);
           Session session = Util.getSession().openSession();
           session.beginTransaction();
           session.save(user);
           session.getTransaction().commit();
           session.close();
           System.out.println("Saved");
       }catch (HibernateException e){
           System.out.println(e.getMessage());
           System.out.println("!!!!");
       }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = Util.getSession().openSession();
            session.beginTransaction();
            User user = (User) session.get(User.class,id);
            session.delete(user);
            session.getTransaction().commit();
            session.close();
            System.out.println("deleted by ID");
        }catch (HibernateException e){
            System.out.println(e.getMessage());
            System.out.println("!!!!");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            Session session = Util.getSession().openSession();
            session.beginTransaction();
            List<User> userList = session.createQuery("from User").list();
            session.getTransaction().commit();
            session.close();
            System.out.println("finded " + userList.size() + " users.........");
            return userList;
        }catch (HibernateException e ) {
            System.out.println(e.getMessage());
        }
            return null;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = Util.getSession().openSession();
            session.beginTransaction();
            Query query = session.createQuery("delete from User");
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("The table is empty!");
        }catch (HibernateException e){
            System.out.println(e.getMessage());
            System.out.println("!!!!");
        }
    }
}
