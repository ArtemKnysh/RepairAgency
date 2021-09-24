package com.epam.rd.java.basic.repairagency.repository;

import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RepairRequestRepository extends GenericRepository<RepairRequest> {

    void setStatusForRepairRequest(Connection connection, long repairRequestId, RepairRequestStatus status) throws SQLException;

    void setCostToRepairRequest(Connection connection, long repairRequestId, double cost) throws SQLException;

    void setDescriptionToRepairRequest(Connection connection, long repairRequestId, String description) throws SQLException;

    void setMasterToRepairRequest(Connection connection, long repairRequestId, long masterId) throws SQLException;

    List<RepairRequest> findAllByMasterId(Connection connection, long masterId) throws SQLException, NotFoundException;

    List<RepairRequest> findAllByCustomer(Connection connection, User customer) throws SQLException, NotFoundException;

    List<RepairRequest> findAllByCustomerId(Connection connection, long customerId) throws SQLException, NotFoundException;

    void removeMasterFromRepairRequest(Connection connection, long repairRequestId) throws SQLException;

    List<RepairRequest> findAllByCustomerIdAndMasterIdAndStatusMoreThenStatusId(Connection connection, long customerId, long masterId, long statusId) throws SQLException, NotFoundException;

}
