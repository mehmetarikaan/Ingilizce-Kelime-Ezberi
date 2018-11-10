package com.arsey.ingilizcepratikkelimeezberi;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mehme on 9.11.2018.
 */

public class BoardFragment extends Fragment {

    private Context context;
    private List<Model> modelListLeft,modelListRight;
    private int widthButton, heightButton;
    private LinearLayout containerLeft,containerRight;
    //private final int buttonColor = getResources().getColor(R.color.colorButton);
    private int x,y;
    private static final int DURATION = 400;
    private Button selectButtonLeft = null;
    private Button selectButtonRight = null;
    private boolean buttonClickEnable = true;

    private View.OnTouchListener touchlistener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            x = (int) event.getX();
            y = (int) event.getY();
            return false;
        }
    };

    private View.OnClickListener onClickListenerLeft = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!buttonClickEnable)
                return;

            if(selectButtonRight !=null){
                //eşleştirme kontrolü
                Toast.makeText(context, "eşleştirme", Toast.LENGTH_SHORT).show();
                selectButtonLeft = (Button) v;
                buttonClickEnable = false;

                if(selectButtonLeft.getId()==selectButtonRight.getId()){
                    revealEffect();
                }else{
                    revealEffectRed();
                }

            }else{
                if(selectButtonLeft != null)
                    selectButtonLeft.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
                    selectButtonLeft = (Button) v;
                    selectButtonLeft.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButtonSelect)));
            }

        }
    };

    private void revealEffectRed() {
    }

    private void revealEffect() {
    }

    private View.OnClickListener onClickListenerRight = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(!buttonClickEnable)
                return;

            if(selectButtonLeft !=null){
                //eşleştirme olacağı zaman
                Toast.makeText(context, "eşleştirme", Toast.LENGTH_SHORT).show();
            }else{
                if(selectButtonRight != null)
                    selectButtonRight.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
                selectButtonRight = (Button) v;
                selectButtonRight.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButtonSelect)));
            }

        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.board_fragment,container,false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        modelListLeft = new ArrayList<>();
        modelListRight = new ArrayList<>();
        containerLeft=((AppCompatActivity)context).findViewById(R.id.containerLeft);
        containerRight=((AppCompatActivity)context).findViewById(R.id.containerRight);

    //Buton genişlik ve yüksek hesaplamaları
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        widthButton = (int) ((size.x)/(2+.4));
        heightButton = (int) ((size.y)/(6+1.5));

        modelListLeft.add(new Model("zero","sıfır",0));
        modelListLeft.add(new Model("one","bir",1));
        modelListLeft.add(new Model("two","iki",2));
        modelListLeft.add(new Model("three","üç",3));
        modelListLeft.add(new Model("four","dört",4));
        modelListLeft.add(new Model("five","beş",5));
        modelListLeft.add(new Model("six","altı",6));
        modelListLeft.add(new Model("seven","yedi",7));
        modelListLeft.add(new Model("eight","sekiz",8));
        modelListLeft.add(new Model("nine","dokuz",9));
        modelListLeft.add(new Model("ten","on",10));

        //Model listesi klonlama
        for (Model model:modelListLeft){
            modelListRight.add(model);
        }

        //Karıştır
        Collections.shuffle(modelListLeft);
        Collections.shuffle(modelListRight);

        //soldaki butonlar konteynıra dolduruluyor.
        for (int i=0; i<6; i++){
            Button button = new Button(context);
            button.setWidth(widthButton);
            button.setHeight(heightButton);
            button.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
            button.setAllCaps(false);
            button.setTextSize(heightButton/7);
            button.setId(modelListLeft.get(0).getId());
            button.setText(modelListLeft.get(0).getKelime());
            button.setOnTouchListener(touchlistener);
            button.setOnClickListener(onClickListenerLeft);
            containerLeft.addView(button);

            modelListLeft.remove(0);
        }

        //sağdaki butonlar konteynıra dolduruluyor.
        for (int i=0; i<6; i++){
            Button button = new Button(context);
            button.setWidth(widthButton);
            button.setHeight(heightButton);
            button.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorButton)));
            button.setAllCaps(false);
            button.setTextSize(heightButton/7);
            button.setId(modelListRight.get(0).getId());
            button.setText(modelListRight.get(0).getAnlam());
            button.setOnTouchListener(touchlistener);
            button.setOnClickListener(onClickListenerRight);
            containerRight.addView(button);

            modelListRight.remove(0);
        }

        girisAnimasyonu();
    }

    private void girisAnimasyonu() {

        for(int i=0; i< containerLeft.getChildCount(); i++){
            Button button = (Button) containerLeft.getChildAt(5-i);
            ObjectAnimator animator = ObjectAnimator.ofFloat(button,"translationX",-widthButton*1.5F,0);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(DURATION + i*70);
            animator.start();
        }
        for(int i=0; i< containerRight.getChildCount(); i++){
            Button button = (Button) containerRight.getChildAt(5-i);
            ObjectAnimator animator = ObjectAnimator.ofFloat(button,"translationX",widthButton*1.5F,0);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(DURATION + i*70);
            animator.start();
        }

    }
}
