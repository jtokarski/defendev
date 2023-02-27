package org.defendev.common.domain.query;

import org.defendev.common.domain.query.sort.SortOrder;

import java.util.List;



public interface QuerySort {

    List<SortOrder> getSortOrders();

}
