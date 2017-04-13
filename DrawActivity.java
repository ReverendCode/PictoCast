package com.example.reverendcode.pictocast;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class DrawActivity extends AppCompatActivity {

    private DrawingView drawView;
    private ImageButton currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        drawView = (DrawingView)findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paintLayout);
        currentColor = (ImageButton)paintLayout.getChildAt(0);
        currentColor.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
    }

    public void paintClicked(View view) {
        if (view != currentColor) {
            ImageButton selectedButton = (ImageButton)view;
            String colorValue = view.getTag().toString();
            drawView.setColor(colorValue);
            selectedButton.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currentColor.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currentColor = (ImageButton)view;
        }
    }
}
