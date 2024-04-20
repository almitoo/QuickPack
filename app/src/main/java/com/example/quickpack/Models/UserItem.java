package com.example.quickpack.Models;

public class UserItem {
    private String name;
    private String category;
    private boolean checked;
    private boolean system;


    public UserItem() {

    }

    public UserItem(String name, String category, boolean checked, boolean system) {
        this.name = name;
        this.category = category;
        this.checked = checked;
        this.system=system;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isSystem() {
        return system;
    }
}


