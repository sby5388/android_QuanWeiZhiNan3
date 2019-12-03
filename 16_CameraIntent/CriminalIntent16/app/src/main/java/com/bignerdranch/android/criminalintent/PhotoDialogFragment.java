package com.bignerdranch.android.criminalintent;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

/**
 * @author Administrator  on 2019/12/3.
 */
public class PhotoDialogFragment extends DialogFragment {
    private static final String KEY_PHOTO_PATH = "KEY_PHOTO_PATH";

    public static DialogFragment newInstance(String filePath) {
        final PhotoDialogFragment fragment = new PhotoDialogFragment();
        final Bundle args = new Bundle();
        args.putString(KEY_PHOTO_PATH, filePath);
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String path = getArguments().getString(KEY_PHOTO_PATH);
        // FIXME: 2019/12/3 当这里使用了 #getLayoutInflater() 出现了不停地闪退
        final LayoutInflater inflater = LayoutInflater.from(getContext());

        final View view = inflater.inflate(R.layout.dialog_photo, null);
        // TODO: 2019/12/3 添加对话框布局文件
        final ImageView imageView = view.findViewById(R.id.imageView);
        final Bitmap bitmap = getBitmap(path);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

    private Bitmap getBitmap(final String path) {
        return PictureUtils.getScaledBitmap(path, getActivity());
    }
}
