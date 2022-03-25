// OpenCVApplication.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "common.h"
#include <queue>
#include<random>

using namespace std;

void testOpenImage()
{
	char fname[MAX_PATH];
	while(openFileDlg(fname))
	{
		Mat src;
		src = imread(fname);
		imshow("image",src);
		waitKey();
	}
}

void testOpenImagesFld()
{
	char folderName[MAX_PATH];
	if (openFolderDlg(folderName)==0)
		return;
	char fname[MAX_PATH];
	FileGetter fg(folderName,"bmp");
	while(fg.getNextAbsFile(fname))
	{
		Mat src;
		src = imread(fname);
		imshow(fg.getFoundFileName(),src);
		if (waitKey()==27) //ESC pressed
			break;
	}
}

void testImageOpenAndSave()
{
	Mat src, dst;

	src = imread("Images/Lena_24bits.bmp", CV_LOAD_IMAGE_COLOR);	// Read the image

	if (!src.data)	// Check for invalid input
	{
		printf("Could not open or find the image\n");
		return;
	}

	// Get the image resolution
	Size src_size = Size(src.cols, src.rows);

	// Display window
	const char* WIN_SRC = "Src"; //window for the source image
	namedWindow(WIN_SRC, CV_WINDOW_AUTOSIZE);
	cvMoveWindow(WIN_SRC, 0, 0);

	const char* WIN_DST = "Dst"; //window for the destination (processed) image
	namedWindow(WIN_DST, CV_WINDOW_AUTOSIZE);
	cvMoveWindow(WIN_DST, src_size.width + 10, 0);

	cvtColor(src, dst, CV_BGR2GRAY); //converts the source image to a grayscale one

	imwrite("Images/Lena_24bits_gray.bmp", dst); //writes the destination to file

	imshow(WIN_SRC, src);
	imshow(WIN_DST, dst);

	printf("Press any key to continue ...\n");
	waitKey(0);
}

void testNegativeImage()
{
	char fname[MAX_PATH];
	while(openFileDlg(fname))
	{
		double t = (double)getTickCount(); // Get the current time [s]
		
		Mat src = imread(fname,CV_LOAD_IMAGE_GRAYSCALE);
		int height = src.rows;
		int width = src.cols;
		Mat dst = Mat(height,width,CV_8UC1);
		// Asa se acceseaaza pixelii individuali pt. o imagine cu 8 biti/pixel
		// Varianta ineficienta (lenta)
		for (int i=0; i<height; i++)
		{
			for (int j=0; j<width; j++)
			{
				uchar val = src.at<uchar>(i,j);
				uchar neg = MAX_PATH-val;
				dst.at<uchar>(i,j) = neg;
			}
		}

		// Get the current time again and compute the time difference [s]
		t = ((double)getTickCount() - t) / getTickFrequency();
		// Print (in the console window) the processing time in [ms] 
		printf("Time = %.3f [ms]\n", t * 1000);

		imshow("input image",src);
		imshow("negative image",dst);
		waitKey();
	}
}

void testParcurgereSimplaDiblookStyle()
{
	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
		int height = src.rows;
		int width = src.cols;
		int w = src.step; // no dword alignment is done !!!
		Mat dst = src.clone();

		double t = (double)getTickCount(); // Get the current time [s]

		// the fastest approach using the “diblook style”
		uchar *lpSrc = src.data;
		uchar *lpDst = dst.data;
		for (int i = 0; i<height; i++)
			for (int j = 0; j < width; j++) {
				uchar val = lpSrc[i*w + j];
				lpDst[i*w + j] = 255 - val;
				/* sau puteti scrie:
				uchar val = lpSrc[i*width + j];
				lpDst[i*width + j] = 255 - val;
				//	w = width pt. imagini cu 8 biti / pixel
				//	w = 3*width pt. imagini cu 24 biti / pixel
				*/
			}

		// Get the current time again and compute the time difference [s]
		t = ((double)getTickCount() - t) / getTickFrequency();
		// Print (in the console window) the processing time in [ms] 
		printf("Time = %.3f [ms]\n", t * 1000);

		imshow("input image", src);
		imshow("negative image", dst);
		waitKey();
	}
}

void testColor2Gray()
{
	char fname[MAX_PATH];
	while(openFileDlg(fname))
	{
		Mat src = imread(fname);

		int height = src.rows;
		int width = src.cols;

		Mat dst = Mat(height,width,CV_8UC1);
		
		// Asa se acceseaaza pixelii individuali pt. o imagine RGB 24 biti/pixel
		// Varianta ineficienta (lenta)
		for (int i=0; i<height; i++)
		{
			for (int j=0; j<width; j++)
			{
				Vec3b v3 = src.at<Vec3b>(i,j);
				uchar b = v3[0];
				uchar g = v3[1];
				uchar r = v3[2];
				dst.at<uchar>(i,j) = (r+g+b)/3;
			}
		}
		
		imshow("input image",src);
		imshow("gray image",dst);
		waitKey();
	}
}

void testBGR2HSV()
{
	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		Mat src = imread(fname);
		int height = src.rows;
		int width = src.cols;
		int w = src.step; // latimea in octeti a unei linii de imagine
		
		Mat dstH = Mat(height, width, CV_8UC1);
		Mat dstS = Mat(height, width, CV_8UC1);
		Mat dstV = Mat(height, width, CV_8UC1);
		
		// definire pointeri la matricele (8 biti/pixeli) folosite la afisarea componentelor individuale H,S,V
		uchar* dstDataPtrH = dstH.data;
		uchar* dstDataPtrS = dstS.data;
		uchar* dstDataPtrV = dstV.data;

		Mat hsvImg;
		cvtColor(src, hsvImg, CV_BGR2HSV);
		// definire pointer la matricea (24 biti/pixeli) a imaginii HSV
		uchar* hsvDataPtr = hsvImg.data;

		for (int i = 0; i<height; i++)
		{
			for (int j = 0; j<width; j++)
			{
				int hi = i*width * 3 + j * 3;
				// sau int hi = i*w + j * 3;	//w = 3*width pt. imagini 24 biti/pixel
				int gi = i*width + j;
				
				dstDataPtrH[gi] = hsvDataPtr[hi] * 510/360;		// H = 0 .. 255
				dstDataPtrS[gi] = hsvDataPtr[hi + 1];			// S = 0 .. 255
				dstDataPtrV[gi] = hsvDataPtr[hi + 2];			// V = 0 .. 255
			}
		}

		imshow("input image", src);
		imshow("H", dstH);
		imshow("S", dstS);
		imshow("V", dstV);
		waitKey();
	}
}

