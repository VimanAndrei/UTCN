#include "stdafx.h"
#include "common.h"
#include<opencv2/objdetect.hpp>
#include<opencv2/highgui.hpp>
#include<opencv2/imgproc.hpp>
#include<iostream>
#include <fstream>
#include<stdlib.h>

using namespace std;
using namespace cv;
String emotion[8] = { "anger","disgust","fear","happiness","neutral","sadness","surprise" };



Mat gaussianFilter(Mat src, int w)
{
	float sigma = (float)w / 6;
	int mid = w / 2;

	float* gx;
	gx = (float*)calloc(w, sizeof(float));

	for (int i = 0; i < w; i++)
	{

		float numi = sqrt(2.0 * PI) * sigma;
		float fraction = 1.0 / numi;
		float e1 = (pow(i - mid, 2)) / (2 * pow(sigma, 2));
		e1 = -e1;
		float val1 = fraction * exp(e1);
		gx[i] = val1;
	}

	Mat dst = src.clone();

	for (int i = w / 2; i < src.rows - w / 2; i++)
	{
		for (int j = w / 2; j < src.cols - w / 2; j++)
		{
			float sum = 0;

			for (int k = 0; k < w; k++)
			{
				int pixel1 = src.at<uchar>(i, j + k - w / 2);
				float val1 = gx[k];
				sum += pixel1 * val1;

			}

			dst.at<uchar>(i, j) = (uchar)sum;

		}
	}
	Mat dst1 = dst.clone();

	for (int i = w / 2; i < src.rows - w / 2; i++)
	{
		for (int j = w / 2; j < src.cols - w / 2; j++)
		{
			float sum = 0;

			for (int k = 0; k < w; k++)
			{
				int pixel1 = dst.at<uchar>(i + k - w / 2, j);
				float val1 = gx[k];
				sum += pixel1 * val1;

			}

			dst1.at<uchar>(i, j) = (uchar)sum;

		}
	}
	return dst1;
}


int grX[3][3] = { {-1,0,1}, {-2,0,2}, {-1,0,1} };
int grY[3][3] = { {1,2,1}, {0,0,0}, {-1,-2,-1} };

Mat convolution(Mat src, int Kernal[][3])
{
	Mat dst = src.clone();
	dst.convertTo(dst, CV_32FC1);

	for (int i = 1; i < src.rows - 1; i++)
	{
		for (int j = 1; j < src.cols - 1; j++)
		{
			int sum = 0;
			for (int k = 0; k < 3; k++)
			{
				for (int l = 0; l < 3; l++)
				{
					int pixel = src.at<uchar>(i + k - 1, j + l - 1);
					int filter = Kernal[k][l] * pixel;
					sum += filter;
				}
			}

			dst.at<float>(i, j) = sum;
		}
	}

	return dst;
}

Mat magnitude(Mat src, Mat magx, Mat magy)
{
	Mat dst = src.clone();
	dst.convertTo(dst, CV_32FC1);
	float scalar = 4.0 * sqrt(2.0);

	for (int i = 0; i < src.rows; i++)
	{
		for (int j = 0; j < src.cols; j++)
		{
			float pixel1 = magx.at<float>(i, j);
			float pixel2 = magy.at<float>(i, j);
			float rez = sqrt((pixel1 * pixel1) + (pixel2 * pixel2));
			dst.at<float>(i, j) = rez / scalar;
		}
	}

	return dst;

}

Mat orientation(Mat src, Mat magx, Mat magy)
{
	Mat dst = src.clone();
	dst.convertTo(dst, CV_32FC1);

	for (int i = 0; i < src.rows; i++)
	{
		for (int j = 0; j < src.cols; j++)
		{
			float pixel1 = magx.at<float>(i, j);
			float pixel2 = magy.at<float>(i, j);
			float rez = atan2(pixel2, pixel1);
			rez = rez* (180 / PI);
			if (rez < 0) rez = -rez;
			dst.at<float>(i, j) = rez;		
			
		}
	}

	return dst;

}

