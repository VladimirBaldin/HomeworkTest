package com.ocsico.homeworktest.util;

import android.content.Context;
import android.text.TextUtils;

import com.ocsico.homeworktest.R;

/**
 * Created by v.baldin on 02.03.2017.
 */

public class Utils {

    public static String getExceptionMessage(Context context, Exception e) {
        String msg = e.getMessage();
        if (TextUtils.isEmpty(msg)) {
            msg = context.getString(R.string.msg_error_occurred) + " " + e.toString();
        }
        return msg;
    }
}