void testResize()
{
	char fname[MAX_PATH];
	while(openFileDlg(fname))
	{
		Mat src;
		src = imread(fname);
		Mat dst1,dst2;
		//without interpolation
		resizeImg(src,dst1,320,false);
		//with interpolation
		resizeImg(src,dst2,320,true);
		imshow("input image",src);
		imshow("resized image (without interpolation)",dst1);
		imshow("resized image (with interpolation)",dst2);
		waitKey();
	}
}

void testCanny()
{
	char fname[MAX_PATH];
	while(openFileDlg(fname))
	{
		Mat src,dst,gauss;
		src = imread(fname,CV_LOAD_IMAGE_GRAYSCALE);
		int k = 0.4;
		int pH = 50;
		int pL = k*pH;
		GaussianBlur(src, gauss, Size(5, 5), 0.8, 0.8);
		Canny(gauss,dst,pL,pH,3);
		imshow("input image",src);
		imshow("canny",dst);
		waitKey();
	}
}

void testVideoSequence()
{
	VideoCapture cap("Videos/rubic.avi"); // off-line video from file
	//VideoCapture cap(0);	// live video from web cam
	if (!cap.isOpened()) {
		printf("Cannot open video capture device.\n");
		waitKey();
		return;
	}
		
	Mat edges;
	Mat frame;
	char c;

	while (cap.read(frame))
	{
		Mat grayFrame;
		cvtColor(frame, grayFrame, CV_BGR2GRAY);
		Canny(grayFrame,edges,40,100,3);
		imshow("source", frame);
		imshow("gray", grayFrame);
		imshow("edges", edges);
		c = cvWaitKey();  // waits a key press to advance to the next frame
		if (c == 27) {
			// press ESC to exit
			printf("ESC pressed - capture finished\n"); 
			break;  //ESC pressed
		};
	}
}


void testSnap()
{
	VideoCapture cap(0); // open the deafult camera (i.e. the built in web cam)
	if (!cap.isOpened()) // openenig the video device failed
	{
		printf("Cannot open video capture device.\n");
		return;
	}

	Mat frame;
	char numberStr[256];
	char fileName[256];
	
	// video resolution
	Size capS = Size((int)cap.get(CV_CAP_PROP_FRAME_WIDTH),
		(int)cap.get(CV_CAP_PROP_FRAME_HEIGHT));

	// Display window
	const char* WIN_SRC = "Src"; //window for the source frame
	namedWindow(WIN_SRC, CV_WINDOW_AUTOSIZE);
	cvMoveWindow(WIN_SRC, 0, 0);

	const char* WIN_DST = "Snapped"; //window for showing the snapped frame
	namedWindow(WIN_DST, CV_WINDOW_AUTOSIZE);
	cvMoveWindow(WIN_DST, capS.width + 10, 0);

	char c;
	int frameNum = -1;
	int frameCount = 0;

	for (;;)
	{
		cap >> frame; // get a new frame from camera
		if (frame.empty())
		{
			printf("End of the video file\n");
			break;
		}

		++frameNum;
		
		imshow(WIN_SRC, frame);

		c = cvWaitKey(10);  // waits a key press to advance to the next frame
		if (c == 27) {
			// press ESC to exit
			printf("ESC pressed - capture finished");
			break;  //ESC pressed
		}
		if (c == 115){ //'s' pressed - snapp the image to a file
			frameCount++;
			fileName[0] = NULL;
			sprintf(numberStr, "%d", frameCount);
			strcat(fileName, "Images/A");
			strcat(fileName, numberStr);
			strcat(fileName, ".bmp");
			bool bSuccess = imwrite(fileName, frame);
			if (!bSuccess) 
			{
				printf("Error writing the snapped image\n");
			}
			else
				imshow(WIN_DST, frame);
		}
	}

}

void MyCallBackFunc(int event, int x, int y, int flags, void* param)
{
	//More examples: http://opencvexamples.blogspot.com/2014/01/detect-mouse-clicks-and-moves-on-image.html
	Mat* src = (Mat*)param;
	if (event == CV_EVENT_LBUTTONDOWN)
		{
			printf("Pos(x,y): %d,%d  Color(RGB): %d,%d,%d\n",
				x, y,
				(int)(*src).at<Vec3b>(y, x)[2],
				(int)(*src).at<Vec3b>(y, x)[1],
				(int)(*src).at<Vec3b>(y, x)[0]);
		}
}

void testMouseClick()
{
	Mat src;
	// Read image from file 
	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		src = imread(fname);
		//Create a window
		namedWindow("My Window", 1);

		//set the callback function for any mouse event
		setMouseCallback("My Window", MyCallBackFunc, &src);

		//show the image
		imshow("My Window", src);

		// Wait until user press some key
		waitKey(0);
	}
}

void testGrayScaleAditivFactor() {
	char fname[MAX_PATH];
	int aditiveFactor = 50;
	while (openFileDlg(fname))
	{
		double t = (double)getTickCount(); // Get the current time [s]

		Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
		int height = src.rows;
		int width = src.cols;
		Mat dst = Mat(height, width, CV_8UC1);
		// Asa se acceseaaza pixelii individuali pt. o imagine cu 8 biti/pixel
		// Varianta ineficienta (lenta)
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				uchar val = src.at<uchar>(i, j);
				uchar neg = val + aditiveFactor;
				//if (neg > 255) neg = 255;
				//if (neg < 0) neg = 0;
				dst.at<uchar>(i, j) = neg;
			}
		}

		// Get the current time again and compute the time difference [s]
		t = ((double)getTickCount() - t) / getTickFrequency();
		// Print (in the console window) the processing time in [ms] 
		printf("Time = %.3f [ms]\n", t * 1000);

		imshow("input image", src);
		imshow("aditive image", dst);
		waitKey();
	}
}

