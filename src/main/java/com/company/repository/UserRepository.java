package com.company.repository;

import com.company.configs.HibernateUtils;
import com.company.domains.Users;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class UserRepository {

    public Optional<Users> getByUsername(String username) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query<Users> query = session
                .createQuery("select t from Users t where (t.username) = (:username) ",
                        Users.class);
        query.setParameter("username", username);
        Optional<Users> resultOrNull = Optional.ofNullable(query.getSingleResultOrNull());
        session.getTransaction().commit();
        session.close();
        return resultOrNull;
    }

    public Long save(Users users) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.persist(users);
        session.getTransaction().commit();
        session.close();
        return users.getId();
    }

    public List<Users> getAll() {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        if (!session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        List<Users> resultList = session.createQuery("from Users", Users.class)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return resultList;
    }
}
