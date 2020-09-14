import string
import random

def main():

	start = 80000000000 
	end = 90000000000
	pool = string.ascii_letters
	commands = ["Append", "Get", "Set", "Dbsize", "Lpop"]
	keys = ["khfdvhfdvb"]

	with open ("random_commands.txt", "w") as f_w:
		for i in range(1000000-1):

			command = random.choice(commands)
			key = ''.join(random.choices(pool, k = 10))
			value = str(random.randint(start, end))

			if command == "Dbsize" or command == "Lpop":
				f_w.write(command+"\n")
			elif command == "Get":			
				f_w.write(command+"?"+key+"\n")
			elif command == "Append":
				keys.append(key)
				f_w.write(command+"?"+key+"&"+value+"\n")
			else: 
				key = random.choice(keys)
				f_w.write(command+"?"+key+"&"+value+"\n")

		# last step
		command = random.choice(commands)
		key = ''.join(random.choices(pool, k = 10))
		value = str(random.randint(start, end))

		if command == "Dbsize" or command == "Lpop":
			f_w.write(command+"\n")
		elif command == "Get":			
			f_w.write(command+"?"+key+"\n")
		elif command == "Append":
			keys.append(key)
			f_w.write(command+"?"+key+"&"+value+"\n")
		else: 
			key = random.choice(keys)
			f_w.write(command+"?"+key+"&"+value+"\n")
		

if __name__ == '__main__':
	main()