void testGrayScaleMultiplicativFactor() {
	char fname[MAX_PATH];
	int multiplicativeFactor = 2;
	while (openFileDlg(fname))
	{
		double t = (double)getTickCount(); // Get the current time [s]

		Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
		int height = src.rows;
		int width = src.cols;
		Mat dst = Mat(height, width, CV_8UC1);
		// Asa se acceseaaza pixelii individuali pt. o imagine cu 8 biti/pixel
		// Varianta ineficienta (lenta)
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				uchar val = src.at<uchar>(i, j);
				uchar neg = val * multiplicativeFactor;
				if (neg > 255) neg = 255;
				if (neg < 0) neg = 0;
				dst.at<uchar>(i, j) = neg;
			}
		}

		// Get the current time again and compute the time difference [s]
		t = ((double)getTickCount() - t) / getTickFrequency();
		// Print (in the console window) the processing time in [ms] 
		printf("Time = %.3f [ms]\n", t * 1000);

		imwrite("C:/Users/Public/Pictures/multiplicativeImage.bmp", dst); //writes the destination to file

		imshow("input image", src);
		imshow("multiplicativ image", dst);
		waitKey();
	}
}

void testCadraneImage() {
	Mat img(256, 256, CV_8UC3);

	for (int i = 0; i < img.rows; i++) {
		for (int j = 0; j < img.cols; j++) {
			Vec3b pixel = img.at< Vec3b>(i, j);
			if (i < 128 && j < 128) {
				//cadranul1
				pixel[0] = 255;
				pixel[1] = 255;
				pixel[2] = 255;
			}

			if (i < 128 && j >= 128) {
				//cadranul2
				pixel[0] = 0;
				pixel[1] = 0;
				pixel[2] = 255;
			}

			if (i >= 128 && j < 128) {
				//cadranul3
				pixel[0] = 0;
				pixel[1] = 255;
				pixel[2] = 0;
			}

			if (i >= 128 && j >= 128) {
				//cadranul4
				pixel[0] = 0;
				pixel[1] = 255;
				pixel[2] = 255;
			}
			img.at<Vec3b>(i,j) = pixel;
		}
	}

	imshow("cadraneImage", img);
	waitKey(0);
	
}

void testInversaMatrice() {
	float values[] = {
	  0,1,1,
	  1,0,0,
	  0,0,1
	};
	Mat matrice(3, 3, CV_32FC1,values);
	Mat inversa = matrice.inv();

	std::cout << "Matricea normala: "<< endl << matrice<<endl;
	std::cout << "Matricea inversata: " <<endl<< inversa<<endl;
	system("pause");

	


}

void lab2pb1() {
	/*
		Adăugați la framework o funcție care copiază canalele R, G, B ale unei
	imagini RGB24 (tip CV_8UC3) în trei matrice de tip CV_8UC1. Afișați 
	aceste matrice în 3 ferestre diferite.
	*/
	char fname[MAX_PATH];

	while (openFileDlg(fname))
	{
		double t = (double)getTickCount(); // Get the current time [s]

		Mat src = imread(fname, CV_LOAD_IMAGE_COLOR);
		int height = src.rows;
		int width = src.cols;
		Mat dst1 = Mat(height, width, CV_8UC1);
		Mat dst2 = Mat(height, width, CV_8UC1);
		Mat dst3 = Mat(height, width, CV_8UC1);
		// Asa se acceseaaza pixelii individuali pt. o imagine cu 8 biti/pixel
		// Varianta ineficienta (lenta)
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				Vec3b pixel = src.at< Vec3b>(i, j);
				unsigned char B = pixel[0];
				unsigned char G = pixel[1];
				unsigned char R = pixel[2];
				dst1.at<uchar>(i, j) = R;
				dst2.at<uchar>(i, j) = G;
				dst3.at<uchar>(i, j) = B;
			}
		}

		// Get the current time again and compute the time difference [s]
		t = ((double)getTickCount() - t) / getTickFrequency();
		// Print (in the console window) the processing time in [ms] 
		printf("Time = %.3f [ms]\n", t * 1000);

		imshow("input image", src);
		imshow("img red", dst1);
		imshow("img green", dst2);
		imshow("img blue", dst3);
		waitKey();
	}
}


void lab2pb2() {
	/*
	* Adăugați la framework o funcție de conversie de la o imagine color 
	RGB24 (tip CV_8UC3) la o imagine grayscale de tip (CV_8UC1) și afișați 
	imaginea rezultat într-o fereastră destinație.
	*/

	char fname[MAX_PATH];

	while (openFileDlg(fname))
	{
		double t = (double)getTickCount(); // Get the current time [s]

		Mat src = imread(fname, CV_LOAD_IMAGE_COLOR);
		int height = src.rows;
		int width = src.cols;
		Mat dst1 = Mat(height, width, CV_8UC1);
		// Asa se acceseaaza pixelii individuali pt. o imagine cu 8 biti/pixel
		// Varianta ineficienta (lenta)
		for (int i = 0; i < height; i++)		{
			for (int j = 0; j < width; j++)
			{
				Vec3b pixel = src.at< Vec3b>(i, j);
				unsigned char B = pixel[0];
				unsigned char G = pixel[1];
				unsigned char R = pixel[2];
				unsigned char total = (R + G + B) / 3;
				dst1.at<uchar>(i, j) = total;
			
			}
		}

		// Get the current time again and compute the time difference [s]
		t = ((double)getTickCount() - t) / getTickFrequency();
		// Print (in the console window) the processing time in [ms] 
		printf("Time = %.3f [ms]\n", t * 1000);

		imshow("input image", src);
		imshow("output image", dst1);
	
		waitKey();
	}
}

