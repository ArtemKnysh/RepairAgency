package com.epam.rd.java.basic.repairagency.web.command.impl.base;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.entity.sorting.SortingType;
import com.epam.rd.java.basic.repairagency.exception.DBException;
import com.epam.rd.java.basic.repairagency.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class GetListWithSortingAndPaginationCommand extends GetCommand {

    @Override
    protected void processRequest(HttpServletRequest request) throws Exception {
        int currentPage = 1;
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }
        int recordsOnPage = 5;
        if (request.getParameter("recordsOnPage") != null) {
            recordsOnPage = Integer.parseInt(request.getParameter("recordsOnPage"));
        }

        int entitiesCount = getCountOfEntities(request);
        int numberOfPages = (int) Math.ceil(entitiesCount * 1.0 / recordsOnPage); //todo check if change type  of count
        if (currentPage > numberOfPages) {
            currentPage = numberOfPages;
        }

        String sortingParamAsString = request.getParameter("param");
        String activeSortingParamAsString = request.getParameter("activeParam");

        SortingType activeSortingType = SortingType.getSortingType(request.getParameter("activeType"));

        if (activeSortingParamAsString != null && sortingParamAsString != null) {
            if (sortingParamAsString.equals(activeSortingParamAsString)) {
                activeSortingType = SortingType.reverse(activeSortingType);
            } else {
                activeSortingParamAsString = sortingParamAsString;
                activeSortingType = SortingType.DESC;
            }
        } else if (activeSortingParamAsString == null && sortingParamAsString != null) {
            activeSortingParamAsString = sortingParamAsString;
        } else if (activeSortingParamAsString == null) {
            activeSortingParamAsString = getDefaultSortingParameter();
        }

        int offset = (currentPage - 1) * recordsOnPage;
        List<? extends AbstractEntity> sortedEntities = getSortedEntities(request, offset, recordsOnPage, activeSortingType, activeSortingParamAsString);

        request.setAttribute("entities", sortedEntities);
        request.setAttribute("recordsOnPage", recordsOnPage);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("entitiesCount", entitiesCount);

        request.setAttribute("activeType", activeSortingType.getType().toLowerCase());
        request.setAttribute("activeParam", activeSortingParamAsString);
    }

    protected abstract int getCountOfEntities(HttpServletRequest request) throws DBException;

    protected abstract String getDefaultSortingParameter();

    protected abstract List<? extends AbstractEntity> getSortedEntities(HttpServletRequest request, int offset, int rowsNumber,
                                                                        SortingType sortingType, String sortingParameter
    ) throws NotFoundException, DBException;

}
