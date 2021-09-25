package com.epam.rd.java.basic.repairagency.service;

import com.epam.rd.java.basic.repairagency.entity.Feedback;
import com.epam.rd.java.basic.repairagency.entity.sorting.FeedbackSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.util.List;

public interface FeedbackService extends GenericService<Feedback> {

    Feedback findByCustomerIdAndMasterId(long customerId, long masterId) throws NotFoundException, DBException;

    List<Feedback> findAllByMasterIdExceptCustomerId(long masterId, long customerId) throws DBException, NotFoundException;

    List<Feedback> findAll(int offset, int amount, FeedbackSortingParameter sortingParameter, SortingType sortingType
    ) throws DBException, NotFoundException;

    List<Feedback> findAllByMasterIdIncludeHidden(long masterId) throws DBException, NotFoundException;

    List<Feedback> findAllByMasterId(long masterId, int offset, int amount,
                                     FeedbackSortingParameter sortingParameter, SortingType sortingType
    ) throws DBException, NotFoundException;

    List<Feedback> findAllByCustomerIdIncludeHidden(long customerId) throws DBException, NotFoundException;

    List<Feedback> findAllByCustomerId(long customerId) throws DBException, NotFoundException;

    List<Feedback> findAllByMasterId(long masterId) throws DBException, NotFoundException;

    List<Feedback> findAllByMasterIdIncludeHidden(long masterId) throws DBException, NotFoundException;

    void hide(long feedbackId) throws DBException;

    void show(long feedbackId) throws DBException;

    int findCountOfFeedbacks() throws DBException;

    int findCountOfFeedbacksByCustomerId(long customerId) throws DBException;

    int findCountOfFeedbacksByMasterId(long masterId) throws DBException;
}
