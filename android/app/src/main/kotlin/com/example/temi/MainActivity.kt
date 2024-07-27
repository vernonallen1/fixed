package com.example.temi

import android.content.pm.PackageManager
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnRobotReadyListener
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel


class MainActivity : FlutterActivity(), OnRobotReadyListener {

    private lateinit var robot: Robot
    private val CHANNEL = "temi_channel"

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        robot = Robot.getInstance()
//    }

    private fun printS() {
        println("SSSS")
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "startTemiActivity" -> {
                    println("this is working already initially")
//                    val intent = Intent(this@MainActivity, MyTemiApp::class.java)
//                    try {
//                        startActivity(intent)
//                        println("this is working already")
//                        result.success(null)
//                    } catch (e: Exception) {
//                        result.error("ACTIVITY_START_ERROR", e.message, null)
//                    }
                }
                else -> {
                    println("Error: Method not implemented - ${call.method}")
                    result.notImplemented()
                }
            }
        }
    }


//    override fun onStart() {
//        super.onStart()
//        robot.addOnRobotReadyListener(this)
//    }

//    override fun onStop() {
//        super.onStop()
//        robot.removeOnRobotReadyListener(this)
//    }

    override fun onStart() {
        super.onStart()
        robot.addOnRobotReadyListener(this)
        robot.addNlpListener(this)
        getInstance.getInstance().addOnBeWithMeStatusChangedListener(this)
        getInstance.getInstance().addOnGoToLocationStatusChangedListener(this)
        getInstance.getInstance().addConversationViewAttachesListenerListener(this)
        getInstance.getInstance().addWakeupWordListener(this)
        getInstance.getInstance().addTtsListener(this)
        getInstance.getInstance().addOnLocationsUpdatedListener(this)
    }

    override fun onStop() {
        super.onStop()
        getInstance.getInstance().removeOnRobotReadyListener(this)
        getInstance.getInstance().removeNlpListener(this)
        getInstance.getInstance().removeOnBeWithMeStatusChangedListener(this)
        getInstance.getInstance().removeOnGoToLocationStatusChangedListener(this)
        getInstance.getInstance().removeConversationViewAttachesListenerListener(this)
        getInstance.getInstance().removeWakeupWordListener(this)
        getInstance.getInstance().removeTtsListener(this)
        getInstance.getInstance().removeOnLocationsUpdateListener(this)
    }

    override fun onRobotReady(isReady: Boolean) {
        if (isReady) {
            try {
                val activityInfo =
                    packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA)
                robot.onStart(activityInfo)
            } catch (e: PackageManager.NameNotFoundException) {
                throw RuntimeException(e)
            }
        }
    }
}
