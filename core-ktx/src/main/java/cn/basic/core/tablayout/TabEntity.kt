package cn.basic.core.tablayout

import cn.basic.core.tablayout.listener.CustomTabEntity

/**
 * TabEntity2021/1/15 8:43 PM
 * @desc :
 *
 */
class TabEntity
constructor(
    val title: String,
    val selectedIcon: Int,
    val unSelectedIcon: Int
) : CustomTabEntity {


    override val tabTitle: String?
        get() = title
    override val tabSelectedIcon: Int
        get() = selectedIcon
    override val tabUnselectedIcon: Int
        get() = unSelectedIcon
}