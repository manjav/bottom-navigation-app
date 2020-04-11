package com.grantech.cinnagen.solife.controls;
/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.grantech.cinnagen.solife.R;

public class CheckableItem extends ConstraintLayout implements Checkable
{
    private ImageView checkImage;
    private TextView labelText;

    public CheckableItem(Context context)
    {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_checkable_group, this, true);
        checkImage = view.findViewById(R.id.checkable_image);
        labelText = view.findViewById(R.id.checkable_text);
        this.setBackgroundResource(R.color.colorPrimaryLight);

    }

    public void setLabel(String label)
    {
        this.labelText.setText(label);
//        measureText();
        // my custom method where I set package id, date, and time
    }

/*
    public void measureText()
    {
        labelText.invalidate();

//      int newHeight = labelText.getLineCount() * (int) (labelText.getPaint().getFontMetrics().bottom - labelText.getPaint().getFontMetrics().top);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) labelText.getLayoutParams();
        this.labelText.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        Log.i(Fragments.TAG, labelText.getMeasuredHeight() + " " + lp.topMargin + " " + lp.bottomMargin + " " + labelText.getLineCount() + " " + labelText.getLineHeight() + " " + labelText.getPaint().getFontMetrics().bottom + " " + labelText.getPaint().getFontMetrics().top);
    }
*/
    private Boolean checked = true;

    @Override
    public boolean isChecked()
    {
        return checked;
        // if I had checkbox in my layout I could
        // return testCheckBox.checked();
    }

    @Override
    public void setChecked(boolean checked)
    {
        if( this.checked == checked )
            return;
        this.checked = checked;

        // since I choose not to have check box in my layout, I change background color
        // according to checked state
        this.checkImage.setVisibility(this.checked ? View.VISIBLE : View.INVISIBLE);
        this.setBackgroundResource(this.checked ? R.color.colorPrimaryLight : R.color.colorWhite);
    }

    @Override
    public void toggle()
    {
        setChecked(!checked);
        // if I had checkbox in my layout I could
        // return testCheckBox.toggle();
    }

}
