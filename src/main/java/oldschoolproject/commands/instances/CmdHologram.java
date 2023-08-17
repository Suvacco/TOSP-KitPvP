package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.exceptions.OperationFailException;
import oldschoolproject.managers.FeastManager;
import oldschoolproject.managers.HologramManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHologram extends BaseCommand {

	public CmdHologram() {
		super("holo", true);
	}

	@Override
    public void onCommand(CommandSender sender, String[] args) {
        Player p = (Player)sender;

        if (args.length == 0) {
            p.sendMessage("§cError: /holo [create : delete : setline : addline : removeline : goto : movehere : list]");
            return;
        }
        
        try {
        
	        if (args[0].equalsIgnoreCase("create")) {
	            if (args.length < 3) {
	                p.sendMessage("§cError: /holo create <id> <line-text>");
	                return;
	            }
	
	            StringBuilder sb = new StringBuilder();
	
	            for (int i = 2; i < args.length; i++) {
	                sb.append(args[i]).append(" ");
	            }
	
	            sb.deleteCharAt(sb.length() - 1);

	            HologramManager.createHologram(args[1], sb.toString(), p.getLocation());
	            p.sendMessage("§aHologram \"" + args[1] + "\" created successfully!");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("addline")) {
	            if (args.length < 3) {
	                p.sendMessage("§cError: /holo addline <id> <line-text>");
	                return;
	            }
	
	            StringBuilder sb = new StringBuilder();
	
	            for (int i = 2; i < args.length; i++) {
	                sb.append(args[i]).append(" ");
	            }
	
	            sb.deleteCharAt(sb.length() - 1);
	
	            HologramManager.addLine(args[1], sb.toString());
	            p.sendMessage("§aLine \"" + sb.toString() + "\" successfully added to the hologram \"" + args[1] + "\"");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("delete")) {
	            if (args.length < 2) {
	                p.sendMessage("§cError: /holo delete <id>");
	                return;
	            }
	
	            HologramManager.deleteHologram(args[1]);
	            p.sendMessage("§aHologram \"" + args[1] + "\" deleted successfully!");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("setline")) {
	            if (args.length < 4) {
	                p.sendMessage("§cError: /holo setline <id> <line-number> <line-text>");
	                return;
	            }
	
	            StringBuilder sb = new StringBuilder();
	
	            for (int i = 3; i < args.length; i++) {
	                sb.append(args[i]).append(" ");
	            }
	
	            sb.deleteCharAt(sb.length() - 1);

	            HologramManager.setlineHologram(args[1], Integer.parseInt(args[2]), sb.toString());
	            p.sendMessage("§aLine text \"" + sb.toString() + "\" set successfully to line number [" + args[2] + "] to the hologram \"" + args[1] + "\"");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("removeline")) {
	            if (args.length < 3) {
	                p.sendMessage("§cError: /holo removeline <id> <line>");
	                return;
	            }
	
	            HologramManager.removeLine(args[1], Integer.parseInt(args[2]));
	            p.sendMessage("§aLine number \"" + args[2] + "\" successfully removed from the hologram \"" + args[1] + "\"");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("goto")) {
	            if (args.length < 2) {
	                p.sendMessage("§cError: /holo goto <id>");
	                return;
	            }
	
	            p.teleport(HologramManager.findHologram(args[1]).getLocation());
				p.sendMessage("§aTeleported to hologram \"" + args[1] + "\" successfully!");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("movehere")) {
	            if (args.length < 2) {
	                p.sendMessage("§cError: /holo movehere <id>");
	                return;
	            }

	            HologramManager.moveHologram(args[1], p.getLocation());
				p.sendMessage("§aHologram \"" + args[1] + "\" location updated successfully!");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("list")) {
				p.sendMessage("§aHologram ID List: " + HologramManager.getHologramList());
	            return;
	        }
        
        } catch (OperationFailException e) {
        	p.sendMessage(e.getMessage());
			return;
        } catch (NumberFormatException e) {
			p.sendMessage("§cError: Invalid number format");
			return;
		} catch (IndexOutOfBoundsException e) {
			p.sendMessage("§cError: Invalid line");
			return;
		}

        p.sendMessage("§cError: /holo [create : delete : setline : addline : removeline : goto : movehere : list]");
    }
}
