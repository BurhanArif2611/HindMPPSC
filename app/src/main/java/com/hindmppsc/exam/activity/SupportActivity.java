package com.hindmppsc.exam.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.Live_Classes.SubjectActivity;
import com.hindmppsc.exam.utility.ErrorMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SupportActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.contact_first_tv)
    TextView contactFirstTv;
    @BindView(R.id.firstContactBtn)
    TextView firstContactBtn;
    @BindView(R.id.contact_second_tv)
    TextView contactSecondTv;
    @BindView(R.id.secondContactBtn)
    TextView secondContactBtn;
    @BindView(R.id.contact_whats_up_tv)
    TextView contactWhatsUpTv;
    @BindView(R.id.whatsup_Btn)
    TextView whatsupBtn;
    @BindView(R.id.address_tv)
    TextView addressTv;
    String Check = "";

    @Override
    protected int getContentResId() {
        return R.layout.activity_support;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTextTv.setText("Support");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Check=bundle.getString("Check");
        }
    }

    @OnClick({R.id.firstContactBtn, R.id.secondContactBtn, R.id.whatsup_Btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.firstContactBtn:
                try {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("tel:" + contactFirstTv.getText().toString()));
                    startActivity(callIntent);
                } catch (Exception e) {
                }
                break;
            case R.id.secondContactBtn:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                     Uri data = Uri.parse("mailto:" + "mppschindclass@gmail.com" + "?subject=Ask From HIND MPPSC Support Team");
                    intent.setData(data);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("Exaception e", "" + e);
                }
                break;
            case R.id.whatsup_Btn:
                try {
                    String toNumber = contactFirstTv.getText().toString(); // Replace with mobile phone number without +Sign or leading zeros.
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber));
                    startActivity(intent);
                } catch (Exception e) {
                    ErrorMessage.T(SupportActivity.this, " Please Install a What's Up ");
                    Log.e("Exaception e", "" + e);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!Check.equals("")){
            ErrorMessage.I(SupportActivity.this,DashboardActivity.class,null);
        }
        super.onBackPressed();
    }
}
