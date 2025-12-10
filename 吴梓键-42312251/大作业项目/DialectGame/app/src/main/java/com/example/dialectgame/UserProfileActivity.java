package com.example.dialectgame;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.dialectgame.database.AppDatabase;
import com.example.dialectgame.model.Follow;
import com.example.dialectgame.model.User;
import com.example.dialectgame.utils.UserManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserProfileActivity extends AppCompatActivity {
    private static final int REQUEST_GALLERY = 1001;
    private static final int REQUEST_CAMERA = 1002;
    private static final int PERMISSION_REQUEST_CODE = 1003;
    private String currentPhotoPath;
    private TextView tvUsername, tvFollowingCount, tvFollowerCount;
    private EditText etNickname, etHometown, etEmail;
    private Button btnUpdate, btnLogout, btnBack;
    private ImageView ivAvatar, ivEditAvatar;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private LinearLayout llFollowing, llFollowers;
    private User currentUser;
    private boolean isEditing = false;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        db = AppDatabase.getInstance(this);

        // 检查登录状态
        UserManager userManager = UserManager.getInstance(this);
        if (!userManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        currentUser = userManager.getCurrentUser();

        // 初始化UI
        initViews();

        // 设置用户信息
        setUserInfo();

        // 加载关注和粉丝数量
        loadFollowStats();

        ivEditAvatar.setOnClickListener(v -> checkPermissionAndChooseAvatar());

        // 返回按钮点击事件
        btnBack.setOnClickListener(v -> finish());

        // 更新/保存信息按钮点击事件
        btnUpdate.setOnClickListener(v -> {
            if (isEditing) {
                saveUserInfo();
            } else {
                enterEditMode();
            }
        });

        // 退出登录
        btnLogout.setOnClickListener(v -> {
            userManager.logout();
            Toast.makeText(this, "已退出登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // 关注列表点击事件
        llFollowing.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserListActivity.class);
            intent.putExtra("TYPE", "following");
            intent.putExtra("USER_ID", currentUser.getId());
            startActivity(intent);
        });

        // 粉丝列表点击事件
        llFollowers.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserListActivity.class);
            intent.putExtra("TYPE", "followers");
            intent.putExtra("USER_ID", currentUser.getId());
            startActivity(intent);
        });
    }
    private void checkPermissionAndChooseAvatar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_CODE);
            } else {
                showImagePickerDialog();
            }
        } else {
            showImagePickerDialog();
        }
    }

    // 显示选择对话框：从相册或相机选择
    private void showImagePickerDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("选择头像来源");
        String[] options = {"从相册选择", "拍照"};
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                openGallery();
            } else {
                openCamera();
            }
        });
        builder.show();
    }

    // 打开相册
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    // 打开相机
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.dialectgame.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        }
    }

    // 创建图片文件
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* 前缀 */
                ".jpg",         /* 后缀 */
                storageDir      /* 目录 */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // 处理权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImagePickerDialog();
            } else {
                Toast.makeText(this, "需要权限才能选择头像", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 处理选择结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY && data != null) {
                Uri selectedImage = data.getData();
                try {
                    InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                    saveAvatar(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CAMERA) {
                Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
                saveAvatar(bitmap);
            }
        }
    }

    // 保存头像并更新
    private void saveAvatar(Bitmap bitmap) {
        // 获取头像边框的尺寸（假设为100dp）
        float borderSizeDp = 100;
        float scale = getResources().getDisplayMetrics().density;
        int borderSizePx = (int) (borderSizeDp * scale + 0.5f);

        // 裁剪图片为正方形
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int cropSize = Math.min(width, height);
        int x = (width - cropSize) / 2;
        int y = (height - cropSize) / 2;

        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, x, y, cropSize, cropSize);

        // 缩放至边框大小
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(croppedBitmap, borderSizePx, borderSizePx, true);

        // 保存图片到本地并获取路径
        String avatarPath = saveBitmapToFile(scaledBitmap);

        if (avatarPath != null) {
            // 更新用户头像信息
            currentUser.setAvatar(avatarPath);

            // 保存到数据库
            new Thread(() -> {
                db.userDao().update(currentUser);
                runOnUiThread(() -> {
                    // 更新UI显示
                    Glide.with(this).load(avatarPath).into(ivAvatar);
                    Toast.makeText(this, "头像更新成功", Toast.LENGTH_SHORT).show();
                });
            }).start();
        }

        // 回收Bitmap资源
        if (bitmap != scaledBitmap) {
            bitmap.recycle();
        }
        if (croppedBitmap != scaledBitmap) {
            croppedBitmap.recycle();
        }
    }

    // 保存Bitmap到文件
    private String saveBitmapToFile(Bitmap bitmap) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File file = new File(storageDir, "avatar_" + timeStamp + ".jpg");

            // 保存图片
            java.io.FileOutputStream out = new java.io.FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();

            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 修改setUserInfo方法以加载头像
    private void setUserInfo() {
        tvUsername.setText("用户名: " + currentUser.getUsername());
        etNickname.setText(currentUser.getNickname());
        etHometown.setText(currentUser.getHometown());
        etEmail.setText(currentUser.getEmail());

        // 设置性别选择
        String gender = currentUser.getGender();
        if ("male".equals(gender)) {
            rbMale.setChecked(true);
        } else if ("female".equals(gender)) {
            rbFemale.setChecked(true);
        }

        // 加载头像
        String avatarPath = currentUser.getAvatar();
        if (avatarPath != null && !avatarPath.isEmpty() && !"default_avatar".equals(avatarPath)) {
            Glide.with(this).load(new File(avatarPath)).into(ivAvatar);
        } else {
            ivAvatar.setImageResource(R.drawable.ic_user);
        }
    }


    private void initViews() {
        tvUsername = findViewById(R.id.tv_username);
        etNickname = findViewById(R.id.et_nickname);
        etHometown = findViewById(R.id.et_hometown);
        etEmail = findViewById(R.id.et_email);
        btnUpdate = findViewById(R.id.btn_update);
        btnLogout = findViewById(R.id.btn_logout);
        btnBack = findViewById(R.id.btn_back);
        ivAvatar = findViewById(R.id.iv_avatar);
        ivEditAvatar = findViewById(R.id.iv_edit_avatar);
        rgGender = findViewById(R.id.rg_gender);
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        tvFollowingCount = findViewById(R.id.tv_following_count);
        tvFollowerCount = findViewById(R.id.tv_follower_count);
        llFollowing = findViewById(R.id.ll_following);
        llFollowers = findViewById(R.id.ll_followers);
    }

    private void loadFollowStats() {
        new Thread(() -> {
            int followingCount = db.followDao().getFollowingCount(currentUser.getId());
            int followerCount = db.followDao().getFollowerCount(currentUser.getId());

            runOnUiThread(() -> {
                tvFollowingCount.setText(String.valueOf(followingCount));
                tvFollowerCount.setText(String.valueOf(followerCount));
            });
        }).start();
    }

    private void enterEditMode() {
        isEditing = true;
        btnUpdate.setText("保存信息");

        // 启用编辑控件
        etNickname.setEnabled(true);
        etHometown.setEnabled(true);
        etEmail.setEnabled(true);
        rgGender.setEnabled(true);
        btnUpdate.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.red));
    }

    private void saveUserInfo() {
        String nickname = etNickname.getText().toString().trim();
        if (nickname.isEmpty()) {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 获取性别选择
        String gender = "";
        if (rbMale.isChecked()) {
            gender = "male";
        } else if (rbFemale.isChecked()) {
            gender = "female";
        }

        String hometown = etHometown.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // 更新当前用户对象
        currentUser.setNickname(nickname);
        currentUser.setGender(gender);
        currentUser.setHometown(hometown);
        currentUser.setEmail(email);

        // 更新数据库
        new Thread(() -> {
            try {
                db.userDao().update(currentUser);
                runOnUiThread(() -> {
                    Toast.makeText(this, "信息更新成功", Toast.LENGTH_SHORT).show();
                    exitEditMode();
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show());
                e.printStackTrace();
            }
        }).start();
    }

    private void exitEditMode() {
        isEditing = false;
        btnUpdate.setText("更新信息");

        // 禁用编辑控件
        etNickname.setEnabled(false);
        etHometown.setEnabled(false);
        etEmail.setEnabled(false);
        rgGender.setEnabled(false);
        btnUpdate.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary));
    }
}