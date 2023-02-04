package org.defendev.common.fixture.loader;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.defendev.common.domain.HasId;
import org.defendev.common.fixture.loader.exception.DuplicateHardcodedIdException;
import org.defendev.common.fixture.loader.exception.MissingActualIdForHardcodedIdException;
import org.defendev.common.fixture.loader.exception.MissingHardcodedIdException;
import org.defendev.common.fixture.loader.exception.UnableToLoadByActualIdException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;



public abstract class FixturesLoader<E extends HasId<ID>, ID extends Serializable> {

    private final Class<E> entityClazz;

    protected SessionFactory sessionFactory;

    protected Unmarshaller unmarshaller;

    protected Map<ID, ID> hardcodedToActualIds = new HashMap<>();

    protected final String resourcePath;

    public FixturesLoader(String resourcePath, SessionFactory sessionFactory, Class<E> entityClazz)
        throws JAXBException
    {
        this.entityClazz = entityClazz;
        this.sessionFactory = sessionFactory;
        this.resourcePath = resourcePath;
        unmarshaller = setUpUnmarshaller();
    }

    protected InputStream getResourceInputStream(String resourcePath) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream(resourcePath);
    }

    public abstract Unmarshaller setUpUnmarshaller() throws JAXBException;

    public abstract void readFromResourceAndPersist() throws JAXBException;

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

    // To be used by other Fixture-loaders (for related entities resolution):
    public E loadOneByHardcodedId(ID hardcodedId) {
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

    // To be used by other Fixture-loaders (for related entities resolution):
    public Set<E> loadSetByHardcodedIds(Set<E> entitiesWithHardcodedIds) {
        if (isNull(entitiesWithHardcodedIds) || entitiesWithHardcodedIds.isEmpty()) {
            return Set.of();
        }
        return entitiesWithHardcodedIds.stream().map(
            (E entity) -> {
                final ID hardcodedId = entity.getId();
                final E entityLoaded = loadOneByHardcodedId(hardcodedId);
                return entityLoaded;
            }
        ).collect(Collectors.toSet());
    }

}
