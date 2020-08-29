package com.hindmppsc.exam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.utility.ErrorMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermsConditionActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.terms_condition_tv)
    TextView termsConditionTv;
    @BindView(R.id.accept_terms_tv)
    Button acceptTermsTv;
    private String exam_type = "",id="",price="";

    @Override
    protected int getContentResId() {
        return R.layout.activity_terms_condition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTextTv.setText("Terms & Condition");
        termsConditionTv.setText("1.आप वीडियो को अनलिमिटेड टाईम्स वॉच कर सकते हैं .\n" + "\n" + "2.अधिकतम 5 वीडियो ही शेयर किए जा सकते हैं इससे अधिक वीडियो शेयर करने पर आपका सब्सक्रिप्शन समाप्त हो जाएगा .\n" + "\n" + "3. अपनी सब्सक्रिप्शन आईडी को अलग-अलग डिवाइस में ओपन ना करें .");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            exam_type = bundle.getString("exam_type");
            id = bundle.getString("id");
            price = bundle.getString("price");
            ErrorMessage.E("price"+price);
        }
    }

    @OnClick(R.id.accept_terms_tv)
    public void onClick() {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("fromActivity", "Payment");
        bundle.putString("exam_type", exam_type);
        bundle.putString("id", id);
        bundle.putString("price", price);
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