void lab2pb3() {
/*Adăugați la framework o funcție de procesare pentru conversia de la 
grayscale la alb-negru pentru imagini grayscale (CV_8UC1), folosind (2.2).
Citiți valoarea pragului de la linia de comandă. Testați operația de 
binarizare folosind diverse imagini și diverse praguri.
*/
	int intrare;
	cout << "Introduceti valoarea de threshold: ";
	cin >> intrare;

	char fname[MAX_PATH];

	while (openFileDlg(fname)) {
		double t = (double)getTickCount(); // Get the current time [s]

		Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
		int height = src.rows;
		int width = src.cols;
		Mat dst1 = Mat(height, width, CV_8UC1);
		// Asa se acceseaaza pixelii individuali pt. o imagine cu 8 biti/pixel
		// Varianta ineficienta (lenta)
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				uchar val = src.at<uchar>(i, j);
				uchar pixel;
				if (val < intrare) {
					pixel = 0;
				}
				else {
					pixel = 255;
				}
				dst1.at<uchar>(i, j) = pixel;

			}
		}

		// Get the current time again and compute the time difference [s]
		t = ((double)getTickCount() - t) / getTickFrequency();
		// Print (in the console window) the processing time in [ms] 
		printf("Time = %.3f [ms]\n", t * 1000);

		imshow("input image", src);
		imshow("output image", dst1);

		waitKey();
	}

}
void lab2pb4() {
/*
4. Adăugați la framework o funcție care convertește canalele R,G,B ale unei 
imagini RGB24 (tip CV_8UC3) în componente H, S, V folosind ecuațiile din
2.6. Memorați componente H, S, V în câte o matrice de tip CV_8UC1 
corespunzătoare canalelor H, S, V. Afișați aceste matrice în 3 ferestre 
diferite. Verificați corectitudinea implementării prin comparație vizuală 
cu rezultatele de mai jos.
*/

	char fname[MAX_PATH];

	while (openFileDlg(fname)) {
		double t = (double)getTickCount(); // Get the current time [s]
		Mat src = imread(fname, CV_LOAD_IMAGE_COLOR);
		int height = src.rows;
		int width = src.cols;
		Mat dst1 = Mat(height, width, CV_8UC1);
		Mat dst2 = Mat(height, width, CV_8UC1);
		Mat dst3 = Mat(height, width, CV_8UC1);

		for (int i = 0; i < height; i++) {

			for (int j = 0; j < width; j++)
			{
				Vec3b pixel = src.at< Vec3b>(i, j);
				unsigned char B = pixel[0];
				unsigned char G = pixel[1];
				unsigned char R = pixel[2];
				
				float r = (float)R / 255;
				float g = (float)G / 255;
				float b = (float)B / 255;

				float M = max(max(r, g), b);
				float m = min(min(r, g), b);

				float C = M - m;

				float H, S, V;

				V = M;

				if (V != 0) {
					S = C / V;
				}
				else {
					S = 0;
				}

				if (C != 0) {
					if (M == r) H = 60 * (g - b) / C;
					if (M == g) H = 120 + 60 * (b - r) / C;
					if (M == b) H = 240 + 60 * (r - g) / C;
				}
				else {
					H = 0;
				}
				
				if (H < 0) {
					H = H + 360;
				}

				float H_norm, S_norm, V_norm;
				H_norm = H * 255 / 360;
				S_norm = S * 255;
				V_norm = V * 255;

				dst1.at<uchar>(i, j) = H_norm;
				dst2.at<uchar>(i, j) = S_norm;
				dst3.at<uchar>(i, j) = V_norm;

			}
		}

		t = ((double)getTickCount() - t) / getTickFrequency();
		printf("Time = %.3f [ms]\n", t * 1000);

		imshow("input image", src);
		imshow("H", dst1);
		imshow("S", dst2);
		imshow("V", dst3);
		waitKey();
	}

}

int isInside(int row, int col, Mat img){
	if (row >= 0 && row <= img.rows && col >= 0 && col <= img.cols) {
		return 1;
	}
	return 0;
}

void lab2pb5() {
	char fname[MAX_PATH];

	while (openFileDlg(fname))
	{
		double t = (double)getTickCount(); // Get the current time [s]

		Mat src = imread(fname, CV_LOAD_IMAGE_COLOR);
		int row, col;
		cout << "Introduceti linia: ";
		cin >> row;
		cout << "Introduceti coloana: ";
		cin >> col;
		cout << endl;

		int este = isInside(row, col, src);

		if (este == 1) {
			cout << "Este inauntru.";
		}
		else {
			cout << "Nu este inauntru.";
		}

		// Get the current time again and compute the time difference [s]
		t = ((double)getTickCount() - t) / getTickFrequency();
		// Print (in the console window) the processing time in [ms] 
		printf("\nTime = %.3f [ms]\n", t * 1000);

		imshow("input image", src);

		waitKey();
	}
}

void lab3pb1() {
	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
		int height = src.rows;
		int width = src.cols;

		int list[256] = { 0 };
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				uchar g = src.at<uchar>(i, j);
				list[g] = list[g] + 1;
			}
		}
		std::cout << endl;
		for (int i = 0; i < 256; i++) {
			std::cout << list[i] << " ";
		}
		
		imshow("input image", src);
		waitKey();
	}

}

void lab3pb2() {
	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
		int height = src.rows;
		int width = src.cols;

		int list[256] = { 0 };
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				uchar g = src.at<uchar>(i, j);
				list[g] = list[g] + 1;
			}
		}

		float FDP[256] = { 0.0 };
		int M = height * width;
		for (int i = 0; i < 256; i++) {
			FDP[i] = ((float)list[i]) / ((float)M);
		}

		std::cout << endl;
		for (int i = 0; i < 256; i++) {
			std::cout << FDP[i] << " ";
		}

		imshow("input image", src);
		waitKey();
	}
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

void lab3pb3() {
	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
		int height = src.rows;
		int width = src.cols;

		int list[256] = { 0 };
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				uchar g = src.at<uchar>(i, j);
				list[g] = list[g] + 1;
			}
		}

		float FDP[256] = { 0.0 };
		int M = height * width;
		for (int i = 0; i < 256; i++) {
			FDP[i] = ((float)list[i]) / ((float)M);
		}

		showHistogram("MyHist", list, width, height);

		imshow("input image", src);
		waitKey();
	}

}

