package p32929.easypasscodelock.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import p32929.easypasscodelock.R;
import p32929.easypasscodelock.Utils.EasyLock;
import p32929.easypasscodelock.Utils.FayazSP;
import p32929.easypasscodelock.Utils.LockscreenHandler;

public class LockscreenActivity extends LockscreenHandler{

    private static Class<?> classToGoAfter;

    String tempPass = "";
    private final int[] passButtonIds = {
            R.id.lbtn1,
            R.id.lbtn2,
            R.id.lbtn3,
            R.id.lbtn4,
            R.id.lbtn5,
            R.id.lbtn6,
            R.id.lbtn7,
            R.id.lbtn8,
            R.id.lbtn9,
            R.id.lbtn0
    };
    private TextView textViewDot;
    private TextView textViewHAHA;

    private String passString = "", realPass = "";
    private String status = "";
    //
    private final String checkStatus = "check";
    private final String setStatus = "set";
    private final String setStatus1 = "set1";
    private final String changeStatus = "change";
    private final String changeStatus1 = "change1";
    private final String changeStatus2 = "change2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_lockscreen);

        FayazSP.init(this);
        realPass = getPassword();
        initViews();

        status = getIntent().getExtras().getString("passStatus", "check");
        if (status.equals(setStatus))
            textViewHAHA.setText(getString(R.string.digital_enter_password));
        String disableStatus = "disable";
        if (status.equals(disableStatus)) {
            FayazSP.put("password", null);
            Toast.makeText(this, "Password Disabled", Toast.LENGTH_SHORT).show();
            gotoActivity();
        }
    }

    private void initViews() {
        textViewHAHA = findViewById(R.id.haha_text);
        textViewDot = findViewById(R.id.dotText);
        TextView textViewForgotPassword = findViewById(R.id.forgot_pass_textview);
        Button buttonTick = findViewById(R.id.lbtnTick);
        ImageButton imageButtonDelete = findViewById(R.id.lbtnDelete);
        RelativeLayout relativeLayoutBackground = findViewById(R.id.background_layout);
        relativeLayoutBackground.setBackgroundColor(EasyLock.backgroundColor);

        textViewForgotPassword.setOnClickListener(EasyLock.onClickListener);

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passString.length() > 0)
                    passString = passString.substring(0, passString.length() - 1);
                textViewDot.setText(passString);
            }
        });

        buttonTick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //
                switch (status) {
                    case checkStatus:
                        if (passString.equals(realPass)) {
                            finish();
                        } else {
                            passString = "";
                            textViewDot.setText(passString);
                            Toast.makeText(LockscreenActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    //
                    case setStatus:
                        //
                        tempPass = passString;
                        passString = "";
                        status = setStatus1;

                        textViewHAHA.setText(getString(R.string.digital_confirm_password));
                        textViewDot.setText(passString);
                        break;

                    //
                    case setStatus1:
                        //
                        if (passString.equals(tempPass)) {
                            FayazSP.put("password", passString);
                            // Toast.makeText(LockscreenActivity.this, "Password is set", Toast.LENGTH_SHORT).show();
                            onPasswordSet();

                            gotoActivity();
                        } else {

                            tempPass = passString;
                            passString = "";
                            tempPass = "";
                            status = setStatus;

                            textViewDot.setText(passString);
                            textViewHAHA.setText(R.string.digital_enter_password);
                            // Toast.makeText(LockscreenActivity.this, "Please Enter a New Password Again", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    //
                    case changeStatus:
                        if (passString.equals(realPass)) {
                            tempPass = passString;
                            passString = "";
                            tempPass = "";
                            status = changeStatus1;

                            textViewHAHA.setText(R.string.digital_enter_password);
                            textViewDot.setText(passString);
                        } else {
                            passString = "";
                            textViewDot.setText(passString);
                            // Toast.makeText(LockscreenActivity.this, "Please Enter Current Password", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    //
                    case changeStatus1:
                        tempPass = passString;
                        passString = "";
                        status = changeStatus2;

                        textViewHAHA.setText(R.string.digital_confirm_password);
                        textViewDot.setText(passString);
                        break;

                    //
                    case changeStatus2:
                        if (passString.equals(tempPass)) {
                            FayazSP.put("password", passString);
                            // Toast.makeText(LockscreenActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                            gotoActivity();
                        } else {

                            tempPass = passString;
                            passString = "";
                            tempPass = "";
                            status = changeStatus1;

                            textViewDot.setText(passString);
                            textViewHAHA.setText(R.string.digital_enter_password);
                            // Toast.makeText(LockscreenActivity.this, "Please Enter a New Password Again", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }
        });

        for (int passButtonId : passButtonIds) {
            final Button button = findViewById(passButtonId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (passString.length() >= 8) {
                        Toast.makeText(LockscreenActivity.this, "Max 8 characters", Toast.LENGTH_SHORT).show();
                    } else {
                        passString += button.getText().toString();
                    }
                    textViewDot.setText(passString);
                }
            });
        }
    }

    protected void onPasswordSet() {

    }

    private String getPassword() {
        return FayazSP.getString("password", null);
    }

    protected void gotoActivity() {
        if (classToGoAfter != null) {
            Intent intent = new Intent(LockscreenActivity.this, classToGoAfter);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (status.equals("check")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else {
                ActivityCompat.finishAffinity(this);
            }
        }
    }

}
