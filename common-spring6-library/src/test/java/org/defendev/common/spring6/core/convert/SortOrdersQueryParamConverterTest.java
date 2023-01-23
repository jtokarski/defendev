package org.defendev.common.spring6.core.convert;

import org.defendev.common.domain.query.SortOrder;
import org.defendev.common.domain.query.SortDirection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;



public class SortOrdersQueryParamConverterTest {


    @DisplayName("Sort ordes specification - example 1")
    @Test
    public void sortOrdersSpecificationExample1() {
        final SortOrdersQueryParamConverter underTest = new SortOrdersQueryParamConverter();

        // given
        final String specificationString = "lastName:ASC,firstName:DESC,createdAt:ASC";

        // when
        final List<SortOrder> sortOrders = underTest.convert(specificationString);

        // then
        assertThat(sortOrders)
            .isNotEmpty()
            .hasSize(3)
            .containsExactly(
                new SortOrder(SortDirection.ASC, "lastName"),
                new SortOrder(SortDirection.DESC, "firstName"),
                new SortOrder(SortDirection.ASC, "createdAt")
            );
    }

    @DisplayName("Sort ordes specification - example 2")
    @Test
    public void sortOrdersSpecificationExample2() {
        final SortOrdersQueryParamConverter underTest = new SortOrdersQueryParamConverter();

        // given
        final String specificationString = "createdAt:ASC";

        // when
        final List<SortOrder> sortOrders = underTest.convert(specificationString);

        // then
        assertThat(sortOrders)
            .isNotEmpty()
            .hasSize(1)
            .containsExactly(
                new SortOrder(SortDirection.ASC, "createdAt")
            );
    }

    @DisplayName("Should return non-null empty List of null specification string")
    @Test
    public void emptyListForNullSpecification() {
        final SortOrdersQueryParamConverter underTest = new SortOrdersQueryParamConverter();

        // given
        final String specificationString = null;

        // when
        final List<SortOrder> sortOrders = underTest.convert(specificationString);

        // then
        assertThat(sortOrders)
            .isNotNull()
            .isEmpty();
    }

    @DisplayName("Should return non-null empty List of empty specification string")
    @Test
    public void emptyListForEmptySpecification() {
        final SortOrdersQueryParamConverter underTest = new SortOrdersQueryParamConverter();

        // given
        final String specificationString = "";

        // when
        final List<SortOrder> sortOrders = underTest.convert(specificationString);

        // then
        assertThat(sortOrders)
            .isNotNull()
            .isEmpty();
    }

}
