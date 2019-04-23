package com.lvfq.kotlinbase.views

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lvfq.kotlinbase.Const.AppConst
import com.lvfq.kotlinbase.R

/**
 * SwipeRefreshView
 * @author FaQiang on 2018/6/11 下午9:25
 * @Github: <a href="https://github.com/lvfaqiang"/>
 * @Blog: <a href="http://blog.csdn.net/lv_fq"/>
 * @desc :
 *
 */
class SwipeRefreshView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : SwipeRefreshLayout(context, attrs), BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private val DEFAULT_PAGE = AppConst.PAGE

    private var recyclerView: RecyclerView
    private lateinit var mAdapter: BaseQuickAdapter<*, BaseViewHolder>


    var swipeRefreshListener: SwipeRefreshListener? = null

    // 是否可刷新
    private var isCanRefresh = false
    // 是否可加载更多
    private var isCanLoadMore = false

    var pageNo = DEFAULT_PAGE
        private set


    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        recyclerView = RecyclerView(context)
        recyclerView.apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
        recyclerView.isNestedScrollingEnabled = false

        // 设置刷新的默认背景颜色
        setProgressBackgroundColorSchemeResource(android.R.color.white)
        // 设置刷新的进度颜色
        setColorSchemeResources(R.color.c_2d2d46, R.color.c_838291, R.color.c_517cff)

        addView(recyclerView)
        setOnRefreshListener(this)
    }

    /**
     * 初始化配置
     */
    fun initOptions(options: ConfigOptions) {
        recyclerView.layoutManager = options.layoutManager()
        // 对外提供 RecyclerView
        options.configRecyclerView(recyclerView)
        // 对外提供 SwipeRefreshLayout
        options.configSwipeRefreshLayout(this)
        // 对内提供 Adapter
        mAdapter = options.configAdapter()

        // 是否可刷新
        setEnableRefresh(options.setEnableRefresh())
        // 是否可加载
        setEnableLoadMore(options.setEnableLoadMore())

        initAdapter()
    }

    /**
     * 获取 Adapter
     */
    fun getAdapter(): BaseQuickAdapter<*, BaseViewHolder> {
        checkInited()
        return mAdapter
    }

    // 获取 RecyclerView 实例
    fun getRecyclerView(): RecyclerView = recyclerView


    fun setBackgroudColor(color: Int) {
        recyclerView.setBackgroundColor(color)
    }

    /**
     * 设置 RecyclerView LayoutManager
     */
    private fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = layoutManager
    }

    /**
     * 初始化 Adapter
     */
    private fun initAdapter() {
//        recyclerView.layoutManager ?: let { throw NullPointerException("Please invoke the setLayoutManager method first") }
        if (isCanLoadMore) {
            mAdapter.setOnLoadMoreListener(this, recyclerView)
        }
//        mAdapter.bindToRecyclerView(recyclerView)
        recyclerView.adapter = mAdapter
    }


    // baseAdapter 刷新方法
    override fun onLoadMoreRequested() {
        if (!isRefreshing && swipeRefreshListener != null) {
            pageNo++
            swipeRefreshListener?.onLoadMoreRequested()
        }
    }

    /**
     * 设置是否可刷新
     */
    fun setEnableRefresh(isCanRefresh: Boolean) {
        this.isCanRefresh = isCanRefresh
        isEnabled = isCanRefresh
    }

    /**
     * 设置是否可加载更多
     */
    fun setEnableLoadMore(isCanLoadMore: Boolean) {
        this.isCanLoadMore = isCanLoadMore
        checkInited()
        mAdapter.setEnableLoadMore(isCanLoadMore)
    }

    /**
     * 设置空布局
     */
    fun setEmptyView(view: View) {
        checkInited()
        mAdapter.emptyView = view
    }

    /**
     * 设置空布局
     */
    fun setEmptyView(layoutId: Int) {
        checkInited()
        mAdapter.setEmptyView(layoutId, recyclerView)
    }


    /**
     * 加载到最后一页
     * 是否显示加载完成布局
     */
    fun loadMoreEnd(gone: Boolean = false) {
        checkInited()
        mAdapter.loadMoreEnd(gone)
        reset()
    }

    /**
     * 当前页加载完成，非最后一页
     */
    fun loadMoreComplete() {
        checkInited()
        mAdapter.loadMoreComplete()
        reset()
    }

    /**
     * 加载失败
     */
    fun loadMoreFail() {
        if (pageNo > DEFAULT_PAGE) {
            pageNo--
        }
        checkInited()
        mAdapter.loadMoreFail()
        reset()
    }

    // swipeRefreshLayout 刷新方法
    override fun onRefresh() {
        checkInited()
        mAdapter.setEnableLoadMore(false)
        if (swipeRefreshListener != null) {
            pageNo = DEFAULT_PAGE
            swipeRefreshListener?.onRefresh()
        }
    }

    private fun reset() {
        checkInited()
        if (isCanLoadMore) {
            mAdapter.setEnableLoadMore(true)
        }
        if (isCanRefresh) {
            isRefreshing = false
        }
    }

    /**
     *
     */
    private fun checkInited() {
        if (!::mAdapter.isInitialized) {
            throw IllegalAccessException(" invoke the initOption method first please")
        }
    }

    interface SwipeRefreshListener {
        fun onRefresh()
        fun onLoadMoreRequested()
    }

    interface ConfigOptions {

        // 初始化 layoutmanager
        fun layoutManager(): RecyclerView.LayoutManager

        // 初始化 Adapter
        fun configAdapter(): BaseQuickAdapter<*, BaseViewHolder>

        // 获取当前 SwipeRefreshLayout
        fun configSwipeRefreshLayout(refreshLayout: SwipeRefreshLayout) = Unit

        // 获取当前的 RecyclerView
        fun configRecyclerView(recyclerView: RecyclerView) = Unit

        // 设置是否可刷新
        fun setEnableRefresh(): Boolean = false

        // 设置是否可加载更多
        fun setEnableLoadMore(): Boolean = false
    }


    open class DefaultConfigs constructor(val layoutManager: RecyclerView.LayoutManager, val adapter: BaseQuickAdapter<*, BaseViewHolder>, val loadMore: Boolean = false, val refresh: Boolean = false) : SwipeRefreshView.ConfigOptions {

        override fun layoutManager(): RecyclerView.LayoutManager {
            return layoutManager
        }

        override fun configAdapter(): BaseQuickAdapter<*, BaseViewHolder> {
            return adapter
        }

        override fun setEnableLoadMore(): Boolean {
            return loadMore
        }

        override fun setEnableRefresh(): Boolean {
            return refresh
        }
    }
}