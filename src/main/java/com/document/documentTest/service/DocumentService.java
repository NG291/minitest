package com.document.documentTest.service;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import com.document.documentTest.model.Document;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class DocumentService implements DocumentServiceInterface {
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Document> findAll() {
        String query = "select d from Document as d";
        TypedQuery<Document> q = entityManager.createQuery(query, Document.class);
        return q.getResultList();
    }

    @Override
    public void save(Document document) {
        Transaction transaction = null;
        Document origin = null;

        System.out.println(document.getId());
        if (document.getId() != null) {
            origin = findById(document.getId()); // save update
        } else {
            origin = new Document(); // create new
        }

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            origin.setCode(document.getCode());
            origin.setName(document.getName());
            origin.setYear(document.getYear());
            origin.setDescription(document.getDescription());
            origin.setAuthor(document.getAuthor());

            session.saveOrUpdate(origin);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Document findById(long id) {
        String query = "select d from Document as d where d.id = :id";
        TypedQuery<Document> q = entityManager.createQuery(query, Document.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }

    @Override
    public void remove(long id) {
        Document document = findById(id);
        if (document != null) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.delete(document);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
}
