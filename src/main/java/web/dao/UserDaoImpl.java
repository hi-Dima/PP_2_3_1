package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public User showUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(int id, User user) {
        User userToUpdate = entityManager.find(User.class, id);
        entityManager.detach(userToUpdate);
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        entityManager.merge(userToUpdate);
    }

    @Override
    public void deleteUser(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }


}
