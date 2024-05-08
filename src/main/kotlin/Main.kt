import DAO.UserDAOH2
import DAO.UserEntity
import dbconexion.DataSourceFactory
import output.Consola
import output.Iconsola
import service.UserServiceImpl

fun main() {
    // Creamos la instancia de la base de datos
    val dataSource = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI)

    //creamos la consola
    val consola = Consola()

    // Creamos la instancia de UserDAO
    val userDao = UserDAOH2(dataSource, consola)

    // Creamos la instancia de UserService
    val userService = UserServiceImpl(userDao)

    // Creamos un nuevo usuario
    val newUser = UserEntity(name = "John Doe", email = "johndoe@example.com")
    var createdUser = userService.create(newUser)

    consola.showMessage("Created user: ${createdUser ?: "error"}")

    // Obtenemos un usuario por su ID
    val foundUser =
        if (createdUser != null) {
            userService.getById(createdUser.id)
        } else {
            null
        }

    consola.showMessage("Found user: ${foundUser ?: "error"}")

    // Actualizamos el usuario
    val updatedUser = foundUser?.copy(name = "Jane Doe")
    val savedUser =
        if (updatedUser != null) {
            userService.update(updatedUser)
        } else {
            null
        }

    consola.showMessage("Updated user: $savedUser")

    val otherUser = UserEntity(name = "Eduardo Fernandez", email = "eferoli@gmail.com")
    createdUser =
        if (otherUser != null) {
            userService.create(otherUser)

        } else {
            null
        }
    consola.showMessage("Created user: ${createdUser ?: "error"}")


    // Obtenemos todos los usuarios
    var allUsers = userService.getAll()
    consola.show(allUsers)

    // Eliminamos el usuario
    if (savedUser != null) {
        userService.delete(savedUser.id)
        consola.showMessage("User deleted  ok")
    } else {
        consola.showMessage("User deleted error")
    }

    // Obtenemos todos los usuarios
    allUsers = userService.getAll()
    consola.show(allUsers)

    // Eliminamos el usuario
    userService.delete(otherUser.id)
    consola.showMessage("User deleted")
}
