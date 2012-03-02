package com.smartgrid.messenger;

import java.util.Map;

public interface Messenger<T> {
    public <MessageType, ResponseType> ResponseType message(Integer id, Message<MessageType> m);
    public <MessageType, ResponseType> Map<Integer, ResponseType> messageMany(Integer[] id, Message<MessageType> m);
}