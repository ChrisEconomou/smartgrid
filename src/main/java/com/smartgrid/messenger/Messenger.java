package com.smartgrid.messenger;

import java.util.Map;

public interface Messenger {
    public <MessageType, ResponseType> ResponseType message(int id, Message<MessageType> m);
    public <MessageType, ResponseType> Map<Integer, ResponseType> messageMany(Integer[] id, Message<MessageType> m);
}