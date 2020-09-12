import string
 
def unique_strings(k: int, ntokens: int, pool: str=string.ascii_letters) -> list:
    seen = []
    while len(seen) < ntokens:
        token = ''.join(random.choices(pool, k = k))
        seen.append(token)
    return seen

keys = unique_strings(k = 10, ntokens = 1000)