void lab3pb4() {
	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
		int height = src.rows;
		int width = src.cols;

		int m;
		printf("Introduceti un  m <= 256 : ");
		scanf("%d", &m);

		int* x;
		x = (int*)calloc(m, sizeof(int));
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				uchar g = src.at<uchar>(i, j);
				g /= m;
				x[g]++;
			}
		}
		

		showHistogram("MyHist", x,m, height);


		imshow("input image", src);
		waitKey();	  
	}
}
void lab3pb5() {
	char fname[MAX_PATH];
	int WH = 5;
	float TH = 0.0003f;
	while (openFileDlg(fname))
	{
		Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
		int height = src.rows;
		int width = src.cols;
		Mat dst = Mat(height, width, CV_8UC1);

		int list[256] = { 0 };
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				uchar g = src.at<uchar>(i, j);
				list[g] = list[g] + 1;
			}
		}

		float FDP[256] = { 0.0 };
		int M = height * width;
		for (int i = 0; i < 256; i++) {
			FDP[i] = ((float)list[i]) / ((float)M);
		}
		vector<uchar> vf, mijloace;
		vf.push_back(0);
		mijloace.push_back(0);

		for (int i = WH; i <= 255 - WH; i++) {
			float medie = 0.0;
			int max = 1;
			for (int j = 0; j < 2 * WH + 1; j++) {
				medie += FDP[i + j - WH];
				if (FDP[i + j - WH] > FDP[i]) {
					max = 0;
				}
			}

		    medie = medie / ((float)(2 * WH + 1));

			if (max && FDP[i] > medie + TH) {
				vf.push_back(i);
			
		}}			

		vf.push_back(255);
		
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				uchar current = src.at<uchar>(i, j);
				for (int k = 0; k < vf.size(); k++)
				{
					if (current >= vf[k] && current <= vf[k + 1])
					{
						if (current - vf[k] < vf[k + 1] - current)
							dst.at<uchar>(i, j) = vf[k];
						else
							dst.at<uchar>(i, j) = vf[k + 1];
					}
					
				}
			}
		}
		showHistogram("MyHist", list, width, height);
		imshow("multi level image", dst);
		imshow("initial image", src);
		waitKey();
	}
}


void lab3pb6() {
	int WH = 5;
	float TH = 0.0003f;
	char fname[MAX_PATH];

		while (openFileDlg(fname))
		{
			Mat src = imread(fname, CV_LOAD_IMAGE_GRAYSCALE);
			int height = src.rows;
			int width = src.cols;
			Mat dst = Mat(height, width, CV_8UC1);
			Mat dstFin = Mat(height, width, CV_8UC1);

			int list[256] = { 0 };
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					uchar g = src.at<uchar>(i, j);
					list[g] = list[g] + 1;
				}
			}

			float FDP[256] = { 0.0 };
			int M = height * width;
			for (int i = 0; i < 256; i++) {
				FDP[i] = ((float)list[i]) / ((float)M);
			}
			vector<uchar> vf, mijloace;
			vf.push_back(0);
			mijloace.push_back(0);

			for (int i = WH; i <= 255 - WH; i++) {
				float medie = 0.0;
				int max = 1;
				for (int j = 0; j < 2 * WH + 1; j++) {
					medie += FDP[i + j - WH];
					if (FDP[i + j - WH] > FDP[i]) {
						max = 0;
					}
				}

				medie = medie / ((float)(2 * WH + 1));

				if (max && FDP[i] > medie + TH) {
					vf.push_back(i);

				}
			}

			vf.push_back(255);

			for (int i = 0; i < height; i++)
			{
				for (int j = 0; j < width; j++)
				{
					uchar current = src.at<uchar>(i, j);
					for (int k = 0; k < vf.size(); k++)
					{
						if (current >= vf[k] && current <= vf[k + 1])
						{
							if (current - vf[k] < vf[k + 1] - current)
								dst.at<uchar>(i, j) = vf[k];
							else
								dst.at<uchar>(i, j) = vf[k + 1];
						}

					}
				}
			}

			dstFin = dst.clone();

			int a;
			for (int i = 0; i < height-1; i++) {
				for (int j = 0; j < width-1; j++) {
					uchar oldpixel = src.at<uchar>(i, j);
					uchar newpixel = dst.at<uchar>(i, j);
					
					int error = oldpixel - newpixel;
					
					if (isInside(i, j + 1, dstFin) == 1) {
						
						
						a = dstFin.at<uchar>(i, j+1) + (7*error/16);
						
						if (a > 255) {
							dstFin.at<uchar>(i, j + 1) = 255;
						}else if (a < 0) {
							dstFin.at<uchar>(i, j + 1) = 0;
						}
						else {
							dstFin.at<uchar>(i, j + 1) = a;
						}
					}

					if (isInside(i+1, j - 1, dstFin) == 1) {
						
						a = dstFin.at<uchar>(i+1, j-1) + (3 * error / 16);
						
						if (a > 255) {
							dstFin.at<uchar>(i + 1, j - 1) = 255;
						}
						else if (a < 0) {
							dstFin.at<uchar>(i + 1, j - 1) = 0;
						}
						else {
							dstFin.at<uchar>(i + 1, j - 1) = a;
						}
					}

					if (isInside(i+1, j , dstFin) == 1) {
						
						a = dstFin.at<uchar>(i+1, j) + (5 * error / 16);
					
						if (a > 255) {
							dstFin.at<uchar>(i + 1, j) = 255;
						}
						else if (a < 0) {
							dstFin.at<uchar>(i + 1, j) = 0;
						}
						else {
							dstFin.at<uchar>(i + 1, j) = a;
						}
					}

					if (isInside(i + 1, j + 1, dstFin) == 1) {
						
						a = dstFin.at<uchar>(i+1, j+1); + (7 * error / 16);
						
						if (a > 255) {
							dstFin.at<uchar>(i + 1, j + 1) = 255;
						}
						else if (a < 0) {
							dstFin.at<uchar>(i + 1, j + 1) = 0;
						}
						else {
							dstFin.at<uchar>(i + 1, j + 1) = a;
						}
					}					
				}
				
			}
			

			showHistogram("MyHist", list, width, height);
			imshow("multi level image", dst);
			imshow("algorithm img", dstFin);
			imshow("initial image", src);
			waitKey();
		}
}

int ArieLab4(Vec3b pixel, Mat pic) {
	int arie = 0;
	for (int i = 0; i < pic.rows; i++)
	{
		for (int j = 0; j < pic.cols; j++)
		{
			Vec3b pixelCurent = pic.at<Vec3b>(i, j);
			if (pixel[0] == pixelCurent[0] && pixel[1] == pixelCurent[1] && pixel[2] == pixelCurent[2]) {
				arie++;
			}

		}
	}

	return arie;
}

