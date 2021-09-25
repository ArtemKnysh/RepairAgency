package com.epam.rd.java.basic.repairagency.web.command.impl.base;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class GetListWithSortingAndPaginationAndFilteringCommand extends GetListWithSortingAndPaginationCommand {

    @Override
    protected void processRequest(HttpServletRequest request) throws Exception {
        String filterName = request.getParameter("filterName");
        String filterValue = request.getParameter("filterValue");
        System.out.println("filterName = " + filterName);
        System.out.println("filterValue = " + filterValue);
        super.processRequest(request);
        if (filterName != null && !filterName.trim().isEmpty() && filterValue != null && !filterValue.trim().isEmpty()) {
            request.setAttribute("filterName", filterName);
            request.setAttribute("filterValue", filterValue);
        }
    }

    @Override
    protected final int getCountOfEntities(HttpServletRequest request) throws DBException {
        String filterName = request.getParameter("filterName");
        String filterValue = request.getParameter("filterValue");
        if (filterName != null && !filterName.trim().isEmpty() && filterValue != null && !filterValue.trim().isEmpty()) {
            return getCountOfEntitiesWithFilter(request, filterName, filterValue);
        } else {
            return getCountOfEntitiesWithoutFilter(request);
        }
    }

    protected abstract int getCountOfEntitiesWithFilter(HttpServletRequest request, String filterName, String filterValue
    ) throws DBException;

    protected abstract int getCountOfEntitiesWithoutFilter(HttpServletRequest request) throws DBException;

    @Override
    protected final List<? extends AbstractEntity> getSortedEntities(HttpServletRequest request, int offset, int rowsNumber,
                                                               SortingType sortingType, String sortingParameter
    ) throws NotFoundException, DBException {
        String filterName = request.getParameter("filterName");
        String filterValue = request.getParameter("filterValue");
        if (filterName != null && !filterName.trim().isEmpty() && filterValue != null && !filterValue.trim().isEmpty()) {
            return getSortedEntitiesWithFilter(request, offset, rowsNumber, sortingType, sortingParameter, filterName, filterValue);
        } else {
            return getSortedEntitiesWithoutFilter(request, offset, rowsNumber, sortingType, sortingParameter);
        }
    }

    protected abstract List<? extends AbstractEntity> getSortedEntitiesWithFilter(HttpServletRequest request,
                                                                                  int offset, int rowsNumber,
                                                                                  SortingType sortingType,
                                                                                  String sortingParameter,
                                                                                  String filterName,
                                                                                  String filterValue
    ) throws NotFoundException, DBException;

    protected abstract List<? extends AbstractEntity> getSortedEntitiesWithoutFilter(HttpServletRequest request,
                                                                                     int offset, int rowsNumber,
                                                                                     SortingType sortingType,
                                                                                     String sortingParameter
    ) throws DBException, NotFoundException;
}
