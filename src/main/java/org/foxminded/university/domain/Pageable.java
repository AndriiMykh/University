package org.foxminded.university.domain;

import java.util.List;
import java.util.Objects;

public class Pageable<T> {
    private final List<T> items;
    private final int pageNumber;
    private final int itemsNumberPerPage;

    public Pageable(List<T> items, int pageNumber, int itemsNumberPerPage) {
        this.items = items;
        this.pageNumber = pageNumber;
        this.itemsNumberPerPage = itemsNumberPerPage;
    }

    public List<T> getItems() {
        return items;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getItemsNumberPerPage() {
        return itemsNumberPerPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pageable<?> pageable = (Pageable<?>) o;
        return pageNumber == pageable.pageNumber &&
                itemsNumberPerPage == pageable.itemsNumberPerPage &&
                Objects.equals(items, pageable.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, pageNumber, itemsNumberPerPage);
    }
}
