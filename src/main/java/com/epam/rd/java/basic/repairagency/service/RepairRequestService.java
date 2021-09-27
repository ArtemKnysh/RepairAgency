package com.epam.rd.java.basic.repairagency.service;

import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.filtering.RepairRequestFilterParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.RepairRequestSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.util.List;

public interface RepairRequestService extends GenericService<RepairRequest> {

    List<RepairRequest> findAllByCustomer(User customer) throws DBException, NotFoundException;

    List<RepairRequest> findAllByCustomerId(long customerId) throws DBException, NotFoundException;

    void setStatusToRepairRequest(long repairRequestId, RepairRequestStatus status) throws DBException;

    void cancelRepairRequest(long repairRequestId) throws DBException, NotFoundException;

    void setCostToRepairRequest(long repairRequestId, double cost) throws DBException;

    void setDescriptionToRepairRequest(long repairRequestId, String description) throws DBException;

    void setMasterToRepairRequest(long repairRequestId, long masterId) throws DBException;

    List<RepairRequest> findAllByMasterIdAndStatusMoreThenPaid(long masterId) throws DBException, NotFoundException;

    void removeMasterFromRepairRequest(long repairRequestId) throws DBException;

    List<RepairRequest> findAllByCustomerIdAndMasterIdAndStatusMoreThenPaid(long customerId, long masterId
    ) throws DBException, NotFoundException;

    List<RepairRequest> findAllByCustomerIdAndMasterIdAndStatusCompleted(long customerId, long masterId) throws DBException, NotFoundException;

    int findCountOfRepairRequests() throws DBException;

    int findCountOfRepairRequestsByCustomerId(long customerId) throws DBException;

    int findCountOfRepairRequestsByMasterIdAndStatusMoreThenPaid(long masterId) throws DBException;

    List<RepairRequest> findAll(int offset, int amount,
                                RepairRequestSortingParameter sortingParam, SortingType sortingType
    ) throws DBException, NotFoundException;

    List<RepairRequest> findAllByCustomerId(long customerId, int offset, int amount,
                                            RepairRequestSortingParameter sortingParameter, SortingType sortingType
    ) throws DBException, NotFoundException;

    List<RepairRequest> findAllByMasterIdAndStatusMoreThenPaid(long masterId, int offset, int amount,
                                                               RepairRequestSortingParameter sortingParam, SortingType sortingType
    ) throws DBException, NotFoundException;

    List<RepairRequest> findAll(int offset, int amount, RepairRequestSortingParameter sortingParam,
                                SortingType sortingType, RepairRequestFilterParameter filterParam, String filterValue
    ) throws DBException, NotFoundException;

    int findCountOfRepairRequests(RepairRequestFilterParameter filterParam, String filterValue) throws DBException;

    int findCountOfRepairRequestsByCustomerId(long customerId, RepairRequestFilterParameter filterParam, String filterValue) throws DBException;

    List<RepairRequest> findAllByCustomerId(long customerId, int offset, int amount, RepairRequestSortingParameter sortingParam, SortingType sortingType, RepairRequestFilterParameter filterParam, String filterValue) throws DBException, NotFoundException;
}
