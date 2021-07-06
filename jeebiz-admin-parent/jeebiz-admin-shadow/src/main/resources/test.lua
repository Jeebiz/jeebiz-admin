if redis.call('exists', KEYS[1]) == 0 then
	redis.call('set', KEYS[1], ARGV[1]);
	return 0;
end;
local current = redis.call('incr', KEYS[1], ARGV[1]);
if current < 0 then
	redis.call('decr', KEYS[1], ARGV[1]);
	return 0;
else
	return current;
end;
 