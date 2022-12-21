package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
// DAO класс. С помощью entityManager можем выполянть операции с БД
@Repository
public class UserDaoImpl  implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void saveUser(User user) { entityManager.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User userInfo(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(int id) {
      Query query = entityManager.createQuery("delete from User user where user.id = :id");
      query.setParameter("id", id);
      query.executeUpdate();
    }
}
