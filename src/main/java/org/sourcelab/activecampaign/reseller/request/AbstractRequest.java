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
package org.sourcelab.activecampaign.reseller.request;

import org.sourcelab.http.rest.request.Request;
import org.sourcelab.http.rest.request.RequestMethod;
import org.sourcelab.http.rest.request.body.RequestBodyContent;
import org.sourcelab.http.rest.request.body.UrlEncodedFormBodyContent;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Reseller Account Request.
 *
 * @param <Self> reference to parent class.
 * @param <T> return type from parsed response.
 */
public abstract class AbstractRequest<Self, T> implements Request<T> {
    // Param holder
    private final Map<String, String> params = new HashMap<>();

    protected AbstractRequest(final String apiAction) {
        setParam("api_action", apiAction);
    }

    @Override
    public String getApiEndpoint() {
        return "";
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public RequestBodyContent getRequestBody() {
        // return copy of our parameters
        final UrlEncodedFormBodyContent requestBody = new UrlEncodedFormBodyContent();

        params.forEach(requestBody::addParameter);

        return requestBody;
    }

    @SuppressWarnings("unchecked")
    protected Self setParam(final String name, String value) {
        if (value == null) {
            params.remove(name);
        } else {
            params.put(name, value);
        }
        return (Self) this;
    }
}
