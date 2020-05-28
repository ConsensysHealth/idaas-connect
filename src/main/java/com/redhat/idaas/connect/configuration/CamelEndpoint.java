package com.redhat.idaas.connect.configuration;

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Objects;

final class CamelEndpoint {

    private String scheme;

    private String uri;

    private final Map<String, String> options = new HashMap<>();

    public String getScheme() {
        return scheme == null ? "" : scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getUri() {
        return uri == null ? "" : uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void addOption(String optionName, String optionValue) {
        options.put(optionName, optionValue);
    }

    /**
     * Determines if this instance is equal to another object.
     * @param obj The object to compare
     * @return true if the objects are equal, otherwise return false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        CamelEndpoint otherEndpoint = (CamelEndpoint) obj;

        return getScheme().equals(otherEndpoint.getScheme()) &&
                getUri().equals(otherEndpoint.getUri());
    }

    /**
     * @return the hash code for this instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(getScheme(), getUri());
    }


    /**
     * @return the string representation for this endpoint
     */
    @Override
    public String toString() {
        StringBuilder endpoint = new StringBuilder();
        endpoint.append(getScheme());
        endpoint.append("://");
        endpoint.append(getUri());

        if (options.size() > 0) {
            endpoint.append("?");

            for (Entry<String, String> optionEntry : options.entrySet()) {
                endpoint.append(optionEntry.getKey());
                endpoint.append("=");
                endpoint.append(optionEntry.getValue());
                endpoint.append("&");
            }
            endpoint.setLength(endpoint.length() - 1);
        }
        return endpoint.toString();
    }
}
