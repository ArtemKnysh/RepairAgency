package com.epam.rd.java.basic.repairagency.service;

import com.epam.rd.java.basic.repairagency.entity.Feedback;
import com.epam.rd.java.basic.repairagency.entity.filtering.FeedbackFilterParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.FeedbackSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.util.List;

public interface FeedbackService extends GenericService<Feedback> {

    Feedback findByCustomerIdAndMasterIdExcludeHidden(long customerId, long masterId) throws NotFoundException, DBException;

    List<Feedback> findAllByMasterIdExceptCustomerIdExcludeHidden(long masterId, long customerId) throws DBException, NotFoundException;

    List<Feedback> findAll(int offset, int amount, FeedbackSortingParameter sortingParameter, SortingType sortingType
    ) throws DBException, NotFoundException;

    List<Feedback> findAllByMasterId(long masterId) throws DBException, NotFoundException;

    List<Feedback> findAllByMasterIdExcludeHidden(long masterId, int offset, int amount,
                                                  FeedbackSortingParameter sortingParameter, SortingType sortingType
    ) throws DBException, NotFoundException;

    List<Feedback> findAllByCustomerId(long customerId) throws DBException, NotFoundException;

    List<Feedback> findAllByCustomerIdExcludeHidden(long customerId) throws DBException, NotFoundException;

    List<Feedback> findAllByMasterIdExcludeHidden(long masterId) throws DBException, NotFoundException;

    List<Feedback> findAllByCustomerIdExcludeHidden(long customerId, int offset, int amount,
                                                    FeedbackSortingParameter sortingParameter, SortingType sortingType
    ) throws DBException, NotFoundException;

    void hide(long feedbackId) throws DBException;

    void show(long feedbackId) throws DBException;

    int findCountOfFeedbacks() throws DBException;

    int findCountOfFeedbacksByCustomerIdExcludeHidden(long customerId) throws DBException;

    int findCountOfFeedbacksByMasterIdExcludeHidden(long masterId) throws DBException;

    int findCountOfFeedbacks(FeedbackFilterParameter filterParam, String filterValue) throws DBException;

    List<Feedback> findAll(int offset, int amount, FeedbackSortingParameter sortingParam, SortingType sortingType, FeedbackFilterParameter filterParam, String filterValue) throws DBException, NotFoundException;
}
