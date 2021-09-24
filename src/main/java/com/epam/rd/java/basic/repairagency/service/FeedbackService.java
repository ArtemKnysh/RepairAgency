package com.epam.rd.java.basic.repairagency.service;

import com.epam.rd.java.basic.repairagency.entity.Feedback;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.util.List;

public interface FeedbackService extends GenericService<Feedback> {

    Feedback findByCustomerIdAndMasterId(long customerId, long masterId) throws NotFoundException, DBException;

    List<Feedback> findAllByMasterIdExceptCustomerId(long masterId, long customerId) throws DBException, NotFoundException;

    List<Feedback> findAllByCustomerIdIncludeHidden(long customerId) throws DBException, NotFoundException;

    List<Feedback> findAllByCustomerId(long customerId) throws DBException, NotFoundException;

    List<Feedback> findAllByMasterId(long masterId) throws DBException, NotFoundException;

    List<Feedback> findAllByMasterIdIncludeHidden(long masterId) throws DBException, NotFoundException;

    void hide(long feedbackId) throws DBException;

    void show(long feedbackId) throws DBException;
}
