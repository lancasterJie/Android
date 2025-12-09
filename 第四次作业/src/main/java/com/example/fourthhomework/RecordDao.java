package com.example.fourthhomework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class RecordDao {
    private MyDbHelper dbHelper;

    // 初始化数据库帮助类
    public RecordDao(Context context) {
        dbHelper = new MyDbHelper(context);
    }

    /**
     * 新增一条记录
     * @param record 要保存的记录对象
     * @return 新增记录的主键 _id（失败返回 -1）
     */
    public long addRecord(Record record) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDbHelper.COL_TITLE, record.getTitle());
        values.put(MyDbHelper.COL_CONTENT, record.getContent());
        values.put(MyDbHelper.COL_TIME, record.getTime());

        // insert() 方法返回新增记录的 _id，失败返回 -1
        long id = db.insert(MyDbHelper.TABLE_NAME, null, values);
        //db.close(); // 关闭数据库，释放资源
        return id;
    }

    /**
     * 查询所有记录（按时间倒序，最新的在前面）
     * @return 所有记录的列表
     */
    public List<Record> getAllRecords() {
        List<Record> recordList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 查询所有数据，按 _id 倒序（因为 _id 自增，最新的 id 最大）
        Cursor cursor = db.query(
                MyDbHelper.TABLE_NAME,
                null, // 查询所有字段
                null, // 无查询条件
                null, // 无查询参数
                null, // 无分组
                null, // 无过滤
                MyDbHelper.COL_ID + " DESC" // 排序：按 _id 倒序
        );

        // 遍历游标，封装成 Record 对象
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MyDbHelper.COL_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COL_TITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COL_CONTENT));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COL_TIME));
                recordList.add(new Record(id, title, content, time));
            } while (cursor.moveToNext());
        }

        // 关闭游标和数据库
        cursor.close();
        //db.close();
        return recordList;
    }

    /**
     * 根据 _id 查询单条记录（用于详情/编辑）
     * @param id 记录的主键 _id
     * @return 对应的记录对象（不存在返回 null）
     */
    public Record getRecordById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                MyDbHelper.TABLE_NAME,
                null,
                MyDbHelper.COL_ID + " = ?", // 查询条件：_id = ?
                new String[]{String.valueOf(id)}, // 参数：id 的字符串形式
                null,
                null,
                null
        );

        Record record = null;
        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COL_TITLE));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COL_CONTENT));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COL_TIME));
            record = new Record(id, title, content, time);
        }

        cursor.close();
        //db.close();
        return record;
    }

    /**
     * 编辑一条记录（根据 _id 更新）
     * @param record 包含新内容和 _id 的记录对象
     * @return 是否更新成功（true/false）
     */
    public boolean updateRecord(Record record) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDbHelper.COL_TITLE, record.getTitle());
        values.put(MyDbHelper.COL_CONTENT, record.getContent());
        values.put(MyDbHelper.COL_TIME, record.getTime()); // 编辑时更新时间为当前时间

        // update() 方法返回受影响的行数，>0 表示成功
        int rowsAffected = db.update(
                MyDbHelper.TABLE_NAME,
                values,
                MyDbHelper.COL_ID + " = ?",
                new String[]{String.valueOf(record.get_id())}
        );

        //db.close();
        return rowsAffected > 0;
    }

    /**
     * 根据 _id 删除一条记录
     * @param id 记录的主键 _id
     * @return 是否删除成功（true/false）
     */
    public boolean deleteRecord(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete() 方法返回受影响的行数，>0 表示成功
        int rowsAffected = db.delete(
                MyDbHelper.TABLE_NAME,
                MyDbHelper.COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        //db.close();
        return rowsAffected > 0;
    }
}