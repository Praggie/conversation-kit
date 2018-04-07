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
package com.conversationkit.model;

/**
 * The conversation state is a data store designed to persist a user's progress
 * through a conversation, help customize the messages sent by the bot to the 
 * user, and to save data from user responses during the conversation. In many
 * cases the implementation will be backed by a database or other permanent
 * storage.
 * @author pdtyreus
 */
public interface IConversationState{
    /**
     * Returns the id of the node representing the last message sent to the 
     * user.
     * @return the node id
     */
    public int getCurrentNodeId();

//    /**
//     * Returns the last unprocessed response from the user to the bot.
//     * @return the user's response
//     */
//    public R getMostRecentResponse();
//    /**
//     * Stores a response from the user to the bot for subsequent processing.
//     * @param currentResponse the response entered by the user
//     */
//    public void setMostRecentResponse(R currentResponse);

    /**
     * Return the value stored under the specified key or null if nothing is
     * present.
     * @param propertyName key
     * @return stored value or null
     */
    public Object get(String propertyName);
}
