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
package org.sourcelab.activecampaign.apiv3;

import org.sourcelab.http.rest.configuration.BasicConfiguration;

/**
 * ActiveCampaign API Configuration class.
 */
public class ApiConfig extends BasicConfiguration<ApiConfig> {

    private static String API_HOST_TEMPLATE = "https://%s.api-us1.com/";

    /**
     * Constructor.
     * @param accountName ActiveCampaign account name.
     * @param apiToken ActiveCampaign API token.
     */
    public ApiConfig(final String accountName, final String apiToken) {
        super(
            String.format(API_HOST_TEMPLATE, accountName)
        );
        withApiToken(apiToken);
    }

    /**
     * Configure ActiveCampaign API Token.
     * @param apiToken api token.
     * @return Configuration instance.
     */
    private ApiConfig withApiToken(final String apiToken) {
        withRequestHeader("Api-Token", apiToken);
        return this;
    }
}
