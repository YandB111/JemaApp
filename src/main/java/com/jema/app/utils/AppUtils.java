/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 07-May-2023
*
*/

package com.jema.app.utils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class AppUtils {

	public static <T> List<T> parseTuple(List<Tuple> data, Class<T> returnclass, Gson gson) {
		List<T> finallist = new ArrayList<>();
		for (Tuple tuple : data) {

			JSONObject object = new JSONObject();
			for (int i = 0; i < tuple.getElements().size(); i++) {
				try {
					object.put(tuple.getElements().get(i).getAlias(), tuple.get(tuple.getElements().get(i).getAlias()));
				} catch (Exception e) {
					try {
						object.put(tuple.getElements().get(i).getAlias(), "");
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
			}
			T modal = null;
			try {
				modal = gson.fromJson(object.toString(), returnclass);
			} catch (Exception e) {
				e.printStackTrace();
			}
			finallist.add(modal);
		}
		return finallist;
	}
}
