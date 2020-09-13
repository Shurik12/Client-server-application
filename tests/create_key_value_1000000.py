import string
import random
 
def unique_strings(k: int, ntokens: int, pool: str=string.ascii_letters) -> list:
    seen = []
    while len(seen) < ntokens:
        token = ''.join(random.choices(pool, k = k))
        seen.append(token.lower())
    return seen

def main():
	start = 80000000000 
	end = 90000000000
	keys = unique_strings(k = 10, ntokens = 1000000)
	with open ("Key-Value-1000000.txt", "w") as f_w:
		for key in keys:
			phone = str(random.randint(start, end))
			f_w.write("Append?"+key+"&"+phone+"\n")
		

if __name__ == '__main__':
	main()