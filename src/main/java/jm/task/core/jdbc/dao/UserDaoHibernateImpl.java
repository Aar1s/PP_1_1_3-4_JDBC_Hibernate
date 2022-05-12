package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory;
    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(45),`last_name` VARCHAR(45),`age` INT, PRIMARY KEY (`id`))").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Successful table creation.");
        } catch (Exception e) {
            System.out.println("Table was not created.");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS  users").executeUpdate();
            session.getTransaction().commit();
            System.out.print("\nTable successfully dropped");
        } catch (Exception e) {
            System.out.print("\nTable drop failure.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User newUser = new User(name, lastName, age);
            session.save(newUser);
            session.getTransaction().commit();
            System.out.printf("\nUser %s successfully saved.",newUser.getName());
        } catch (Exception e) {
            System.out.print("\nUser saving failure.");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
            System.out.print("\nUser successfully removed.");
        } catch (Exception e) {
            System.out.print("\nRemoval error.");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User>  cq = cb.createQuery(User.class);
            session.beginTransaction();
            Root<User> root = cq.from(User.class);
            cq.select(root);
            Query query = session.createQuery(cq);
            userList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
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
        }
    }
}
