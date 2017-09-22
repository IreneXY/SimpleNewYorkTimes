package com.mintminter.simplenewyorktimes.interfaces;

import com.mintminter.simplenewyorktimes.models.NYTSearchResult;

/**
 * Created by Irene on 9/21/17.
 */

public interface ApiCallback {
    void setSearchResult(NYTSearchResult searchResult);
    void setFailure(int statusCode, String res);
}
