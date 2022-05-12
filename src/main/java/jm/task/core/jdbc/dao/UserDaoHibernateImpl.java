package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory;
    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        try  {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(`id` INT NOT NULL AUTO_INCREMENT," +
                    "`name` VARCHAR(45),`last_name` VARCHAR(45)," +
                    "`age` INT, PRIMARY KEY (`id`))")
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Successful table creation.");
        } catch (Exception e) {
            System.out.println("Table was not created.");
            e.printStackTrace();
            if (session != null) {
                session.beginTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        try  {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS  users").executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.print("\nTable successfully dropped");
        } catch (Exception e) {
            System.out.print("\nTable drop failure.");
            if (session != null) {
                session.beginTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        try  {
            session.beginTransaction();
            User newUser = new User(name, lastName, age);
            session.save(newUser);
            session.getTransaction().commit();
            System.out.printf("\nUser %s successfully saved.",newUser.getName());
        } catch (Exception e) {
            System.out.print("\nUser saving failure.");
            e.printStackTrace();
            if (session != null) {
                session.beginTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        try  {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
            System.out.print("\nUser successfully removed.");
        } catch (Exception e) {
            System.out.print("\nRemoval error.");
            e.printStackTrace();
            if (session != null) {
                session.beginTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        Session session = sessionFactory.openSession();
        try  {
            Query query = session.createQuery("from User");
            userList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.beginTransaction().rollback();
            }
        } finally {
            session.close();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            List<User> userList = getAllUsers();
            session.beginTransaction();
            for (User user : userList) {
                session.delete(user);
                System.out.printf("\nDeleting %s", user.getName());
            }
            session.getTransaction().commit();
            System.out.println("\nTable successfully truncated");
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.beginTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
