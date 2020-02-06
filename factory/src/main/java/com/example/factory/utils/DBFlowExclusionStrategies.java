package com.example.factory.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

public class DBFlowExclusionStrategies implements ExclusionStrategy {
    //被跳过的字段
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getDeclaredClass().equals(ModelAdapter.class);
    }
    // 被跳过的class
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
