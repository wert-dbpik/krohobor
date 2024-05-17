package ru.wert.krohobor.database;

import retrofit2.Call;
import ru.wert.krohobor.database.connect.RetrofitClient;


import java.io.IOException;
import java.util.List;

public class PresetService implements IPresetService {

    private static PresetService instance;
    private PresetApiInterface api;

    private PresetService() {
        api = RetrofitClient.getInstance().getRetrofit().create(PresetApiInterface.class);
    }

    public PresetApiInterface getApi() {
        return api;
    }

    public static PresetService getInstance() {
        if (instance == null)
            return new PresetService();
        return instance;
    }

    @Override
    public Preset findById(Long id) {
        try {
            Call<Preset> call = api.getById(id);
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Preset findByName(String name) {
        try {
            Call<Preset> call = api.getByName(name);
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Preset findByValue(double value) {
        try {
            Call<Preset> call = api.getByValue(value);
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Preset> findAll() {
        try {
            Call<List<Preset>> call = api.getAll();
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Preset> findAllByText(String text) {
        try {
            Call<List<Preset>> call = api.getAllByText(text);
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Preset save(Preset entity) {
        try {
            Call<Preset> call = api.create(entity);
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Preset entity) {
        try {
            Call<Void> call = api.update(entity);
            return call.execute().isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Preset entity) {
        Long id = entity.getId();
        try {
            Call<Void> call = api.deleteById(id);
            return call.execute().isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
