package com.darongshu.login;

import android.content.Context;

import java.io.File;

/**
 * 文件缓存
 * <p/>
 * Created by Liu Jianping
 *
 * @date : 15/12/9.
 */
public class FileCache {

    //缓存目录
    private File cacheDir;

    public FileCache(Context context) {
        // 如果有SD卡则在SD卡中建一个LazyList的目录存放缓存的图片
        // 没有SD卡就放在系统的缓存目录中
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
        {
            /*cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "LazyList");*/
            cacheDir = FileUtil.CACHE_DIR;
        }
        else
        {
            cacheDir = context.getCacheDir();
        }

        //如果目录不存在，那么创建一个缓存目录
        if (!cacheDir.exists())
        {
            cacheDir.mkdirs();
        }
    }

    /**
     * 根据url获取缓存文件
     * @param url
     * @return
     */
    public File getFile(String url) {
        // 将url的hashCode作为缓存的文件名
        String filename = String.valueOf(url.hashCode());
        // Another possible solution
        // String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;

    }

    /**
     * 清除缓存
     */
    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
        {
            return;
        }

        for (File f : files)
        {
            f.delete();
        }
    }

}