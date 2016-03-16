package com.sphereinc.chairlift.views.models;


import java.util.List;

public interface TreeModel {
    int getId();
    List<TreeModel> getChilds();
}
