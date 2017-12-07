/*
 * The MIT License
 *
 * Copyright 2016 Synclab Consulting LLC.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.conversationkit.impl;

import com.conversationkit.model.IConversationState;
import java.util.HashMap;

/**
 *
 * @author pdtyreus
 */
public class MapBackedState<R> extends HashMap<String,Object> implements IConversationState<R> {

    @Override
    public int getCurrentNodeId() {
        Integer id = (Integer)this.get("currentNodeId");
        return id;
    }

    @Override
    public void setCurrentNodeId(int currentNodeId) {
        this.put("currentNodeId", currentNodeId);
    }

    @Override
    public void set(String propertyName, Object value) {
        this.put(propertyName, value);
    }

    @Override
    public R getMostRecentResponse() {
        return (R)this.get("mostRecentResponse");
    }

    @Override
    public void setMostRecentResponse(R currentResponse) {
        if (currentResponse == null) {
            this.remove("mostRecentResponse");
        } else {
            this.put("mostRecentResponse", currentResponse);
        }
    }

    @Override
    public Object get(String propertyName) {
        return super.get(propertyName);
    }
    
}
