package com.example.myapplication.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchHelper {
    public static List<String> match(String data, String p, int position) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            list.add(matcher.group(position));
        }
        return list;
    }
}
