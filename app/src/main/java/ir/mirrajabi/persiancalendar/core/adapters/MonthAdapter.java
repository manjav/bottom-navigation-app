package ir.mirrajabi.persiancalendar.core.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grantech.cinnagen.solife.R;

import java.util.List;

import ir.mirrajabi.persiancalendar.core.Constants;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.fragments.MonthFragment;
import ir.mirrajabi.persiancalendar.core.models.Day;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {
    private Context mContext;
    private MonthFragment mMonthFragment;
    private final int TYPE_HEADER = 0;
    private final int TYPE_DAY = 1;
    private List<Day> mDays;
    private int itemSize;
    private int mSelectedDay = -1;
    private PersianCalendarHandler mCalendarHandler;
    private final int mFirstDayOfWeek;
    private final int mTotalDays;

    public MonthAdapter(Context context, MonthFragment monthFragment, List<Day> days) {
        this.itemSize = (int) (context.getResources().getDisplayMetrics().density * 40);
        mFirstDayOfWeek = days.get(0).getDayOfWeek();
        mTotalDays = days.size();
        this.mMonthFragment = monthFragment;
        this.mContext = context;
        this.mDays = days;
        mCalendarHandler = PersianCalendarHandler.getInstance(context);
    }

    public void clearSelectedDay() {
        mSelectedDay = -1;
        notifyDataSetChanged();
    }

    public void selectDay(int dayOfMonth) {
//        mSelectedDay = dayOfMonth + 6 + mFirstDayOfWeek;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private String text;
        private int textColor = Color.DKGRAY;
        private int backroundId = -1;
        private final FrameLayout layout;
        private final TextView textView;
        private View backgroundView;
//        View mToday;
//        View mSelectDay;
//        View mEvent;

        ViewHolder(View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.day_layout);
            textView = new TextView(itemView.getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(this.textColor);
            mCalendarHandler.setFont(textView);
            layout.addView(textView);

            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            position += 6 - (position % 7) * 2;
            if( mTotalDays < position - 6 - mFirstDayOfWeek )
                return;

            if (position - 7 - mFirstDayOfWeek >= 0) {
                mMonthFragment.onClickItem(mDays.get(position - 7 - mFirstDayOfWeek).getPersianDate());

//                notifyDataSetChanged();
            }
        }

        public void setTextColor(int textColor) {
            if( this.textColor == textColor )
                return;
            this.textView.setTextColor(this.textColor = textColor);
        }

        public void setText(String text) {
            if( this.text == text )
                return;
            this.textView.setText(this.text = text);
        }

        public void setBackground(int backroundId)
        {
            if( this.backroundId == backroundId )
                return;

            this.backroundId = backroundId;

            if( this.backroundId == 0 )
            {
                if( this.backgroundView != null )
                    this.backgroundView.setVisibility(View.GONE);
                return;
            }

            if( this.backgroundView == null )
            {
                this.backgroundView = new View(this.itemView.getContext());
                float d = this.itemView.getResources().getDisplayMetrics().density;
                this.layout.addView(this.backgroundView, 0, new FrameLayout.LayoutParams(itemSize, itemSize, Gravity.CENTER));
            }
            else
            {
                this.backgroundView.setVisibility(View.VISIBLE);
            }

            this.backgroundView.setBackgroundResource(backroundId);

//            mToday.setVisibility(isToday ? View.VISIBLE : View.GONE);
        }
    }

    @NonNull
    @Override
    public MonthAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_day, parent, false));
    }

    @Override
    public void onBindViewHolder(MonthAdapter.ViewHolder holder, int position) {
//        Log.i("a", position+"");
//        holder.mToday.setBackgroundResource(mCalendarHandler.getTodayBackground());
//        holder.mSelectDay.setBackgroundResource(mCalendarHandler.getSelectedDayBackground());
//        holder.mEvent.setBackgroundColor(mCalendarHandler.getColorEventUnderline());
        position += 6 - (position % 7) * 2;
        if( mTotalDays < position - 6 - mFirstDayOfWeek )
            return;

        if (!isPositionHeader(position)) {
            if (position - 7 - mFirstDayOfWeek >= 0) {
                holder.setText(mDays.get(position - 7 - mDays.get(0).getDayOfWeek()).getNum());
//                holder.textView.setVisibility(View.VISIBLE);

//                holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP  , mCalendarHandler.getDaysFontSize());

//                if (mDays.get(position - 7 - mFirstDayOfWeek).isHoliday()) {
//                    holder.setTextColor(mCalendarHandler.getColorHoliday());
//                } else {
//                    holder.setTextColor(mCalendarHandler.getColorNormalDay());
//                }

                Day day = mDays.get(position - 7 - mFirstDayOfWeek);
//                if (day.isEvent()) {
//                    if(day.isLocalEvent() && mCalendarHandler.isHighlightingLocalEvents())
//                        holder.mEvent.setVisibility(View.VISIBLE);
//                    else if(!day.isLocalEvent() && mCalendarHandler.isHighlightingOfficialEvents())
//                        holder.mEvent.setVisibility(View.VISIBLE);
//                    else holder.mEvent.setVisibility(View.GONE);
//                } else {
//                    holder.mEvent.setVisibility(View.GONE);
//                }


                holder.setBackground(getBackgroundId(day));
                holder.setTextColor(day.isEvent() && (day.isNext() || day.isToday()) ? Color.WHITE : mCalendarHandler.getColorNormalDay());

//                if (position == mSelectedDay) {
//                    holder.mSelectDay.setVisibility(View.VISIBLE);
//
//                    if (mDays.get(position - 7 - mFirstDayOfWeek).isHoliday()) {
//                        holder.textView.setTextColor(mCalendarHandler.getColorHolidaySelected());
//                    } else {
//                        holder.textView.setTextColor(mCalendarHandler.getColorNormalDaySelected());
//                    }
//                } else {
//                    holder.mSelectDay.setVisibility(View.GONE);
//                }

            } else {
//                holder.mToday.setVisibility(View.GONE);
//                holder.mSelectDay.setVisibility(View.GONE);
//                holder.textView.setVisibility(View.GONE);
//                holder.mEvent.setVisibility(View.GONE);
            }
//            mCalendarHandler.setFontAndShape(holder.textView);
        } else {
            holder.setText(Constants.FIRST_CHAR_OF_DAYS_OF_WEEK_NAME[position]);
//            holder.setTextColor(mCalendarHandler.getColorDayName());
//            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mCalendarHandler.getHeadersFontSize());
//            holder.textView.setTypeface(mCalendarHandler.getHeadersTypeface());
//            holder.mToday.setVisibility(View.GONE);
//            holder.mSelectDay.setVisibility(View.GONE);
//            holder.mEvent.setVisibility(View.GONE);
//            holder.textView.setVisibility(View.VISIBLE);
//            mCalendarHandler.setFont(holder.textView);
        }
    }

    private int getBackgroundId(Day day)
    {
        if( day.isEvent() && day.isToday() )
            return day.isNext() ? R.drawable.circle_event_today : R.drawable.circle_event_prev_today;
        if( day.isEvent()  )
            return day.isNext() ? R.drawable.circle_event_next : R.drawable.circle_event_prev;
        if( day.isToday() )
            return R.drawable.circle_today;
        return 0;
    }

    @Override
    public int getItemCount() {
        return 7 * 7;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        } else {
            return TYPE_DAY;
        }
    }

    private boolean isPositionHeader(int position) {
        return position < 7;
    }
}