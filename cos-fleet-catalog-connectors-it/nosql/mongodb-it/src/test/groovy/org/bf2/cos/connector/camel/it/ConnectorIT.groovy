package org.bf2.cos.connector.camel.it

import com.mongodb.client.MongoClients
import groovy.util.logging.Slf4j
import org.bf2.cos.connector.camel.it.support.KafkaConnectorSpec
import org.testcontainers.containers.MongoDBContainer

import java.util.concurrent.TimeUnit

import static com.mongodb.client.model.Filters.and
import static com.mongodb.client.model.Filters.eq

@Slf4j
class ConnectorIT extends KafkaConnectorSpec {
    static MongoDBContainer db

    @Override
    def setupSpec() {
        db = new MongoDBContainer('mongo:5.0.8')
        db.withLogConsumer(logger('tc-mongodb'))
        db.withNetwork(network)
        db.withNetworkAliases('tc-mongodb')
        db.start()
    }

    @Override
    def cleanupSpec() {
        closeQuietly(db)
    }

    def "mongodb sink"() {
        setup:
            def mongoClient = MongoClients.create(db.replicaSetUrl)
            def database = mongoClient.getDatabase("toys")
            def collection = database.getCollection("cards")

            def topic = topic()
            def group = UUID.randomUUID().toString()
            def payload = '''{ "value": "4", "suit": "hearts" }'''

            def cnt = connectorContainer('mongodb_sink_0.1.json', [
                'kafka_topic' : topic,
                'kafka_bootstrap_servers': kafka.outsideBootstrapServers,
                'kafka_consumer_group': UUID.randomUUID().toString(),
                'mongodb_hosts': 'tc-mongodb:27017',
                'mongodb_collection': collection.getNamespace().getCollectionName(),
                'mongodb_database': database.getName(),
                'mongodb_create_collection': 'true'
            ])

            cnt.start()
        when:
            kafka.send(topic, payload)
        then:
            def records = kafka.poll(group, topic)
            records.size() == 1
            records.first().value() == payload

            await(10, TimeUnit.SECONDS) {
                return collection.countDocuments(and(eq('value', '4'), eq('suit', 'hearts'))) == 1

            }

        cleanup:
            closeQuietly(mongoClient)
            closeQuietly(cnt)
    }
}
