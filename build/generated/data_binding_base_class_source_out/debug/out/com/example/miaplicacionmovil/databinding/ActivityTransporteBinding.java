// Generated by view binder compiler. Do not edit!
package com.example.miaplicacionmovil.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.miaplicacionmovil.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityTransporteBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final FrameLayout fragment;

  @NonNull
  public final AppCompatButton linea4;

  @NonNull
  public final AppCompatButton linea9;

  @NonNull
  public final AppCompatButton linean3;

  @NonNull
  public final AppCompatButton lineau1;

  @NonNull
  public final ImageView mapa;

  @NonNull
  public final LinearLayout principal;

  private ActivityTransporteBinding(@NonNull FrameLayout rootView, @NonNull FrameLayout fragment,
      @NonNull AppCompatButton linea4, @NonNull AppCompatButton linea9,
      @NonNull AppCompatButton linean3, @NonNull AppCompatButton lineau1, @NonNull ImageView mapa,
      @NonNull LinearLayout principal) {
    this.rootView = rootView;
    this.fragment = fragment;
    this.linea4 = linea4;
    this.linea9 = linea9;
    this.linean3 = linean3;
    this.lineau1 = lineau1;
    this.mapa = mapa;
    this.principal = principal;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityTransporteBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityTransporteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_transporte, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityTransporteBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      FrameLayout fragment = (FrameLayout) rootView;

      id = R.id.linea4;
      AppCompatButton linea4 = ViewBindings.findChildViewById(rootView, id);
      if (linea4 == null) {
        break missingId;
      }

      id = R.id.linea9;
      AppCompatButton linea9 = ViewBindings.findChildViewById(rootView, id);
      if (linea9 == null) {
        break missingId;
      }

      id = R.id.linean3;
      AppCompatButton linean3 = ViewBindings.findChildViewById(rootView, id);
      if (linean3 == null) {
        break missingId;
      }

      id = R.id.lineau1;
      AppCompatButton lineau1 = ViewBindings.findChildViewById(rootView, id);
      if (lineau1 == null) {
        break missingId;
      }

      id = R.id.mapa;
      ImageView mapa = ViewBindings.findChildViewById(rootView, id);
      if (mapa == null) {
        break missingId;
      }

      id = R.id.principal;
      LinearLayout principal = ViewBindings.findChildViewById(rootView, id);
      if (principal == null) {
        break missingId;
      }

      return new ActivityTransporteBinding((FrameLayout) rootView, fragment, linea4, linea9,
          linean3, lineau1, mapa, principal);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
