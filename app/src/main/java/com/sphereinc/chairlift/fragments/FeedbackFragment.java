package com.sphereinc.chairlift.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.adapter.UserAdapter;
import com.sphereinc.chairlift.api.entity.response.DepartmentsSearchResult;
import com.sphereinc.chairlift.api.entity.response.UserSearchResult;
import com.sphereinc.chairlift.api.facade.DepartmentFacade;
import com.sphereinc.chairlift.api.facade.UserFacade;
import com.sphereinc.chairlift.api.facadeimpl.DepartmentFacadeImpl;
import com.sphereinc.chairlift.api.facadeimpl.UserFacadeImpl;
import com.sphereinc.chairlift.common.Preferences;
import com.sphereinc.chairlift.common.utils.DialogUtils;
import com.sphereinc.chairlift.converters.DepartmentConverter;
import com.sphereinc.chairlift.decorator.DividerItemDecorator;
import com.sphereinc.chairlift.views.SelectorListLayout;
import com.sphereinc.chairlift.views.models.DepartmentModel;
import com.sphereinc.chairlift.views.models.TagClass;
import com.sphereinc.chairlift.views.tag.Constants;
import com.sphereinc.chairlift.views.tag.Tag;
import com.sphereinc.chairlift.views.tag.TagView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackFragment extends Fragment {

    @Bind(R.id.tag_group)
    TagView tagGroup;


    ArrayList<TagClass> tagList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_feedback_fragment, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_feedback));
        ButterKnife.bind(this, v);

        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prepareTags();
//        loadDepartments();
    }

    private void prepareTags() {
        tagList = new ArrayList<>();
        JSONArray jsonArray = null;
        JSONObject temp;
        try {
            jsonArray = new JSONArray(Constants.COUNTRIES);
            for (int i = 0; i < jsonArray.length(); i++) {
                temp = jsonArray.getJSONObject(i);
                tagList.add(new TagClass(temp.getString("code"), temp.getString("name")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Tag> tags = new ArrayList<>();

        for (TagClass tagClass : tagList) {
            tags.add(new Tag(tagClass.getName()));
        }
        tagGroup.addTags(tags);
    }


//    private void loadDepartments() {
//        DialogUtils.showDialog(getString(R.string.loading), getActivity());
//        departmentFacade.getDepartments(new Callback<DepartmentsSearchResult>() {
//            @Override
//            public void onResponse(Response<DepartmentsSearchResult> response) {
//                DepartmentsSearchResult result = response.body();
//                if (result != null &&
//                        result.getDepartments() != null) {
//                    List<DepartmentModel> parentModels = DepartmentConverter.fromDepartmentsToModels(result.getDepartments());
//                    addSelectorLayout(parentModels);
//                }
//                DialogUtils.hideProgressDialogs();
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                t.printStackTrace();
//                DialogUtils.hideProgressDialogs();
//            }
//        });
//    }
//
//    private void addSelectorLayout(List<DepartmentModel> departmentModels) {
//        SelectorListLayout selectorListLayout = new SelectorListLayout(getActivity(), departmentModels,
//                new SelectorListLayout.OnLoadChildsListener() {
//                    @Override
//                    public void onItemClick(DepartmentModel departmentModel) {
//                        if (departmentModel.getChildDepartment() != null &&
//                                !departmentModel.getChildDepartment().isEmpty()) {
//                            addSelectorLayout(departmentModel.getChildDepartment());
//                        }
//                    }
//                });
//        layouts.add(selectorListLayout);
//        _flytMain.addView(selectorListLayout);
//    }


}
