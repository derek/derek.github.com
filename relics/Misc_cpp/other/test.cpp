#include <iostream.h>
#include <fstream.h>

int main()
{
ifstream infile;
infile.open("test.txt");
char c;
int a = 0;
while (infile.get(c))
{
	cout << c;
   if (c == '\n')
   	a++;
}

cout << "\n\na = " << a;
}

