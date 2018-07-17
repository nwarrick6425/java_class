package edu.nicholaidudakiwwarrick.advancedjava.services;

import edu.nicholaidudakiwwarrick.advancedjava.model.DatabasePerson;
import edu.nicholaidudakiwwarrick.advancedjava.model.DatabasePersonStock;
import edu.nicholaidudakiwwarrick.advancedjava.model.DatabaseStockSymbol;
import edu.nicholaidudakiwwarrick.advancedjava.util.DatabaseUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a concrete implementation of PersonService that reads data from a database.
 *
 * @author Nicholai Dudakiw-Warrick
 */
public class DatabasePersonService implements PersonService {

    public static final DatabasePersonService INSTANCE = new DatabasePersonService();

    // hides the constructor and delegates instance creation through ServiceFactory
    protected DatabasePersonService() {
    }

    /**
     * Gets a list of all persons stored in the "person" database.
     * Changes made by mutating objects in the returned list do not propagate to the database.
     * @return a list of Person instances
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final List<DatabasePerson> getPersons() throws PersonServiceException {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        List<DatabasePerson> persons = null;
        Transaction transaction = null;
        try {
            // assigns all instances of Person within session to Criteria
            // then converts Criteria to a list of the criteria type which is Person.class
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(DatabasePerson.class);

            // intentional suppression of warnings for unchecked exceptions thrown by criteria.list()
            @SuppressWarnings("unchecked") List<DatabasePerson> list = criteria.list();
            persons = list;
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new PersonServiceException("Could not get Person data. " + e.getMessage(), e);
        } finally {
            session.close();
        }
        return persons;
    }

    /**
     * Adds a new person or updates the data of an existing person
     * @param person a person object to either update or create
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final void addOrUpdatePerson(DatabasePerson person) throws PersonServiceException {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // updates instance of Person if already exists within table
            // or adds as last row of people table
            transaction = session.beginTransaction();
            session.saveOrUpdate(person);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new PersonServiceException("Could not add/update Person data. " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    /**
     * Gets a list of all of the stocks a person is interested in
     * @return a list of Strings representing stock symbols
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final List<DatabaseStockSymbol> getStockSymbols(DatabasePerson person) throws PersonServiceException {
        Session session =  DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        List<DatabaseStockSymbol> stockSymbols = null;
        try {
            // assigns from the session to Criteria all instances of PersonStock containing the person parameter value
            // then converts Criteria to a list of the criteria type which is PersonStock.class
            // and gets from each PersonStock instance the associated stock stockSymbol
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(DatabasePersonStock.class);
            criteria.add(Restrictions.eq("person", person));

            // intentional suppression of warnings for unchecked exceptions thrown by criteria.list()
            @SuppressWarnings("unchecked") List<DatabasePersonStock> list = criteria.list();
            stockSymbols = new ArrayList<DatabaseStockSymbol>();
            for (DatabasePersonStock personStock : list) {
                stockSymbols.add(personStock.getStockSymbol());
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new PersonServiceException("Could not get Stock Symbols for entered Person. " + e.getMessage(), e);
        } finally {
            session.close();
        }
        return stockSymbols;
    }

    /**
     * Assigns a stock to a person
     * @param stockSymbol  The stockSymbol to assign
     * @param person The person to whom the stockSymbol is assigned
     * @throws PersonServiceException if a service can not perform the requested operation
     */
    public final void addStockToPerson(DatabaseStockSymbol stockSymbol, DatabasePerson person) throws PersonServiceException {
        Session session =  DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            // updates instance of PersonStock if already exists within table
            // or adds as last row of personStock table
            transaction = session.beginTransaction();
            session.saveOrUpdate(stockSymbol);
            DatabasePersonStock personStock = new DatabasePersonStock();
            personStock.setStockSymbol(stockSymbol);
            personStock.setPerson(person);
            session.saveOrUpdate(stockSymbol);
            session.saveOrUpdate(personStock);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new PersonServiceException("Could not add/update Stock Symbols for entered Person. " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }
}