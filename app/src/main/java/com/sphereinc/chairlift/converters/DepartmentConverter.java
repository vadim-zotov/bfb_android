package com.sphereinc.chairlift.converters;


import com.sphereinc.chairlift.api.entity.Department;
import com.sphereinc.chairlift.views.models.DepartmentModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentConverter {

    public static List<DepartmentModel> fromDepartmentsToModels(List<Department> departments) {
        Map<Integer, DepartmentModel> allModels = new HashMap<>();
        for (Department department : departments) {
            allModels.put(department.getId(), new DepartmentModel(department));
        }

        for (DepartmentModel model : allModels.values()) {
            if (model.getDepartment().getParentDepartmentId() > 0) {
                allModels.get(model.getDepartment().getParentDepartmentId()).getChildDepartment().add(model);
            }
        }

        List<DepartmentModel> parentModels = new ArrayList<>();
        for (DepartmentModel model : allModels.values()) {
            if (model.getDepartment().getParentDepartmentId() == 0) {
                parentModels.add(model);
            }
        }

        return parentModels;
    }

}
