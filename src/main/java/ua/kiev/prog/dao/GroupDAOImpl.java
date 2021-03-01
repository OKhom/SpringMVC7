package ua.kiev.prog.dao;

import org.springframework.stereotype.Repository;
import ua.kiev.prog.model.Group;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GroupDAOImpl implements GroupDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Group group) {
        entityManager.persist(group);
    }

    @Override
    public void delete(Group group) {
        entityManager.remove(group);
    }

    @Override
    public void delete(long[] ids) {
        Group g;
        for (long id : ids) {
            g = entityManager.getReference(Group.class, id);
            entityManager.remove(g);
        }
    }

    @Override
    public Group findOne(long id) {
        return entityManager.getReference(Group.class, id);
    }

    @Override
    public List<Group> list() {
        TypedQuery<Group> query = entityManager.createQuery("SELECT g FROM Group g", Group.class);
        return query.getResultList();
    }

    @Override
    public List<Group> list(String pattern) {
        TypedQuery<Group> query = entityManager.createQuery("SELECT g FROM Group g WHERE g.name LIKE :pattern", Group.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return query.getResultList();
    }

    @Override
    public List<Group> list(int start, int count) {
        TypedQuery<Group> query = entityManager.createQuery("SELECT g FROM Group g ORDER BY g.id ASC", Group.class);
        if (start >= 0) {
            query.setFirstResult(start);
            query.setMaxResults(count);
        }
        return query.getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(g) FROM Group g", Long.class);
        return query.getSingleResult();
    }
}
