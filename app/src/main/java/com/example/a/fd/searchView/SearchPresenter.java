package com.example.a.fd.searchView;

import com.example.a.fd.model.SearchKeyword;
import com.example.a.fd.searchHistory.db.BaseDaoMethod;

/**
 * @Package: com.example.a.fd.searchView
 * @ClassName: SearchPresenter
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/24 22:07
 */
public class SearchPresenter {
    private BaseDaoMethod<SearchKeyword> keywordBaseDaoMethod;
    public SearchPresenter(BaseDaoMethod<SearchKeyword> keywordBaseDaoMethod ){
        this.keywordBaseDaoMethod = keywordBaseDaoMethod;
    }
    public void deleteOneKeyWord(SearchKeyword searchKeyword){
        keywordBaseDaoMethod.deleteObject(searchKeyword);
    }
    public void deleteAllKeyWord(){
        keywordBaseDaoMethod.deleteAll(SearchKeyword.class);
    }
}
