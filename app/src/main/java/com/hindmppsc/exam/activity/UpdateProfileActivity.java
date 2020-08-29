package com.hindmppsc.exam.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.database.UserProfileModel;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.LoadInterface;
import com.hindmppsc.exam.utility.NetworkUtil;
import com.hindmppsc.exam.utility.SavedData;
import com.hindmppsc.exam.utility.UserAccount;
import com.hindmppsc.exam.utility.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class UpdateProfileActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.proffileImage)
    CircleImageView proffileImage;
    @BindView(R.id.takeimage)
    ImageView takeimage;
    @BindView(R.id.user_name_etv)
    EditText userNameEtv;
    @BindView(R.id.email_id_etv)
    EditText emailIdEtv;
    @BindView(R.id.mobile_etv)
    EditText mobileEtv;
    @BindView(R.id.signup_btn)
    Button signupBtn;


    private Dialog dialog;
    private EditText otp_et, verify_phone_et;
    private String mVerificationId;
    Unbinder unbinder;
    public static final int PERMISSION_REQUEST_CODE = 1111;
    private static final int REQUEST = 1337;
    public static int SELECT_FROM_GALLERY = 2;
    public static int CAMERA_PIC_REQUEST = 0;
    Uri mImageCaptureUri;
    private File finalFile;

    private String Camera_bitmap = "";
    private File file;

    @Override
    protected int getContentResId() {
        return R.layout.activity_update_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTextTv.setText("Update Profile");

        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            userNameEtv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getDisplayName());
            emailIdEtv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getEmaiiId());
            mobileEtv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUserPhone());
            if (!UserProfileHelper.getInstance().getUserProfileModel().get(0).getProfile_pic().equals("")) {
                Picasso.with(UpdateProfileActivity.this).load(UserProfileHelper.getInstance().getUserProfileModel().get(0).getProfile_pic()).placeholder(R.drawable.ic_defult_user).into(proffileImage);
            }
        }
    }

    @OnClick({R.id.takeimage, R.id.signup_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takeimage:
                selectImage();
                break;
            case R.id.signup_btn:
                if (UserAccount.isEmpty(userNameEtv, emailIdEtv, mobileEtv)) {
                    if (UserAccount.isEmailValid(emailIdEtv)) {
                        if (UserAccount.isPhoneNumberLength(mobileEtv)) {
                            updateProfileApi();
                        } else {
                            UserAccount.EditTextPointer.setError("Mobile Number Invalid !");
                            UserAccount.EditTextPointer.requestFocus();
                        }
                    } else {
                        UserAccount.EditTextPointer.setError("Email-ID Invalid !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else {
                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                    UserAccount.EditTextPointer.requestFocus();
                }
                break;

        }
    }

    private void selectImage() {
        final CharSequence[] options = {"From Camera", "From Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please choose an Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("From Camera")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkCameraPermission()) openCameraIntent();
                        else requestPermission();
                    } else openCameraIntent();
                } else if (options[item].equals("From Gallery")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkGalleryPermission()) galleryIntent();
                        else requestGalleryPermission();
                    } else galleryIntent();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.create().show();
    }

    /* private void galleryIntent() {
         Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
         startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FROM_GALLERY);
     }*/
    private void galleryIntent() {
        Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FROM_GALLERY);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(UpdateProfileActivity.this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);
    }

    private void requestGalleryPermission() {
        ActivityCompat.requestPermissions(UpdateProfileActivity.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST);
    }

    private boolean checkCameraPermission() {
        int result1 = ContextCompat.checkSelfPermission(UpdateProfileActivity.this, CAMERA);
        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkGalleryPermission() {
        int result2 = ContextCompat.checkSelfPermission(UpdateProfileActivity.this, READ_EXTERNAL_STORAGE);
        return result2 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {
            Glide.with(this).load(Camera_bitmap).into(proffileImage);
            try {
                file = Util.getCompressed(this, Camera_bitmap);
                Camera_bitmap = file.getPath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == SELECT_FROM_GALLERY && resultCode == Activity.RESULT_OK && null != data) {
            Uri galleryURI = data.getData();
            proffileImage.setImageURI(galleryURI);
            Camera_bitmap = getRealPathFromURIPath(galleryURI, UpdateProfileActivity.this);
            try {
                file = Util.getCompressed_Gellery(this, Camera_bitmap, galleryURI);
                Camera_bitmap = file.getAbsolutePath();
                ErrorMessage.E("selectedImagePath" + Camera_bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                ErrorMessage.E("Exception" + e.toString());

            }
        }
    }


    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = null;
        int idx = 0;
        String s = "";
        try {
            cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {
                return contentURI.getPath();
            } else {
                cursor.moveToFirst();
                idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                s = cursor.getString(idx);
            }
        } catch (IllegalStateException e) {
            Log.e("Exception image", "selected " + e.toString());
        }
        return s;
    }


    private File storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate) {
        File outputFile1 = new File(Environment.getExternalStorageDirectory(), "Hind MPPSC");
        if (!outputFile1.exists()) {
            outputFile1.mkdir();
        }
        File outputFile = new File(outputFile1, "photo_" + currentDate + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    private Bitmap getImageFileFromSDCard(String filename) {
        Bitmap bitmap = null;
        File imageFile = new File(Environment.getExternalStorageDirectory() + filename);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(UpdateProfileActivity.this, "com.hindmppsc.exam.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, CAMERA_PIC_REQUEST);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */);
        Camera_bitmap = image.getAbsolutePath();
        return image;
    }




    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }






    private void updateProfileApi() {
        ErrorMessage.E("selectImagePath in Update Profile" + Camera_bitmap);
        if (NetworkUtil.isNetworkAvailable(UpdateProfileActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(UpdateProfileActivity.this);
            Call<ResponseBody> call = null;
            if (!Camera_bitmap.equals("")) {
                MultipartBody.Part body=null;
                try {
                    final RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    body = MultipartBody.Part.createFormData("image", Camera_bitmap, requestfile);
                    Log.d("rrrr", body.toString());
                }catch (Exception e){}
                RequestBody fullname = RequestBody.create(MediaType.parse("text/plain"), userNameEtv.getText().toString());

                RequestBody auth_token = RequestBody.create(MediaType.parse("text/plain"), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
                RequestBody email = RequestBody.create(MediaType.parse("text/plain"), emailIdEtv.getText().toString());
                RequestBody mobile = RequestBody.create(MediaType.parse("text/plain"), mobileEtv.getText().toString());
                RequestBody FCM = RequestBody.create(MediaType.parse("text/plain"), SavedData.getFCM_ID());
                RequestBody IMEI = RequestBody.create(MediaType.parse("text/plain"), SavedData.getIMEI_Number());
                LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
                call = apiService.updateProfile(auth_token,fullname, mobile, email, FCM, IMEI, body);
            } else {
                LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
                call = apiService.updateProfile_withoutimage(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(),userNameEtv.getText().toString(), mobileEtv.getText().toString(), emailIdEtv.getText().toString(), SavedData.getFCM_ID(), SavedData.getIMEI_Number());
            }

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        materialDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            ErrorMessage.E("==========profile update  :" + jsonObject.toString());
                            if (jsonObject.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + jsonObject.toString());
                                JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                                ErrorMessage.T(UpdateProfileActivity.this, jsonObject.getString("message"));
                                UserProfileModel userProfileModel = new UserProfileModel();
                                userProfileModel.setDisplayName(jsonObject1.getString("first_name"));
                                userProfileModel.setUser_id(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
                                userProfileModel.setEmaiiId(jsonObject1.getString("email"));
                                userProfileModel.setUserPhone(jsonObject1.getString("mobile"));
                                userProfileModel.setProfile_pic(jsonObject1.getString("profile_image"));
                                UserProfileHelper.getInstance().delete();
                                UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                ErrorMessage.I_clear(UpdateProfileActivity.this, DashboardActivity.class, null);
                            } else if (jsonObject.getString("status").equals("403")) {
                                ErrorMessage.T(UpdateProfileActivity.this, jsonObject.getString("message"));
                                UserProfileHelper.getInstance().delete();

                            } else {
                                ErrorMessage.T(UpdateProfileActivity.this, jsonObject.getString("message"));
                                materialDialog.dismiss();
                                System.out.println("==========response not geting 400 :");

                            }
                        } catch (JSONException e) {
                            materialDialog.dismiss();
                            System.out.println("==========response not  :" + e.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        materialDialog.dismiss();
                        System.out.println("==========response not success :");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    materialDialog.dismiss();
                    System.out.println("==========response faiil :");
                }
            });


        } else {
            ErrorMessage.T(UpdateProfileActivity.this, this.getString(R.string.no_internet));
        }
    }
}
