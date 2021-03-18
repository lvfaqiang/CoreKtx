package cn.basic.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import cn.basic.core.common.ILoading
import cn.basic.core.factory.VMFactory
import cn.basic.core.util.KeyBoardUtils

/**
 * BaseVMFragment2020-02-24 16:54
 * @desc :
 *
 */
abstract class BasicVMFragment<T : ViewBinding, VM : BasicViewModel> : Fragment() {
    protected abstract val viewModelClass: Class<VM>

    protected lateinit var vM: VM

    abstract val loadingView: ILoading

    private fun initViewModel() {
        vM = VMFactory.findVM(this, viewModelClass)
    }

    abstract fun init(savedInstanceState: Bundle?)

    protected abstract fun bindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): T

    protected lateinit var binding: T

    open var useEventBus: Boolean = false
        protected set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        view?.let {
            it.isClickable = true
        }
        KeyBoardUtils.setupUISoftKeyBoardHideSystem(view ?: return, viewGroupFocused())
        super.onActivityCreated(savedInstanceState)
    }

    /**
     * 如果页面自动弹出软键盘，则重写该方法返回 true
     */
    open fun viewGroupFocused(): Boolean {
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        LanguageUtil.updateLanguage(requireContext(), AppCache.getLanguage())    // 默认中文

        binding = bindingView(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        loadingObserve()

        init(savedInstanceState)
    }

    private fun loadingObserve() {
        vM.updateMessage.observe(this.viewLifecycleOwner) {
            if (loadingView.isShowing()) {
                loadingView.setMessage(it)
            }
        }
        vM.loadingState.observe(this.viewLifecycleOwner) {
            if (it.loading) {
                loadingView.setCancelable(it.cancelable)
                loadingView.setCanceledOnTouchOutside(it.cancelable)

                if (it.message.isNotEmpty()) {
                    loadingView.setMessage(it.message)
                }

                if (!loadingView.isShowing()) {
                    loadingView.show()
                }

            } else {
                loadingView.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    protected inline fun <reified T : ViewModel> findVM(): T {
        return VMFactory.findVM(this, T::class.java)
    }

    protected inline fun <reified T : ViewModel> findVMByActivity(): T {
        return activity?.run {
            VMFactory.findVM(this, T::class.java)
        } ?: throw Exception("Invalid Activity")
    }


}