package fr.ippon.blog.primegroovy.web

import fr.ippon.blog.primegroovy.domain.User
import fr.ippon.blog.primegroovy.repository.UserRepository
import javax.enterprise.context.SessionScoped
import javax.inject.Inject
import javax.inject.Named
import javax.annotation.PostConstruct
import groovy.transform.CompileStatic

@Named("userController")
@SessionScoped
@CompileStatic
class UserController implements Serializable {

    @Inject
    UserRepository userRepository

    @PostConstruct
    void init() {
        users = userRepository.users
    }

    Collection<User> users
}
