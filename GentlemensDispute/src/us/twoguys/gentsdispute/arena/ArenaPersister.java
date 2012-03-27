package us.twoguys.gentsdispute.arena;

	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.ObjectInputStream;
	import java.io.ObjectOutputStream;

import org.bukkit.Location;

import us.twoguys.gentsdispute.GentlemensDispute;


	public class ArenaPersister {

		GentlemensDispute plugin;
		ArenaMaster arenaMaster;
		
		public ArenaPersister(GentlemensDispute instance){
			this.plugin = instance;
			this.arenaMaster = plugin.arenaMaster;
		}
		
		public void Serialize(){
			//create a new file
			File saveFile = new File("plugins" + File.separator + "GentlemensDispute" + File.separator + "ArenaData.dat");
			if(!saveFile.exists()){
				try{
					new File("plugins" + File.separator + "GentlemensDispute").mkdir();
					saveFile.createNewFile();
				} catch( IOException e){
					e.printStackTrace();
				}
			}
			try{
				FileOutputStream fostream = new FileOutputStream("plugins"+File.pathSeparator+"GentlemensDispute"+File.pathSeparator+"ArenaData.dat");
				ObjectOutputStream oostream = new ObjectOutputStream(fostream);
				
				oostream.writeInt(arenaMaster.getArenaDataList().size());
				
				if(arenaMaster.getArenaDataList().isEmpty()){
					plugin.log("No arenas to save...");
				}else{
					try{
						for (ArenaData arenaData : arenaMaster.getArenaDataList()){
							if(arenaMaster.isDeleted(arenaData.getName())== false){
								oostream.writeObject(arenaData);
								plugin.log(arenaData.getName() + " saved");
							}
						}
					}catch(Exception e){
						plugin.log("serialization unsuccessful");
					}
				oostream.close();
				plugin.log("Arenas successfully serialized");
				}
			}catch (IOException e){
				e.printStackTrace();
			}
			
			
		}
		
		public void Deserialize(){
			File saveFile = new File("plugins"+File.pathSeparator+"GentlemensDispute"+File.pathSeparator+"ArenaData.dat");
			
			if(saveFile.exists()){
				FileInputStream fistream = null;
				ObjectInputStream oistream = null;
				try{
					fistream = new FileInputStream("plugins"+File.pathSeparator+"GentlemensDispute"+File.pathSeparator+"ArenaData.dat");
					oistream = new ObjectInputStream(fistream);
					
					Integer recordCount = oistream.readInt();
					
					if(recordCount == 0)return;
					
					plugin.log("Loading "+ recordCount +" region(s)");
					
					for(int i = 0; i < recordCount; i++){
						ArenaData arena = (ArenaData)oistream.readObject();
						
						Location loc1 = arena.getCorner1();
						Location loc2 = arena.getCorner2();
						Location spawn = arena.getSpawnLocation();
						String arenaName = arena.getName();
						
						arenaMaster.createArena(loc1, loc2, spawn, arenaName);
						
						plugin.log(arenaName + "loaded");
								
					}
				}catch(FileNotFoundException e){
					plugin.log("Could not locate data file... ");
					e.printStackTrace();
				}catch(IOException e){
					plugin.log("IOException while trying to read data file");
				}catch(ClassNotFoundException e){
					plugin.log("Could not find class to load");
				}finally{
					try{
						oistream.close();
					}catch(IOException e){
						plugin.log("Error while trying to close input stream");
					}
				}
			}
		}
	}

