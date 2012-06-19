package fr.ippon.blog.primegroovy.domain

import groovy.transform.CompileStatic

@CompileStatic
class User implements Serializable {
    String login
    String username
    String domain
    String firstName
    String lastName
}
