package com.epam.rd.java.basic.repairagency.repository;

import com.epam.rd.java.basic.repairagency.entity.Feedback;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface FeedbackRepository extends GenericRepository<Feedback> {

    Feedback findByCustomerIdAndMasterId(Connection connection, long customerId, long masterId) throws SQLException, NotFoundException;

    List<Feedback> findAllByMasterIdExceptCustomerId(Connection connection, long masterId, long customerId) throws SQLException, NotFoundException;

    List<Feedback> findAllByCustomerId(Connection connection, long customerId) throws SQLException, NotFoundException;

    List<Feedback> findAllByCustomerIdIncludeHidden(Connection connection, long customerId) throws SQLException, NotFoundException;

    List<Feedback> findAllByMasterId(Connection connection, long masterId) throws SQLException, NotFoundException;

    List<Feedback> findAllByMasterIdIncludeHidden(Connection connection, long masterId) throws SQLException, NotFoundException;

    void hide(Connection connection, long feedbackId) throws SQLException;

    void show(Connection connection, long feedbackId) throws SQLException;
}
