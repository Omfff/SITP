package com.example.a.fd.recordDiet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.a.fd.FoodAnalyze;
import com.example.a.fd.R;
import com.example.a.fd.model.DietScore;
import com.example.a.fd.model.Food;
import com.example.a.fd.searchHistory.db.DaoUtils;
import com.example.a.fd.searchHistory.db.FoodMethod;
import com.example.a.fd.searchView.FoodSearchView;
import com.example.a.fd.searchView.SearchResultFragment;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;
import com.oushangfeng.pinnedsectionitemdecoration.callback.OnHeaderClickAdapter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import ezy.ui.view.RoundButton;

public class RecordDiet extends AppCompatActivity {

    public static final int CODE = 1;
    public RecordPresenter recordPresenter;
    private AppBarLayout appBarLayout;
    private RecyclerView recyclerView;
    private PinnedHeaderItemDecoration headerItemDecoration;
    private DietAdapter adapter;
    private List<Food> testData;
    private Button b1,b2,b3;
    private Date displayDate;
    private ImageView arrow;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", /*Locale.getDefault()*/Locale.ENGLISH);

    private CompactCalendarView compactCalendarView;

    private boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recordPresenter = new RecordPresenter(this.getApplicationContext());
        initView();
        testData = new FoodMethod(this).queryByKeyWord("香",0);
        DaoUtils.init(this);
    }

    private void initView(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        appBarLayout = (AppBarLayout)findViewById(R.id.app_bar_layout);
        initCalendarView();
        initRecyclerview();

        final ImageView search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToSearhView();
            }
        });

        arrow = findViewById(R.id.date_picker_arrow);
        RelativeLayout datePickerButton = findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(v -> {
            float rotation = isExpanded ? 0 : 180;
            ViewCompat.animate(arrow).rotation(rotation).start();
            isExpanded = !isExpanded;
            appBarLayout.setExpanded(isExpanded, true);
        });


    }
    private void initCalendarView(){
        compactCalendarView = (CompactCalendarView)findViewById(R.id.compactcalendar_view);
        compactCalendarView.setLocale(TimeZone.getDefault(), Locale.CHINESE);
        compactCalendarView.displayOtherMonthDays(false);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
        setCurrentDate(new Date());
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                String score="";
                if(events != null){
                    for(Event e : events){
                        score = score + " " +e.getData().toString();
                    }
                }
                resetDisplayRecord(dateClicked);
                displayDate = dateClicked;
                setSubtitle(dateFormat.format(dateClicked));
                //收起日历
                float rotation = isExpanded ? 0 : 180;
                ViewCompat.animate(arrow).rotation(rotation).start();
                isExpanded = !isExpanded;
                appBarLayout.setExpanded(isExpanded, true);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                resetDisplayRecord(firstDayOfNewMonth);
                setEventsByMonth(firstDayOfNewMonth);
                setSubtitle(dateFormat.format(firstDayOfNewMonth));
                displayDate = firstDayOfNewMonth;

            }
        });
        setEventsByMonth(compactCalendarView.getFirstDayOfCurrentMonth());
    }

    private void initRecyclerview(){
        recyclerView = findViewById(R.id.diet_recyclerview);

        List<DietInfo>data = new ArrayList<>();
        adapter = new DietAdapter(data);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                RecordPresenter.deleteRecord((DietInfo)adapter.getData().get(position),displayDate);
                ((DietAdapter)adapter).deleteData(position);
                if(RecordPresenter.getScoreByDate(displayDate)==0&&adapter.getItemCount()<=1){
                    compactCalendarView.removeEvents(displayDate);
                }

            }
        });
        final View footerView = getLayoutInflater().inflate(R.layout.record_footer,null);
        final RoundButton roundButton = footerView.findViewById(R.id.analysis_button);
        roundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             jumpToAnalysisResult();
            }
        });
        adapter.addFooterView(footerView);
        adapter.setMyFooterView(footerView);
        List<DietInfo> dietInfos = RecordPresenter.getRecords(displayDate);
        if(dietInfos!=null) {
            adapter.insertData(dietInfos);
        }
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, final View view, final int i) {
                // Log.i("OnItemTouchListener", "onSimpleItemChildClick: " + view.toString());
               /* if (view instanceof CheckBox) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // CheckBox点击后先是触发点击事件，再触发改变选中状态，估计是代码更新了逻辑，这里延时200毫秒
                            CheckBox checkBox = (CheckBox) view;
                            boolean checked = checkBox.isChecked();
                            // Log.i("OnItemTouchListener", "1 onSimpleItemChildClick: " + checked);
                            ((DietInfo)adapter.getData().get(i)).check= checked;
                            if (headerItemDecoration.getPinnedHeaderView() != null && headerItemDecoration.getPinnedHeaderPosition() >= i + adapter
                                    .getHeaderLayoutCount()) {
                                ((CheckBox) headerItemDecoration.getPinnedHeaderView().findViewById(view.getId())).setChecked(checked);
                            }
                        }
                    }, 200);
                }*/
            }
        });

        // 因为添加了1个头部，他是不在clickAdapter.getData这个数据里面的，所以这里要设置数据的偏移值告知ItemDecoration真正的数据索引
        recyclerView.setLayoutManager(new LinearLayoutManager(RecordDiet.this, LinearLayoutManager.VERTICAL, false));
        OnHeaderClickAdapter clickAdapter = new OnHeaderClickAdapter() {

            @Override
            public void onHeaderClick(View view, int id, int position) {
                switch (id) {
                    case R.id.diet_header:
                        // case OnItemTouchListener.HEADER_ID:
                        break;
                    case R.id.checkbox:
                        final CheckBox checkBox = (CheckBox) view;
                        checkBox.setChecked(!checkBox.isChecked());
                        // 刷新ItemDecorations，导致重绘刷新头部
                        recyclerView.invalidateItemDecorations();

                        adapter.getData().get(position).check = checkBox.isChecked();
                        adapter.notifyItemChanged(position + headerItemDecoration.getDataPositionOffset());

                        break;
                }
            }
        };
        headerItemDecoration = new PinnedHeaderItemDecoration.Builder(DietInfo.TYPE_HEADER).setDividerId(R.drawable.divider).enableDivider(true)
                .setClickIds( R.id.diet_header, R.id.checkbox).disableHeaderClick(false).setHeaderClickListener(clickAdapter).create();

        headerItemDecoration.setDataPositionOffset(adapter.getHeaderLayoutCount());
        recyclerView.addItemDecoration(headerItemDecoration);
    }
    private void setCurrentDate(Date date) {
        displayDate = date;
        setSubtitle(dateFormat.format(date));
        if (compactCalendarView != null) {
            compactCalendarView.setCurrentDate(date);
        }
    }

    private void setSubtitle(String subtitle) {

        TextView datePickerTextView = findViewById(R.id.date_picker_text_view);

        if (datePickerTextView != null) {
            datePickerTextView.setText(subtitle);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //noinspection SimplifiableIfStatement
        if (item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void jumpToSearhView(){
        Intent intent = new Intent(RecordDiet.this,FoodSearchView.class);
        intent.putExtra("type",SearchResultFragment.RECORD);
        startActivityForResult(intent,CODE);
    }


    private void jumpToAnalysisResult(){
        RecordPresenter.addRecordToDb(adapter.getData(),displayDate);
        RecordPresenter.insertScore(RecordPresenter.calScore(adapter.getData()),displayDate);
        if(compactCalendarView.getEvents(displayDate)!=null && compactCalendarView.getEvents(displayDate).size()>0) {
            compactCalendarView.removeEvents(displayDate);
        }
        compactCalendarView.addEvent(new Event(Color.RED, displayDate.getTime(), 0));
        List<DietInfo> list = getDietList();
        if(list.size()>0)
        {
            Bundle bundle = new Bundle();
            bundle.putSerializable("list", (Serializable)list);
            bundle.putString("date",displayDate.toString());
            Intent intent = new Intent(RecordDiet.this,FoodAnalyze.class);
            intent.putExtra("record", bundle);
            startActivity(intent);
        }
    }
    private List<DietInfo> getDietList(){
        List<DietInfo> list = adapter.getData();
        List<DietInfo> resultList = new ArrayList<>();
        if(list!=null){
            for(DietInfo diet :list){
                if(diet.getItemType()==DietInfo.TYPE_DATA){
                    resultList.add(diet);
                }
            }
        }
        return resultList;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (resultCode){
            case RESULT_OK:
                //获得一个List<DietInfo>
                Bundle bundle = data.getBundleExtra("record");
                List<DietInfo> newDiet =(ArrayList<DietInfo>)  bundle.getSerializable("record");
                adapter.insertData(newDiet);
                RecordPresenter.insertMultiRecords(newDiet,displayDate);
                if(compactCalendarView.getEvents(displayDate)==null || compactCalendarView.getEvents(displayDate).size()==0) {
                    compactCalendarView.addEvent(new Event(Color.RED,displayDate.getTime()));
                    RecordPresenter.insertScore(RecordPresenter.calScore(adapter.getData()),displayDate);
                }
                break;
            default:
                break;
        }

    }
    private void resetDisplayRecord(Date date){
        if (!dateFormat.format(date).equals(displayDate)){
            adapter.removeAll();
            adapter.footerView.setVisibility(View.GONE);
            List<DietInfo> dietInfos = RecordPresenter.getRecords(date);
            if(dietInfos!=null) {
                adapter.insertData(dietInfos);
            }
        }
    }
    private void setEventsByMonth(Date firstDateOfMonth){
        List<DietScore> dateList = RecordPresenter.getRecordsByMonth(firstDateOfMonth);
        if(dateList!=null){
            for (DietScore dietScore :dateList){
                if(compactCalendarView.getEvents(dietScore.getDate()).size()!= 0) {
                   compactCalendarView.removeEvents(dietScore.getDate());
                }
                Event event = new Event(Color.RED, dietScore.getDate().getTime(), dietScore.getScore());
                compactCalendarView.addEvent(event);
            }
        }
    }
}
