if redis.call('exists', KEYS[1]) == 0 then \n
	redis.call('set', KEYS[1], ARGV[1])  \n
	return 0  \n
end \n 
local current = redis.call('incr', KEYS[1], ARGV[1])  \n
if current < 0 then \n
	redis.call('decr', KEYS[1], ARGV[1])  \n
	return 0  \n
else  \n
	return current  \n
end
 