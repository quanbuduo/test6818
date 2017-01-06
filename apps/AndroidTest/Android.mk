LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_STATIC_JAVA_LIBRARIES += android-support-v4
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := android-support-v4:libs/android-support-v4.jar

LOCAL_SRC_FILES := $(call all-subdir-java-files)

LOCAL_PACKAGE_NAME := AndroidTest
LOCAL_JNI_SHARED_LIBRARIES := libSerialPort
LOCAL_REQUIRED_MODULES := libSerialPort

include $(BUILD_PACKAGE)

include $(call all-makefiles-under, $(LOCAL_PATH))
