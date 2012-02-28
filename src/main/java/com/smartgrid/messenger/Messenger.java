package com.smartgrid.messenger;

import java.util.Map;

public interface Messenger {
    public Message message(int id, Message m);
    public Map<Integer, Message> messageMany(int[] id, Message m);
}