void lab4CentruDeMasa(Vec3b pixel, Mat pic, int arie, int *x, int *y) {
	int cx = 0, cy = 0;

	for (int i = 0; i < pic.rows; i++)
	{
		for (int j = 0; j < pic.cols; j++)
		{
			Vec3b pixelCurent = pic.at<Vec3b>(i, j);
			if (pixel == pixelCurent) {
				cx += i;
				cy += j;
			}

		}
	}

	*x = cx / (float)arie;
	*y = cy / (float)arie;
}

float lab4AxaDeElongatie(Vec3b pixel, Mat pic, float cx, float cy) {
	float numa = 0.0, numi = 0.0;
	for (int i = 0; i < pic.rows; i++)
	{
		for (int j = 0; j < pic.cols; j++)
		{
			Vec3b pixelCurent = pic.at<Vec3b>(i, j);
			if (pixel[0] == pixelCurent[0] && pixel[1] == pixelCurent[1] && pixel[2] == pixelCurent[2]) {
				numa += 2 * (i-cx) * (j-cy);
				numi += ((i - cx) * (i - cx)) - ((j - cy) * (j - cy));
			}

		}
	}

	return atan2(2 * numa, numi) / 2.0;
}

bool esteConturObiect(Vec3b pixel, Mat pic, int x, int y) {
	if (isInside(x + 1, y + 1, pic)==1 && pic.at<Vec3b>(x + 1, y + 1) != pixel)
		return true;
	if (isInside(x, y + 1, pic)==1 && pic.at<Vec3b>(x, y + 1) != pixel)
		return true;
	if (isInside(x - 1, y + 1,pic)==1 && pic.at<Vec3b>(x - 1, y + 1) != pixel)
		return true;
	if (isInside(x - 1, y,pic)==1 && pic.at<Vec3b>(x - 1, y) != pixel)
		return true;
	if (isInside(x - 1, y - 1,pic)==1 && pic.at<Vec3b>(x - 1, y - 1) != pixel)
		return true;
	if (isInside(x, y - 1, pic)==1 && pic.at<Vec3b>(x, y - 1) != pixel)
		return true;
	if (isInside( x + 1, y - 1, pic)==1 && pic.at<Vec3b>(x + 1, y - 1) != pixel)
		return true;
	if (isInside(x + 1, y, pic)==1 && pic.at<Vec3b>(x + 1, y) != pixel)
		return true;
	return false;

}

float lab4Perimetrul(Vec3b pixel, Mat pic) {
	int perimetrul = 0;
	for (int i = 0; i < pic.rows; i++)
	{
		for (int j = 0; j < pic.cols; j++)
		{
			Vec3b pixelCurent = pic.at<Vec3b>(i, j);
			if (pixel[0] == pixelCurent[0] && pixel[1] == pixelCurent[1] && pixel[2] == pixelCurent[2]) {
				if(esteConturObiect(pixel,pic,i,j))
					perimetrul++;
			}

		}
	}
	return perimetrul;
}

void conturul(Vec3b pixel, Mat pic, Mat* dst,int x, int y) {
	for (int i = 0; i < pic.rows; i++)
	{
		for (int j = 0; j < pic.cols; j++)
		{
			Vec3b pixelCurent = pic.at<Vec3b>(i, j);
			if (pixel[0] == pixelCurent[0] && pixel[1] == pixelCurent[1] && pixel[2] == pixelCurent[2]) {
				if (esteConturObiect(pixel, pic, i, j))
					(*dst).at<uchar>(i, j) = 255.0;
				else (*dst).at<uchar>(i, j) = 0.0;
			}
			else (*dst).at<uchar>(i, j) = 0.0;

		}
	}
	
}

void lab4CallBackFunc(int event, int x, int y, int flags, void* param)
{
	//More examples: http://opencvexamples.blogspot.com/2014/01/detect-mouse-clicks-and-moves-on-image.html
	Mat* src = (Mat*)param;
	if (event == CV_EVENT_LBUTTONDOWN)
	{
		Vec3b pixel_color = src->at<Vec3b>(y, x);
		printf("Pos(x,y): %d,%d  Color(RGB): %d,%d,%d\n",
			x, y,
			pixel_color[2],
			pixel_color[1],
			pixel_color[0]);

		int arie = ArieLab4(pixel_color, *src);
		cout << "Aria este: " << arie << endl;
		
		int cx=0, cy=0;
		lab4CentruDeMasa(pixel_color, *src, arie, &cx, &cy);
		cout << "Centrrul de  masa se afla la coordonatele: (" << cy<<", "<<cx<<")" << endl;
		
		float axaDeElongatie = lab4AxaDeElongatie(pixel_color, *src, cx, cy);
		cout << "Unghiul pentru axa de elongatie este: " << (180*axaDeElongatie)/PI << endl;

		int perimetru = lab4Perimetrul(pixel_color, *src);
		cout << "Perimetrul este: " << perimetru << endl;

		int sqp = perimetru * perimetru;
		float factorSubtiere = (4 * PI * arie) / (float)sqp;
		cout << "Factorul de subtiere este: " << factorSubtiere << endl << endl;

		Mat dst1 = Mat(src->rows,src->cols , CV_8UC1);
		
		conturul(pixel_color, *src, &dst1,cx,cy);


		drawMarker(dst1, Point(cy, cx), Scalar(255, 255, 255));
		/*float panta = tan(axaDeElongatie);
		int x1 = (((-cx) / panta) + cy), y2 = (((-panta) * cy) + cx);
		Point P1(x1, 0);
		Point P2(0, y2);*/
		int delta = 30;
		Point P1 = Point(cy - delta, cx - (int)(delta * tan(axaDeElongatie)));
		Point P2 = Point(cy + delta, cx+ (int)(delta * tan(axaDeElongatie)));
		line(dst1, P1, P2, Scalar(255, 255, 255));

		imshow("Contur", dst1);
	}
}



void lab4MouseClick()
{
	Mat src;
	// Read image from file 
	char fname[MAX_PATH];
	while (openFileDlg(fname))
	{
		src = imread(fname);
		//Create a window
		namedWindow("My Window", 1);

		//set the callback function for any mouse event
		setMouseCallback("My Window", lab4CallBackFunc, &src);

		//show the image
		imshow("My Window", src);

		// Wait until user press some key
		waitKey(0);
	}
}

