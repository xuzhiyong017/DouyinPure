package com.example.douyindownload.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.douyindownload.R
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            fullScreen(true)
        }
        setContentView(R.layout.activity_splash)
        imageView.postDelayed({
            PermissionUtils.permissionGroup(PermissionConstants.STORAGE).callback(object :
                PermissionUtils.SimpleCallback{
                override fun onGranted() {
                    startActivity(Intent(this@SplashActivity,HomeActivity::class.java))
                    finish()
                }

                override fun onDenied() {
                    finish()
                }
            }).request()
        },1000)
    }
}