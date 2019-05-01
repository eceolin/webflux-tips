package com.reactor.controller.dto;

public class BookDTO {

    private String id;
    private String name;
    private Integer pages;

    public BookDTO() {
    }

    public BookDTO(String id, String name, Integer pages) {
        this.id = id;
        this.name = name;
        this.pages = pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
