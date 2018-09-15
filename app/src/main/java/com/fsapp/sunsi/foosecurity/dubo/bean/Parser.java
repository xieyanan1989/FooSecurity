package com.fsapp.sunsi.foosecurity.dubo.bean;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * @author Messi
 *
 * @param <T>
 */
public interface Parser<T extends CpType> {

	public abstract T parse(JSONObject json) throws JSONException;
}
