package com.fsapp.sunsi.foosecurity.dubo.bean;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractParser<T extends CpType> implements Parser<T> {

	public abstract T parse(JSONObject json) throws JSONException;

}
