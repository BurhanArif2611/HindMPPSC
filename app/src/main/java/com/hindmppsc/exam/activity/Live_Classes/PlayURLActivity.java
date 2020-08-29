package com.hindmppsc.exam.activity.Live_Classes;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.LoadInterface;
import com.hindmppsc.exam.utility.NetworkUtil;
import com.hindmppsc.exam.utility.SavedData;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayURLActivity extends AppCompatActivity {
    private static final int RECOVERY_REQUEST = 1;
    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.input_etv)
    EditText inputEtv;
    @BindView(R.id.send_btn)
    FloatingActionButton sendBtn;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;

    //private YouTubePlayerView youTubeView;
    String API_Key = "AIzaSyA2Zqd6LuuT62ip4pCnBd2sl1t1S9AfkJE";
    @BindView(R.id.read_comment_tv)
    TextView readCommentTv;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @BindView(R.id.more_btn)
    LinearLayout moreBtn;
    private String videoId = "";
    private String Live_id = "";
    private YouTubePlayer youTubePlayer;
    int orientation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        setContentView(R.layout.activity_play_url);
        ButterKnife.bind(this);
        try {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Exception e) {
        }
        titleTextTv.setText("Live Class");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String currentString = bundle.getString("URL");
            Live_id = bundle.getString("Live_id");
            if (currentString.contains("v=")) {
                String[] separated = currentString.split("v=");
                videoId = separated[1];
            } else {
                videoId = currentString.substring(currentString.lastIndexOf("/")).replaceAll("/", "");
                ErrorMessage.E("videoId" + videoId);
            }
        }
        // youTubeView = (YouTubePlayerView) findViewById(R.id.player);
        YouTubePlayerSupportFragment youTubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        //youTubePlayerFragment.initialize(API_Key, this);

       /* int display_mode = getResources().getConfiguration().orientation;
        if (display_mode == Configuration.ORIENTATION_LANDSCAPE) {
            toolBarLayout.setVisibility(View.GONE); //<< this
        } else {
            toolBarLayout.setVisibility(View.VISIBLE);
        }*/

        if (youTubePlayerFragment == null) return;
        youTubePlayerFragment.initialize(API_Key, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer = player;
                    //set the player style default
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                    youTubePlayer.setShowFullscreenButton(true);
                    //cue the 1st video by default
                    youTubePlayer.cueVideo(videoId);
                    youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                        @Override
                        public void onPlaying() {

                        }

                        @Override
                        public void onPaused() {

                        }

                        @Override
                        public void onStopped() {

                        }

                        @Override
                        public void onBuffering(boolean b) {

                        }

                        @Override
                        public void onSeekTo(int i) {

                        }
                    });
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {

                if (arg1.isUserRecoverableError()) {
                    arg1.getErrorDialog(PlayURLActivity.this, RECOVERY_REQUEST).show();
                } else {
                    String error = String.format("Error a rahi he", arg1.toString());
                    Toast.makeText(PlayURLActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }
        });
        readCommentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Live_id", Live_id);
                ErrorMessage.I(PlayURLActivity.this, ReadCommentActivity.class, bundle);
            }
        });
        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            if (UserProfileHelper.getInstance().getUserProfileModel().get(0).getEmaiiId().equals("mppschindclass@gmail.com")) {
                readCommentTv.setVisibility(View.VISIBLE);
                bottomLayout.setVisibility(View.GONE);
            }
        }
        try {
             orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // In landscape
                toolBarLayout.setVisibility(View.GONE);
                bottomLayout.setVisibility(View.GONE);
               // moreBtn.setVisibility(View.VISIBLE);
                ErrorMessage.E("check>>" + "landscape");
            } else {
                // In portrait
                toolBarLayout.setVisibility(View.VISIBLE);
                bottomLayout.setVisibility(View.VISIBLE);
                moreBtn.setVisibility(View.GONE);
                ErrorMessage.E("check>>" + "portrait");
            }
        } catch (Exception e) {
        }
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomLayout.setVisibility(View.VISIBLE);
                moreBtn.setVisibility(View.GONE);
            }
        });
    }

   /* @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            player.setShowFullscreenButton(true);
            player.loadVideo(videoId); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format("Error a rahi he", errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(API_Key, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }*/

    @OnClick(R.id.send_btn)
    public void onClick() {
        if (!inputEtv.getText().toString().equals("")) {
            SendMessageOnServer();
        } else {
            ErrorMessage.T(PlayURLActivity.this, "This Field Can't be Empty !");
        }
    }

    private void SendMessageOnServer() {
        if (NetworkUtil.isNetworkAvailable(PlayURLActivity.this)) {
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.live_class_comment(Live_id, inputEtv.getText().toString(), SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GetSubjectOnServer" + object.toString());
                            //ErrorMessage.T(PlayURLActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                inputEtv.setText("");
                              /*  if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                    // In landscape
                                    toolBarLayout.setVisibility(View.GONE);
                                    bottomLayout.setVisibility(View.GONE);
                                    moreBtn.setVisibility(View.VISIBLE);
                                    ErrorMessage.E("check>>" + "landscape");
                                } else {
                                    // In portrait
                                    toolBarLayout.setVisibility(View.VISIBLE);
                                    bottomLayout.setVisibility(View.VISIBLE);
                                    moreBtn.setVisibility(View.GONE);
                                    ErrorMessage.E("check>>" + "portrait");
                                }*/

                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(PlayURLActivity.this, object.getString("message"));

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                }
            });
        } else {
            ErrorMessage.T(PlayURLActivity.this, "No Internet");
        }
    }


}
