package p32929.easypasscodelock.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import p32929.easypasscodelock.Activities.LockscreenActivity;

/**
 * Created by p32929 on 7/17/2018.
 */

public class EasyLock {

    public static int backgroundColor = Color.parseColor("#019689");
    public static View.OnClickListener onClickListener;

    private static void init(Context context) {
        FayazSP.init(context);
    }

    public static void setPassword(Context context, Class activityClassToGo) {
        init(context);
        Intent intent = new Intent(context, LockscreenActivity.class);
        intent.putExtra("passStatus", "set");
        context.startActivity(intent);
    }

    public static void changePassword(Context context, Class activityClassToGo) {
        init(context);
        Intent intent = new Intent(context, LockscreenActivity.class);
        intent.putExtra("passStatus", "change");
        context.startActivity(intent);
    }

    public static void disablePassword(Context context, Class activityClassToGo) {
        init(context);
        Intent intent = new Intent(context, LockscreenActivity.class);
        intent.putExtra("passStatus", "disable");
        context.startActivity(intent);
    }

    public static void checkPassword(Context context) {
        init(context);
        if (FayazSP.getString("password", null) != null) {
            Intent intent = new Intent(context, LockscreenActivity.class);
            intent.putExtra("passStatus", "check");
            context.startActivity(intent);
        }
    }

    public static void setBackgroundColor(int backgroundColor) {
        EasyLock.backgroundColor = backgroundColor;
    }

    public static void forgotPassword(View.OnClickListener onClickListener) {
        EasyLock.onClickListener = onClickListener;
    }

}
