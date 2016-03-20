package com.coolweather.app.model;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.db.CoolWeatherOpenHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {

	/**
	 * 数据库名
	 */
	public static final String DB_NAME="cool_weather";
	
	public static final int VERSION=1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	/**
	 * 将构造函数私有化
	 * @param context
	 */
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dpHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db=dpHelper.getWritableDatabase();
	}
	
	/**
	 * @param context
	 * @return 返回一个CoolWeatherDB的对象
	 */
	public synchronized static  CoolWeatherDB getInstance(Context context){
		if (coolWeatherDB==null) {
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	/**
	 * @param province
	 * 将Province实例存放到数据库中
	 */
	public void saveProvince(Province province){
		if (province!=null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province",null,values);
			
		}
	}
	public List<Province> loadProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		do{
			if (cursor.moveToFirst()) {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}
		}while(cursor.moveToNext());
		if (cursor!=null) {
			cursor.close();
		}
		return list;
	}
	/**
	 * 将City实例存放到数据库中
	 */
	public void saveCity(City city){
		if (city!=null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			db.insert("City",null,values);
			
		}
	}
	/**
	 * @param provinceId
	 * @return
	 * 查询某个省的所有城市
	 */
	public List<City> loadCities(int provinceId){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
		do{
			if (cursor.moveToFirst()) {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				list.add(city);
			}
		}while(cursor.moveToNext());
		if (cursor!=null) {
			cursor.close();
		}
		return list;
	}
	/**
	 * 将County实例存放到数据库中
	 */
	public void saveCounty(County county){
		if (county!=null) {
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			db.insert("County",null,values);
			
		}
	}
	/**
	 * @param provinceId
	 * @return
	 * 读取数据库中某个市的所有县
	 */
	public List<County> loadCounties(int cityId){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
		do{
			if (cursor.moveToFirst()) {
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				list.add(county);
			}
		}while(cursor.moveToNext());
		if (cursor!=null) {
			cursor.close();
		}
		return list;
	}
}
