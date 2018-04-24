package com.gdut.dkmfromcg.ojbk_ui.autophoto;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gdut.dkmfromcg.ojbk_ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkmFromCG on 2018/4/5.
 * function:
 */

public class AutoPhotoView extends FrameLayout {

    /**
     * UI
     */
    private RecyclerView mRecyclerView = null;
    private ViewGroup mParentRootView = null;    // XMl 的根布局
    private View mDeleteView;

    /**
     * Data
     */
    private int mMaxNum; //允许的最大图片数
    private int mMaxNumOneLine;//每行允许的最大图片数
    private int mImgWidth;
    private int mImgHeight;
    private List<Uri> mPhotoUriList;

    private Context mContext;
    private PhotoAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;

    private Fragment mFragment = null;
    private Activity mActivity = null;
    //添加图片的 Dialog
    private AlertDialog mAddDialog = null;
    private static final RequestOptions OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE);


    public void setFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public AutoPhotoView(Context context) {
        this(context, null);
    }

    public AutoPhotoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPhotoView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final FrameLayout.LayoutParams lp = (LayoutParams) this.getLayoutParams();
        lp.setMargins(10, 5, 10, 5);

        initData(context, attrs);
        initView();
    }

    private void initData(Context context, AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable") final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.camera_flow_recycler_view);
        mMaxNum = typedArray.getInt(R.styleable.camera_flow_recycler_view_max_count, 9);
        mMaxNumOneLine = typedArray.getInt(R.styleable.camera_flow_recycler_view_max_count_one_line, 3);
        mImgWidth = typedArray.getInt(R.styleable.camera_flow_recycler_view_img_width, (int) context.getResources().getDimension(R.dimen.img_dimens));
        mImgHeight = typedArray.getInt(R.styleable.camera_flow_recycler_view_img_height, (int) context.getResources().getDimension(R.dimen.img_dimens));
        typedArray.recycle();

        this.mContext = context;
        this.mPhotoUriList = new ArrayList<>();
        this.mAdapter = new PhotoAdapter(mPhotoUriList, mContext);
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        this.mRecyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view_auto_photo, this);
        final ViewGroup content=mRecyclerView.getRootView().findViewById(android.R.id.content);
        this.mParentRootView = (ViewGroup) content.getChildAt(0);
        this.mDeleteView=View.inflate(mContext,R.layout.view_delete_photo,null);
        final int dvHeight=mDeleteView.getMeasuredHeight();
        final int dvWidth=mDeleteView.getMeasuredWidth();
        final int dvLeft=0; //屏幕左边缘
        final int dvTop=mParentRootView.getBottom()-dvHeight;
        mDeleteView.layout(dvLeft,dvTop,dvLeft+dvWidth,mParentRootView.getBottom());

        /**
         * init RecyclerView
         */
        final RvItemTouchCallback callback = new RvItemTouchCallback(mAdapter, mPhotoUriList, mPhotoUriList, mContext, mDeleteView);
        this.mItemTouchHelper = new ItemTouchHelper(callback);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(mMaxNumOneLine,
                StaggeredGridLayoutManager.VERTICAL));
        //重要属性
        mRecyclerView.setClipToPadding(false);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemTouchListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                //如果是添加按钮
               /* if(){

                }*/
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //如果item不是最后一个，则执行拖拽
                /*if (vh.getLayoutPosition() != dragImages.size() - 1) {
                    // mParentRootView 中显示一个 滑动到此处删除的View
                    mParentRootView.addView(mDeleteView);
                    mItemTouchHelper.startDrag(vh);
                }*/
            }
        });
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAddDialog = new AlertDialog.Builder(getContext()).create();
    }

    public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

        private List<Uri> mUris = null;
        private final Context mContext;

        public PhotoAdapter(List<Uri> uris, Context context) {
            this.mUris = uris;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final FrameLayout frameLayout = new FrameLayout(mContext);
            final FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, (int) mContext.getResources().getDimension(R.dimen.article_img_margin_top), 0, 0);
            frameLayout.setLayoutParams(layoutParams);
            frameLayout.setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);

            final ImageView imageView = new ImageView(mContext);
            final LinearLayout.LayoutParams layoutParams1 =
                    new LinearLayout.LayoutParams(mImgWidth, mImgHeight);
            imageView.setLayoutParams(layoutParams1);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setId(R.id.item_auto_photo_iv);
            return new ViewHolder(frameLayout);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position == mUris.size()) {
                //添加图标
                Glide.with(mContext).load(R.drawable.btn_plus).apply(OPTIONS).into(holder.imageView);
                holder.imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAddDialog.show();
                        final Window window = mAddDialog.getWindow();
                        if (window != null) {
                            window.setContentView(R.layout.dialog_camera_panel);
                            window.setGravity(Gravity.BOTTOM);
                            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            final WindowManager.LayoutParams params = window.getAttributes();
                            params.width = WindowManager.LayoutParams.MATCH_PARENT;
                            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                            params.dimAmount = 0.5f;
                            window.setAttributes(params);
                            // 打开照相机
                            window.findViewById(R.id.photodialog_tv_take).setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (mActivity != null) {

                                    }
                                }
                            });
                            //打开相册
                            window.findViewById(R.id.photodialog_tv_native).setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }
                    }
                });
            } else {
                //手机图片
                Glide.with(mContext).load(mUris.get(position)).apply(OPTIONS).into(holder.imageView);
            }

            if (position >= mMaxNum) {//图片已选完时，隐藏添加按钮
                holder.imageView.setVisibility(View.GONE);
            } else {
                holder.imageView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return mUris == null ? 0 : mUris.size() ;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.item_auto_photo_iv);
            }
        }
    }

}
