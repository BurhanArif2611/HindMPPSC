package com.hindmppsc.exam.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.Prelims_Video_Course.Answer_Model;
import com.hindmppsc.exam.activity.Prelims_Video_Course.ShowResultActivity;
import com.hindmppsc.exam.models.MockTest.Example;
import com.hindmppsc.exam.models.MockTest.Result;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FinalAnswerFragment extends Fragment {
    View view;
    @BindView(R.id.question_tv)
    TextView questionTv;
    @BindView(R.id.option_first_tv)
    TextView optionFirstTv;
    @BindView(R.id.radio_first)
    TextView radioFirst;
    @BindView(R.id.first_layout)
    LinearLayout firstLayout;
    @BindView(R.id.option_second_tv)
    TextView optionSecondTv;
    @BindView(R.id.radio_second)
    TextView radioSecond;
    @BindView(R.id.second_layout)
    LinearLayout secondLayout;
    @BindView(R.id.option_third_tv)
    TextView optionThirdTv;
    @BindView(R.id.radio_third)
    TextView radioThird;
    @BindView(R.id.third_layout)
    LinearLayout thirdLayout;
    @BindView(R.id.option_fourth_tv)
    TextView optionFourthTv;
    @BindView(R.id.radio_fourth)
    TextView radioFourth;
    @BindView(R.id.fourth_layout)
    LinearLayout fourthLayout;
    @BindView(R.id.done_btn)
    Button doneBtn;
    @BindView(R.id.preview_btn)
    Button previewBtn;
    @BindView(R.id.next_btn)
    Button nextBtn;
    private Unbinder unbinder;
    Result result;
    ArrayList<Answer_Model> myList;
    int Position = 0; String Size = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_final_answer, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            result = (Result) bundle.getSerializable("AllData");
            myList = (ArrayList<Answer_Model>) getArguments().getSerializable("AllQuestion_Answer");
            Position = Integer.parseInt(bundle.getString("Postion"));

            Size = bundle.getString("Size");
            ErrorMessage.E("QuestionFragmentPosition" + Position);
            questionTv.setText("Q." + (Integer.parseInt(bundle.getString("Postion")) + 1) + " " + result.getQuestion());
            radioFirst.setText(result.getOption().get(0).getOption());
            radioSecond.setText(result.getOption().get(1).getOption());
            radioThird.setText(result.getOption().get(2).getOption());
            radioFourth.setText(result.getOption().get(3).getOption());

            optionFirstTv.setText("A");
            optionSecondTv.setText("B");
            optionThirdTv.setText("C");
            optionFourthTv.setText("D");
           /* if (Position.equals("0")) {
                previewBtn.setVisibility(View.INVISIBLE);
            } else {
                previewBtn.setVisibility(View.VISIBLE);
            }*/
            if (Integer.parseInt(Size) - 1 == (Position)) {
                doneBtn.setVisibility(View.VISIBLE);
            } else {
                doneBtn.setVisibility(View.GONE);
            }
            try {
                int Index = -1;
                if (myList.size() > 0) {
                    for (int i = 0; i < myList.size(); i++) {
                        ErrorMessage.E("Data Position" + myList.get(i).getPosition() + ">>" + Position);
                        if (Integer.parseInt(myList.get(i).getPosition()) == Position) {
                            Index = i;
                        }
                    }
                    if (Index != -1) {
                        if (myList.get(Index).getAnswer().equals(radioFirst.getText().toString())) {
                          //  radioFirst.setBackground(getActivity().getResources().getDrawable(R.drawable.red_corners));
                            firstLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corner_colour));
                            secondLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            thirdLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            fourthLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            optionFirstTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle_white));
                            optionFirstTv.setTextColor(getActivity().getResources().getColor(R.color.optioncolour));

                            optionSecondTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionSecondTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionThirdTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionThirdTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionFourthTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionFourthTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            radioFirst.setTextColor(getActivity().getResources().getColor(R.color.white));
                        }
                        if (myList.get(Index).getAnswer().equals(radioSecond.getText().toString())) {
                             //radioSecond.setBackground(getActivity().getResources().getDrawable(R.drawable.red_corners));
                            firstLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            secondLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corner_colour));
                            thirdLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            fourthLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));

                            optionSecondTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle_white));
                            optionSecondTv.setTextColor(getActivity().getResources().getColor(R.color.optioncolour));

                            optionFirstTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionFirstTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionThirdTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionThirdTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionFourthTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionFourthTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            radioSecond.setTextColor(getActivity().getResources().getColor(R.color.white));
                        }
                        if (myList.get(Index).getAnswer().equals(radioThird.getText().toString())) {
                           // radioThird.setBackground(getActivity().getResources().getDrawable(R.drawable.red_corners));
                            firstLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            secondLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            thirdLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corner_colour));
                            fourthLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));

                            optionThirdTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle_white));
                            optionThirdTv.setTextColor(getActivity().getResources().getColor(R.color.optioncolour));

                            optionFirstTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionFirstTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionSecondTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionSecondTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionFourthTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionFourthTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            radioThird.setTextColor(getActivity().getResources().getColor(R.color.white));
                        }
                        if (myList.get(Index).getAnswer().equals(radioFourth.getText().toString())) {
                           // radioFourth.setBackground(getActivity().getResources().getDrawable(R.drawable.red_corners));

                            firstLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            secondLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            thirdLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            fourthLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corner_colour));

                            optionFourthTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle_white));
                            optionFourthTv.setTextColor(getActivity().getResources().getColor(R.color.optioncolour));

                            optionFirstTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionFirstTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionSecondTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionSecondTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionThirdTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionThirdTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            radioFourth.setTextColor(getActivity().getResources().getColor(R.color.white));
                        }

                        if (result.getAnswer().equals(radioFirst.getText().toString())) {
                            //radioFirst.setBackground(getActivity().getResources().getDrawable(R.drawable.green_corners));
                            firstLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corner_colour));
                            secondLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            thirdLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            fourthLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            optionFirstTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle_white));
                            optionFirstTv.setTextColor(getActivity().getResources().getColor(R.color.optioncolour));

                            optionSecondTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionSecondTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionThirdTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionThirdTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionFourthTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionFourthTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            radioFirst.setTextColor(getActivity().getResources().getColor(R.color.white));
                        }/* else {
                            radioFirst.setBackground(getActivity().getResources().getDrawable(R.drawable.red_corners));
                        }*/
                        if (result.getAnswer().equals(radioSecond.getText().toString())) {
                           // radioSecond.setBackground(getActivity().getResources().getDrawable(R.drawable.green_corners));
                            firstLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            secondLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corner_colour));
                            thirdLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            fourthLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));

                            optionSecondTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle_white));
                            optionSecondTv.setTextColor(getActivity().getResources().getColor(R.color.optioncolour));

                            optionFirstTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionFirstTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionThirdTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionThirdTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionFourthTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionFourthTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            radioSecond.setTextColor(getActivity().getResources().getColor(R.color.white));
                        } /*else {
                            radioSecond.setBackground(getActivity().getResources().getDrawable(R.drawable.red_corners));
                        }*/
                        if (result.getAnswer().equals(radioThird.getText().toString())) {
                            //radioThird.setBackground(getActivity().getResources().getDrawable(R.drawable.green_corners));
                            firstLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            secondLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            thirdLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corner_colour));
                            fourthLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));

                            optionThirdTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle_white));
                            optionThirdTv.setTextColor(getActivity().getResources().getColor(R.color.optioncolour));

                            optionFirstTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionFirstTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionSecondTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionSecondTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionFourthTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionFourthTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            radioThird.setTextColor(getActivity().getResources().getColor(R.color.white));
                        } /*else {
                            radioThird.setBackground(getActivity().getResources().getDrawable(R.drawable.red_corners));
                        }*/
                        if (result.getAnswer().equals(radioFourth.getText().toString())) {
                            //radioFourth.setBackground(getActivity().getResources().getDrawable(R.drawable.green_corners));
                            firstLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            secondLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            thirdLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corners_white));
                            fourthLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.background_corner_colour));

                            optionFourthTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle_white));
                            optionFourthTv.setTextColor(getActivity().getResources().getColor(R.color.optioncolour));

                            optionFirstTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionFirstTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionSecondTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionSecondTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            optionThirdTv.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_circle));
                            optionThirdTv.setTextColor(getActivity().getResources().getColor(R.color.white));

                            radioFourth.setTextColor(getActivity().getResources().getColor(R.color.white));
                        }/* else {
                            radioFourth.setBackground(getActivity().getResources().getDrawable(R.drawable.red_corners));
                        }*/


                        ErrorMessage.E("Your AnswerSheetG>" + result.getAnswer() + ">>" + myList.get(Index).getAnswer());
                    } else {
                        /*holder.right_wron_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_close));
                        holder.actual_answer_tv.setText(test.getAnswer());
                        holder.your_answer_tv.setText("Not Attemted");*/
                    }
                } else {
                    /*holder.right_wron_img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_close));
                    holder.actual_answer_tv.setText(test.getAnswer());
                    holder.your_answer_tv.setText("Not Attemted");*/
                }
            } catch (Exception e) {
                ErrorMessage.E("Exception" + e.toString());
            }
        }

        /*radioGroupId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                RadioButton radioSexButton = (RadioButton)view.findViewById(checkedId);
                Toast.makeText(getActivity(), radioSexButton.getText().toString(), Toast.LENGTH_SHORT).show();
                ((TestPaperPaid_FreeActivity) getActivity()).Answer_with_position(Integer.parseInt(Position),radioSexButton.getText().toString());
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TestPaperPaid_FreeActivity) getActivity()).Done_AllQuestions();
            }
        });*/
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ShowResultActivity) getActivity()).Done_AllQuestions();
            }
        });
        return view;
    }

    @OnClick({R.id.preview_btn, R.id.next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.preview_btn:
                if ((Position) == 0) {
                } else {
                    ((ShowResultActivity) getActivity()).preview_next((Position) - 1);
                }
                break;
            case R.id.next_btn:
                ErrorMessage.E("Current Position" + Position);
                ((ShowResultActivity) getActivity()).next_next((Position) + 1);
                break;
        }
    }


}
