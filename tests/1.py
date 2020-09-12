import random

start = 80000000000 
end = 90000000000
with open ("Keys.txt", "r") as f_r:
	with open ("Key-Value.txt", "w") as f_w:
		for i in range(124):
			key = f_r.readline().strip("\n")
			phone = str(random.randint(start, end))
			f_w.write(key+"&"+phone+"\n")