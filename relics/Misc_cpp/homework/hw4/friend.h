#ifndef FRIEND_H
#define FRIEND_H
class Friend
{
	private:
   	char name[50];
      int age;
   public:
   	Friend();
      void setName(const char *str) const;
      void setAge(const int amount) const;
      void displayInfo();
      char *getName();
		int getAge();
};
#endif
