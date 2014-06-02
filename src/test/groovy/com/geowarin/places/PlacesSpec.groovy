package com.geowarin.places

import com.github.tomakehurst.wiremock.junit.WireMockRule
import groovyx.net.http.RESTClient
import org.junit.Rule
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*


/**
 *
 * Date: 02/06/2014
 * Time: 22:45
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
class PlacesSpec extends Specification {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8090)
    static final double[] AV_GDE_ARMEE_PARIS = [48.8754201, 2.2888042]

    def "should_return_stubbed"() {
        given:
        stubRequest('/maps/api/place/nearbysearch/json?.*', '/sydney-places.json')
        Places places = new Places('http://localhost:8090')

        when:
        def response = places.query(AV_GDE_ARMEE_PARIS)

        then:
        response.data.results*.name == ['Pancakes on the Rocks', 'Kazbah', 'Criniti\'s Darling Harbour Italian Restaurant', 'Rhythmboat Cruises', 'Sydney Harbour Dinner Cruises', 'Nisbets Darling Harbour']
    }

    def "should_query_real_API"() {
        given:
        Places places = new Places()

        when:
        def response = places.query(AV_GDE_ARMEE_PARIS)

        then:
        response.data.results*.name == ['Le Méridien Etoile', 'Le Relais de Venise', 'Hotel Splendid Etoile', 'Raphael', 'Palais Maillot', 'L\'Arc', 'Le Congrès Maillot', 'Hotel Mac Mahon', 'Le Rech', 'Chez Georges', 'Le Sud', 'Guy Savoy, Restaurant', 'Chez Clement', 'Bellini', 'Café Latéral', 'La Casa Di Sergio', 'Chez Bébert - Palais des Congrès', 'Sormani', 'Le Hide', 'Pau Brasil']
    }

    private stubRequest(String url, String path) {
        stubFor(get(urlMatching(url))
                .willReturn(aResponse()
                .withHeader('Content-Type', 'application/json')
                .withBody(this.getClass().getResource(path).text)))
    }
}