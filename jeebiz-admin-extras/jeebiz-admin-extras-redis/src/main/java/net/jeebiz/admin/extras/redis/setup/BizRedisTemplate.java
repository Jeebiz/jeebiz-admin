package net.jeebiz.admin.extras.redis.setup;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.redis.core.RedisOperationTemplate;

import com.alibaba.fastjson.JSON;

import hitool.core.lang3.time.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import net.jeebiz.admin.api.UserProfiles;
import net.jeebiz.admin.api.dto.UserInfoDTO;
import net.jeebiz.admin.api.dto.UserInfoSimpleDTO;

@Slf4j
public class BizRedisTemplate {

    private RedisOperationTemplate redisOperation;

    public BizRedisTemplate(RedisOperationTemplate redisOperation) {
        this.redisOperation = Objects.requireNonNull(redisOperation);
    }

    // 1、用户信息缓存-------------------------------------------------------------------------------------

    public int getGender(String uid) {
        // 1、查询是否缓存（在线用户缓存数据）
    	String userKey = BizRedisKey.USER_INFO.getKey(uid.toString());
        Object cacheValue = redisOperation.hGet(userKey, UserProfiles.GENDER);
        return Objects.isNull(cacheValue) ? 2 : Integer.parseInt(cacheValue.toString());
    }

    public String getUserAppId(String uid) {
        // 1、查询是否缓存（在线用户缓存数据）
    	String userKey = BizRedisKey.USER_INFO.getKey(uid.toString());
        Object cacheValue = redisOperation.hGet(userKey, UserProfiles.APP_ID);
        return Objects.isNull(cacheValue) ? "1.0.0" : cacheValue.toString();
    }

