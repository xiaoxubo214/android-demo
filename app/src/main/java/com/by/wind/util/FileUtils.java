package com.by.wind.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.by.wind.BaseApplication;
import com.by.wind.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2015/10/30.
 */
public class FileUtils {

    //sd卡的根目录
    public static String mSDRootPath = Environment.getExternalStorageDirectory().getPath();
    //手机缓存的根目录
    public static String mDataRootPath = null;
    //保存图片的目录
    public static String IMAGE_FOLDER = "/androidImage";

    //初始化手机缓存的根目录
    public FileUtils(Context context) {
        mDataRootPath = context.getCacheDir().getPath();
    }

    //获取存储image的目录,sd卡挂载了就返回sd卡的目录，没挂载返回内存目录
    public String getStorageDirectory() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? mSDRootPath + IMAGE_FOLDER : mDataRootPath;
    }

    //保存图片的方法，有sd卡存sd卡，没有sd卡存内存
    public void saveBitmap(String fileName, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        String path = getStorageDirectory();
        File folderFile = new File(path);
        //判断文件是否存在
        if (!folderFile.exists()) {
            folderFile.mkdirs();//创建图片存放文件夹的路径
        }
        File file = new File(folderFile + File.separator + fileName);
        try {
            file.createNewFile();//创建文件
            //压缩图片
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();//刷入文件
            fos.close();//关闭流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //从手机或sd卡获取图片
    public Bitmap getBitmap(String fileName) {
        return BitmapFactory.decodeFile(getStorageDirectory() + File.separator + fileName);
    }

    //判断文件是否存在
    public boolean isFileExist(String fileName) {
        return new File(getStorageDirectory() + File.separator + fileName).exists();
    }

    //获取文件的大小
    public long getFileSize(String fileName) {
        return new File(getStorageDirectory() + File.separator + fileName).length();
    }

    //删除sd卡或者手机的缓存图片和目录
    public void deleteFile() {
        File dirFile = new File(getStorageDirectory());
        if (!dirFile.exists()) {//存储目录不存在直接返回
            return;
        }
        if (dirFile.isDirectory()) {//如果存储目录是个文件夹，遍历
            String[] childFile = dirFile.list();
            for (int i = 0; i < childFile.length; i++) {
                new File(dirFile, childFile[i]).delete();
            }
        }
        //如果是文件
        dirFile.delete();
    }

    /**
     * 判断SD是否可以
     */
    public static boolean isSdcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getRootFilePath() {
        if (isSdcardExist()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;// filePath:/sdcard/teacher/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data/" + BaseApplication.getInstance().getPackageName() + File.separator; // filePath: /data/data/
        }
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public static void deleteFolderFiles(String filePath, boolean deletePath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFiles(files[i].getAbsolutePath(), true);
                    }
                }
                if (deletePath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建文件
     */
    public static File createNewFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return file;
    }

    /**
     * 创建根目录
     *
     * @param path 目录路径
     */
    public static boolean createDirFile(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            return true;
        }
        return dir.mkdirs();
    }

    /**
     * 根据文件转换成对应的Uri
     *
     * @param ctx
     * @param file
     * @return
     */
    public static Uri file2Uri(Context ctx, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(ctx, Constants.APPLICATION_ID + ".fileprovider", file);
        } else {
            return Uri.fromFile(file);
        }
    }
}

