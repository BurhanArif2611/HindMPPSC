package com.hindmppsc.exam.activity.Prelims_Video_Course;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.DashboardActivity;
import com.hindmppsc.exam.activity.LoginActivity;
import com.hindmppsc.exam.adapter.TabsWithQuestionFragAdapter;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.fragments.AnswerFragment;
import com.hindmppsc.exam.models.MockTest.Example;
import com.hindmppsc.exam.models.MockTest.Result;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.LoadInterface;
import com.hindmppsc.exam.utility.NetworkUtil;
import com.hindmppsc.exam.utility.SavedData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockTestPrelimsActivity extends BaseActivity {
    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    com.hindmppsc.exam.models.Live_class_subjects.Result result1;
    private String Material_type_id = "", Exam_type_id = "";
    ArrayList<Answer_Model> answer_models = new ArrayList<>();
    Example example;
    @Override
    protected int getContentResId() {
        return R.layout.activity_mock_test_prelims;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        answer_models.clear();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Material_type_id = bundle.getString("id");
            Exam_type_id = bundle.getString("exam_type");
            titleTextTv.setText(bundle.getString("material"));
            if (bundle.getString("Exam_type").toLowerCase().trim().equals("prelims")) {
                GetMockTestListOnServer();
            } else if (bundle.getString("Exam_type").equals("Task_for_you")) {
                 result1=(com.hindmppsc.exam.models.Live_class_subjects.Result)bundle.getSerializable("Alldata");
                GetTaskForYouPrelimsListOnServer(result1.getDate());
            } else {
                GetMainMockTestListOnServer();
            }
        }

    }

    private void GetMainMockTestListOnServer() {
        if (NetworkUtil.isNetworkAvailable(MockTestPrelimsActivity.this)) {
            Dialog materialDialog = ErrorMessage.initProgressDialog(MockTestPrelimsActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.exam_mock_test(Exam_type_id, Material_type_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GetMainMockTestListOnServer" + object.toString());
                            //ErrorMessage.T(MockTestPrelimsActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("prv_paper_prelims" + object.toString());
                                 example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    List<Fragment> fragments = new ArrayList<Fragment>();
                                    for (int i = 0; i < example.getResult().size(); i++) {
                                        Bundle bundle1 = new Bundle();
                                        Result result = example.getResult().get(i);
                                        bundle1.putSerializable("AllData", result);
                                        bundle1.putString("Postion", String.valueOf(i));
                                        bundle1.putString("Size", String.valueOf(example.getResult().size()));
                                        // bundle1.putSerializable("AllQuestion_Answer", myList);
                                        fragments.add(Fragment.instantiate(MockTestPrelimsActivity.this, AnswerFragment.class.getName(), bundle1));

                                    }
                                    TabsWithQuestionFragAdapter adapter = new TabsWithQuestionFragAdapter(MockTestPrelimsActivity.this, getSupportFragmentManager(), fragments, example.getResult());
                                    pager.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                   /* remainingSecondTv.setText("1/" + example.getPaid().get(0).getTest().size());
                                    progressBar.setProgress(1);
                                    progressBar.setMaximum(example.getPaid().get(0).getTest().size());*/
                                    ErrorMessage.E("list_size" + example.getResult().size());
                                }
                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(MockTestPrelimsActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(MockTestPrelimsActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(MockTestPrelimsActivity.this, object.getString("message"));

                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {

                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();

                }
            });
        } else {
            ErrorMessage.T(MockTestPrelimsActivity.this, "No Internet");
        }
    }
  private void GetTaskForYouPrelimsListOnServer(String date) {
        if (NetworkUtil.isNetworkAvailable(MockTestPrelimsActivity.this)) {
            Dialog materialDialog = ErrorMessage.initProgressDialog(MockTestPrelimsActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.tsak_prelims(SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(),date);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GetTaskForYouPrelimsListOnServer" + object.toString());
                            //ErrorMessage.T(MockTestPrelimsActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("GetTaskForYouPrelimsListOnServer" + object.toString());
                                 example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    List<Fragment> fragments = new ArrayList<Fragment>();
                                    for (int i = 0; i < example.getResult().size(); i++) {
                                        Bundle bundle1 = new Bundle();
                                        Result result = example.getResult().get(i);
                                        bundle1.putSerializable("AllData", result);
                                        bundle1.putString("Postion", String.valueOf(i));
                                        bundle1.putString("Size", String.valueOf(example.getResult().size()));
                                        // bundle1.putSerializable("AllQuestion_Answer", myList);
                                        fragments.add(Fragment.instantiate(MockTestPrelimsActivity.this, AnswerFragment.class.getName(), bundle1));

                                    }
                                    TabsWithQuestionFragAdapter adapter = new TabsWithQuestionFragAdapter(MockTestPrelimsActivity.this, getSupportFragmentManager(), fragments, example.getResult());
                                    pager.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                   /* remainingSecondTv.setText("1/" + example.getPaid().get(0).getTest().size());
                                    progressBar.setProgress(1);
                                    progressBar.setMaximum(example.getPaid().get(0).getTest().size());*/
                                    ErrorMessage.E("list_size" + example.getResult().size());
                                }
                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(MockTestPrelimsActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(MockTestPrelimsActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(MockTestPrelimsActivity.this, object.getString("message"));

                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {

                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();

                }
            });
        } else {
            ErrorMessage.T(MockTestPrelimsActivity.this, "No Internet");
        }
    }

    public void preview_next(int Position) {
        pager.setCurrentItem((Position));
    }

    public void next_next(int Position) {
        pager.setCurrentItem((Position));
    }

    @Override
    public void onBackPressed() {
        ErrorMessage.T(MockTestPrelimsActivity.this, "Test is runing");
        //super.onBackPressed();
    }

    public void Done_AllQuestions() {
        ErrorMessage.E("Done_AllQuestions"+example.getResult().size());
        Bundle bundle = new Bundle();
        bundle.putSerializable("AllData", example);
        bundle.putSerializable("AllQuestion_Answer", answer_models);
        ErrorMessage.I_clear(MockTestPrelimsActivity.this, MockTestResultActivity.class, bundle);
    }

    private void GetMockTestListOnServer() {
        if (NetworkUtil.isNetworkAvailable(MockTestPrelimsActivity.this)) {
            Dialog materialDialog = ErrorMessage.initProgressDialog(MockTestPrelimsActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.mock_test_prelims(Exam_type_id, Material_type_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GetMockTestListOnServer" + object.toString());
                            //ErrorMessage.T(MockTestPrelimsActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("prv_paper_prelims" + object.toString());
                                 example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    List<Fragment> fragments = new ArrayList<Fragment>();
                                    for (int i = 0; i < example.getResult().size(); i++) {
                                        Bundle bundle1 = new Bundle();
                                        Result result = example.getResult().get(i);
                                        bundle1.putSerializable("AllData", result);
                                        bundle1.putString("Postion", String.valueOf(i));
                                        bundle1.putString("Size", String.valueOf(example.getResult().size()));
                                        // bundle1.putSerializable("AllQuestion_Answer", myList);
                                        fragments.add(Fragment.instantiate(MockTestPrelimsActivity.this, AnswerFragment.class.getName(), bundle1));

                                    }
                                    TabsWithQuestionFragAdapter adapter = new TabsWithQuestionFragAdapter(MockTestPrelimsActivity.this, getSupportFragmentManager(), fragments, example.getResult());
                                    pager.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                   /* remainingSecondTv.setText("1/" + example.getPaid().get(0).getTest().size());
                                    progressBar.setProgress(1);
                                    progressBar.setMaximum(example.getPaid().get(0).getTest().size());*/
                                    ErrorMessage.E("list_size" + example.getResult().size());
                                }
                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(MockTestPrelimsActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(MockTestPrelimsActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(MockTestPrelimsActivity.this, object.getString("message"));

                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {

                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();

                }
            });
        } else {
            ErrorMessage.T(MockTestPrelimsActivity.this, "No Internet");
        }
    }
    public void Answer_with_position(int Position, String Answer) {
        int Index = -1;
        if (answer_models.size() > 0) {
            for (int i = 0; i < answer_models.size(); i++) {
                if (answer_models.get(i).getPosition().equals(String.valueOf(Position))) {
                    Index = i;
                }
            }
            if (Index != -1) {
                Answer_Model answer_model = new Answer_Model();
                answer_model.setPosition(String.valueOf(Position));
                answer_model.setAnswer(Answer);
                answer_models.set(Index, answer_model);
            } else {
                Answer_Model answer_model = new Answer_Model();
                answer_model.setPosition(String.valueOf(Position));
                answer_model.setAnswer(Answer);
                answer_models.add(answer_model);
            }
        } else {
            Answer_Model answer_model = new Answer_Model();
            answer_model.setPosition(String.valueOf(Position));
            answer_model.setAnswer(Answer);
            answer_models.add(answer_model);
        }
        ErrorMessage.E("answer_models size" + answer_models.size());
    }
}
