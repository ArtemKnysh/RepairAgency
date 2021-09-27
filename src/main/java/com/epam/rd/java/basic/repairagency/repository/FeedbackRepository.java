package com.epam.rd.java.basic.repairagency.repository;

import com.epam.rd.java.basic.repairagency.entity.Feedback;
import com.epam.rd.java.basic.repairagency.entity.filtering.FeedbackFilterParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.FeedbackSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface FeedbackRepository extends GenericRepository<Feedback> {

    Feedback findByCustomerIdAndMasterIdExcludeHidden(Connection connection, long customerId, long masterId
    ) throws SQLException, NotFoundException;

    List<Feedback> findAllByMasterIdExceptCustomerIdExcludeHidden(Connection connection, long masterId, long customerId
    ) throws SQLException, NotFoundException;

    List<Feedback> findAllByCustomerIdExcludeHidden(Connection connection, long customerId) throws SQLException, NotFoundException;

    List<Feedback> findAllByCustomerId(Connection connection, long customerId
    ) throws SQLException, NotFoundException;

    List<Feedback> findAllByMasterIdExcludeHidden(Connection connection, long masterId) throws SQLException, NotFoundException;

    List<Feedback> findAllByMasterId(Connection connection, long masterId
    ) throws SQLException, NotFoundException;

    void hide(Connection connection, long feedbackId) throws SQLException;

    void show(Connection connection, long feedbackId) throws SQLException;

    int findCountOfFeedbacks(Connection connection) throws SQLException;

    int findCountOfFeedbacksByCustomerIdExcludeHidden(Connection connection, long customerId) throws SQLException;

    int findCountOfFeedbacksByMasterIdExcludeHidden(Connection connection, long masterId) throws SQLException;

    List<Feedback> findAllByCustomerIdExcludeHidden(Connection connection, long customerId, int offset, int amount,
                                                    FeedbackSortingParameter sortingParam, SortingType sortingType
    ) throws SQLException, NotFoundException;

    List<Feedback> findAllByMasterIdExcludeHidden(Connection connection, long masterId, int offset, int amount,
                                                  FeedbackSortingParameter sortingParam, SortingType sortingType
    ) throws SQLException, NotFoundException;

    List<Feedback> findAll(Connection connection, int offset, int amount, FeedbackSortingParameter sortingParameter,
                           SortingType sortingType) throws SQLException, NotFoundException;

    int findCountOfFeedbacks(Connection connection, FeedbackFilterParameter filterParam, String filterValue
    ) throws SQLException;

    List<Feedback> findAll(Connection connection, int offset, int amount, FeedbackSortingParameter sortingParam,
                           SortingType sortingType, FeedbackFilterParameter filterParam, String filterValue
    ) throws SQLException, NotFoundException;

    Feedback findByCustomerIdAndMasterIdIncludeHidden(Connection connection, long customerId, long masterId) throws SQLException, NotFoundException;
}
