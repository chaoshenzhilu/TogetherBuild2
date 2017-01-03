package com.bwf.togetherbuild.utils.json;

/**
 * Created by Fanhy on 2016/12/5 0005.
 */

public interface JsonParse {
    <T extends Class> T parseJson(String json, T t);
    String generateJson(Object t);
}
