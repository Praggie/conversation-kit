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
package com.conversationkit.impl.edge;

import com.conversationkit.impl.edge.JavaScriptEdge;
import com.conversationkit.impl.MapBackedState;
import com.conversationkit.model.IConversationState;
import java.util.Optional;
import java.util.logging.Logger;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 *
 * @author pdtyreus
 */
public class JavaScriptEdgeTest extends TestCase {

    private static final Logger logger = Logger.getLogger(JavaScriptEdgeTest.class.getName());

    public JavaScriptEdgeTest(String testName) {
        super(testName);
    }

    public void testIsMatchForState() {
        IConversationState state = new MapBackedState();
        JavaScriptEdge instance = new JavaScriptEdge("return true;", null);
        Optional<String> response = Optional.of("word");
        assertEquals(true, instance.isMatchForState(response,state));

        instance = new JavaScriptEdge("return (response == 'word');", null);

        assertEquals(true, instance.isMatchForState(response,state));

        response = Optional.of("number");

        assertEquals(false, instance.isMatchForState(response,state));

        instance = new JavaScriptEdge("return invalidFunc();", null);

        assertEquals(false, instance.isMatchForState(response, state));
    }

}
