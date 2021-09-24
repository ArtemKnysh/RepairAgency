package com.epam.rd.java.basic.repairagency.service.impl;

import com.epam.rd.java.basic.repairagency.entity.Feedback;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.factory.anotation.Inject;
import com.epam.rd.java.basic.repairagency.factory.anotation.Service;
import com.epam.rd.java.basic.repairagency.repository.FeedbackRepository;
import com.epam.rd.java.basic.repairagency.repository.GenericRepository;
import com.epam.rd.java.basic.repairagency.service.FeedbackService;
import com.epam.rd.java.basic.repairagency.util.db.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service(FeedbackService.class)
public class FeedbackServiceImpl extends AbstractService<Feedback> implements FeedbackService {

    @Inject
    private FeedbackRepository repository;

    private FeedbackServiceImpl() {
    }

    @Override
    protected GenericRepository<Feedback> getRepository() {
        return repository;
    }

    @Override
    public Feedback findByCustomerIdAndMasterId(long customerId, long masterId) throws NotFoundException, DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findByCustomerIdAndMasterId(connection, customerId, masterId);
        } catch (SQLException e) {
            throw new DBException("Can't find feedback with customerId '" + customerId + "' " +
                    "and masterId '" + masterId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<Feedback> findAllByMasterIdExceptCustomerId(long masterId, long customerId) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByMasterIdExceptCustomerId(connection, masterId, customerId);
        } catch (SQLException e) {
            throw new DBException("Can't find feedback with masterId '" + masterId + "' " +
                    "except customerId '" + customerId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<Feedback> findAllByCustomerId(long customerId) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByCustomerId(connection, customerId);
        } catch (SQLException e) {
            throw new DBException("Can't find feedbacks with customerId '" + customerId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<Feedback> findAllByCustomerIdIncludeHidden(long customerId) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByCustomerIdIncludeHidden(connection, customerId);
        } catch (SQLException e) {
            throw new DBException("Can't find feedbacks with customerId '" + customerId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<Feedback> findAllByMasterId(long masterId) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByMasterId(connection, masterId);
        } catch (SQLException e) {
            throw new DBException("Can't find feedbacks with masterId '" + masterId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<Feedback> findAllByMasterIdIncludeHidden(long masterId) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByMasterIdIncludeHidden(connection, masterId);
        } catch (SQLException e) {
            throw new DBException("Can't find feedbacks with masterId '" + masterId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public void hide(long feedbackId) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            repository.hide(connection, feedbackId);
        } catch (SQLException e) {
            throw new DBException("Can't hide feedback with id '" + feedbackId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public void show(long feedbackId) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            repository.show(connection, feedbackId);
        } catch (SQLException e) {
            throw new DBException("Can't show feedback with id '" + feedbackId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }
}
