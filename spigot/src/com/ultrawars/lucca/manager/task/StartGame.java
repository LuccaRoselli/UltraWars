package com.ultrawars.lucca.manager.task;

import org.bukkit.scheduler.BukkitRunnable;

import com.ultrawars.lucca.manager.Game;
import com.ultrawars.lucca.manager.PlayerGame;
import com.ultrawars.lucca.utils.StringManager;
import com.ultrawars.lucca.utils.Title;

public class StartGame extends BukkitRunnable{

	private int timeSeconds;
	private Game game;
	
	
	public StartGame(int timeSeconds, Game game) {
		this.timeSeconds = timeSeconds;
		this.game = game;
	}
	
	public void run() {
		if(timeSeconds == 0){
			game.stopTaskContage();
			game.startGame();
		}
		else if((timeSeconds % 10) == 0 )
			game.bc(StringManager.PREFIX + "§eO jogo inicia em §6" + timeSeconds + " §esegundos");	
		else if( timeSeconds <= 5){
			Title t = new Title("&c" + timeSeconds,"&ePrepare para o jogo!");
			for(PlayerGame pg : game.getPlayers()){
				t.send(pg.getPlayer());
				pg.getPlayer().sendMessage(StringManager.PREFIX + "§eO jogo inicia em §c" + timeSeconds + " §esegundos");
				if (timeSeconds == 0)
					pg.sendOtherScore();
			}
		}
		
		game.setTimeStart(timeSeconds);
		timeSeconds--;
	}
	
	
}
