package com.example.firebasephoneverification.model;

public class College {
    private Long id;
    private Long division_id;
    private String division_name;
    private String name;
    private Long eiin_number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDivision_id() {
        return division_id;
    }

    public void setDivision_id(Long division_id) {
        this.division_id = division_id;
    }

    public String getDivision_name() {
        return division_name;
    }

    public void setDivision_name(String division_name) {
        this.division_name = division_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEiin_number() {
        return eiin_number;
    }

    public void setEiin_number(Long eiin_number) {
        this.eiin_number = eiin_number;
    }

    @Override
    public String toString() {
        return "College{" +
                "id=" + id +
                ", division_id=" + division_id +
                ", division_name='" + division_name + '\'' +
                ", name='" + name + '\'' +
                ", eiin_number=" + eiin_number +
                '}';
    }
}
