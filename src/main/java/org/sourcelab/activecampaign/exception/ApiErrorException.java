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
package org.sourcelab.activecampaign.exception;

import org.sourcelab.activecampaign.apiv3.response.error.RequestErrorResponse;
import org.sourcelab.http.rest.exceptions.InvalidRequestException;

/**
 * Thrown when the API returns an error.
 */
public class ApiErrorException extends InvalidRequestException {
    private final RequestErrorResponse errorResponse;

    public ApiErrorException(final String message, final int errorCode) {
        super("", errorCode);
        throw new RuntimeException("Not implemented");
    }

    public ApiErrorException(final RequestErrorResponse requestErrorResponse) {
        super(requestErrorResponse.toString(), 422);
        this.errorResponse = requestErrorResponse;
    }

    public RequestErrorResponse getErrorResponse() {
        return errorResponse;
    }

    @Override
    public String toString() {
        return "ApiErrorException{"
            + "errorResponse=" + errorResponse
            + '}';
    }
}
