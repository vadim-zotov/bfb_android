package com.sphereinc.chairlift.views.customsearchable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.adapter.UserAdapter;
import com.sphereinc.chairlift.api.entity.response.UserSearchResult;
import com.sphereinc.chairlift.api.facade.UserFacade;
import com.sphereinc.chairlift.api.facadeimpl.UserFacadeImpl;
import com.sphereinc.chairlift.common.utils.ErrorHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity {
    @Bind(R.id.custombar_text)
    EditText searchInput;

    @Bind(R.id.cs_result_list)
    RecyclerView searchResultList;

    @Bind(R.id.empty_results)
    TextView emptySearchResult;

    private UserFacade userFacade = new UserFacadeImpl();
    public String nextSearchString;
    public boolean searchInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.custom_searchable);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.textPrimaryColor));
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        searchResultList.setLayoutManager(linearLayoutManager);

        this.searchInput.setMaxLines(1);

        implementSearchTextListener();
        searchUserByName("");
    }


    @OnClick({R.id.delete_icon})
    public void onDeleteIcon() {
        searchInput.setText("");
    }

    @OnClick({R.id.custombar_return_wrapper})
    public void onReturnIcon() {
        finish();
    }


    private void implementSearchTextListener() {
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                searchUserByName(String.valueOf(s));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    private void searchUserByName(String name) {
        nextSearchString = "";
        searchInProgress = true;
        userFacade.searchByName(name, new Callback<UserSearchResult>() {
            @Override
            public void onResponse(Response<UserSearchResult> response) {
                if (nextSearchString.isEmpty()) {
                    UserSearchResult result = response.body();
                    if (result != null && result.getUsers() != null) {
                        if (result.getUsers().size() > 0) {
                            searchResultList.setVisibility(View.VISIBLE);
                            emptySearchResult.setVisibility(View.GONE);

                            searchResultList.swapAdapter(new UserAdapter(result.getUsers(),
                                    new UserAdapter.OnUserClickListener() {
                                        @Override
                                        public void onItemClick(int userId) {
                                            Intent intent = new Intent();
                                            intent.putExtra("user_id", userId);
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    }), false);
                        } else {
                            searchResultList.setVisibility(View.GONE);
                            emptySearchResult.setVisibility(View.VISIBLE);
                        }

                    }
                    searchInProgress = false;
                } else {
                    searchUserByName(nextSearchString);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                ErrorHandler.checkConnectionError(SearchActivity.this, t);
            }
        });
    }

}