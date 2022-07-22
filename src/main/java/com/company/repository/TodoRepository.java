package com.company.repository;

import com.company.configs.HibernateUtils;
import com.company.domains.Todo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class TodoRepository {

    public List<Todo> getAll() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        if (!session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        Query<Todo> resultList = session.createQuery("select t from Todo t", Todo.class);
        List<Todo> list = resultList.getResultList();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public List<Todo> getMyTodos() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        if (!session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        Long in_id = com.company.domains.Session.sessionUser.getId();
        Query<Todo> resultList = session.createQuery("select t from Todo t where t.user.id = :in_id", Todo.class);
        resultList.setParameter("in_id", in_id);
        List<Todo> list = resultList.getResultList();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public List<Todo> getUncompletedTodos() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        if (!session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        Long in_id = com.company.domains.Session.sessionUser.getId();
        Query<Todo> resultList = session.createQuery("select t from Todo t where t.user.id = :in_id and t.completed = false", Todo.class);
        resultList.setParameter("in_id", in_id);
        List<Todo> list = resultList.getResultList();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public Long save(Todo todo) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(todo);
        session.getTransaction().commit();
        session.close();
        return todo.getId();
    }

    public Optional<Todo> getById(String id) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query<Todo> query = session.createQuery("select t from Todo t where t.id = :id", Todo.class);
        query.setParameter("id", id);
        Optional<Todo> resultOrNull = Optional.ofNullable(query.getSingleResultOrNull());
        session.getTransaction().commit();
        session.close();
        return resultOrNull;
    }

    public void update(String id, String newDesc) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("update Todo set description = :newDesc where id = :id");
        query.setParameter("newDesc", newDesc);
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void delete(String id) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from Todo where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void doTodo(String id) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("update Todo set completed = true where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
