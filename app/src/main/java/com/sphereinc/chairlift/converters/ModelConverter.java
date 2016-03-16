package com.sphereinc.chairlift.converters;


import com.sphereinc.chairlift.api.entity.Department;
import com.sphereinc.chairlift.views.models.DepartmentModel;
import com.sphereinc.chairlift.views.models.TreeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelConverter {

    public static List<TreeModel> fromDepartmentsToModels(List<Department> departments) {
        Map<Integer, TreeModel> allModels = new HashMap<>();
        for (Department department : departments) {
            allModels.put(department.getId(), new DepartmentModel(department));
        }

        for (TreeModel model : allModels.values()) {

            if (((DepartmentModel) model).getDepartment().getParentDepartmentId() > 0) {
                allModels.get(((DepartmentModel) model).getDepartment().getParentDepartmentId()).getChilds().add(model);
            }
        }

        List<TreeModel> parentModels = new ArrayList<>();
        for (TreeModel model : allModels.values()) {
            if (((DepartmentModel) model).getDepartment().getParentDepartmentId() == 0) {
                parentModels.add(model);
            }
        }

        return parentModels;
    }

}