    public int getUserVip(String uid) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid);
        Object cacheValue = redisOperation.hGet(userKey, UserProfiles.VIP);
        return Objects.isNull(cacheValue) ? 0 : Integer.parseInt(cacheValue.toString());
    }

    public Object getUserFiled(String uid, String filed) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid);
        return redisOperation.hGet(userKey, filed);
    }

    public <T> T getUserFiled(String uid, String filed, Class<T> clazz) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid);
        return (T) redisOperation.hGet(userKey, filed);
    }

    public <T> T getUserFiled(String uid, String filed, Object def, Class<T> clazz) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid);
        Object obj = redisOperation.hGet(userKey, filed);
        return (T) (Objects.isNull(obj) ? def : obj);
    }

    public boolean setUserFiled(Long uid, String filed, Object value) {
        // 1、查询是否缓存（在线用户缓存数据）
    	String userKey = BizRedisKey.USER_INFO.getKey(uid.toString());
        return redisOperation.hSet(userKey, filed, value);
    }

    public boolean setUserFiled(String uid, String filed, Object value) {
        // 1、查询是否缓存（在线用户缓存数据）
        String userKey = BizRedisKey.USER_INFO.getKey(uid);
        return redisOperation.hSet(userKey, filed, value);
    }

    public String getUserFiledString(String uid, String filed) {
        Object cacheValue = this.getUserFiled(uid, filed);
        return Objects.isNull(cacheValue) ? "" : cacheValue.toString();
    }

    public Map<String, Object> getInfoMap(String uid, Collection<Object> fields) {
    	// 1、查询是否缓存（在线用户缓存数据）
    	String userKey = BizRedisKey.USER_INFO.getKey(uid);
        Map<String, Object> cacheMap = redisOperation.hmMultiGet(userKey, fields);
        return cacheMap;
    }

    public List<Map<String, Object>> getInfoMaps(List<String> uids, Collection<Object> fields) {
        return batchGetUserFields(uids, fields);
    }

    public UserInfoDTO getUserInfoByUserId(Long userId) {
        log.debug("getUserInfoByUserId start userId:{}", userId);
        //从redis中查询用户信息
        String key = BizRedisKey.USER_INFO.getKey(userId.toString());
        Map<String, Object> map = redisOperation.hmGet(key);
        if (map != null && map.get("userId") != null) {
            UserInfoDTO userInfoDTO = JSON.parseObject(JSON.toJSONString(map), UserInfoDTO.class);
            if (Objects.nonNull(userInfoDTO.getBirthday())) {
                userInfoDTO.setAge(TimestampUtils.getAgeFromUnixTime(userInfoDTO.getBirthday() / 1000));
            }
            return userInfoDTO;
        }
        return null;
    }

    public List<UserInfoDTO> getUserInfoListByUserIds(Collection<String> userIds) {

        // 1、缓存获取用户信息
        List<Map<String, Object>> userHashMaps = batchGetUserFields(userIds);
        List<UserInfoDTO> list = userHashMaps.stream()
                .filter(hash -> Objects.nonNull(hash.get("userId")))
                .map(object -> {
                    UserInfoDTO userInfoDTO = JSON.parseObject(JSON.toJSONString(object), UserInfoDTO.class);
                    if (Objects.nonNull(userInfoDTO.getBirthday())) {
                        userInfoDTO.setAge(TimestampUtils.getAgeFromUnixTime(userInfoDTO.getBirthday() / 1000));
                    }
                    return userInfoDTO;
                }).collect(Collectors.toList());
        //
        List<Long> collect = list.stream().map(UserInfoDTO::getUserId).collect(Collectors.toList());
        return list;
    }

    /**
     * 该方法输出简单的用户信息
     * !!!!!!!!不保证数据: 只查缓存,不查数据库
     *
     * @param userIds
     * @return
     */
    public List<UserInfoSimpleDTO> getUserInfoSimpleListByUserIds(Collection<Long> userIds) {
        // 1、缓存获取用户信息
        List<Map<String, Object>> objects = batchGetUserInfo(userIds);
        List<UserInfoSimpleDTO> list = objects.stream().filter(object -> ((HashMap) object).get("userId") != null).map(object -> {
            UserInfoSimpleDTO userInfoDTO = JSON.parseObject(JSON.toJSONString(object), UserInfoSimpleDTO.class);
            return userInfoDTO;
        }).collect(Collectors.toList());
        return list;
    }

    public UserInfoSimpleDTO getUserInfoSimpleByUserId(Long userId) {
        String userKey = BizRedisKey.USER_INFO.getKey(userId);
        Map<String, Object> userCache = redisOperation.hmGet(userKey);
        UserInfoSimpleDTO userInfoDTO = JSON.parseObject(JSON.toJSONString(userCache), UserInfoSimpleDTO.class);
        return userInfoDTO;
    }
    
    // ===============================batchGet=================================

    public <K> List<Map<String, Object>> batchGetUserInfo(Collection<K> uids) {
        Collection<Object> uKeys = uids.stream().map(uid -> {
            return BizRedisKey.USER_INFO.getKey(Objects.toString(uid));
        }).collect(Collectors.toList());
        return redisOperation.hmMultiGetAll(uKeys);
    }

    public <K> Map<String, Object> batchGetUserFields(K uid, Collection<Object> hashKeys) {
        String userKey = BizRedisKey.USER_INFO.getKey(Objects.toString(uid));
        return redisOperation.hmMultiGet(userKey, hashKeys);
    }

    public <K> Map<String, Object> batchGetUserFields(K uid, String... hashKeys) {
        String userKey = BizRedisKey.USER_INFO.getKey(Objects.toString(uid));
        return redisOperation.hmMultiGet(userKey, Stream.of(hashKeys).collect(Collectors.toList()));
    }

    public <K> List<Map<String, Object>> batchGetUserFields(Collection<K> uids, String... hashKeys) {
        List<String> uKeys = uids.stream().map(uid -> {
            return BizRedisKey.USER_INFO.getKey(Objects.toString(uid));
        }).collect(Collectors.toList());
        return redisOperation.hmMultiGet(uKeys, Stream.of(hashKeys).collect(Collectors.toList()));
    }

    public <K> List<Map<String, Object>> batchGetUserFields(Collection<K> uids, Collection<Object> hashKeys) {
        List<String> uKeys = uids.stream().map(uid -> {
            return BizRedisKey.USER_INFO.getKey(Objects.toString(uid));
        }).collect(Collectors.toList());
        return redisOperation.hmMultiGet(uKeys, hashKeys);
    }

    public <K> Map<String, Map<String, Object>> batchGetUserFields(Collection<K> uids, String identityField,
                                                                   Collection<Object> hashKeys) {
        List<String> uKeys = uids.stream().map(uid -> {
            return BizRedisKey.USER_INFO.getKey(Objects.toString(uid));
        }).collect(Collectors.toList());
        return redisOperation.hmMultiGet(uKeys, identityField, hashKeys);
    }

}
