package com.haocang.base.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.haocang.base.R;
import com.haocang.base.bean.PictureInfo;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.ui.PictureNewPreviewFragment;
import com.haocang.base.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2510:56
 * 修 改 者：
 * 修改时间：
 */
public class PictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int PICTURE = 0;
    private static final int VIDEO = 1;
    private Context ctx;
    private List<PictureInfo> mList = new ArrayList<>();

    public PictureAdapter(Context ctx) {
        this.ctx = ctx;
    }

    private boolean isDeleteDisplay = true;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PICTURE) {

            View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_picture, null);

            return new PictureHolder(view);

        } else if (viewType == VIDEO) {

            View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_video, null);
            return new VideoViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PictureHolder) {
            ImageView pictureIv = ((PictureHolder) holder).pictureIv;
            ImageView deleteIv = ((PictureHolder) holder).deleteIv;
            if (!isDeleteDisplay) {
                deleteIv.setVisibility(View.GONE);
            }
//            showPicture(position, pictureIv);
            if (!TextUtils.isEmpty(mList.get(position).getThumbnailUrl())) {
                Glide.with(ctx).load(mList.get(position).getThumbnailUrl()).apply(options).into(pictureIv);
            } else if (!TextUtils.isEmpty(mList.get(position).getImgUrl())) {
                Glide.with(ctx).load(mList.get(position).getImgUrl()).apply(options).into(pictureIv);
            } else {
                Glide.with(ctx).load(mList.get(position).getLocalImgPath()).apply(options).into(pictureIv);
            }
            pictureIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, CommonActivity.class);
                    if (!TextUtils.isEmpty(mList.get(position).getLocalImgPath())) {
                        intent.putExtra("picturePath", mList.get(position).getLocalImgPath());
                    } else {
                        intent.putExtra("picturePath", mList.get(position).getImgUrl());
                    }
                    if (isDeleteDisplay) {
                        intent.putExtra("displayDelete", "delete");
                    }
                    intent.putExtra("position", position + "");
                    intent.putExtra("url", new Gson().toJson(getFilesList()));
                    intent.putExtra("fragmentName", PictureNewPreviewFragment.class.getName());
                    ctx.startActivity(intent);
                }
            });
            deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(position);
                    notifyDataSetChanged();
                }
            });

        } else if (holder instanceof VideoViewHolder) {

            ImageView videoThumbnailIv = ((VideoViewHolder) holder).videoThumbnailIv;
            ImageView deleteIv = ((VideoViewHolder) holder).deleteIv;
            if (!isDeleteDisplay) {
                deleteIv.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mList.get(position).getVideoPath())) {
                showVideoThumbnail(position, videoThumbnailIv);
            } else if (!TextUtils.isEmpty(mList.get(position).getThumbnailUrl())) {
                Glide.with(ctx).load(mList.get(position).getThumbnailUrl()).apply(options).into(videoThumbnailIv);
            } else if (!TextUtils.isEmpty(mList.get(position).getNetWordVideoPath())) {
                Glide.with(ctx).load(R.drawable.ic_picture_default).apply(options).into(videoThumbnailIv);
                showVideoThumbnail2(position, videoThumbnailIv);
            } else if (!TextUtils.isEmpty(mList.get(position).getVideoUrl())) {
                showVideoThumbnail2(position, videoThumbnailIv);
            }
            videoThumbnailIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, CommonActivity.class);
                    if (mList.get(position).getVideoPath() != null) {
                        intent.putExtra("videoPath", mList.get(position).getVideoPath());
                    } else if (!TextUtils.isEmpty(mList.get(position).getVideoUrl())) {
                        intent.putExtra("networkVideoPath", mList.get(position).getVideoUrl());
                    } else {
                        intent.putExtra("networkVideoPath", mList.get(position).getNetWordVideoPath());
                    }
                    if (isDeleteDisplay) {
                        intent.putExtra("displayDelete", "delete");
                    }
                    intent.putExtra("url", new Gson().toJson(getFilesList()));
                    intent.putExtra("position", position + "");
                    intent.putExtra("fragmentName", PictureNewPreviewFragment.class.getName());
                    ctx.startActivity(intent);

                }
            });
            deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(position);
                    notifyDataSetChanged();
                }
            });

        }
    }

    private void showVideoThumbnail2(final int position, final ImageView videoThumbnailIv) {
        new AsyncTask<Void, Void, String>() {
            private RequestBuilder<Drawable> builder = null;

            @Override
            protected String doInBackground(final Void... voids) {
                if (!TextUtils.isEmpty(mList.get(position).getNetWordVideoPath())) {
                    builder = Glide.with(ctx).load(FileUtils.getNetWorkVideoThumbnail(mList.get(position).getNetWordVideoPath()));
                } else {
                    builder = Glide.with(ctx).load(FileUtils.getNetWorkVideoThumbnail(mList.get(position).getVideoUrl()));
                }
                return null;
            }

            protected void onPostExecute(final String s) {
                builder.apply(options).into(videoThumbnailIv);
            }
        }.execute();
    }

    private void showVideoThumbnail(final int position, final ImageView videoThumbnailIv) {
        new AsyncTask<Void, Void, String>() {
            private RequestBuilder<Drawable> builder = null;

            @Override
            protected String doInBackground(final Void... voids) {
                builder = Glide.with(ctx).load(FileUtils.getVideoThumbnail(mList.get(position).getVideoPath()));
                return null;
            }

            protected void onPostExecute(final String s) {
                builder.apply(options).into(videoThumbnailIv);
            }
        }.execute();
    }

    public List<String> getFileList() {
        List<String> listFilePath = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            PictureInfo entity = mList.get(i);
            if (entity.getType() == 0 && !TextUtils.isEmpty(entity.getLocalImgPath()) && !entity.getLocalImgPath().contains("http")) {
                listFilePath.add(entity.getLocalImgPath());
            } else if (entity.getType() == 1 && !TextUtils.isEmpty(entity.getVideoPath())) {
                listFilePath.add(entity.getVideoPath());
            }
        }
        return listFilePath;
    }

    public String getNetWordFile() {
        String path = "";
        for (int i = 0; i < getItemCount(); i++) {
            PictureInfo entity = mList.get(i);
            if (entity.getType() == 0 && !TextUtils.isEmpty(entity.getImgUrl()) && entity.getImgUrl().contains("http")) {
                path += entity.getImgUrl() + ",";
            } else if (entity.getType() == 1 && !TextUtils.isEmpty(entity.getVideoUrl()) && entity.getVideoUrl().contains("http")) {
                path += entity.getVideoUrl() + ",";
            }
        }
        if (!TextUtils.isEmpty(path)) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    public String getNetWordThumbnailUrl() {
        String path = "";
        for (int i = 0; i < getItemCount(); i++) {
            PictureInfo entity = mList.get(i);
            if (entity.getType() == 0 && !TextUtils.isEmpty(entity.getThumbnailUrl()) && entity.getThumbnailUrl().contains("http")) {
                path += entity.getThumbnailUrl() + ",";
            } else if (entity.getType() == 1 && !TextUtils.isEmpty(entity.getThumbnailUrl()) && entity.getThumbnailUrl().contains("http")) {
                path += entity.getThumbnailUrl() + ",";
            }
        }
        if (!TextUtils.isEmpty(path)) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }


    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getType() == PICTURE) {
            return PICTURE;
        } else {
            return VIDEO;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.ic_picture_default)// 正在加载中的图片
            .error(R.drawable.ic_picture_default) // 加载失败的图片
            .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略


    public class PictureHolder extends RecyclerView.ViewHolder {

        public ImageView pictureIv;
        public ImageView deleteIv;

        public PictureHolder(View itemView) {
            super(itemView);
            pictureIv = itemView.findViewById(R.id.picture_iv);
            deleteIv = itemView.findViewById(R.id.delete_iv);
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public ImageView videoThumbnailIv;
        public ImageView playIv;
        public ImageView deleteIv;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoThumbnailIv = itemView.findViewById(R.id.video_thumbnail_iv);
            playIv = itemView.findViewById(R.id.plays_iv);
            deleteIv = itemView.findViewById(R.id.delete_iv);
        }

    }

    public void addAll(List<PictureInfo> list) {
        mList.addAll(list);

    }

    public void addItem(PictureInfo entity) {
        mList.add(entity);
        notifyDataSetChanged();
    }

    public void addItemWithoutNotifyList(final PictureInfo entity) {
        mList.add(entity);
    }


    public void clear() {
        if (mList != null && mList.size() > 0) {
            mList.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 查看的时候隐藏
     */
    public void setDisplay() {
        isDeleteDisplay = false;
    }


    /**
     * 判断文件路径是否存在
     */
    public void isFileEmpty() {
        try {
            for (int i = 0; i < mList.size(); ) {
                PictureInfo entity = mList.get(i);
                if (!TextUtils.isEmpty(entity.getLocalImgPath())) {
                    File f = new File(entity.getLocalImgPath());
                    if (!f.exists()) {
                        mList.remove(i);
                        i = 0;
                    } else {
                        i++;
                    }
                } else if (!TextUtils.isEmpty(entity.getVideoPath())) {
                    File f = new File(entity.getVideoPath());
                    if (!f.exists()) {
                        mList.remove(i);
                        i = 0;
                    } else {
                        i++;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    private List<PictureInfo> getFilesList() {
        List<PictureInfo> list = new ArrayList<>();
        for (PictureInfo entity : mList) {
            if (!TextUtils.isEmpty(entity.getLocalImgPath())) {
                entity.setFileType(PictureInfo.LOCAL_IMAGE);
                list.add(entity);
            } else if (!TextUtils.isEmpty(entity.getImgUrl())) {
                entity.setFileType(PictureInfo.IMAGE);
                list.add(entity);
            } else if (!TextUtils.isEmpty(entity.getVideoPath())) {
                entity.setFileType(PictureInfo.LOCAL_VIDEO);
                list.add(entity);
            } else if (!TextUtils.isEmpty(entity.getVideoUrl())) {
                entity.setFileType(PictureInfo.VIDEO);
                list.add(entity);
            } else if (!TextUtils.isEmpty(entity.getNetWordVideoPath())) {
                entity.setFileType(PictureInfo.VIDEO);
                list.add(entity);
            }
        }
        return list;
    }
}
