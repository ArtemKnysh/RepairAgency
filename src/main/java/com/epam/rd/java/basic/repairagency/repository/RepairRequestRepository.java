package com.epam.rd.java.basic.repairagency.repository;

import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.filtering.RepairRequestFilterParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.RepairRequestSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RepairRequestRepository extends GenericRepository<RepairRequest> {

    void setStatusForRepairRequest(Connection connection, long repairRequestId, RepairRequestStatus status
    ) throws SQLException;

    void setCostToRepairRequest(Connection connection, long repairRequestId, double cost) throws SQLException;

    void setDescriptionToRepairRequest(Connection connection, long repairRequestId, String description
    ) throws SQLException;

    void setMasterToRepairRequest(Connection connection, long repairRequestId, long masterId) throws SQLException;

    List<RepairRequest> findAllByMasterId(Connection connection, long masterId) throws SQLException, NotFoundException;

    List<RepairRequest> findAllByCustomer(Connection connection, User customer) throws SQLException, NotFoundException;

    List<RepairRequest> findAllByCustomerId(Connection connection, long customerId
    ) throws SQLException, NotFoundException;

    void removeMasterFromRepairRequest(Connection connection, long repairRequestId) throws SQLException;

    List<RepairRequest> findAllByCustomerIdAndMasterIdAndStatusMoreThenStatusId(Connection connection, long customerId,
                                                                                long masterId, long statusId
    ) throws SQLException, NotFoundException;


    int findCountOfRepairRequests(Connection connection) throws SQLException;

    int findCountOfRepairRequestsByCustomerId(Connection connection, long customerId) throws SQLException;

    int findCountOfRepairRequestsByMasterId(Connection connection, long masterId) throws SQLException;

    List<RepairRequest> findAll(Connection connection, int offset, int amount,
                                RepairRequestSortingParameter sortingParam, SortingType sortingType
    ) throws SQLException, NotFoundException;

    List<RepairRequest> findAllByCustomerId(Connection connection, long customerId, int offset, int amount,
                                            RepairRequestSortingParameter sortingParam, SortingType sortingType
    ) throws SQLException, NotFoundException;

    List<RepairRequest> findAllByMasterId(Connection connection, long masterId, int offset, int amount,
                                          RepairRequestSortingParameter sortingParam, SortingType sortingType
    ) throws SQLException, NotFoundException;

    List<RepairRequest> findAll(Connection connection, int offset, int amount,
                                RepairRequestSortingParameter sortingParam, SortingType sortingType,
                                RepairRequestFilterParameter filterParam, String filterValue
    ) throws SQLException, NotFoundException;

    int findCountOfRepairRequests(Connection connection, RepairRequestFilterParameter filterParam, String filterValue
    ) throws SQLException;

    int findCountOfRepairRequests(Connection connection, long customerId, RepairRequestFilterParameter filterParam,
                                  String filterValue) throws SQLException;

    List<RepairRequest> findAllByCustomerId(Connection connection, long customerId, int offset, int amount,
                                            RepairRequestSortingParameter sortingParam, SortingType sortingType,
                                            RepairRequestFilterParameter filterParam, String filterValue
    ) throws SQLException, NotFoundException;
}
