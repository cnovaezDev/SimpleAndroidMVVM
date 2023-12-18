//package com.cursokotlin.mvvmexample.ui.view
//
//import android.app.Application
//import android.content.Context
//import androidx.test.runner.AndroidJUnitRunner
//import dagger.hilt.android.testing.HiltTestApplication
//
///**
// ** Created by Carlos A. Novaez Guerrero on 15/11/2023 18:17
// ** cnovaez.dev@outlook.com
// **/
//class HiltTestRunner: AndroidJUnitRunner() {
//    override fun newApplication(
//        cl: ClassLoader?,
//        className: String?,
//        context: Context?
//    ): Application {
//        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
//    }
//}