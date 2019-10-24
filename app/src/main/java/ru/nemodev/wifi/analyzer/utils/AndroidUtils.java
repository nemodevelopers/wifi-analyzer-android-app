package ru.nemodev.wifi.analyzer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

import ru.nemodev.wifi.analyzer.core.app.AndroidApplication;


public final class AndroidUtils
{
    private static final String LOG_TAG = AndroidUtils.class.getSimpleName();

    private static final String DIALOG_SHARE_TYPE = "text/plain";

    private AndroidUtils() { }

    public static String getString(int resId)
    {
        return AndroidApplication.getInstance().getResources().getString(resId);
    }

    public static int getInteger(int resId)
    {
        return AndroidApplication.getInstance().getResources().getInteger(resId);
    }

    public static List<String> getStringList(int resId)
    {
        return Arrays.asList(AndroidApplication.getInstance().getResources().getStringArray(resId));
    }

    public static void openShareDialog(Context context, String dialogTitle, String shareContent)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        sendIntent.setType(DIALOG_SHARE_TYPE);
        context.startActivity(Intent.createChooser(sendIntent, dialogTitle));
    }

    public static void openAppByURI(Activity activity, String rawURI)
    {
        Uri uri = Uri.parse(rawURI);
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, uri);
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                | Intent.FLAG_ACTIVITY_NEW_DOCUMENT
                | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        startActivity(activity, viewIntent);
    }

    private static void startActivity(Activity activity, Intent intent)
    {
        activity.startActivity(intent);
    }

    public static void showSnackBarMessage(View whereShow, int textId)
    {
        showSnackBarMessageShort(whereShow, AndroidUtils.getString(textId));
    }

    public static void showSnackBarMessageShort(View whereShow, String message)
    {
        showSnackBarMessage(whereShow, message, Snackbar.LENGTH_SHORT);
    }

    public static void showSnackBarMessageLong(View whereShow, String message)
    {
        showSnackBarMessage(whereShow, message, Snackbar.LENGTH_LONG);
    }

    public static void showSnackBarMessage(View whereShow, String message, int snackBarDuration)
    {
        try
        {
            Snackbar
                    .make(whereShow, message, snackBarDuration)
                    .show();
        }
        catch (Exception e)
        {
            LogUtils.error(LOG_TAG, "Ошибка при показе ShackBar сообщения!", e);
        }
    }

    public static Spannable getColoredString(String text, int startPos, int endPos, int color) {
        Spannable spannable = new SpannableString(text);
        spannable.setSpan(new ForegroundColorSpan(color), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
}
