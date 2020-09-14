import string
import random

def main():

	start = 80000000000 
	end = 90000000000
	pool = string.ascii_letters

	with open ("Key-Value-1000000.txt", "w") as f_w:
		for i in range(1000000-1):
			key = ''.join(random.choices(pool, k = 10))
			value = str(random.randint(start, end))
			f_w.write("Append?"+key+"&"+value+"\n")
		# last step
		key = ''.join(random.choices(pool, k = 10))
		value = str(random.randint(start, end))
		f_w.write("Append?"+key+"&"+value)
		

if __name__ == '__main__':
	main()