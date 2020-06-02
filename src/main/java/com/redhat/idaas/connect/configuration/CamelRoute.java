package com.redhat.idaas.connect.configuration;

import java.util.Objects;

/**
 * Models a Camel Java DSL Route.
 * A Camel Java DSL Route has a consumer and one or more producers.
 * The route also stores the name of the blue print used to construct it.
 */
public final class CamelRoute {

    private String routeId;

    private String routeBluePrint;

    private CamelEndpoint consumer = new CamelEndpoint();

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteBluePrint(String routeBluePrint) { this.routeBluePrint = routeBluePrint; }

    public String getRouteBluePrint() { return routeBluePrint; }

    public CamelEndpoint getConsumer() {
        return consumer;
    }

    public void setConsumer(CamelEndpoint consumer) {
        this.consumer = consumer;
    }

    /**
     * Determines if this CamelRoute instance is equal to another object.
     *
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

        CamelRoute otherRoute = (CamelRoute) obj;
        return getRouteId().equals(otherRoute.getRouteId());
    }

    /**
     * @return the hash code for this instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(getRouteId());
    }
}
