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
package org.sourcelab.activecampaign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sourcelab.activecampaign.apiv3.response.error.RequestErrorResponse;
import org.sourcelab.activecampaign.exception.ApiErrorException;
import org.sourcelab.activecampaign.exception.InvalidCredentialsException;
import org.sourcelab.http.rest.HttpClientRestClient;
import org.sourcelab.http.rest.RestClient;
import org.sourcelab.http.rest.RestResponse;
import org.sourcelab.http.rest.configuration.BasicConfiguration;
import org.sourcelab.http.rest.exceptions.InvalidRequestException;
import org.sourcelab.http.rest.exceptions.ResourceNotFoundException;
import org.sourcelab.http.rest.exceptions.UnauthorizedRequestException;
import org.sourcelab.http.rest.request.Request;

import java.io.IOException;

/**
 * Abstract client class.
 */
public abstract class AbstractClient implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(AbstractClient.class);

    /**
     * Our API Configuration.
     */
    private final BasicConfiguration apiConfig;

    /**
     * Underlying RestClient to use.
     */
    private final RestClient restClient;

    /**
     * Internal State flag.
     */
    private boolean isInitialized = false;

    /**
     * Constructor.
     * @param apiConfig API Configuration properties.
     */
    public AbstractClient(final BasicConfiguration apiConfig) {
        this.apiConfig = apiConfig;
        this.restClient = new HttpClientRestClient();
    }

    protected <T> T submitRequest(final Request<T> request) {
        // Submit request
        final RestResponse restResponse = getRestClient().submitRequest(request);
        final int responseCode = restResponse.getHttpCode();
        String responseStr = restResponse.getResponseStr();

        // If we have a valid response
        logger.debug("Response: {}", restResponse);

        // Check for invalid credential responses.
        validateResponseForInvalidCredentials(restResponse);

        // Check for invalid http status codes
        if (responseCode >= 200 && responseCode < 300) {
            // These response codes have no values
            if ((responseCode == 204 || responseCode == 205) && responseStr == null) {
                // Avoid NPE
                responseStr = "";
            }

            // Then parse real response.
            try {
                return request.parseResponse(responseStr);
            } catch (final IOException exception) {
                throw new RuntimeException(exception.getMessage(), exception);
            }
        }

        // Server reject's client's authentication.
        if (responseCode == 403) {
            // Invalid credentials
            throw new InvalidCredentialsException("The ActiveCampaign API token and/or account name is not valid.", 403);
        } else if (responseCode == 404) {
            // Invalid end point requested.
            throw new ResourceNotFoundException("The requested resource does not exist. (" + request.getApiEndpoint() + ")");
        } else if (responseCode == 401) {
            // Typically thrown if proxy requires authentication
            // Throw contextual error msg based on if credentials are configured or not.
            String errorMsg;
            if (apiConfig.getBasicAuthUsername() == null) {
                errorMsg = "Server required authentication credentials but none were provided in client configuration.";
            } else {
                errorMsg = "Client authentication credentials (username=" + apiConfig.getBasicAuthUsername() + ") was rejected by server.";
            }
            errorMsg = errorMsg + " Server responded with: \"" + responseStr + "\"";
            throw new UnauthorizedRequestException(errorMsg, responseCode);
        } else if (responseCode == 422) {
            // Attempt to parse error response
            try {
                final RequestErrorResponse errorResponse = JacksonFactory.newInstance().readValue(responseStr, RequestErrorResponse.class);
                throw new ApiErrorException(errorResponse);
            } catch (final IOException exception) {
                // swallow
            }
        }

        throw new InvalidRequestException("Invalid response from server: " + responseStr, restResponse.getHttpCode());
    }

    private RestClient getRestClient() {
        // If we haven't initialized.
        if (!isInitialized) {
            // Call Init.
            restClient.init(apiConfig);

            // Flip state flag
            isInitialized = true;
        }

        // return our rest client.
        return restClient;
    }

    @Override
    public void close() {
        if (isInitialized) {
            restClient.close();
        }
    }

    /**
     * Custom validations to check for invalid credentials.
     * @param restResponse the resulting API response.
     * @throws InvalidCredentialsException if the credentials are invalid.
     */
    protected abstract void validateResponseForInvalidCredentials(final RestResponse restResponse) throws InvalidCredentialsException;
}
