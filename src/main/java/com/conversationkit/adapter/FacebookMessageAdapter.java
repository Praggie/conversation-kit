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
package com.conversationkit.adapter;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.conversationkit.model.IConversationSnippet;
import com.conversationkit.model.IConversationSnippetButton;
import com.conversationkit.model.IConversationState;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author pdtyreus
 */
public class FacebookMessageAdapter implements IMessageAdapter {

    private static final Logger logger = Logger.getLogger(FacebookMessageAdapter.class.getName());

    /**
     * Generates the JSON string to indicate that the bot is typing.
     *
     * @param userId Facebook user ID or phone number
     * @return String representation of JSON payload
     */
    public String typingOnToJson(String userId) {

        JsonObject json = Json.object()
                .add("recipient", Json.object()
                        .add("id", userId)).asObject();

        json.add("sender_action", "typing_on");

        logger.fine(json.toString());

        return json.toString();
    }

    @Override
    public String snippetToJson(IConversationSnippet snippet, IConversationState state, Object... arguments) {
        if (arguments.length == 0) {
            throw new IllegalArgumentException("The user's phone number or facebookId should be sent as the first argument.");
        }

        JsonObject json = Json.object()
                .add("recipient", Json.object()
                        .add("id", (String) arguments[0])).asObject();

        JsonValue quickReplies = null;
        if (snippet.getSuggestedResponses(state) != null) {
            List<String> quickReplyList = new ArrayList();
            for (Object suggestedResponse : snippet.getSuggestedResponses(state)) {
                quickReplyList.add((String) suggestedResponse);
            }
            if (!quickReplyList.isEmpty()) {
                quickReplies = Json.array();
                for (String suggestedResponse : quickReplyList) {
                    quickReplies.asArray().add(Json.object()
                            .add("content_type", "text")
                            .add("title", suggestedResponse)
                            .add("payload", suggestedResponse));
                }
            }
        }

        String type = null;
        JsonObject message = Json.object();
        switch (snippet.getContentType()) {
            case TEXT:
                message.add("text", snippet.renderContent(state));
                if (quickReplies != null) {
                    message.add("quick_replies", quickReplies);
                }
                json.add("message", message);
                return json.toString();
            case LOCATION_REQUEST:
                message.add("text", snippet.renderContent(state));
                JsonValue quickRepliesWithLocation = Json.array();

                quickRepliesWithLocation.asArray().add(Json.object()
                        .add("content_type", "location"));

                if (quickReplies != null) {
                    for (JsonValue value : quickReplies.asArray()) {
                        quickRepliesWithLocation.asArray().add(value);
                    }
                }

                message.add("quick_replies", quickRepliesWithLocation);
                json.add("message", message);
                return json.toString();
            case BUTTONS:

                JsonValue buttons = Json.array();
                if (snippet.getButtons() != null) {
                    for (Object obj : snippet.getButtons()) {
                        IConversationSnippetButton iButton = (IConversationSnippetButton) obj;
                        buttons.asArray().add(createButton(iButton));
                    }
                }
                JsonObject payload = Json.object();
                payload.add("buttons", buttons);
                payload.add("template_type", "button");
                payload.add("text", snippet.renderContent(state));
                JsonObject attachment = Json.object();
                attachment.add("payload", payload);
                attachment.add("type", "template");
                message.add("attachment", attachment);
                json.add("message", message);
                logger.info(json.toString());
                return json.toString();
            case AUDIO:
                type = "audio";
                message.add("attachment", Json.object().add("type", type).add("payload", Json.object().add("url", snippet.renderContent(state))));
                break;
            case VIDEO:
                type = "video";
                message.add("attachment", Json.object().add("type", type).add("payload", Json.object().add("url", snippet.renderContent(state))));
                break;
            case FILE:
                type = "file";
                message.add("attachment", Json.object().add("type", type).add("payload", Json.object().add("url", snippet.renderContent(state))));
                break;
            case IMAGE:
                type = "image";
                message.add("attachment", Json.object().add("type", type).add("payload", Json.object().add("url", snippet.renderContent(state))));
                break;

        }

        if (quickReplies != null) {
            message.add("quick_replies", quickReplies);
        }
        json.add("message", message);

        logger.fine(json.toString());

        return json.toString();

    }

    private enum ButtonType {

        web_url, postback, phone_number, element_share
    }

    private JsonObject createButton(IConversationSnippetButton iButton) {

        ButtonType type = ButtonType.valueOf(iButton.getType());

        JsonObject button = Json.object();

        switch (type) {
            case web_url:
                button.add("type", iButton.getType());
                button.add("url", iButton.getValue());
                button.add("title", iButton.getText());
                break;
            case postback:
            case phone_number:
                button.add("type", iButton.getType());
                button.add("payload", iButton.getValue());
                button.add("title", iButton.getText());
                break;
            case element_share:
                button.add("type", iButton.getType());
                break;
        }

        for (Map.Entry<String, Object> entry : iButton.getAttributes().entrySet()) {
            if (entry.getValue() instanceof String) {
                button.add(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof Boolean) {
                button.add(entry.getKey(), (Boolean) entry.getValue());
            }
        }

        return button;
    }
}
