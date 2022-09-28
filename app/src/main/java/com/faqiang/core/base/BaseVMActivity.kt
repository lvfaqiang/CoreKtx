package com.faqiang.core.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import io.douwan.basic.core.ktx.i
import io.douwan.basic.core.util.FragmentUtil
import io.douwan.basic.core.util.LanguageUtil
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType
import java.util.*

/**
 * BaseVMActivity2022/4/15 00:23
 * @desc :
 *
 */
abstract class BaseVMActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var binding: VB
    protected lateinit var viewModel: VM
    protected abstract val viewModelClass: Class<VM>
    private var haveShownUpgradeDialog = false

    protected val fragmentUtil by lazy {
        FragmentUtil(supportFragmentManager)
    }

    private var progressDialog: AlertDialog? = null

    open fun useEventBus(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        super.onCreate(savedInstanceState)

        LanguageUtil.setDefaultLanguage(this, Locale.ENGLISH.language)

        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }

        initBinding()
        initViewModel()

        viewModel.message.observe(this) {
            toastError(it)
        }
        initView()
        initData()

    }

    private fun initBinding() {
        val type = javaClass.genericSuperclass

        if (type is ParameterizedType) {
            val clazz = type.actualTypeArguments[0] as Class<VB>
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            binding = method.invoke(null, layoutInflater) as VB
            setContentView(binding.root)
        }
    }

    abstract fun initView()
    abstract fun initData()


    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(viewModelClass)
    }

    open fun toastError(message: String?) {
        disLoading()
        message?.let {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            showErrorDialog(it)
        }
    }

    open fun toastError(@StringRes strRes: Int) {
        toastError(getString(strRes))
    }

    open fun toastSuccess(message: String?) {
        disLoading()
        message?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    open fun toastSuccess(@StringRes strRes: Int) {
        toastSuccess(getString(strRes))
    }

    open fun showLoading(isCancelable: Boolean = true) {
        "showLoading ---------- thread: ${Thread.currentThread().name} ".i()
//        if (progressDialog == null) {
//            progressDialog = AlertDialog.Builder(this)
//                .setCancelable(isCancelable)
//                .setView(DialogProgressBinding.inflate(layoutInflater).root)
//                .create()
//            val window = progressDialog?.window
//            if (window != null) {
//                val layoutParams = WindowManager.LayoutParams()
//                layoutParams.copyFrom(window.attributes)
//                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
//                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
//                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                window.attributes = layoutParams
//                if (isCancelable) {
//                    window.setDimAmount(0f)
//                } else {
//                    window.setDimAmount(0.3f)
//                }
//            }
//            progressDialog?.show()
//        }
//        if (progressDialog != null && progressDialog?.isShowing != true) {
//            progressDialog?.show()
//        }

    }

    open fun disLoading() {
        if (!this.isFinishing) {
            "disLoading ----------thread: ${Thread.currentThread().name} ".i()
            try {
                progressDialog?.dismiss()
                progressDialog = null
            } catch (e: Exception) {
            }
        }
    }

    override fun onResume() {
        super.onResume()
        "${this.javaClass.simpleName} ------- onResume ".i()
        intent?.extras?.keySet()?.map {
            "key:  $it , value:  ${intent.extras?.get(it)}".i()
        }
//        if (!checkedDeepLink()) {
//            onResumed()
//        }
    }

    protected open fun onResumed() {

    }

    override fun onPause() {
        super.onPause()
        "${this.javaClass.simpleName} ------- onPause ".i()
    }

    override fun onStop() {
        super.onStop()
        "${this.javaClass.simpleName} ------- onStop ".i()
    }


    override fun onDestroy() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        "${this.javaClass.simpleName} ------- onDestroy ".i()
        super.onDestroy()
    }

    var errorDialog: Dialog? = null
    private fun showErrorDialog(errorMessage: String) {
//        if (errorDialog == null) {
//            val dialogBinding = DialogOneBtnBinding.inflate(layoutInflater)
//            errorDialog = DialogBuilder[this]
//                .setCancelable(false)
//                .setCanceledOnTouchOutside(false)
//                .build(dialogBinding.root)
//            dialogBinding.tvTitle.text = getString(R.string.Error)
//            dialogBinding.tvContent.text = errorMessage
//            dialogBinding.tvBtn.text = getString(R.string.OK)
//            dialogBinding.tvBtn.click {
//                errorDialog?.dismiss()
//                errorDialog = null
//            }
//        } else {
//            if (errorDialog?.isShowing == true) {
//                val textView: TextView? = errorDialog?.findViewById<TextView>(R.id.tvContent)
//                textView?.text = errorMessage
//            }
//        }
    }


//    val loginLauncher = registerForActivityResult(LoginContract()) { loginResult ->
//        onLoginResult(loginResult)
//    }

//    open fun onLoginResult(loginResult: LoginResult) {
//
//    }

}