void lab5pb1si2(int n) {

	uchar val;
	int label = 0;
	int di[8] = {-1,  0, 1, 0, -1, -1, 1, 1 };
	int dj[8] = { 0, -1, 0, 1, 1, -1, -1, 1 };


	char fname[MAX_PATH];
	while (openFileDlg(fname)) {
		Mat src = imread(fname, IMREAD_GRAYSCALE);
		Mat labels = Mat::zeros(src.size(), CV_8UC1);
		Mat dst = Mat::zeros(src.size(), CV_8UC3);

		queue<Point> que;

		for (int i = 1; i < src.rows - 1; i++) {
			for (int j = 1; j < src.cols - 1; j++) {
				val = src.at<uchar>(i, j);
				if ((val == 0) && (labels.at<uchar>(i, j) == 0)) {
					label++;
					labels.at<uchar>(i, j) = label;
					que.push(Point(i,j));
					while (!que.empty()) {
						Point q = que.front();
						que.pop();
						int ii = q.x;
						int jj = q.y;
						for (int k = 0; k < n; k++) {
							if ((src.at<uchar>(ii + di[k], jj + dj[k]) == 0) &&
								(labels.at<uchar>(ii + di[k], jj + dj[k]) == 0)) {
								labels.at<uchar>(ii + di[k], jj + dj[k]) = label;
								que.push(Point( ii + di[k], jj + dj[k] ));
							}
						}
					}
				}
			}
		}


		
		for (int k = 1; k <= label; k++) {
			uchar red = rand() % 256;
			uchar blue = rand() % 256;
			uchar green = rand() % 256;
			Vec3b color = Vec3b(blue, green, red);
			for (int i = 1; i < src.rows - 1; i++) {
				for (int j = 1; j < src.cols - 1; j++) {
					if (labels.at<uchar>(i, j) == k)
						dst.at<Vec3b>(i, j) = color;
				}
			}
		}
		imshow("sursa", src);
		imshow("coloured", dst);
		waitKey(0);
	}
}

void lab5pb3() {
	char fname[MAX_PATH];
	while (openFileDlg(fname)) {
		Mat src = imread(fname, IMREAD_GRAYSCALE);
		Mat labels(src.rows,src.cols, CV_32FC1, Scalar(0));
		Mat dst1(src.rows, src.cols, CV_8UC3, Scalar(0));

		int di[4] = { -1, -1, -1,  0 };
		int dj[4] = { -1,  0,  1, -1 };
		int label = 0;
		map<int, vector<int>> edges;
		for (int i = 0; i < src.rows; i++)
		{
			for (int j = 0; j < src.cols; j++)
			{
				
				if (src.at<uchar>(i, j) == 0) {
					if (labels.at<int>(i, j) == 0) {
						vector<int> L;
						for (int k = 0; k <= 3; k++)
						{
							int ii = i + di[k];
							int jj = j + dj[k];

							if (isInside(ii, jj, src))
								if (labels.at<int>(ii, jj) > 0) {
									L.push_back(labels.at<int>(ii, jj));
								}
						}

						if (L.empty())
						{
							label++;
							labels.at<int>(i, j) = label;
						}
						else
						{

							int min = *min_element(L.begin(), L.end());
							labels.at<int>(i, j) = min;

							for each (int y in L)
							{
								if (min != y) {
									if (edges.find(min) == edges.end())
										edges.emplace(min, vector<int>{});
									edges[min].push_back(y);
									if (edges.find(min) == edges.end())
										edges.emplace(y, vector<int>{});
									edges[y].push_back(min);
								}
							}
						}
					}
				}
			}
		}

		
		
		for (int k = 1; k <= label; k++) {
			uchar red = rand() % 256;
			uchar blue = rand() % 256;
			uchar green = rand() % 256;
			Vec3b color = Vec3b(blue, green, red);
			for (int i = 1; i < src.rows ; i++) {
				for (int j = 1; j < src.cols ; j++) {
					if (labels.at<int>(i, j) == k)
						dst1.at<Vec3b>(i, j) = color;
				}
			}
		}

		imshow("sursa", src);
		imshow("intermediar", dst1);


		int newLabel = 0;
		vector<int> newLabels(label + 1);
		fill(newLabels.begin(), newLabels.end(), 0);
		Mat dst2(src.rows, src.cols, CV_8UC3, Scalar(0));

		for (int i = 0; i <label; i++)
		{
			if (newLabels[i] == 0) 
			{
				newLabel++;
				newLabels[i] = newLabel;
				queue<int> Q;
				Q.push(i);

				while (!Q.empty())
				{
					int x = Q.front();
					Q.pop();					
					for each ( int y in edges[x])
					{
						if (newLabels[y] == 0) {
							newLabels[y] = newLabel;
							Q.push(y);
						}
					}
				}				
			}
		}

		for (int i = 0; i < src.rows; i++) {
			for (int j = 0; j <src.cols; j++) {
				labels.at<int>(i, j) = newLabels[labels.at<int>(i, j)];
			}
		}

		for (int k = 1; k <= label; k++) {
			uchar red = rand() % 256;
			uchar blue = rand() % 256;
			uchar green = rand() % 256;
			Vec3b color = Vec3b(blue, green, red);
			for (int i = 1; i < src.rows; i++) {
				for (int j = 1; j < src.cols; j++) {
					if (labels.at<int>(i, j) == k)
						dst2.at<Vec3b>(i, j) = color;
				}
			}
		}

		imshow("final", dst2);
		waitKey(0);


	}
}

