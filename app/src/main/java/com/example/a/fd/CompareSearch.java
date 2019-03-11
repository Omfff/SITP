package com.example.a.fd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a.fd.model.SearchKeyword;
import com.example.a.fd.searchHistory.db.BaseDaoMethod;
import com.example.a.fd.searchHistory.db.DaoManager;
import com.example.a.fd.searchHistory.db.DaoUtils;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompareSearch extends AppCompatActivity {

    @BindView(R.id.query_one) Button queryOne;
    @BindView(R.id.query_all) Button queryAll;
    @BindView(R.id.delete_all)Button deleteAll;
    @BindView(R.id.insert_one)Button insertOne;
    @BindView(R.id.update_one)Button updateOne;
    @BindView(R.id.delete_one)Button deleteOne;
    @BindView(R.id.key_id) EditText keyWord;

    private BaseDaoMethod<SearchKeyword> keywordBaseDaoMethod;
    private long count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_search);
        keywordBaseDaoMethod = new BaseDaoMethod<>(this.getApplicationContext());
        ButterKnife.bind(this);
        Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.query_all,R.id.query_one,R.id.delete_one,R.id.update_one,R.id.delete_all,R.id.insert_one})
    public void onClick(View v){
        //Toast.makeText(this,"click",Toast.LENGTH_SHORT).show();
        SearchKeyword temp = new SearchKeyword();
        SearchKeyword keyword = new SearchKeyword();
        List<SearchKeyword> tempList;
        String db = "";
        if(keyWord.getText()!=null && !keyWord.getText().toString().equals("")){
            keyword.setId(Long.parseLong(keyWord.getText().toString()));
        }
        switch(v.getId()){
            case R.id.query_all:
                tempList =  keywordBaseDaoMethod.QueryAll(SearchKeyword.class);
                if(tempList != null) {
                    for(SearchKeyword e :tempList)
                        db = db + e.getKeyword() + "\n";
                }
                Toast.makeText(this, "query_all: " + db, Toast.LENGTH_SHORT).show();
                break;
            case R.id.query_one:
                keyword.setKeyword("hello world" + keyword.getId());
                temp = keywordBaseDaoMethod.QueryById(keyword.getId(),SearchKeyword.class);
                if(temp != null) {
                    Toast.makeText(this, "query_one: " +temp.getKeyword(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"not exsited",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.delete_one:
                keywordBaseDaoMethod.deleteObject(keyword);
                //count--;
                break;
            case R.id.update_one:
                keyword.setKeyword("update" + (keyword.getId() + 1));
                keywordBaseDaoMethod.updateObject(keyword);
                break;
            case R.id.delete_all:
                keywordBaseDaoMethod.deleteAll(SearchKeyword.class);
                count = 1;
                break;
             case R.id.insert_one:
                 //keyword.setId();
                 keyword.setKeyword("hello world" + count);
                 keywordBaseDaoMethod.insertObject(keyword);
                 count++;
                 break;
             default:
                 break;
        }
    }
}
