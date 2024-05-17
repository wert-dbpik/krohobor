package ru.wert.krohobor.database;


import java.util.List;

public interface IPresetService {

    Preset findByName(String name);

    Preset findByValue(double value);

    Preset findById(Long id);

    Preset save(Preset preset);

    boolean update(Preset preset);

    boolean delete(Preset preset);

    List<Preset> findAll();

    List<Preset> findAllByText(String text);

}
