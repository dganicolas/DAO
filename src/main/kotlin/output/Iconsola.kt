package output

import DAO.UserEntity

interface Iconsola {
    fun showMessage(message: String, lineBreak: Boolean=true)
    fun show(userList: List<UserEntity>?,message: String = "All users:")
}