package org.apache.shiro.session.mgt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@SuppressWarnings("serial")
public class OnlineSession extends SimpleOnlineSession {

	private static final int USEr_id_BIT_MASK = 1 << bitIndexCounter++;
	private static final int USER_AGENT_BIT_MASK = 1 << bitIndexCounter++;
	private static final int STATUS_BIT_MASK = 1 << bitIndexCounter++;
	private static final int USERname_BIT_MASK = 1 << bitIndexCounter++;
	//private static final int REMEMBER_ME_BIT_MASK = 1 << bitIndexCounter++;

	/** 当前登录的用户Id */
    private String userid;
    /** 当前登录的用户名称 */
    private String username;
    
    public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
     * Serializes this object to the specified output stream for JDK Serialization.
     *
     * @param out output stream used for Object serialization.
     * @throws java.io.IOException if any of this object's fields cannot be written to the stream.
     * @since 1.0
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        short alteredFieldsBitMask = getAlteredFieldsBitMask();
        out.writeShort(alteredFieldsBitMask);
        if (userid != null) {
            out.writeObject(userid);
        }
        if (userAgent != null) {
            out.writeObject(userAgent);
        }
        if (status != null) {
            out.writeObject(status);
        }
        if (username != null) {
            out.writeObject(username);
        }
    }

    /**
     * Reconstitutes this object based on the specified InputStream for JDK Serialization.
     *
     * @param in the input stream to use for reading data to populate this object.
     * @throws IOException            if the input stream cannot be used.
     * @throws ClassNotFoundException if a required class needed for instantiation is not available in the present JVM
     * @since 1.0
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        short bitMask = in.readShort();

        if (isFieldPresent(bitMask, USEr_id_BIT_MASK)) {
            this.userid = (String) in.readObject();
        }
        if (isFieldPresent(bitMask, USER_AGENT_BIT_MASK)) {
            this.userAgent = (String) in.readObject();
        }
        if (isFieldPresent(bitMask, STATUS_BIT_MASK)) {
            this.status = (OnlineStatus) in.readObject();
        }
        if (isFieldPresent(bitMask, USERname_BIT_MASK)) {
            this.username = (String) in.readObject();
        }
    }

    /**
     * Returns a bit mask used during serialization indicating which fields have been serialized. Fields that have been
     * altered (not null and/or not retaining the class defaults) will be serialized and have 1 in their respective
     * index, fields that are null and/or retain class default values have 0.
     *
     * @return a bit mask used during serialization indicating which fields have been serialized.
     * @since 1.0
     */
    private short getAlteredFieldsBitMask() {
        int bitMask = 0;
        bitMask = userid != null ? bitMask | USEr_id_BIT_MASK : bitMask;
        bitMask = userAgent != null ? bitMask | USER_AGENT_BIT_MASK : bitMask;
        bitMask = status != null ? bitMask | STATUS_BIT_MASK : bitMask;
        bitMask = username != null ? bitMask | USERname_BIT_MASK : bitMask;
        return (short) bitMask;
    }

    /**
     * Returns {@code true} if the given {@code bitMask} argument indicates that the specified field has been
     * serialized and therefore should be read during deserialization, {@code false} otherwise.
     *
     * @param bitMask      the aggregate bitmask for all fields that have been serialized.  Individual bits represent
     *                     the fields that have been serialized.  A bit set to 1 means that corresponding field has
     *                     been serialized, 0 means it hasn't been serialized.
     * @param fieldBitMask the field bit mask constant identifying which bit to inspect (corresponds to a class attribute).
     * @return {@code true} if the given {@code bitMask} argument indicates that the specified field has been
     *         serialized and therefore should be read during deserialization, {@code false} otherwise.
     * @since 1.0
     */
    private static boolean isFieldPresent(short bitMask, int fieldBitMask) {
        return (bitMask & fieldBitMask) != 0;
    }
	
}
