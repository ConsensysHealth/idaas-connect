package com.redhat.idaas.connect.configuration;

import java.util.Objects;

/**
 * Models a Camel Java DSL endpoint.
 * A Java DSL endpoint may be a consumer or producer within a Camel Route.
 * The Java DSL endpoint format is a URI structured as [scheme]://[contextPath]?[options]
 * Options are usually separated by "&"
 */
public final class CamelEndpoint {

    private String scheme;

    private String contextPath;

    private String options;

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getOptions() { return options; }

    public void setOptions(String options) { this.options = options; }

    /**
     * Determines if this CamelEndpoint instance is equal to another object.
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
                getContextPath().equals(otherEndpoint.getContextPath());
    }

    /**
     * @return the hash code for this instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(getScheme(), getContextPath());
    }

    /**
     * @return the URI string representation for this endpoint
     */
    @Override
    public String toString() {
        StringBuilder endpointUri = new StringBuilder();
        String scheme = getScheme() == null ? "" : getScheme();
        String context = getContextPath() == null ? "" : getContextPath();

        endpointUri.append(scheme)
            .append(context);

        if (options != null && options.length()  > 1) {
            endpointUri.append("?");
            endpointUri.append(options);
        }
        return endpointUri.toString();
    }
}
