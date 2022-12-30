package org.defendev.common.fixture.loader;

import org.defendev.common.domain.HasId;
import org.defendev.common.fixture.loader.exception.DuplicateHardcodedIdException;
import org.defendev.common.fixture.loader.exception.MissingActualIdForHardcodedIdException;
import org.defendev.common.fixture.loader.exception.MissingHardcodedIdException;
import org.defendev.common.fixture.loader.exception.UnableToLoadByActualIdException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;



public abstract class FixturesLoader<E extends HasId<ID>, ID extends Serializable> {

    private final Class<E> entityClazz;

    protected SessionFactory sessionFactory;

    protected Unmarshaller unmarshaller;

    protected Map<ID, ID> hardcodedToActualIds = new HashMap<>();

    public FixturesLoader(SessionFactory sessionFactory, Class<E> entityClazz) throws JAXBException {
        this.entityClazz = entityClazz;
        this.sessionFactory = sessionFactory;
        unmarshaller = setUpUnmarshaller();
    }

    protected InputStream getResourceInputStream(String resourceName) {
        final ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();
        return classLoader.getResourceAsStream(resourceName);
    }

    public abstract Unmarshaller setUpUnmarshaller() throws JAXBException;

    public abstract void readFromResourceAndPersist(String resourceName) throws JAXBException;

    public ID save(E newEntity) {
        final ID hardcodedId = newEntity.getId();
        if (isNull(hardcodedId)) {
            throw new MissingHardcodedIdException(entityClazz);
        }
        newEntity.setId(null);
        /*
         * How did I know this is the correct way of obtaining Session inside @Transactional method?
         * It's recommended by Javadoc of HibernateTransactionManager.
         * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/orm/hibernate5/HibernateTransactionManager.html
         *
         */
        final Session session = sessionFactory.getCurrentSession();
        final ID actualId = (ID) session.save(newEntity);
        if (hardcodedToActualIds.containsKey(hardcodedId)) {
            throw new DuplicateHardcodedIdException(entityClazz, hardcodedId);
        }
        hardcodedToActualIds.put(hardcodedId, actualId);
        return actualId;
    }

    // for other Fixtures loaders to use:
    public E loadByHardcodedId(ID hardcodedId) {
        final ID actualId = hardcodedToActualIds.get(hardcodedId);
        if (isNull(actualId)) {
            throw new MissingActualIdForHardcodedIdException(entityClazz, hardcodedId);
        }
        final Session session = sessionFactory.getCurrentSession();
        final E loadedEntity = session.load(entityClazz, actualId);
        if (isNull(loadedEntity)) {
            throw new UnableToLoadByActualIdException(entityClazz, actualId);
        }
        return loadedEntity;
    }

    public List<E> loadByHardcodedId(List<ID> hardcodedIds) {
        // TODO:
        return List.of();
    }

//
//  Todo: Is this method still needed?
//
//    public Integer getActualId(Integer hardcodedId) {
//        return hardcodedToActualIds.get(hardcodedId);
//    }



}
