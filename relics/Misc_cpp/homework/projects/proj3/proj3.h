#ifndef proj3_h
#define proj3_h
	#include <iostream.h>
	#include <fstream.h>
	#include <string>
	#include <iomanip.h>

   char 	encryptChar(char c, int key);
	char 	decryptChar(char c, int key);
	void  error(int error_code, char filename[]);
	int 	GetFileName(char filename[], bool &encrypted);
	void 	encrypt_file(int key, char filename[]);
	void 	decrypt_file(int key, char filename[]);
#endif

