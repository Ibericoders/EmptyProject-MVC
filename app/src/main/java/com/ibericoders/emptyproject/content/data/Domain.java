package com.ibericoders.emptyproject.content.data;

/**
 * Desc:
 * Author: Jorge Roldan
 * Version: 1.0
 */

public class Domain {

    /*
     * Singleton.
     */
    private static Domain Singleton;

    public Domain() { }


    public static Domain getDomain() {

        if(Domain.Singleton == null) {

            Domain.Singleton = new Domain();
        }

        return Domain.Singleton;
    }
}
