package com.epam.rd.java.basic.repairagency.service.impl;

import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;
import com.epam.rd.java.basic.repairagency.entity.User;
import com.epam.rd.java.basic.repairagency.entity.sorting.RepairRequestSortingParameter;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;
import com.epam.rd.java.basic.repairagency.factory.anotation.Inject;
import com.epam.rd.java.basic.repairagency.factory.anotation.Service;
import com.epam.rd.java.basic.repairagency.repository.GenericRepository;
import com.epam.rd.java.basic.repairagency.repository.RepairRequestRepository;
import com.epam.rd.java.basic.repairagency.service.RepairRequestService;
import com.epam.rd.java.basic.repairagency.util.db.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service(RepairRequestService.class)
public class RepairRequestServiceImpl extends AbstractService<RepairRequest> implements RepairRequestService {

    @Inject
    private RepairRequestRepository repository;

    private RepairRequestServiceImpl() {
    }

    @Override
    protected GenericRepository<RepairRequest> getRepository() {
        return repository;
    }

    @Override
    public void cancelRepairRequest(long repairRequestId) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            RepairRequest repairRequest = repository.findById(connection, repairRequestId);
            if (repairRequest.getStatus() == RepairRequestStatus.CANCELLED) {
                return;
            }
            if (!repairRequest.isCanBeCancelled()) {
                throw new DBException("Can't cancel repair request " + repairRequest +
                        " with status " + repairRequest.getStatus());
            }
            repository.setStatusForRepairRequest(connection, repairRequestId, RepairRequestStatus.CANCELLED);
        } catch (SQLException e) {
            throw new DBException("Can't cancel repair request with id '" + repairRequestId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public void setStatusToRepairRequest(long repairRequestId, RepairRequestStatus status) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            repository.setStatusForRepairRequest(connection, repairRequestId, status);
        } catch (SQLException e) {
            throw new DBException("Can't set status " + status +
                    " for repair request with id '" + repairRequestId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public void setCostToRepairRequest(long repairRequestId, double cost) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            repository.setCostToRepairRequest(connection, repairRequestId, cost);
        } catch (SQLException e) {
            throw new DBException("Can't set cost '" + cost +
                    "' to repair request with id '" + repairRequestId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public void setDescriptionToRepairRequest(long repairRequestId, String description) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            repository.setDescriptionToRepairRequest(connection, repairRequestId, description);
        } catch (SQLException e) {
            throw new DBException("Can't set description '" + description +
                    "' to repair request with id '" + repairRequestId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public void setMasterToRepairRequest(long repairRequestId, long masterId) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            repository.setMasterToRepairRequest(connection, repairRequestId, masterId);
        } catch (SQLException e) {
            throw new DBException("Can't set master with id '" + masterId + "' " +
                    "to repair request with id '" + repairRequestId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public void removeMasterFromRepairRequest(long repairRequestId) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            repository.removeMasterFromRepairRequest(connection, repairRequestId);
        } catch (SQLException e) {
            throw new DBException("Can't remove master from repair request with id '" + repairRequestId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<RepairRequest> findAllByCustomer(User customer) throws DBException, NotFoundException {
        return findAllByCustomerId(customer.getId());
    }

    @Override
    public List<RepairRequest> findAllByCustomerId(long customerId) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByCustomerId(connection, customerId);
        } catch (SQLException e) {
            throw new DBException("Can't find repair request with customerId '" + customerId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<RepairRequest> findAllByMasterId(long masterId) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByMasterId(connection, masterId);
        } catch (SQLException e) {
            throw new DBException("Can't find repair request with masterId '" + masterId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<RepairRequest> findAllByCustomerIdAndMasterIdAndStatusMoreThenPaid(long customerId, long masterId) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByCustomerIdAndMasterIdAndStatusMoreThenStatusId(connection, customerId, masterId, RepairRequestStatus.PAID.getId());
        } catch (SQLException e) {
            throw new DBException("Can't find repair request with customerId '" + customerId + "' " +
                    "and masterId '" + masterId + "' and status more then paid in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<RepairRequest> findAllByCustomerIdAndMasterIdAndStatusCompleted(long customerId, long masterId) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByCustomerIdAndMasterIdAndStatusMoreThenStatusId(connection, customerId, masterId, RepairRequestStatus.IN_WORK.getId());
        } catch (SQLException e) {
            throw new DBException("Can't find repair request with customerId '" + customerId + "' " +
                    "and masterId '" + masterId + "' and status completed in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public int findCountOfRepairRequests() throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findCountOfRepairRequests(connection);
        } catch (SQLException e) {
            throw new DBException("Can't find count of repair requests", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public int findCountOfRepairRequestsByCustomerId(long customerId) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findCountOfRepairRequestsByCustomerId(connection, customerId);
        } catch (SQLException e) {
            throw new DBException("Can't find count of repair requests with customerId '" + customerId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public int findCountOfRepairRequestsByMasterId(long masterId) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findCountOfRepairRequestsByMasterId(connection, masterId);
        } catch (SQLException e) {
            throw new DBException("Can't find count of repair requests with masterId '" + masterId + "'", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<RepairRequest> findAll(int offset, int amount, RepairRequestSortingParameter sortingParam,
                                       SortingType sortingType
    ) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAll(connection, offset, amount, sortingParam, sortingType);
        } catch (SQLException e) {
            throw new DBException("Can't find repair requests in DB", e);
        } finally {
            DBUtil.close(connection);
        }

    }

    @Override
    public List<RepairRequest> findAllByCustomerId(long customerId, int offset, int amount,
                                                   RepairRequestSortingParameter sortingParam,
                                                   SortingType sortingType
    ) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByCustomerId(connection, customerId, offset, amount, sortingParam, sortingType);
        } catch (SQLException e) {
            throw new DBException("Can't find repair requests with customerId '" + customerId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }

    @Override
    public List<RepairRequest> findAllByMasterId(long masterId, int offset, int amount,
                                                 RepairRequestSortingParameter sortingParam,
                                                 SortingType sortingType
    ) throws DBException, NotFoundException {
        Connection connection = null;
        try {
            connection = getConnection();
            return repository.findAllByMasterId(connection, masterId, offset, amount, sortingParam, sortingType);
        } catch (SQLException e) {
            throw new DBException("Can't find repair requests with masterId '" + masterId + "' in DB", e);
        } finally {
            DBUtil.close(connection);
        }
    }
}
