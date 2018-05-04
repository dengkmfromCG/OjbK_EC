package com.gdut.dkmfromcg.commonlib.ui.autophoto;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gdut.dkmfromcg.commonlib.R;
import com.gdut.dkmfromcg.commonlib.recyclerview.divider.BaseDecoration;
import com.gdut.dkmfromcg.commonlib.util.dimen.DimenUtil;
import com.gdut.dkmfromcg.commonlib.util.log.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dkmFromCG on 2018/4/5.
 * function:
 */

public class AutoPhotoView extends FrameLayout {

    private static final String TAG = "AutoPhotoView";
    private static final int MAX_NUM_ONE_LINE = 3;//每行允许的最大图片数
    private static final int DECORATION_SIZE = 15;
    private static final int MARGIN_LR = 30;

    /**
     * UI
     */
    private RecyclerView mRecyclerView = null;
    private ViewGroup mParentRootView = null;    // XMl 的根布局
    private View mDeleteView;
    private ItemTouchHelper mItemTouchHelper;//拖动 Helper
    private AlertDialog mAddDialog = null; //添加图片的 Dialog

    /**
     * Data
     */
    private int mMaxNum; //允许的最大图片数
    private float mImgWidth;
    private float mImgHeight;

    private List<Uri> mPhotoUriList;
    private PhotoAdapter mAdapter;

