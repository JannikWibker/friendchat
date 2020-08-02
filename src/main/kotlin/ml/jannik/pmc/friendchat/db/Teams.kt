package ml.jannik.pmc.friendchat.db

import java.util.UUID

import java.sql.*
import java.sql.Connection;

object Teams {
  // TODO: load this from config.yml file
  private val host = "localhost"
  private val db = "friendchat"
  private val user = "friendchat"
  private val password = "password"

  private var conn: Connection = DB.connect(host, db, user, password)


  private const val existsSQLSegment = "SELECT count(*) as count FROM FC_Team"
  private const val existsByIdSQL    = "${existsSQLSegment} WHERE id = ?"
  private const val existsByNameSQL  = "${existsSQLSegment} WHERE name = ?"

  private const val findByIdSQL    = "SELECT id, name, room, created_date FROM FC_Team WHERE id = ?"
  

  fun exists(team: FCTeam): Boolean {
    return this.exists(team.id)
  }

  fun exists(uuid: UUID): Boolean {
    val stmt: PreparedStatement = this.conn.prepareStatement(this.existsByIdSQL)
    stmt.setObject(1, uuid)
    val rs = stmt.executeQuery()
    rs.next()
    val count = rs.getInt("count")
    return count == 1
  }

  private fun constructTeamFromStatement(rs: ResultSet): FCTeam? {
    return if(!rs.next())
      null
    else
      FCTeam(
        id = rs.getObject(1, UUID::class.java),
        name = rs.getString(2),
        room = rs.getObject(3, UUID::class.java),
        created_date = rs.getDate(4)
      )
  }

  fun findById(uuid: UUID): FCTeam? {
    val stmt: PreparedStatement = conn.prepareStatement(this.findByIdSQL)
    stmt.setObject(1, uuid)
    return this.constructTeamFromStatement(stmt.executeQuery())
  }
}