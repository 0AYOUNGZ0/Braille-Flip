package com.university.brailleflip.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

public class Utils {
    // Copy text to clipboard
    public static void copyToClipboard(Context context, String content) {
        // Starting from API 11, android recommends using android.content.ClipboardManager
        // For compatibility with older versions, we use the old android.text.ClipboardManager here, although it is marked as deprecated, it does not affect the use.
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // Put the text content in the system clipboard.
        cm.setText(content);
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}