package gank.douya.com.gank.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by douya on 2017/3/14.
 */

public class ActivityUtils {
    public static final String ACTIVITY_TARGET = "target";
    public static final int COUNT_PERPAGE = 15;


    public static void share(Context context, String text) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        context.startActivity(Intent.createChooser(intent, "分享到"));
    }

    public static boolean copyToClipBoard(Context context, String text) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newRawUri("gankUri", Uri.parse(text));
        cmb.setPrimaryClip(data);
        return true;
    }

    public static void openInbrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }
}
