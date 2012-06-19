package fr.ippon.blog.primegroovy.repository

import fr.ippon.blog.primegroovy.domain.User
import javax.annotation.PostConstruct
import javax.ejb.Startup
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named
import me.prettyprint.cassandra.model.CqlQuery
import me.prettyprint.cassandra.serializers.StringSerializer
import me.prettyprint.cassandra.service.CassandraHostConfigurator
import me.prettyprint.cassandra.service.ThriftCluster
import me.prettyprint.hector.api.factory.HFactory
import me.prettyprint.cassandra.model.CqlRows
import me.prettyprint.hector.api.Keyspace

/**
 * Access to the Cassandra User CF.
 */
@Named("userRepository")
@ApplicationScoped
@Startup
class UserRepository implements Serializable {

    Keyspace keyspace

    @PostConstruct
    void init() {
        keyspace = HFactory.createKeyspace("tatami",
                new ThriftCluster("Tatami cluster",
                        new CassandraHostConfigurator("localhost")))
    }

    Collection<User> getUsers() {
        def cqlQuery =
        new CqlQuery(keyspace, StringSerializer.get(), StringSerializer.get(), StringSerializer.get())

        cqlQuery.setQuery("SELECT * FROM User")
        CqlRows<String, String, String> rows = cqlQuery.execute().get()
        def users = new java.util.ArrayList<User>()
        rows.each { row ->
            users.add(new User(login: row.getKey(),
                    username: row.getColumnSlice().getColumnByName("username").getValue(),
                    domain: row.getColumnSlice().getColumnByName("domain").getValue(),
                    firstName: row.getColumnSlice().getColumnByName("firstName").getValue(),
                    lastName: row.getColumnSlice().getColumnByName("lastName").getValue()))
        }
        return users
    }
}
