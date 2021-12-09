package com.project.fyp.models;

public class SearchList {
    int id;
    String searchedItem;

    public SearchList() {
    }

    public SearchList(int id, String searchedItem) {
        this.id = id;
        this.searchedItem = searchedItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchedItem() {
        return searchedItem;
    }

    public void setSearchedItem(String searchedItem) {
        this.searchedItem = searchedItem;
    }
}
