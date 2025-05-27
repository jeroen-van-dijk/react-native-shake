package com.shake

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.Promise
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = ShakeModule.NAME)
abstract class ShakeSpec internal constructor(context: ReactApplicationContext) :
  NativeShakeSpec(context) {
  
  abstract fun addListener(eventName: String)
  abstract fun removeListeners(count: Double)
}
