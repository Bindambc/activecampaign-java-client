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
package org.sourcelab.activecampaign.apiv3.response.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Account object.
 */
@JsonRootName(value = "account")
public class Account {
    private final Long id;
    private final String name;
    private final String accountUrl;

    // Related
    private final long contactCount;
    private final long dealCount;

    // Related links
    private final Map<String, String> links;

    // Timestamps
    private final ZonedDateTime createdTimestamp;
    private final ZonedDateTime updatedTimestamp;

    /**
     * Constructor, tho use of Builder is recommended.
     */
    @JsonCreator
    public Account(
        @JsonProperty("id") final Long id,
        @JsonProperty("name") final String name,
        @JsonProperty("accountUrl") final String accountUrl,
        @JsonProperty("contactCount") final long contactCount,
        @JsonProperty("dealCount") final long dealCount,
        @JsonProperty("createdTimestamp") final ZonedDateTime createdTimestamp,
        @JsonProperty("updatedTimestamp") final ZonedDateTime updatedTimestamp,
        @JsonProperty("links") final Map<String, String> links
    ) {
        this.id = id;
        this.name = name;
        this.accountUrl = accountUrl;
        this.contactCount = contactCount;
        this.dealCount = dealCount;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
        if (links == null) {
            this.links = Collections.emptyMap();
        } else {
            this.links = Collections.unmodifiableMap(new HashMap<>(links));
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccountUrl() {
        return accountUrl;
    }

    public long getContactCount() {
        return contactCount;
    }

    public long getDealCount() {
        return dealCount;
    }

    public ZonedDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public ZonedDateTime getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    /**
     * Convert to a builder instance.
     * @return Builder instance populated with values from the Account object.
     */
    public Account.Builder toBuilder() {
        return new Builder()
            .withId(getId())
            .withName(getName())
            .withAccountUrl(getAccountUrl());

    }

    /**
     * New Account Builder instance.
     * @return account builder instance.
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "Account{"
            + "id=" + id
            + ", name='" + name + '\''
            + ", accountUrl='" + accountUrl + '\''
            + ", contactCount=" + contactCount
            + ", dealCount=" + dealCount
            + ", links=" + links
            + ", createdTimestamp=" + createdTimestamp
            + ", updatedTimestamp=" + updatedTimestamp
            + '}';
    }

    /**
     * Builder for Account.
     */
    public static final class Builder {
        private Long id = null;
        private String name;
        private String accountUrl;

        // Related
        private long contactCount = 0L;
        private long dealCount = 0L;

        // Timestamps
        private ZonedDateTime createdTimestamp = null;
        private ZonedDateTime updatedTimestamp = null;

        private Builder() {
        }

        public Builder withId(final Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAccountUrl(String accountUrl) {
            this.accountUrl = accountUrl;
            return this;
        }

        public Account build() {
            return new Account(id, name, accountUrl, contactCount, dealCount, createdTimestamp, updatedTimestamp, new HashMap<>());
        }
    }
}
