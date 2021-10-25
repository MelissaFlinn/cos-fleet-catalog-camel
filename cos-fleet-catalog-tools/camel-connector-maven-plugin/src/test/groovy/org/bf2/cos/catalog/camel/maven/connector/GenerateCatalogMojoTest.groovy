package org.bf2.cos.catalog.camel.maven.connector

import org.bf2.cos.catalog.camel.maven.connector.support.CatalogConstants
import org.bf2.cos.catalog.camel.maven.connector.support.CatalogSupport
import org.bf2.cos.catalog.camel.maven.connector.support.Connector

class GenerateCatalogMojoTest extends Spec {

    def 'structured source connector with no custom config'() {
        given:
            def adapter = adapter(CatalogConstants.SOURCE, 'application/json')
            def shape = new Connector.DataShapeDefinition()

        when:
            CatalogSupport.computeDataShapes(shape, adapter)

        then:
            shape.consumes != null
            shape.consumes.defaultFormat == 'application/json'

            shape.produces != null
            shape.produces.defaultFormat == 'application/json'
            shape.produces.formats.containsAll(['application/json','avro/binary'])
    }

    def 'structured source connector with custom config'() {
        given:
            def adapter = adapter(CatalogConstants.SOURCE, 'application/json')
            def shape = new Connector.DataShapeDefinition()
            shape.consumes = Connector.dataShape(null, [ 'avro/binary' ])

        when:
            CatalogSupport.computeDataShapes(shape, adapter)

        then:
            shape.consumes != null
            shape.consumes.defaultFormat == 'avro/binary'

            shape.produces != null
            shape.produces.defaultFormat == 'avro/binary'
            shape.produces.formats.containsAll(['application/json', 'avro/binary'])
    }

    def 'structured sink connector with no custom config'() {
        given:
            def adapter = adapter(CatalogConstants.SINK, 'application/json')
            def shape = new Connector.DataShapeDefinition()

        when:
            CatalogSupport.computeDataShapes(shape, adapter)

        then:
            shape.consumes != null
            shape.consumes.defaultFormat == 'application/json'
            shape.consumes.formats.containsAll(['application/json', 'avro/binary'])

            shape.produces != null
            shape.produces.defaultFormat == 'application/json'
    }

    def 'structured sink connector with custom config'() {
        given:
            def adapter = adapter(CatalogConstants.SINK, 'application/json')
            def shape = new Connector.DataShapeDefinition()
            shape.produces = Connector.dataShape(null, [ 'avro/binary' ])

        when:
            CatalogSupport.computeDataShapes(shape, adapter)

        then:

            shape.consumes != null
            shape.consumes.defaultFormat == 'avro/binary'
            shape.consumes.formats.containsAll(['application/json', 'avro/binary'])

            shape.produces != null
            shape.produces.defaultFormat == 'avro/binary'
    }
}
