LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_SRC_FILES := $(call all-subdir-java-files)

LOCAL_PACKAGE_NAME := UvcWebCam
LOCAL_JNI_SHARED_LIBRARIES := libImageProc
LOCAL_REQUIRED_MODULES := libImageProc

include $(BUILD_PACKAGE)

include $(call all-makefiles-under, $(LOCAL_PATH))
