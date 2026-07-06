package com.pokemon.blog.dto.response;

import java.util.List;

/**
 * Response wrapper cho paginated data.
 */
public class PaginationResponse<T> {

    private List<T> content;          // Danh sách data thực tế
    private int currentPage;          // Trang hiện tại (0-indexed)
    private int pageSize;             // Số item trên 1 trang
    private long totalElements;       // Tổng số item trong DB
    private int totalPages;           // Tổng số trang
    private boolean hasNext;          // Có trang tiếp theo không
    private boolean hasPrevious;      // Có trang trước không

    public PaginationResponse() {}

    public PaginationResponse(List<T> content, int currentPage, int pageSize,
                              long totalElements, int totalPages) {
        this.content = content;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.hasNext = currentPage < totalPages - 1;
        this.hasPrevious = currentPage > 0;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }
}