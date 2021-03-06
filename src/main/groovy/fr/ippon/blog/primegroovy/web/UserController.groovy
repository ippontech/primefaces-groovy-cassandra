package fr.ippon.blog.primegroovy.web

import fr.ippon.blog.primegroovy.domain.User
import fr.ippon.blog.primegroovy.repository.UserRepository

import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.inject.Named

@Named("userController")
@RequestScoped
class UserController implements Serializable {

    @Inject
    UserRepository userRepository

    Collection<User> getUsers() {
        return userRepository.users
    }
}
