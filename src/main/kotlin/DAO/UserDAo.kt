package DAO

import java.util.*
import javax.sql.DataSource


class UserDAOH2(private val dataSource: DataSource) : UserDAO {

    override fun create(user: UserEntity): UserEntity {
        val sql = "INSERT INTO tuser (id, name, email) VALUES (?, ?, ?)"
        return dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, user.id.toString())
                stmt.setString(2, user.name)
                stmt.setString(3, user.email)
                user
            }
        }
    }

    override fun getById(id: UUID): UserEntity? {
        val sql = "SELECT * FROM tuser WHERE id = ?"
        return dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, id.toString())
                val rs = stmt.executeQuery()
                if (rs.next()) {
                    UserEntity(
                        id = UUID.fromString(rs.getString("id")),
                        name = rs.getString("name"),
                        email = rs.getString("email")
                    )
                } else {
                    null
                }
            }
        }
    }

    override fun getAll(): List<UserEntity> {
        val sql = "SELECT * FROM tuser"
        return dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                val rs = stmt.executeQuery()
                val users = mutableListOf<UserEntity>()
                while (rs.next()) {
                    users.add(
                        UserEntity(
                            id = UUID.fromString(rs.getString("id")),
                            name = rs.getString("name"),
                            email = rs.getString("email")
                        )
                    )
                }
                users
            }
        }
    }

    override fun update(user: UserEntity):UserEntity {
        val sql = "UPDATE tuser SET name = ?, email = ? WHERE id = ?"
        return dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, user.name)
                stmt.setString(2, user.email)
                stmt.setString(3, user.id.toString())
                stmt.executeUpdate()
                user
            }
        }
    }

    override fun delete(id: UUID) {
        val sql = "DELETE FROM tuser WHERE id = ?"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, id.toString())
                stmt.executeUpdate()
            }
        }
    }
}


interface UserService {
    fun create(user: UserEntity): UserEntity
    fun getById(id: UUID): UserEntity?
    fun update(user: UserEntity): UserEntity
    fun delete(id: UUID)
    fun getAll(): List<UserEntity>
}


class UserServiceImpl(private val userDao: UserDAO) : UserService {
    override fun create(user: UserEntity): UserEntity {
        return userDao.create(user)
    }

    override fun getById(id: UUID): UserEntity? {
        return userDao.getById(id)
    }

    override fun update(user: UserEntity): UserEntity {
        return userDao.update(user)
    }

    override fun delete(id: UUID) {
        userDao.delete(id)
    }

    override fun getAll(): List<UserEntity> {
        return userDao.getAll()
    }
}

