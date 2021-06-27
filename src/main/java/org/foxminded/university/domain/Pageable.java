package org.foxminded.university.domain;

import java.util.List;

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
}
