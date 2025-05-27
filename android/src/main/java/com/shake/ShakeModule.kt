package com.shake

import android.content.Context
import android.hardware.SensorManager
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = ShakeModule.NAME)
class ShakeModule internal constructor(private val context: ReactApplicationContext) :
  ReactContextBaseJavaModule(context), LifecycleEventListener {

  private lateinit var shakeDetector: CustomShakeDetector

  override fun getName(): String {
    return NAME
  }

  override fun initialize() {
    context.addLifecycleEventListener(this)
    shakeDetector = CustomShakeDetector({
      sendEvent()
    })

    shakeDetector.start(
      context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    )
  }

  private fun sendEvent() {
    context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(EVENT_NAME, null)
  }

  @ReactMethod
  fun addListener(eventName: String) {
    // Keep: Required for RN built in Event Emitter Calls
  }

  @ReactMethod
  fun removeListeners(count: Double) {
    // Keep: Required for RN built in Event Emitter Calls
  }

  override fun onHostResume() {
    shakeDetector.start(
      context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    )
  }

  override fun onHostPause() {
    shakeDetector.stop()
  }

  override fun onHostDestroy() {
    shakeDetector.stop()
  }

  companion object {
    const val NAME = "RNShake"
    const val EVENT_NAME: String = "ShakeEvent"
  }
}
