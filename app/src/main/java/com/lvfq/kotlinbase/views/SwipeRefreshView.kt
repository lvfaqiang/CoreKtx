package com.lvfq.kotlinbase.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lvfq.kotlinbase.Const.AppConst
import com.lvfq.kotlinbase.R

/**
 * SwipeRefreshView
 * @author FaQiang on 2018/6/11 下午9:25
 * @desc :
 *
 */
class SwipeRefreshView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    SwipeRefreshLayout(context, attrs), OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private val DEFAULT_PAGE = AppConst.PAGE

    private var recyclerView: RecyclerView
    private var adapter: BaseQuickAdapter<*, BaseViewHolder>? = null


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
        setColorSchemeResources(R.color.colorAccent, R.color.colorAccent, R.color.colorAccent)

        addView(recyclerView)
        setOnRefreshListener(this)
    }


    /**
     * 初始化配置
     */
    fun initConfig(options: ConfigOptions) {
        // 对内提供 Adapter
        adapter = options.configAdapter()
        adapter?.animationEnable = true
        recyclerView.adapter = adapter

        // 是否可刷新
        setEnableRefresh(options.setEnableRefresh())
        // 是否可加载
        setEnableLoadMore(options.setEnableLoadMore())

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

    // baseAdapter 加载更多方法
    override fun onLoadMore() {
        if (!isRefreshing && swipeRefreshListener != null) {
            pageNo++
            swipeRefreshListener?.onLoadMore()
        }
    }

    /**
     * 设置是否可刷新
     */
    private fun setEnableRefresh(isCanRefresh: Boolean) {
        this.isCanRefresh = isCanRefresh
        isEnabled = isCanRefresh
    }

    /**
     * 设置是否可加载更多
     */
    private fun setEnableLoadMore(isCanLoadMore: Boolean) {
        this.isCanLoadMore = isCanLoadMore
        adapter?.loadMoreModule?.isEnableLoadMore = isCanLoadMore
        if (isCanLoadMore) {
            adapter?.loadMoreModule?.setOnLoadMoreListener(this)
        }
    }

    /**
     * 设置空布局
     */
    fun setEmptyView(view: View) {
        adapter?.setEmptyView(view)
    }

    /**
     * 设置空布局
     */
    fun setEmptyView(layoutId: Int) {
        adapter?.setEmptyView(layoutId)
    }


    /**
     * 加载到最后一页
     * 是否显示加载完成布局
     */
    fun loadMoreEnd(gone: Boolean = false) {
        reset()
        adapter?.loadMoreModule?.loadMoreEnd(gone)
    }

    /**
     * 当前页加载完成，非最后一页
     */
    fun loadMoreComplete() {
        reset()
        adapter?.loadMoreModule?.loadMoreComplete()
    }

    /**
     * 加载失败
     */
    fun loadMoreFail() {
        if (pageNo > DEFAULT_PAGE) {
            pageNo--
        }
        reset()
        adapter?.loadMoreModule?.loadMoreFail()
    }

    // swipeRefreshLayout 刷新方法
    override fun onRefresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        adapter?.loadMoreModule?.isEnableLoadMore = false

        if (swipeRefreshListener != null) {
            pageNo = DEFAULT_PAGE
            swipeRefreshListener?.onRefresh()
        }
    }

    private fun reset() {
        if (isCanLoadMore) {
            adapter?.loadMoreModule?.isEnableLoadMore = true
        }
        if (isCanRefresh) {
            isRefreshing = false
        }
    }

    interface SwipeRefreshListener {
        fun onRefresh()
        fun onLoadMore()
    }

    interface ConfigOptions {
        // 初始化 Adapter
        fun configAdapter(): BaseQuickAdapter<*, BaseViewHolder>?

        // 设置是否可刷新
        fun setEnableRefresh(): Boolean = false

        // 设置是否可加载更多
        fun setEnableLoadMore(): Boolean = false
    }


    open class DefaultConfigs constructor(
        val adapter: BaseQuickAdapter<*, BaseViewHolder>?,
        val loadMore: Boolean = false,
        val refresh: Boolean = false
    ) : SwipeRefreshView.ConfigOptions {

        override fun configAdapter(): BaseQuickAdapter<*, BaseViewHolder>? {
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