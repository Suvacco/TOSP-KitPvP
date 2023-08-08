package oldschoolproject.commands.instances;

import oldschoolproject.commands.BaseCommand;
import oldschoolproject.exceptions.HologramManagementException;
import oldschoolproject.holograms.HologramLoader;
import oldschoolproject.managers.HologramManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHologram extends BaseCommand {

	public CmdHologram() {
		super("holo");
	}

	@Override
    public void onCommand(CommandSender sender, String[] args) {
        Player p = (Player)sender;

        if (args.length == 0) {
            p.sendMessage("§cErro: /holo [create : delete : setline : addline : removeline : goto : movehere : list]");
            return;
        }
        
        try {
        
	        if (args[0].equalsIgnoreCase("create")) {
	            if (args.length < 3) {
	                p.sendMessage("§cErro: /holo create <id> <firstline>");
	                return;
	            }
	
	            StringBuilder sb = new StringBuilder();
	
	            for (int i = 2; i < args.length; i++) {
	                sb.append(args[i]).append(" ");
	            }
	
	            sb.deleteCharAt(sb.length() - 1);
	
	            HologramManager.createHologram(args[1], sb.toString(), p.getLocation());
	            
	            p.sendMessage("§aHolograma \"" + args[1] + "\" criado com sucesso!");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("addline")) {
	            if (args.length < 3) {
	                p.sendMessage("§cErro: /holo addline <id> <line>");
	                return;
	            }
	
	            StringBuilder sb = new StringBuilder();
	
	            for (int i = 2; i < args.length; i++) {
	                sb.append(args[i]).append(" ");
	            }
	
	            sb.deleteCharAt(sb.length() - 1);
	
	            HologramManager.addLine(args[1], sb.toString());
	            
	            p.sendMessage("§aLinha \"" + sb.toString() + "\" adicionada com sucesso ao holograma \"" + args[1] + "\"");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("delete")) {
	            if (args.length < 2) {
	                p.sendMessage("§cErro: /holo delete <id>");
	                return;
	            }
	
	            HologramManager.deleteHologram(args[1]);
	            
	            p.sendMessage("§aHolograma \"" + args[1] + "\" deletado com sucesso!");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("setline")) {
	            if (args.length < 4) {
	                p.sendMessage("§cErro: /holo setline <id> <line> <text>");
	                return;
	            }
	
	            StringBuilder sb = new StringBuilder();
	
	            for (int i = 3; i < args.length; i++) {
	                sb.append(args[i]).append(" ");
	            }
	
	            sb.deleteCharAt(sb.length() - 1);
	
	            HologramManager.setlineHologram(args[1], Integer.parseInt(args[2]), sb.toString());
	            
	            p.sendMessage("§aLinha \"" + sb.toString() + "\" [" + args[2] + "] setada com sucesso ao holograma \"" + args[1] + "\"");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("removeline")) {
	            if (args.length < 3) {
	                p.sendMessage("§cErro: /holo removeline <id> <line>");
	                return;
	            }
	
	            HologramManager.removeLine(args[1], Integer.parseInt(args[2]));
	            
	            p.sendMessage("§aLinha \"" + args[2] + "\" removida com sucesso do holograma \"" + args[1] + "\"");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("goto")) {
	            if (args.length < 2) {
	                p.sendMessage("§cErro: /holo goto <id>");
	                return;
	            }
	
	            p.teleport(HologramManager.findHologram(args[1]).getLocation());
	            p.sendMessage("§aTeleportado ao holograma \"" + args[1] + "\"");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("movehere")) {
	            if (args.length < 2) {
	                p.sendMessage("§cErro: /holo movehere <id>");
	                return;
	            }

	            HologramManager.moveHologram(args[1], p.getLocation());
	            
	            p.sendMessage("§aLocalização do holograma \"" + args[1] + "\" atualizada com sucesso!");
	            return;
	        }
	
	        if (args[0].equalsIgnoreCase("list")) {
	            p.sendMessage("§aLista IDs de Hologramas: " + HologramManager.getHologramList());
	            return;
	        }
        
        } catch (HologramManagementException e) {
        	p.sendMessage("§cErro: " + e.getMessage());
        }

        p.sendMessage("§cErro: /holo [create : delete : setline : addline : removeline : goto : movehere : list]");
    }
}
