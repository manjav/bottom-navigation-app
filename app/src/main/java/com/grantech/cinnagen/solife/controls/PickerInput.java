package com.grantech.cinnagen.solife.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.grantech.cinnagen.solife.R;

/**
 * TODO: document your custom view class.
 */
public class PickerInput extends ConstraintLayout implements ConstraintLayout.OnClickListener
{
    private String promptString; // TODO: use a default from R.string...
    private TextView promptView;

    private String textString; // TODO: use a default from R.string...
    private TextView textView;
    private OnClickListener clickListener;

    public PickerInput(Context context)
    {
        super(context);
        this.init(context, null, 0);
    }

    public PickerInput(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.init(context, attrs, 0);
    }

    public PickerInput(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle)
    {
        inflate(context, R.layout.picker_input, this);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PickerInput, defStyle, 0);
        this.promptString = a.getString( R.styleable.PickerInput_prompt);
        this.textString = a.getString( R.styleable.PickerInput_text);
        a.recycle();

        // Set up a default TextPaint object
        this.promptView = this.findViewById(R.id.prompt_display);
        this.promptView.setText(this.promptString);

        this.textView = this.findViewById(R.id.text_display);
        this.textView.setText(this.textString);

        this.findViewById(R.id.hit_object).setOnClickListener(this);
    }

    @Override
    public void setOnClickListener(@androidx.annotation.Nullable OnClickListener listener)
    {
        this.clickListener = listener;
        super.setOnClickListener(listener);
    }

    @Override
    public void onClick(View v)
    {
        if( this.clickListener != null )
            this.clickListener.onClick(this);
    }

    /**
     * Gets the text string attribute value.
     *
     * @return The text string attribute value.
     */
    public String getText() {
        return textString;
    }
    /**
     * Sets the view's text string attribute value. In the text view, this string
     * is the text to draw.
     *
     * @param text The text string attribute value to use.
     */
    public void setText(String text)
    {
        this.textString = text;
        textView.setText(this.textString);
    }

    /**
     * Gets the prompt string attribute value.
     *
     * @return The prompt string attribute value.
     */
    public String getPrompt() {
        return promptString;
    }
    /**
     * Sets the view's prompt string attribute value. In the prompt view, this string
     * is the text to draw.
     *
     * @param prompt The prompt string attribute value to use.
     */
    public void setPrompt(String prompt)
    {
        this.promptString = prompt;
        promptView.setText(this.promptString);
    }
}
