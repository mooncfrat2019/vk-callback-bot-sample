package ser.db

import io.ktor.util.logging.*
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ser.Global.DB_HOST
import ser.Global.DB_NAME
import ser.Global.DB_PASSWORD
import ser.Global.DB_PORT
import ser.Global.DB_USER
import ser.logging.logger

val DB_URL = "jdbc:mysql://$DB_HOST:$DB_PORT/$DB_NAME"

var connection = Database.connect(
    DB_URL,
    driver = "com.mysql.cj.jdbc.Driver",
    user = DB_USER,
    password = DB_PASSWORD,
)
object DB {
    fun init() {
        try {
            connection
            transaction {
                addLogger(StdOutSqlLogger)

                SchemaUtils.create (Schemes.Users)
            }

        } catch (ex: Exception) {
            logger.error(ex)
        }
    }
}

class Schemes {
    object Users : IntIdTable() {
        val user_id = integer("user_id").index()
        val current_state = integer("current_state").default(0)
        val payload = varchar("payload", 1000)
    }
}

data class User (
    val user_id: Int,
    val current_state: Int,
    val payload: String,
) {
    companion object {
        fun fromRow(resultRow: ResultRow) = User(
            user_id = resultRow[Schemes.Users.user_id],
            current_state = resultRow[Schemes.Users.current_state],
            payload = resultRow[Schemes.Users.payload],
        )
    }
}

object UseDB {
    fun usersGet(from_id: Int): User {
        return try {
            Database.connect(DB_URL, driver = "com.mysql.cj.jdbc.Driver", user = DB_USER, password = DB_PASSWORD)
            var user = User(0, 0, "")
            transaction {
                addLogger(StdOutSqlLogger)
                val query: Query = Schemes.Users.select { Schemes.Users.user_id eq from_id }
                if (!query.empty()) {
                    user = query.map { User.fromRow(it) }.first()
                } else {
                    Schemes.Users.insertAndGetId {
                        it[user_id] = from_id
                        it[current_state] = 0
                        it[payload] = ""
                    }
                    user = Schemes.Users.select { Schemes.Users.user_id eq from_id }.map { User.fromRow(it) }.first()
                }
            }
             user
        } catch (e: Exception) {
            println(e)
            logger.error(e)
            return User(0, 0, "Error")
        }
    }
    fun recordCurrentState(user_id: Int, current_state: Int) {
        try {
            Database.connect(DB_URL, driver = "com.mysql.cj.jdbc.Driver", user = DB_USER, password = DB_PASSWORD)
            transaction {
                Schemes.Users.update({ Schemes.Users.user_id eq user_id }) {
                    it[Schemes.Users.current_state] = current_state
                }
            }
        } catch (e: Exception) {
            println(e)
            logger.error(e)
            return
        }
    }
}