package com.wiseassblog.samsaradayplanner.ui.managetaskview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wiseassblog.samsaradayplanner.R;
import com.wiseassblog.samsaradayplanner.domain.constants.COLOR;

public class ColorPickerView extends ConstraintLayout implements View.OnClickListener {

    ImageButton darkBlue, burntOrange, green, red, darkLime, lightBlue, mauve, brown, teal;
    private ColorPickerViewClickHandler callback;

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.imb_dark_blue:
                callback.onColorSelected(COLOR.DARK_BLUE);
                break;
            case R.id.imb_burnt_orange:
                callback.onColorSelected(COLOR.BURNT_ORANGE);
                break;
            case R.id.imb_green:
                callback.onColorSelected(COLOR.GREEN);
                break;
            case R.id.imb_red:
                callback.onColorSelected(COLOR.DARK_RED);
                break;
            case R.id.imb_dark_lime:
                callback.onColorSelected(COLOR.DARK_LIME);
                break;
            case R.id.imb_light_blue:
                callback.onColorSelected(COLOR.LIGHT_BLUE);
                break;
            case R.id.imb_mauve:
                callback.onColorSelected(COLOR.MAUVE);
                break;
            case R.id.imb_brown:
                callback.onColorSelected(COLOR.BROWN);
                break;
            case R.id.imb_teal:
                callback.onColorSelected(COLOR.TEAL);
                break;
        }
    }

    interface ColorPickerViewClickHandler {
        void onColorSelected(COLOR color);
    }

    public void setCallback(ColorPickerViewClickHandler callback) {
        this.callback = callback;
    }


    public ColorPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        build();
    }

    private void build() {
        inflate(getContext(), R.layout.view_color_picker_sheet, this);

        darkBlue = findViewById(R.id.imb_dark_blue);
        darkBlue.setOnClickListener(this);

        burntOrange = findViewById(R.id.imb_burnt_orange);
        burntOrange.setOnClickListener(this);

        green = findViewById(R.id.imb_green);
        green.setOnClickListener(this);

        red = findViewById(R.id.imb_red);
        red.setOnClickListener(this);

        darkLime = findViewById(R.id.imb_dark_lime);
        darkLime.setOnClickListener(this);

        lightBlue = findViewById(R.id.imb_light_blue);
        lightBlue.setOnClickListener(this);

        mauve = findViewById(R.id.imb_mauve);
        mauve.setOnClickListener(this);

        brown = findViewById(R.id.imb_brown);
        brown.setOnClickListener(this);

        teal = findViewById(R.id.imb_teal);
        teal.setOnClickListener(this);
    }
}
