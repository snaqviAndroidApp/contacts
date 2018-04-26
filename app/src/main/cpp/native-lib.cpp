#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_ghar_root_recycleAndDrawer_Facial_MainActivity_sJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "AA Mashto using C++ with basics";
    return env->NewStringUTF(hello.c_str());
}
