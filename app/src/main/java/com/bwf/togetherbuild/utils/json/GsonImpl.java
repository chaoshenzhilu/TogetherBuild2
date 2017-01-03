package com.bwf.togetherbuild.utils.json;

import com.google.gson.Gson;

/**
 * Created by Fanhy on 2016/12/5 0005.
 */

public class GsonImpl implements JsonParse {
    private Gson gson = new Gson();
    @Override
    public <T extends Class> T parseJson(String json, T t) {
        return (T)gson.fromJson(json, t);
    }

    @Override
    public String generateJson(Object t) {
        return gson.toJson(t);
    }
}
