package world.bentobox.bskyblock.commands;

import java.util.ArrayList;
import java.util.List;

import world.bentobox.bentobox.api.commands.CompositeCommand;
import world.bentobox.bentobox.api.commands.island.*;
import world.bentobox.bentobox.api.commands.island.team.IslandTeamCommand;
import world.bentobox.bentobox.api.localization.TextVariables;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bskyblock.BSkyBlock;

public class IslandCommand extends CompositeCommand {

    public IslandCommand(BSkyBlock addon) {
        super(addon, "island", "is");
    }

    /* (non-Javadoc)
     * @see us.tastybento.bskyblock.api.commands.CompositeCommand#setup()
     */
    @Override
    public void setup() {
        setDescription("commands.island.help.description");
        setOnlyPlayer(true);
        // Permission
        setPermission("island");
        // Set up subcommands
        new IslandAboutCommand(this);
        new IslandInfoCommand(this);
        new IslandSchemsPanelCommand(this, new IslandCreateCommand(this));
        new IslandGoCommand(this);
        new IslandSpawnCommand(this);
        new IslandResetCommand(this);
        new IslandSetnameCommand(this);
        new IslandResetnameCommand(this);
        new IslandSethomeCommand(this);
        new IslandSettingsCommand(this);
        new IslandLanguageCommand(this);
        new IslandBanCommand(this);
        new IslandUnbanCommand(this);
        new IslandBanlistCommand(this);
        // Team commands
        new IslandTeamCommand(this);
    }

    /* (non-Javadoc)
     * @see us.tastybento.bskyblock.api.commands.CommandArgument#execute(org.bukkit.command.CommandSender, java.lang.String[])
     */
    @Override
    public boolean execute(User user, String label, List<String> args) {
        if (user == null) {
            return false;
        }
        if (args.isEmpty()) {
            // If user has an island, go
            if (getPlugin().getIslands().getIsland(getWorld(), user.getUniqueId()) != null) {
                return getSubCommand("go").map(goCmd -> goCmd.execute(user, goCmd.getLabel(), new ArrayList<>())).orElse(false);
            }
            // No islands currently
            if(user.hasPermission("island.schemspanel")){
                return getSubCommand("schems").map(schemCmd -> schemCmd.execute(user, schemCmd.getLabel(), new ArrayList<>())).orElse(false);
            }
            return getSubCommand("create").map(createCmd -> createCmd.execute(user, createCmd.getLabel(), new ArrayList<>())).orElse(false);
        }
        user.sendMessage("general.errors.unknown-command", TextVariables.LABEL, getTopLabel());
        return false;
    }
}
