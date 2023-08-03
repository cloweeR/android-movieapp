package com.pah.movieapp.api;


import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse<T> {
    @SerializedName("Search")
    private List<T> searchResults;

    public List<T> getSearchResults() {
        return searchResults;
    }
}
