package com.flickrapp.DAO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {
    @GET("services/feeds/photos_public.gne?tags=bag&format=json&nojsoncallback=1")
    Call<JSONResponse> getJSON();
}
