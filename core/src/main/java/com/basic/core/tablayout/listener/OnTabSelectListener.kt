package com.basic.core.tablayout.listener

interface OnTabSelectListener {
    fun onTabSelect(position: Int)
    fun onTabReselect(position: Int)
    fun onDoubleTabSelect(position: Int)
}