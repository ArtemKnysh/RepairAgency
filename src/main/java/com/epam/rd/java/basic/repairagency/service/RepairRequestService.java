package com.epam.rd.java.basic.repairagency.service;

import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;
import com.epam.rd.java.basic.repairagency.entity.User;
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

    List<RepairRequest> findAllByMasterId(long masterId) throws DBException, NotFoundException;

    void removeMasterFromRepairRequest(long repairRequestId) throws DBException;

    List<RepairRequest> findAllByCustomerIdAndMasterIdAndStatusMoreThenPaid(long customerId, long masterId) throws DBException, NotFoundException;

    List<RepairRequest> findAllByCustomerIdAndMasterIdAndStatusCompleted(long customerId, long masterId) throws DBException, NotFoundException;
}
