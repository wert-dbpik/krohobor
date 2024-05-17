package ru.wert.krohobor.database;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
public class Preset {

    private Long id;
    private String name;
    private int cutting = 20;
    private int bending = 20;
    private int welding = 20;
    private int locksmith = 20;
    private int mechanic = 20;


}
