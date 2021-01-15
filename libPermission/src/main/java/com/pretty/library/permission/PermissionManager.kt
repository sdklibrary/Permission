package com.pretty.library.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class PermissionManager private constructor(
    private val context: AppCompatActivity
) : LifecycleObserver {

    private var grantedFunction: ((permissions: Array<String>) -> Unit)? = null
    private var deniedFunction: ((permissions: Array<String>) -> Unit)? = null
    private var foreverAlertFunction: ((permissions: Array<String>) -> Unit)? = null

    init {
        context.lifecycle.addObserver(this)
    }

    fun onGranted(function: (permissions: Array<String>) -> Unit) {
        this.grantedFunction = function
    }

    fun onDenied(function: (permissions: Array<String>) -> Unit) {
        this.deniedFunction = function
    }

    fun onForeverAlert(function: (permissions: Array<String>) -> Unit) {
        this.foreverAlertFunction = function
    }

    internal fun apply(permissions: Array<String>) {
        if (!isOverMarshmallow())
            grantedFunction?.invoke(permissions)
        else {
            val deniedPermissions = filterGranted(context, *permissions)
            if (deniedPermissions.isEmpty()) {
                grantedFunction?.invoke(permissions)
            } else {
                context.startActivity(
                    Intent(context, PermissionHelperActivity::class.java)
                        .putExtra("PERMISSIONS", permissions)
                )
            }
        }
    }

    internal fun requestPermissionsResult(permissions: Array<String>) {

        val grantedList = ArrayList<String>() //允许集合
        val deniedList = ArrayList<String>() //拒绝集合
        val foreverAlertList = ArrayList<String>() //拒绝不再提示

        permissions.forEach { permission: String ->
            if (isGranted(context, permission))
                grantedList += permission
            else {
                val deniedForever =
                    ActivityCompat.shouldShowRequestPermissionRationale(context, permission)
                if (!deniedForever)
                    foreverAlertList += permission
                deniedList += permission
            }
        }

        if (deniedList.size > 0) { //拒绝
            if (foreverAlertList.size > 0) { //不在提醒
                if (this.foreverAlertFunction != null)
                    this.foreverAlertFunction!!.invoke(foreverAlertList.toTypedArray())
                else
                    this.deniedFunction?.invoke(deniedList.toTypedArray())
            } else
                this.deniedFunction?.invoke(deniedList.toTypedArray())
        } else  //允许
            this.grantedFunction?.invoke(grantedList.toTypedArray())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        grantedFunction = null
        deniedFunction = null
        foreverAlertFunction = null
        manager = null
    }

    companion object {

        @Volatile
        internal var manager: PermissionManager? = null

        /**
         * SDK版是否是大于等于M
         * @author Arvin.xun
         */
        fun isOverMarshmallow() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

        /**
         * 过滤掉已同意的权限
         * @author Arvin.xun
         */
        fun filterGranted(
            context: Context,
            vararg permissions: String
        ): Array<String> {
            val deniedPermissions = mutableListOf<String>()
            permissions.forEach { permission: String ->
                if (!isGranted(context, permission))
                    deniedPermissions += permission
            }

            return deniedPermissions.toTypedArray()
        }

        /**
         * 权限是否已同意
         * @author Arvin.xun
         */
        fun isGranted(
            context: Context,
            permission: String
        ): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                return true
            val granted = ContextCompat.checkSelfPermission(context, permission)
            return granted == PackageManager.PERMISSION_GRANTED
        }

        @Suppress("UNCHECKED_CAST")
        fun requestPermission(
            context: AppCompatActivity,
            vararg permissions: String,
            listener: (PermissionManager.() -> Unit)? = null
        ): PermissionManager {
            manager = PermissionManager(context)
            manager?.let {
                listener?.invoke(it)
                it.apply(permissions as Array<String>)
            }
            return manager!!
        }
    }

}