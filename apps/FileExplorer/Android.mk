LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_STATIC_JAVA_LIBRARIES += android-support-v13
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := android-support-v13:libs/android-support-v13.jar

LOCAL_SRC_FILES := $(call all-subdir-java-files)

LOCAL_PACKAGE_NAME := FileExplorer

include $(BUILD_PACKAGE)