void lab6pb1() {
	char fname[MAX_PATH];
	while (openFileDlg(fname)) {
		Mat src = imread(fname, IMREAD_GRAYSCALE);
		Mat dst(src.size(), CV_8UC1, Scalar(255));

		int hight = src.rows, width = src.cols;
		Point P0, P1, punctActual, punctAnterior;
		bool exist = false;

		for (int i = 0; i < hight; i++) {
			for (int j = 0; j < width; j++) {
				if (src.at<uchar>(i, j) == 0) {					
					P0.x = j;
					P0.y = i;
					exist = true;					
					break;
				}
			}
			if (exist) break;		
		}

		cout << "Primul punct gasit: " << P0 << endl;

		int dj[8] = { 1,1,0,-1,-1,-1,0,1 };
		int di[8] = { 0,-1,-1,-1,0,1,1,1 };
		int dir = 7;
		vector<int> ac, dc;
		int n = 0;
		punctActual = P0;
		int ii, jj;
		do
		{
			n++;

			if (dir % 2 == 1) dir = (dir + 6) % 8;
			else dir = (dir + 7) % 8;

			ii = punctActual.y + di[dir];
			jj = punctActual.x + dj[dir];

			while (src.at<uchar>(ii, jj) == 255)
			{
				dir = (dir + 1) % 8;
				ii = punctActual.y + di[dir];
				jj = punctActual.x + dj[dir];
				
			}
			

			if (n == 1)
			{
				P1.x = P0.x + dj[dir];
				P1.y = P0.y + di[dir];
				punctActual = P1;
			}
			else
			{
				punctAnterior = punctActual;
				punctActual.x = punctActual.x + dj[dir];
				punctActual.y = punctActual.y + di[dir];				
			}

			ac.push_back(dir);
			dst.at<uchar>(punctActual.y, punctActual.x) = 0;
			

		} while (!((punctActual == P1) && (punctAnterior == P0)));

		

		for (int i = 0; i < ac.size() - 1; i++) {
			dc.push_back((ac[i+1] - ac[i] + 8 ) % 8);
		}

		cout << "Codul inlantuit: " << endl;
		for (int i = 0; i < ac.size() - 1; i++)
		{
			cout << ac[i] << " ";
		}
		cout << endl;

		cout << "Derivata: " << endl;
		for (int i = 0; i < ac.size() - 1; i++)
		{
			cout << dc[i] << " ";
		}
		cout << endl;

		imshow("Sursa", src);
		imshow("Destinatia", dst);
		waitKey(0);
	}
}

void lab6pb3() {
	ifstream myfile;
	myfile.open("Images/reconstruct.txt");
	Point p;
	myfile >> p.y;
	myfile >> p.x;
	
	int n;
	myfile >> n;

	int dj[8] = { 1,1,0,-1,-1,-1,0,1 };
	int di[8] = { 0,-1,-1,-1,0,1,1,1 };
	Mat dst(400, 800, CV_8UC1, Scalar(0));

	dst.at<uchar>(p.y, p.x) = 255;

	for (int i = 0; i < n; i++) {
		int dir;
		myfile >> dir;
		Point p1(p.x + dj[dir], p.y + di[dir]);
		p = p1;
		dst.at<uchar>(p.y, p.x) = 255;
	}

	imshow("Destinatia", dst);
	waitKey(0);
}

int main()
{
	int op;
	do
	{
		system("cls");
		destroyAllWindows();
		printf("Menu:\n");
		printf(" 0 - Exit\n");
		printf(" 1 - Open image\n");
		printf(" 2 - Open BMP images from folder\n");
		printf(" 3 - Image negative - diblook style\n");
		printf(" 4 - BGR->HSV\n");
		printf(" 5 - Resize image\n");
		printf(" 6 - Canny edge detection\n");
		printf(" 7 - Edges in a video sequence\n");
		printf(" 8 - Snap frame from live video\n");
		printf(" 9 - Mouse callback demo\n");

		printf(" \nLaboratorl 1\n");
		printf(" 10 - Schimbare nivel de gri cu factor aditiv\n");
		printf(" 11 - Schimbare nivel de gri cu factor multiplicativ\n");
		printf(" 12 - Image open and save\n");
		printf(" 13 - Negative image\n");
		printf(" 14 - Imagine cu cadrane de culori diferite\n");
		printf(" 15 - Inversa unei matrici\n");

		printf(" \nLaboratorl 2\n");
		printf(" 16 - Lab2pb1\n");
		printf(" 17 - Lab2pb2\n");
		printf(" 18 - Lab2pb3\n");
		printf(" 19 - Lab2pb4\n");
		printf(" 20 - Lab2pb5\n");

		printf(" \nLaboratorl 3\n");
		printf(" 21 - Lab3pb1\n");
		printf(" 22 - Lab3pb2\n");
		printf(" 23 - Lab3pb3\n");
		printf(" 24 - Lab3pb4\n");
		printf(" 25 - Lab3pb5\n");
		printf(" 26 - Lab3pb6\n");

		printf(" \nLaboratorl 4\n");
		printf(" 27 - Proprietati geometrice\n");

		printf(" \nLaboratorl 5\n");
		printf(" 28 - Lab5pb1\n");
		printf(" 29 - Lab5pb3\n");

		printf(" \nLaboratorl 5\n");
		printf(" 30 - Lab6pb1sipb2\n");
		printf(" 31 - Lab6pb3\n");

		
		printf("\nOption: ");
		scanf("%d",&op);
		switch (op)
		{
			case 1:
				testOpenImage();
				break;
			case 2:
				testOpenImagesFld();
				break;
			case 3:
				testParcurgereSimplaDiblookStyle(); //diblook style
				break;
			case 4:
				//testColor2Gray();
				testBGR2HSV();
				break;
			case 5:
				testResize();
				break;
			case 6:
				testCanny();
				break;
			case 7:
				testVideoSequence();
				break;
			case 8:
				testSnap();
				break;
			case 9:
				testMouseClick();
				break;
			case 10:
				testGrayScaleAditivFactor();
				break;
			case 11:
				testGrayScaleMultiplicativFactor();
				break;
			case 12:
				testImageOpenAndSave();
				break;
			case 13:
				testNegativeImage();
				break;
			case 14:
				testCadraneImage();
				break;
			case 15:
				testInversaMatrice();
				break;
			case 16:
				lab2pb1();
				break;
			case 17:
				lab2pb2();
				break;
			case 18:
				lab2pb3();
				break;
			case 19:
				lab2pb4();
				break;
			case 20:
				lab2pb5();
				break;
			case 21:
				lab3pb1();
				break;
			case 22:
				lab3pb2();
				break;
			case 23:
				lab3pb3();
				break;
			case 24:
				lab3pb4();
				break;
			case 25:
				lab3pb5();
				break;
			case 26:
				lab3pb6();
				break;
			case 27:
				lab4MouseClick();
				break;
			case 28:
				int n;
				cout<<"Introduceti 4 sau 8: ";
				cin >> n;
				lab5pb1si2(n);
				break;
			case 29:
				lab5pb3();
				break;
			case 30:
				lab6pb1();
				break;
			case 31:
				lab6pb3();
				break;
		}
	}
	while (op!=0);
	return 0;
}