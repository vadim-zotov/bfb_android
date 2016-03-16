package com.sphereinc.chairlift.views.models;


import java.util.List;

public class ParentModel implements TreeModel {

    private String title;
    private List<TreeModel> childs;


    @Override
    public int getId() {
        return 0;
    }

    @Override
    public List<TreeModel> getChilds() {
        return childs;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChilds(List<TreeModel> childs) {
        this.childs = childs;
    }
}
