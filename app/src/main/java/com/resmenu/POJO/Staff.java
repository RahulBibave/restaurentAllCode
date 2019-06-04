package com.resmenu.POJO;

import java.io.Serializable;

public class Staff implements Serializable {
    String staff_name;
    int staff_id;

    public Staff(String staff_name, int staff_id) {
        this.staff_name = staff_name;
        this.staff_id = staff_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }
}
