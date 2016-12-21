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
package com.synclab.conversationkit.impl.node;

import com.synclab.conversationkit.model.IConversationEdge;
import com.synclab.conversationkit.model.IConversationNode;
import com.synclab.conversationkit.model.IConversationState;
import com.synclab.conversationkit.model.SnippetType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pdtyreus
 */
public abstract class ConversationNode<T extends IConversationState> implements IConversationNode<T> {

    private final List<IConversationEdge<T>> edges;
    private final SnippetType type;
    private final int id;

    public ConversationNode(int id, SnippetType type) {
        this.id = id;
        this.type = type;
        this.edges = new ArrayList();
    }

    public List<IConversationEdge<T>> getEdges() {
        return edges;
    }

    public void addEdge(IConversationEdge<T> node) {
        edges.add(node);
    }

    public int getId() {
        return id;
    }

    public SnippetType getType() {
        return type;
    }
}