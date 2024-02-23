// Generated by view binder compiler. Do not edit!
package com.example.miaplicacionmovil.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.miaplicacionmovil.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemFacultyBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final ImageView ivFaculty;

  @NonNull
  public final TextView tvName;

  private ItemFacultyBinding(@NonNull CardView rootView, @NonNull ImageView ivFaculty,
      @NonNull TextView tvName) {
    this.rootView = rootView;
    this.ivFaculty = ivFaculty;
    this.tvName = tvName;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemFacultyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemFacultyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_faculty, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemFacultyBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ivFaculty;
      ImageView ivFaculty = ViewBindings.findChildViewById(rootView, id);
      if (ivFaculty == null) {
        break missingId;
      }

      id = R.id.tvName;
      TextView tvName = ViewBindings.findChildViewById(rootView, id);
      if (tvName == null) {
        break missingId;
      }

      return new ItemFacultyBinding((CardView) rootView, ivFaculty, tvName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
