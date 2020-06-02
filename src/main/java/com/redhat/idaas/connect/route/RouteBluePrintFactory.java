package com.redhat.idaas.connect.route;

/**
 * Creates {@link RouteBluePrint} instances
 */
public class RouteBluePrintFactory {

    /**
     * Creates a {@link RouteBluePrint} instance from a blue print id
     *
     * @param bluePrint The blue print id
     * @return {@link RouteBluePrint}
     */
    public static RouteBluePrint getInstance(String bluePrint) {
        RouteBluePrint routeBluePrint = null;

        if (bluePrint == null) {
            return null;
        }

        switch (bluePrint.toLowerCase()) {
            case "stub":
                return new StubBluePrint();
            case "hl7":
                return new Hl7BluePrint();
            case "fhir":
                return new FhirRestBluePrint();
            default:
                return routeBluePrint;
        }
    }
}
