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
import me.prettyprint.hector.api.Keyspace
import me.prettyprint.hector.api.beans.Row
import groovy.transform.CompileStatic
import me.prettyprint.cassandra.model.CqlRows
import groovy.transform.TypeChecked

/**
 * Access to the Cassandra User CF.
 */
@Named("userRepository")
@ApplicationScoped
@Startup
@CompileStatic
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

        def rows = cqlQuery.setQuery("SELECT * FROM User").execute().get()

        def users = []

        for (Row row in rows) {
            def user = new User()
            user.login      = row.getKey()
            user.username   = row.getColumnSlice().getColumnByName("username").value
            user.domain     = row.getColumnSlice().getColumnByName("domain").value
            user.firstName  = row.getColumnSlice().getColumnByName("firstName").value
            user.lastName   = row.getColumnSlice().getColumnByName("lastName").value
            users << user
        }

        return users
    }
}