Mat suprimareNonMaxime(Mat modulul, Mat orientarea) {
	Mat dst = modulul.clone();
	for (int i = 1; i < dst.rows - 1; i++)
	{
		for (int j = 1; j < dst.cols - 1; j++)
		{
			int pixel = orientarea.at<float>(i, j);
			switch (pixel)
			{
			case 0:
				if (modulul.at<float>(i, j) < modulul.at<float>(i - 1, j) || modulul.at<float>(i, j) < modulul.at<float>(i + 1, j)) {
					dst.at<float>(i, j) = 0;
				}
				break;
			case 1:
				if (modulul.at<float>(i, j) < modulul.at<float>(i - 1, j + 1) || modulul.at<float>(i, j) < modulul.at<float>(i + 1, j - 1)) {
					dst.at<float>(i, j) = 0;
				}
				break;
			case 2:
				if (modulul.at<float>(i, j) < modulul.at<float>(i, j - 1) || modulul.at<float>(i, j) < modulul.at<float>(i, j - 1)) {
					dst.at<float>(i, j) = 0;
				}
				break;
			case 3:
				if (modulul.at<float>(i, j) < modulul.at<float>(i - 1, j - 1) || modulul.at<float>(i, j) < modulul.at<float>(i + 1, j + 1)) {
					dst.at<float>(i, j) = 0;
				}
				break;
			}

		}
	}

	return dst;
}

Mat transformOrientation(Mat src) {
	Mat dst = src.clone();
	for (int i = 0; i < src.rows; i++)
	{
		for (int j = 0; j < src.cols; j++)
		{
			float pixel = src.at<float>(i, j);
			if (pixel >= 0.0 && pixel < 22.5 || pixel <= 180 && pixel >= 157.5) {
				dst.at<float>(i, j) = 2;
			}
			if (pixel >= 22.5 && pixel < 67.5) {
				dst.at<float>(i, j) = 1;
			}
			if (pixel >= 67.5 && pixel < 112.5) {
				dst.at<float>(i, j) = 0;
			}
			if (pixel >= 112.5 && pixel < 157.5) {
				dst.at<float>(i, j) = 3;
			}

		}

	}
	return dst;
}

vector<array<int, 10>>  hogCreate(Mat magnitude, Mat orientation)
{

	vector<array<int, 10>> vec;

	for (int i = 8; i < orientation.rows - 8; i+=8)
	{
		for (int j = 8; j < orientation.cols - 8; j+=8)
		{
			array<int, 10> h{ {0, 0, 0, 0, 0, 0, 0, 0, 0, 0} };

			for (int k = 0; k < 8; k++)
			{
				for (int l = 0; l < 8; l++)
				{
					int indexi = i + k;
					int indexj = j + l;
					int value = orientation.at<float>(indexi, indexj);					
					value /= 20;
					h[value] += magnitude.at<float>(indexi, indexj);
					

				}
			}
			vec.push_back(h);
		}
	}

	return vec;
}

void showHistogram(const std::string& name, int* hist, const int  hist_cols, const int hist_height)
{
	Mat imgHist(hist_height, hist_cols, CV_8UC3, CV_RGB(255, 255, 255)); // constructs a white image

	//computes histogram maximum
	int max_hist = 0;
	for (int i = 0; i < hist_cols; i++)
		if (hist[i] > max_hist)
			max_hist = hist[i];
	double scale = 1.0;
	scale = (double)hist_height / max_hist;
	int baseline = hist_height - 1;

	for (int x = 0; x < hist_cols; x++) {
		Point p1 = Point(x, baseline);
		Point p2 = Point(x, baseline - cvRound(hist[x] * scale));
		line(imgHist, p1, p2, CV_RGB(255, 0, 255)); // histogram bins colored in magenta
	}

	imshow(name, imgHist);
}


int* compHist(Mat src)
{
	CascadeClassifier faceDetection;
	if (!faceDetection.load("A:\\Faculta\\An3-Sem2\\PI(procesare de imagini)\\OpenCVApplication-VS2019_OCV3411_basic\\OpenCV\\face_detection\\haarcascade_frontalface_default.xml")) {
		cout << "\n Face XML is not loaded!";
		return NULL;
	}
	else {

		vector<Rect> faces;

		faceDetection.detectMultiScale(src, faces, 1.1, 5, CASCADE_SCALE_IMAGE);
		Mat cropped_image = src.clone();

		for (int i = 0; i < faces.size(); i++)
		{
			Point p1(faces[i].x, faces[i].y);
			Point p2(faces[i].x + faces[i].height, faces[i].y + faces[i].width);
			cropped_image = src(Range(p1.y, p2.y), Range(p1.x, p2.x));
		}

		resize(cropped_image, cropped_image, Size(128, 128));
		cropped_image = gaussianFilter(cropped_image, 7);


		Mat mgx, mgy, mag, ori, supress;
		mgx = convolution(cropped_image, grX);
		mgy = convolution(cropped_image, grY);
		mag = magnitude(cropped_image, mgx, mgy);
		ori = orientation(cropped_image, mgx, mgy);
		Mat direction = transformOrientation(ori);
		Mat dst = suprimareNonMaxime(mag, direction);


		vector<array<int, 10>> v = hogCreate(dst, ori);

		int* h = (int*)calloc(200, sizeof(int));
		int index = 0;
		for each (array<int, 10> var in v)
		{
			int sum = 0;
			for each (int i in var)
			{
				sum += i;
			}
			h[index] = sum / 10;
			index++;
		}
		return h;
	}
}

