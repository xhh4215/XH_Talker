package com.example.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * 数据库的基本信息
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
public class AppDataBase {
    public static final String NAME = "AppDataBase";
    public static final int VERSION = 2;
}
