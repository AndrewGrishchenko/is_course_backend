package com.andrew.repository;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import com.andrew.model.Supply;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SupplyRepository {
    @Inject
    SessionFactory sessionFactory;

    public void save(Supply supply) {
        sessionFactory.getCurrentSession().persist(supply);
    }

    public List<Supply> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Supply", Supply.class).list();
    }

    public Optional<Supply> findById(Long id) {
        return sessionFactory.getCurrentSession().createQuery("from Supply s where s.id = :id", Supply.class)
            .setParameter("id", id)
            .uniqueResultOptional();
    }

    public Supply update(Supply supply) {
        return sessionFactory.getCurrentSession().merge(supply);
    }

    public void delete(Supply supply) {
        sessionFactory.getCurrentSession().remove(supply);
    }
}
