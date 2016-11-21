#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>

using namespace cv;

extern "C" {

int toGray(Mat img, Mat& gray)
{
    cvtColor(img, gray, CV_RGBA2GRAY);

    if (gray.rows == img.rows && gray.cols == img.cols)
    {
        return (1);
    }
    return(0);
}

jint
Java_com_tistory_webnautes_opencvtest_MainActivity_convertNativeLib(JNIEnv*, jobject, jlong addrRgba, jlong addrGray) {

    Mat &mRgb = *(Mat *) addrRgba;
    Mat &mGray = *(Mat *) addrGray;

    int conv;
    jint retVal;

    conv = toGray(mRgb, mGray);

    retVal = (jint) conv;

    return retVal;
}


}
