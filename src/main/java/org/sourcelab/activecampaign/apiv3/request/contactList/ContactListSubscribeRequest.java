/**
 * Copyright 2019, 2020, 2021 SourceLab.org https://github.com/SourceLabOrg/activecampaign-java-client
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.sourcelab.activecampaign.apiv3.request.contactList;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.sourcelab.activecampaign.JacksonFactory;
import org.sourcelab.http.rest.request.Request;
import org.sourcelab.http.rest.request.RequestMethod;
import org.sourcelab.http.rest.request.body.RequestBodyContent;
import org.sourcelab.http.rest.request.body.StringBodyContent;

import java.io.IOException;
import java.util.Objects;

/**
 * Represents an account create request.
 */
public class ContactListSubscribeRequest implements Request<String> {
    private final ContactList contactList;

    /**
     * Constructor.
     */
    public ContactListSubscribeRequest(final long contactId, final long listId, boolean subscribe) {
        this.contactList = new ContactList(contactId, listId, subscribe);
    }

    public ContactListSubscribeRequest(final ContactList contactList) {
        this.contactList = Objects.requireNonNull(contactList);
    }

    @Override
    public String getApiEndpoint() {
        return "api/3/contactLists";
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Override
    public RequestBodyContent getRequestBody() {
        try {
            return new StringBodyContent(
                JacksonFactory.newInstance().writeValueAsString(contactList)
            );
        } catch (final JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String parseResponse(final String response) throws IOException {
        return JacksonFactory.newInstance().readValue(response, String.class);
    }
}
