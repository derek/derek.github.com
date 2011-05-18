#include "proj3.h"

int main()
{
   bool encrypted;
   char filename[51];
	int key = GetFileName(filename, encrypted);
	if (encrypted == false)
   	encrypt_file(key, filename);
   if (encrypted == true)
   	decrypt_file(key, filename);
	cout << "\nThank You, Good Bye.";
	exit (1);
}

//GetFileName is only used to recieve the input for the filename from the used
//and determines whether it is an encrypted or decrypted file.
int GetFileName(char filename[], bool &encrypted)
{
	int key = 0;
   encrypted = false;
	cout << "Enter the filename: ";
   cin >> setw(51) >> filename;
	for (int i = 0; i <= strlen(filename); i++)
   {
   	key += int(filename[i]);
   }
   int filename_length = strlen(filename);

   if ((filename[filename_length - 4] == '.') && (filename[filename_length - 3]
   	== 's') && (filename[filename_length - 2] == 'e')
      && (filename[filename_length - 1] == 'c'))

   	{
         key -= (int('.') + int('s') + int('e') + int('c'));
      	encrypted = true;
		}
	return (key);
}

//encrypt_file is used when a file is decrypted and it is given the key
//and the filename array and handles the input and output to files to encrypt.
void encrypt_file(int key, char filename[])
{
   ofstream out_file;
   ifstream in_file;
   in_file.open(filename);
   out_file.open(strcat(filename, ".sec"));
   if (!in_file)
	{
		error(1, filename);
	}
	else
	{
	   char c;

	   while (in_file.get(c))
	   {

			c = encryptChar(c, key);
		  	out_file << c;
	   }
		cout << "Encryption done, thank you." << endl;
		out_file.close();
	   in_file.close();
   }
}

// encryptChar is given a character and a key value, and it returns
// a different (encrypted) character
char encryptChar(char c, int key)
{
	int token = ((key + 17) * 257) % 89;
	if ((c < 32) || (c > 120))
	{
		return c;
	}
	return ((((c - 32) + token) % 89) + 32);
}

//decrypt_file is used when a file is already encrypted and it is given the key
//and the filename array and handles the input and output to files to decrypt.
void decrypt_file(int key, char filename[])
{
   ofstream out_file;
   ifstream in_file;
   char out_file_name[51];
   for (int i = 0; i <= 51; i++)
   {
   	out_file_name[i] = filename[i];
   }
   for (int i = 0; i <= strlen(filename); i++)
   {

   	if ((filename[i] == '.') && (filename[i+1] == 's') &&
	      (filename[i+2] == 'e') && (filename[i+3] == 'c'))

         out_file_name[i] ='\0';
   }
	in_file.open(filename);
	out_file.open(out_file_name);
   if (!in_file)
	{
		error(1, filename);
	}
	else
	{
	   char c;
	   while (in_file.get(c))
	   {
			c = decryptChar(c, key);
		  	out_file << c;
	   }
		cout << "Decryption done, thank you." << endl;
		out_file.close();
	   in_file.close();
   }
}

// decryptChar is given a character and a key value, and it returns
// the decrypted character
char decryptChar(char c, int key)
{
	int token = ((key + 17) * 257) % 89;
	if ((c < 32) || (c > 120))
	{
		return c;
	}
	return ((((c - 32) - token + 89) % 89) + 32);
}

//error is given an integer corresponding to an error and outputs the error and
//exits the program.
void error(int error_code, char filename[])
{
	if (error_code == 1)
      {
         cerr <<"The file " << filename << " could not be opened\n";
      }
	cout << "\nThank You, Good Bye.";
   exit (1);
}
