package hu.inf.unideb.library.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

public class EntityManagement {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public void createEntityManager(String persistenceUnitName, Map<String,String> properties) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName, properties);
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void closeEntityManager() {
        this.entityManager.close();
        this.entityManagerFactory.close();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
