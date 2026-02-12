package com.andrew.repository;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import com.andrew.model.Requirement;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RequirementRepository {
    @Inject
    SessionFactory sessionFactory;

    public void save(Requirement requirement) {
        sessionFactory.getCurrentSession().persist(requirement);
    }

    public List<Requirement> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from Requirement", Requirement.class).list();
    }

    public Optional<Requirement> findById(Long id) {
        return sessionFactory.getCurrentSession().createQuery("from Requirement r where r.id = :id", Requirement.class)
            .setParameter("id", id)
            .uniqueResultOptional();
    }

    public Requirement update(Requirement requirement) {
        return sessionFactory.getCurrentSession().merge(requirement);
    }

    public void delete(Requirement requirement) {
        sessionFactory.getCurrentSession().remove(requirement);
    }
}
