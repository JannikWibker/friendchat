package ml.jannik.pmc.friendchat.commands.teams

import org.bukkit.command.CommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class TeamCreateCommand : CommandExecutor {

  override fun onCommand(sender: CommandSender, command: Command, commandLabel: String, args: Array<String>): Boolean {
    sender.sendMessage("team create: " + args.joinToString { it -> "\'${it}\'"})
    return true
  }
}