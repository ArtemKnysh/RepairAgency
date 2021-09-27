package com.epam.rd.java.basic.repairagency.service.impl;

import com.epam.rd.java.basic.repairagency.entity.Feedback;
import com.epam.rd.java.basic.repairagency.entity.filtering.FeedbackFilterParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.FeedbackSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
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
    public Feedback findByCustomerIdAndMasterIdExcludeHidden(long customerId, long masterId) throws NotFoundException, DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findByCustomerIdAndMasterIdExcludeHidden(connection, customerId, masterId);
        } catch (SQLException e) {
            throw new DBException("Can't find feedback with customerId '" + customerId + "' " +
                    "and masterId '" + masterId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<Feedback> findAll(int offset, int amount, FeedbackSortingParameter sortingParameter, SortingType sortingType) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAll(connection, offset, amount, sortingParameter, sortingType);
        } catch (SQLException e) {
            throw new DBException("Can't find feedbacks in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<Feedback> findAllByMasterIdExceptCustomerIdExcludeHidden(long masterId, long customerId) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByMasterIdExceptCustomerIdExcludeHidden(connection, masterId, customerId);
        } catch (SQLException e) {
            throw new DBException("Can't find feedback with masterId '" + masterId + "' " +
                    "except customerId '" + customerId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<Feedback> findAllByCustomerIdExcludeHidden(long customerId) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByCustomerIdExcludeHidden(connection, customerId);
        } catch (SQLException e) {
            throw new DBException("Can't find feedbacks with customerId '" + customerId + "' in DB", e);
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
    public List<Feedback> findAllByMasterIdExcludeHidden(long masterId) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByMasterIdExcludeHidden(connection, masterId);
        } catch (SQLException e) {
            throw new DBException("Can't find feedbacks with masterId '" + masterId + "' in DB", e);
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

    @Override
    public int findCountOfFeedbacks() throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findCountOfFeedbacks(connection);
        } catch (SQLException e) {
            throw new DBException("Can't find count of feedbacks", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public int findCountOfFeedbacksByCustomerIdExcludeHidden(long customerId) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findCountOfFeedbacksByCustomerIdExcludeHidden(connection, customerId);
        } catch (SQLException e) {
            throw new DBException("Can't find count of feedbacks by customerId '" + customerId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public int findCountOfFeedbacksByMasterIdExcludeHidden(long masterId) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findCountOfFeedbacksByMasterIdExcludeHidden(connection, masterId);
        } catch (SQLException e) {
            throw new DBException("Can't find count of feedbacks by masterId '" + masterId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<Feedback> findAllByCustomerIdExcludeHidden(long customerId, int offset, int amount,
                                                           FeedbackSortingParameter sortingParam, SortingType sortingType
    ) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByCustomerIdExcludeHidden(connection, customerId, offset, amount, sortingParam, sortingType);
        } catch (SQLException e) {
            throw new DBException("Can't find feedbacks with customerId '" + customerId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<Feedback> findAllByMasterIdExcludeHidden(long masterId, int offset, int amount,
                                                         FeedbackSortingParameter sortingParam, SortingType sortingType) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByMasterIdExcludeHidden(connection, masterId, offset, amount, sortingParam, sortingType);
        } catch (SQLException e) {
            throw new DBException("Can't find feedbacks with masterId '" + masterId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public int findCountOfFeedbacks(FeedbackFilterParameter filterParam, String filterValue) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findCountOfFeedbacks(connection, filterParam, filterValue);
        } catch (SQLException e) {
            throw new DBException("Can't find count of feedbacks", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<Feedback> findAll(int offset, int amount, FeedbackSortingParameter sortingParam, SortingType sortingType, FeedbackFilterParameter filterParam, String filterValue) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAll(connection, offset, amount, sortingParam, sortingType, filterParam, filterValue);
        } catch (SQLException e) {
            throw new DBException("Can't find feedbacks in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }
}
