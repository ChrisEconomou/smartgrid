package com.smartgrid.messenger;

import java.util.Map;

public interface Messenger<T> {
	public Integer[] memberIds();
    public <ResponseType,MessageType> ResponseType message(Integer id, Message<MessageType> m);
    public <ResponseType,MessageType> Map<Integer, ResponseType> messageMany(Integer[] id, Message<MessageType> m);
}