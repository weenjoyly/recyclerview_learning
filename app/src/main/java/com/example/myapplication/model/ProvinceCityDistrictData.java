package com.example.myapplication.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProvinceCityDistrictData {

    private static final String TAG = "DataModelProcess";

    public interface ICallback {
        boolean isCity(String data);
    }

    private HashMap<String, String> mNameMap;//城市到省的map 合肥到安徽的映射
    private List<String> mCityItemList;//具体每一个城市
    private Map<String, List<String>> mProvinceCityMap;//具体每一个城市

    private HashMap<String, List<String>> cityDistrictMap = new HashMap<>();//每一个市区有哪些区县
    private final static HashMap<String, String> SPECIAL_HASH_MAP = new HashMap<>();

    public HashMap<String, String> nameMap() {
        return mNameMap;
    }

    public Map<String, List<String>> provinceCityMap() {
        return mProvinceCityMap;
    }

    public Map<String, List<String>> cityDistrictMap() {
        return cityDistrictMap;
    }

    public List<String> itemList() {
        return mCityItemList;
    }

    private void initData(List<String> listProvince) {
        processData(listProvince, new ICallback() {
            @Override
            public boolean isCity(String data) {
                return data.startsWith("<span");
            }
        });
    }

    static {
        SPECIAL_HASH_MAP.put("11", "北京市");
        SPECIAL_HASH_MAP.put("12", "天津市");
        SPECIAL_HASH_MAP.put("31", "上海市");
        SPECIAL_HASH_MAP.put("50", "重庆市");
        SPECIAL_HASH_MAP.put("71", "台湾省");
        SPECIAL_HASH_MAP.put("81", "香港特别行政区");
        SPECIAL_HASH_MAP.put("82", "澳门特别行政区");
    }


    /**
     * @param listProvince     省市列表
     * @param listProvinceCode 省市列表代码
     * @param listDistrict     区县列表
     * @param listDistrictCode 区县列表代码
     */
    public void updateProvince(List<String> listProvince, List<String> listProvinceCode, List<String> listDistrict, List<String> listDistrictCode) {
        initData(listProvince);
        //城市到区县的映射
        HashMap<String, List<String>> cityDistrictMap = new HashMap<>();
        for (int i = 0; i < listDistrict.size(); i++) {
            String code = listDistrictCode.get(i);
            String district = listDistrict.get(i);
            String cityCode = code.substring(0, 4) + "00";
            String special = SPECIAL_HASH_MAP.get(cityCode.substring(0, 2));
            String city;
            if (special == null) {
                int index = listProvinceCode.indexOf(cityCode);
                if (index == -1) {
                    Log.d(TAG, "updateProvince: " + cityCode);
                    continue;
                }
                city = listProvince.get(index);
                if (city.length() > 39) {
                    city = city.substring(39);
                }
            } else {
                city = special;
            }
            if (cityDistrictMap.get(city) == null) {
                ArrayList<String> list = new ArrayList();
                cityDistrictMap.put(city, list);
                list.add(district);
            } else {
                cityDistrictMap.get(city).add(district);
            }
        }
        this.cityDistrictMap = cityDistrictMap;
    }



    private void processData(List<String> dataList, ICallback callback) {
        mNameMap = new HashMap<>();
        mCityItemList = new ArrayList<>();
        mCityItemList.add("北京市");
        mNameMap.put("北京市", "北京市");
        mCityItemList.add("天津市");
        mNameMap.put("天津市", "天津市");
        mCityItemList.add("上海市");
        mNameMap.put("上海市", "上海市");
        mCityItemList.add("重庆市");
        mNameMap.put("重庆市", "重庆市");
        String currentName = null;
        mProvinceCityMap = new HashMap<>();
        List<String> currentCityList = null;
        for (String data : dataList) {
            if (callback.isCity(data)) {
                if (data.length() > 39) {
                    data = data.substring(39);
                }
                mCityItemList.add(data);
                currentCityList.add(data);
            } else {
                currentName = data;
                currentCityList = new ArrayList<>();
                mProvinceCityMap.put(currentName, currentCityList);
            }
            mNameMap.put(data, currentName);
        }
    }
}
