package com.example.myapplication.openclose;

import com.example.myapplication.model.MatchHelper;
import com.example.myapplication.model.ProvinceCityDistrictData;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ProvinceDataProcessImpl {
    public ProvinceCityDistrictData processData() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://www.mca.gov.cn/article/sj/xzqh/1980/201903/201903011447.html";
        String data = null;
        try {
            data = client.newCall(new Request.Builder().url(url).build()).execute().body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        final String province = "<td class=xl723852>([\\D\\W]+)</td>";
        final String provinceCode = "<td class=xl723852>([0-9]{6})</td>";
        String district = "<td class=xl733852>([0-9]+)</td>[\\s\\S]+?<td class=xl733852><span style.*</span>(.+)</td>";
        final List<String> listProvince = MatchHelper.match(data, province, 1);
        final List<String> listProvinceCode = MatchHelper.match(data, provinceCode, 1);
        final List<String> listDistrict = MatchHelper.match(data, district, 2);
        final List<String> listDistrictCode = MatchHelper.match(data, district, 1);
        ProvinceCityDistrictData process = new ProvinceCityDistrictData();
        process.updateProvince(listProvince, listProvinceCode, listDistrict, listDistrictCode);
        return process;
    }
}
