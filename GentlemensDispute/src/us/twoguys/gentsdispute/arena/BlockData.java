package us.twoguys.gentsdispute.arena;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BlockData {

	Player player;
	Block block;
	
	public BlockData(Player player, Block block){
		this.player = player;
		this.block = block;
	}
	
	public Block getBlock(){
		return block;
	}
	
	public Player getPlayer(){
		return player;
	}
}
