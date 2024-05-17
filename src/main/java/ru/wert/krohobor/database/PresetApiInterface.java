package ru.wert.krohobor.database;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PresetApiInterface {

    @GET("presets/id/{id}")
    Call<Preset> getById(@Path("id") Long id);

    @GET("presets/name/{name}")
    Call<Preset> getByName(@Path("name") String name);

    @GET("presets/value/{val}")
    Call<Preset> getByValue(@Path("val") double value);

    @GET("presets/all")
    Call<List<Preset>> getAll();

    @GET("presets/all-by-text/{text}")
    Call<List<Preset>> getAllByText(@Path("text") String text);

    @POST("presets/create")
    Call<Preset> create(@Body Preset entity);

    @PUT("presets/update")
    Call<Void> update(@Body Preset entity);

    @DELETE("presets/delete/{id}")
    Call<Void> deleteById(@Path("id") Long id);

}
