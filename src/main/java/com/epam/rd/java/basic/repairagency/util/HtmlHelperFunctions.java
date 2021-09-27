package com.epam.rd.java.basic.repairagency.util;

import com.epam.rd.java.basic.repairagency.entity.RepairRequest;
import com.epam.rd.java.basic.repairagency.entity.RepairRequestStatus;
import com.epam.rd.java.basic.repairagency.entity.UserRole;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HtmlHelperFunctions {

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static boolean isRepairRequestStatusCanBeChangedByRole(RepairRequest repairRequest, String roleAsString) {
        UserRole role = UserRole.valueOf(UserRole.class, roleAsString);
        RepairRequestStatus status = repairRequest.getStatus();
        if (status == RepairRequestStatus.CANCELLED) {
            return false;
        }
        if (role == UserRole.CUSTOMER && status.getId() < RepairRequestStatus.CANCELLED.getId()) {
            return true;
        }
        if (role == UserRole.MANAGER) {
            if (status.getId() < RepairRequestStatus.CANCELLED.getId()) {
                return true;
            }
            if (status == RepairRequestStatus.PAID && repairRequest.getMaster() != null) {
                return true;
            }
        }
        return role == UserRole.MASTER && status.getId() > RepairRequestStatus.PAID.getId() &&
                status.getId() < RepairRequestStatus.COMPLETED.getId();
    }

    public static boolean isRepairRequestCostCanBeChangedByRole(RepairRequest repairRequest, String roleAsString) {
        if (roleAsString == null) {
            return false;
        }
        UserRole role = UserRole.valueOf(UserRole.class, roleAsString);
        RepairRequestStatus status = repairRequest.getStatus();
        return role == UserRole.MANAGER && status == RepairRequestStatus.CREATED;
    }


    public static boolean isRepairRequestMasterCanBeChangedByRole(RepairRequest repairRequest, String roleAsString) {
        if (roleAsString == null) {
            return false;
        }
        UserRole role = UserRole.valueOf(UserRole.class, roleAsString);
        RepairRequestStatus status = repairRequest.getStatus();
        return role == UserRole.MANAGER && status == RepairRequestStatus.PAID;
    }

    public static boolean isRepairRequestCanBeCanceled(RepairRequest repairRequest) {
        return repairRequest.getStatus().getId() < RepairRequestStatus.CANCELLED.getId();
    }

    public static boolean isRepairRequestCanBeEdited(RepairRequest repairRequest) {
        return repairRequest.getStatus() == RepairRequestStatus.CREATED;
    }

    public static boolean isRepairRequestCanBePaidByRole(RepairRequest repairRequest) {
        return repairRequest.getStatus() == RepairRequestStatus.WAIT_FOR_PAYMENT;
    }

}
