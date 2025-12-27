<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".EditActivity">

    <EditText
        android:id="@+id/edit_title"
        android:layout_marginTop="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <EditText
        android:id="@+id/edit_content"
        android:layout_marginTop="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <EditText
        android:id="@+id/edit_time"
        android:layout_marginTop="20dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <Button
        android:id="@+id/edit_button"
        android:layout_marginTop="50dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="编辑完成，保存"/>

    <Button
        android:id="@+id/取消"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="取消编辑"/>


</LinearLayout>