    public View.OnClickListener onLocalPicClickListener = null;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE);

    public void setOnLocalPicClickListener(View.OnClickListener listener) {
        this.onLocalPicClickListener = listener;
    }

    public void setPhotoUriList(List<Uri> uriList) {
        this.mPhotoUriList.addAll(uriList);
        this.mAdapter.notifyDataSetChanged();
        requestLayout();
    }

    public int getMaxPicNum() {
        return mMaxNum;
    }

    public Dialog getAddDialog() {
        return mAddDialog;
    }


    public AutoPhotoView(Context context) {
        this(context, null);
    }

    public AutoPhotoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPhotoView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        @SuppressLint("CustomViewStyleable") final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.camera_flow_recycler_view);
        mMaxNum = typedArray.getInt(R.styleable.camera_flow_recycler_view_max_count, 9);
        typedArray.recycle();
        int screenWidth = DimenUtil.getScreenWidth();
        float imgWid = (screenWidth - MARGIN_LR * 2 - DECORATION_SIZE * 2) / MAX_NUM_ONE_LINE;//一张图片的宽度
        mImgWidth = imgWid;
        mImgHeight = imgWid;
        this.mPhotoUriList = new ArrayList<>();
        this.mAdapter = new PhotoAdapter(mPhotoUriList);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View view = inflater.inflate(R.layout.recycler_view_auto_photo, this);
        this.mRecyclerView = view.findViewById(R.id.recycler_view_ap);
        /*final ViewGroup content = mRecyclerView.getRootView().findViewById(android.R.id.content);
        this.mParentRootView = (ViewGroup) content.getChildAt(0);
        this.mDeleteView = View.inflate(getContext(), R.layout.view_delete_photo, null);
        final int dvHeight = mDeleteView.getMeasuredHeight();
        final int dvWidth = mDeleteView.getMeasuredWidth();
        final int dvLeft = 0; //屏幕左边缘
        final int dvTop = mParentRootView.getBottom() - dvHeight;
        this.mDeleteView.layout(dvLeft, dvTop, dvLeft + dvWidth, mParentRootView.getBottom());*/

        /**
         * init RecyclerView
         */
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), MAX_NUM_ONE_LINE));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.white), DECORATION_SIZE));

        //用于拖动
        mRecyclerView.setClipToPadding(false);
        mRecyclerView.setClipChildren(false);
        final RvItemTouchCallback callback = new RvItemTouchCallback(mAdapter, mPhotoUriList, getContext(), mDeleteView);
        callback.setDragListener(new DragListener() {
            @Override
            public void deleteState(boolean delete) {
                //Logger.d("DragListener","deleteState"+delete);
            }

            @Override
            public void dragState(boolean start) {
                //Logger.d("DragListener","dragState"+start);
            }

            @Override
            public void clearView() {
                //Logger.d("DragListener","clearView");
            }
        });
        this.mItemTouchHelper = new ItemTouchHelper(callback);
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemTouchListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {

            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //如果item不是最后一个，则执行拖拽
                if (vh.getLayoutPosition() != mPhotoUriList.size()) {
                    // mParentRootView 中显示一个 滑动到此处删除的View
                    //mParentRootView.addView(mDeleteView);
                    mItemTouchHelper.startDrag(vh);
                }
            }
        });
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAddDialog = new AlertDialog.Builder(getContext()).create();
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
        private List<Uri> mUris = null;
        private final Context mContext;

        PhotoAdapter(List<Uri> uris) {
            this.mUris = uris;
            this.mContext = getContext();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auto_photo, parent, false);
            final ImageView imageView = view.findViewById(R.id.item_auto_photo_iv);
            final FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams((int) mImgWidth, (int) mImgHeight);
            imageView.setLayoutParams(layoutParams);
            return new ViewHolder(view);
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

                                }
                            });
                            //打开相册
                            if (onLocalPicClickListener != null) {
                                window.findViewById(R.id.photodialog_tv_native).setOnClickListener(onLocalPicClickListener);
                            } else {
                                Toast.makeText(mContext, "onLocalPicClickListener == null", Toast.LENGTH_SHORT).show();
                            }
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

        // 1 是添加按钮
        @Override
        public int getItemCount() {
            return mUris == null ? 1 : mUris.size() + 1;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.item_auto_photo_iv);
            }
        }
    }


    private class RvItemTouchCallback extends ItemTouchHelper.Callback {
        private int dragFlags = 0;
        private int swipeFlags = 0;
        private boolean up;//手指抬起标记位

        private final PhotoAdapter adapter;
        private final List<Uri> images;//图片经过压缩处理
        private final Context context;

        private final View mDeleteView;


        public RvItemTouchCallback(PhotoAdapter adapter, List<Uri> images,
                                   Context context, View deleteView) {
            this.adapter = adapter;
            this.images = images;
            this.context = context;
            this.mDeleteView = deleteView;
        }

        /**
         * 设置item是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向
         *
         * @return
         */
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            //判断 recyclerView的布局管理器数据
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {//设置能拖拽的方向
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                swipeFlags = 0;//0则不响应事件
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        /**
         * 当用户从item原来的位置拖动可以拖动的item到新位置的过程中调用
         *
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();//得到item原来的position
            int toPosition = target.getAdapterPosition();//得到目标position
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(images, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(images, i, i - 1);
                }
            }
            adapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        /**
         * 当用户与item的交互结束并且item也完成了动画时调用
         *
         * @param recyclerView
         * @param viewHolder
         */
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            adapter.notifyDataSetChanged();
            initData();
            if (dragListener != null) {
                dragListener.clearView();
            }
        }

        /**
         * 自定义拖动与滑动交互
         */
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (null == dragListener) {
                return;
            }
            /*if (dY >= (recyclerView.getHeight()
                    - viewHolder.itemView.getBottom()//item底部距离recyclerView顶部高度
                    - context.getResources().getDimension(R.dimen.article_post_delete))) {//拖到删除处
                dragListener.deleteState(true);
                if (up) {//在删除处放手，则删除item
                    viewHolder.itemView.setVisibility(View.INVISIBLE);//先设置不可见，如果不设置的话，会看到viewHolder返回到原位置时才消失，因为remove会在viewHolder动画执行完成后才将viewHolder删除
                    originImages.remove(viewHolder.getAdapterPosition());
                    images.remove(viewHolder.getAdapterPosition());
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    initData();
                    return;
                }
            } else {//没有到删除处
                if (View.INVISIBLE == viewHolder.itemView.getVisibility()) {//如果viewHolder不可见，则表示用户放手，重置删除区域状态
                    dragListener.dragState(false);
                }
                dragListener.deleteState(false);
            }*/
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        /**
         * 当长按选中item的时候（拖拽开始的时候）调用
         */
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (ItemTouchHelper.ACTION_STATE_DRAG == actionState && dragListener != null) {
                dragListener.dragState(true);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        /**
         * 设置手指离开后ViewHolder的动画时间，在用户手指离开后调用
         */
        @Override
        public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
            //手指放开
            up = true;
            return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
        }

        /**
         * 重置
         */
        private void initData() {
            if (dragListener != null) {
                dragListener.deleteState(false);
                dragListener.dragState(false);
            }
            up = false;
        }


        private DragListener dragListener = null;

        void setDragListener(DragListener dragListener) {
            this.dragListener = dragListener;
        }
    }

    interface DragListener {
        /**
         * 用户是否将 item拖动到删除处，根据状态改变颜色
         *
         * @param delete
         */
        void deleteState(boolean delete);

        /**
         * 是否于拖拽状态
         *
         * @param start
         */
        void dragState(boolean start);

        /**
         * 当用户与item的交互结束并且item也完成了动画时调用
         */
        void clearView();
    }


}