void modelCreation()
{
	vector<String> path;
	path.push_back("A:\\Faculta\\An3-Sem2\\PI(procesare de imagini)\\OpenCVApplication-VS2019_OCV3411_basic\\Images\\CK+\\anger\\*.png");
	path.push_back("A:\\Faculta\\An3-Sem2\\PI(procesare de imagini)\\OpenCVApplication-VS2019_OCV3411_basic\\Images\\CK+\\disgust\\*.png");
	path.push_back("A:\\Faculta\\An3-Sem2\\PI(procesare de imagini)\\OpenCVApplication-VS2019_OCV3411_basic\\Images\\CK+\\fear\\*.png");
	path.push_back("A:\\Faculta\\An3-Sem2\\PI(procesare de imagini)\\OpenCVApplication-VS2019_OCV3411_basic\\Images\\CK+\\happiness\\*.png");
	path.push_back("A:\\Faculta\\An3-Sem2\\PI(procesare de imagini)\\OpenCVApplication-VS2019_OCV3411_basic\\Images\\CK+\\neutral\\*.png");
	path.push_back("A:\\Faculta\\An3-Sem2\\PI(procesare de imagini)\\OpenCVApplication-VS2019_OCV3411_basic\\Images\\CK+\\sadness\\*.png");
	path.push_back("A:\\Faculta\\An3-Sem2\\PI(procesare de imagini)\\OpenCVApplication-VS2019_OCV3411_basic\\Images\\CK+\\surprise\\*.png");
	ofstream myfile;
	myfile.open("A:\\Faculta\\An3-Sem2\\PI(procesare de imagini)\\OpenCVApplication-VS2019_OCV3411_basic\\out.txt");
	for each (String p in path)
	{
		cout << p << endl;
		vector<String> fn;
		glob(p, fn, false);
		vector<int*> rez;
		for each (String var in fn)
		{
			Mat src = imread(var, IMREAD_GRAYSCALE);
			int* h = compHist(src);
			rez.push_back(h);
		}
		int* histo = (int*)calloc(200, sizeof(int));
		int scalar = rez.size();
		for (int i = 0; i < 200; i++) {
			int sum = 0;
			for each (int* var in rez)
			{
				sum += var[i];
			}
			sum /= scalar;
			histo[i] = sum;
		}

		for (int i = 0; i < 200; i++)
		{
			myfile << histo[i] << " ";
		}
		myfile << endl;
	}


	myfile.close();
	cout << "gata";
}

vector<int*> readModel()
{
	ifstream myfile;
	myfile.open("A:\\Faculta\\An3-Sem2\\PI(procesare de imagini)\\OpenCVApplication-VS2019_OCV3411_basic\\out.txt");
	vector<int*> model;

	for (int i = 0; i < 7; i++)
	{
		int a;
		int* histo = (int*)calloc(200, sizeof(int));
		for (int i = 0; i < 200; i++) {
			myfile >> a;
			histo[i] = a;
		}
		model.push_back(histo);
	}
	myfile.close();
	return model;

}
void testEmotion()
{
	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{

		Mat src = imread(fname, IMREAD_GRAYSCALE);
		int* h = compHist(src);
		vector<int*> clone = readModel();



		for (int i = 0; i < 200; i++) {
			for each (int* var in clone)
			{
				var[i] = abs(h[i] - var[i]);
			}
		}


		vector<long long int> values;

		for each (int* var in clone)
		{
			long long int sum = 0;
			for (int i = 0; i < 200; i++) {
				sum += var[i];
			}
			values.push_back(sum);
		}

		long int min = 99999;
		for each (long int var in values)
		{
			if (min > var) {
				min = var;
			}
			cout << var << " ";
		}
		cout << endl;

		for (int k = 0; k < values.size(); k++)
		{
			if (min == values[k]) {
				cout << emotion[k] << endl;
			}
		}

	}
}

int main()
{
	//modelCreation();
	testEmotion();
	return 0;
}