package com.faqiang.core.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import cn.iwgang.simplifyspan.SimplifySpanBuild
import cn.iwgang.simplifyspan.unit.SpecialClickableUnit
import com.faqiang.core.R
import com.faqiang.core.databinding.DialogPrivateProxyBinding
import com.faqiang.core.feature.WebActivity
import io.douwan.basic.core.builder.DialogBuilder
import io.douwan.basic.core.ktx.click

/**
 * PrivateProxyDialog2022/10/28 15:57
 * @desc :
 *
 */
fun Context.privateProxyDialog(ok: () -> Unit = {}, cancel: () -> Unit = {}) {
    val binding = DialogPrivateProxyBinding.inflate(LayoutInflater.from(this))

    val dialog = DialogBuilder.get(this)
        .setCancelable(false)
        .setCanceledOnTouchOutside(false)
        .setWidth(0.8f)
        .build(binding.root)

    /**
     * 《用户协议》及《隐私协议》
     */

    val userAgreementText = SpecialClickableUnit(binding.tvContent) { _, _ ->
        WebActivity.startUserAgreement()
    }
    userAgreementText.normalTextColor = ContextCompat.getColor(this, R.color.black)
    userAgreementText.text = "《用户协议》"

    val privacyAgreementText = SpecialClickableUnit(binding.tvContent) { _, _ ->
        WebActivity.startPrivacyPolicy()
    }
    privacyAgreementText.normalTextColor = ContextCompat.getColor(this, R.color.black)
    privacyAgreementText.text = "《隐私协议》"

    val content = SimplifySpanBuild()
        .append("欢迎使用\"喵喵存钱\"我们非常重视您的隐私和个人信息保护，请您在使用前认真阅读")
        .append(userAgreementText)
        .append("及")
        .append(privacyAgreementText)
        .append("您同意并接受全部条款后方可使用\"喵喵存钱\"")
        .build()

    binding.tvContent.text = content

    binding.tvCancel.click {
        cancel()
        dialog.dismiss()
    }
    binding.tvNext.click {
        ok()
        dialog.dismiss()
    }
}