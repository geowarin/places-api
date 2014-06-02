package com.geowarin.places

import groovy.transform.CompileStatic
import groovyx.net.http.RESTClient

/**
 *
 * Date: 02/06/2014
 * Time: 22:46
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
@CompileStatic
class Places {
    public static final RADIUS_IN_METERS = 500
    private RESTClient client
    private String apiKey

    Places(baseUrl = 'https://maps.googleapis.com/') {
        this.client = new RESTClient(baseUrl)
        this.apiKey = getClass().getResourceAsStream('/google_api_key.txt').text
    }

    Object query(double[] location) {
        client.get( path: '/maps/api/place/nearbysearch/json',
                    query: [
                            location: "${location[0]},${location[1]}",
                            radius: RADIUS_IN_METERS,
                            types: 'restaurant',
                            sensor: true,
                            key: apiKey,
                            opennow: true
                    ]
        )
    }
}
