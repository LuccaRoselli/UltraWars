package com.ultrawars.lucca.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.server.v1_7_R4.ChatSerializer;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector;

public class Title {
	private static int VERSAO = 46;
	String title;
	String subtitle;

	public Title(String title, String subtitle) {
		this.title = title;
		this.subtitle = subtitle;
	}
	
	public Title(String title) {
		this.title = title;
		this.subtitle = "";
	}

	public void send(Player p) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager
				.getVersion() < VERSAO)
			return;
		((CraftPlayer) p).getHandle().playerConnection
				.sendPacket(new ProtocolInjector.PacketTitle(
						ProtocolInjector.PacketTitle.Action.TITLE,
						ChatSerializer.a("{\"text\": \"\"}").a(title.replace("&", "§"))));
		((CraftPlayer) p).getHandle().playerConnection
				.sendPacket(new ProtocolInjector.PacketTitle(
						ProtocolInjector.PacketTitle.Action.SUBTITLE,
						ChatSerializer.a("{\"text\": \"\"}").a(subtitle.replace("&", "§"))));
	}

	@SuppressWarnings("unused")
	private void sendTimings(Player Jogador, int TempoDeEntrada, int Tempo,
			int TempoDeSaida) {
		if (((CraftPlayer) Jogador).getHandle().playerConnection.networkManager
				.getVersion() < VERSAO) {
			return;
		}
		try {
			Object Handle = getHandle(Jogador);
			Object ConexaoDoJogador = getField(Handle.getClass(),
					"playerConnection").get(Handle);
			Object Packet = ProtocolInjector.PacketTitle.class.getConstructor(
					new Class[] { ProtocolInjector.PacketTitle.Action.class,
							Integer.TYPE, Integer.TYPE, Integer.TYPE })
					.newInstance(
							new Object[] {
									ProtocolInjector.PacketTitle.Action.TIMES,
									Integer.valueOf(TempoDeEntrada),
									Integer.valueOf(Tempo),
									Integer.valueOf(TempoDeSaida) });
			getMethod(ConexaoDoJogador.getClass(), "sendPacket", new Class[0])
					.invoke(ConexaoDoJogador, new Object[] { Packet });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
		boolean equal = true;
		if (l1.length != l2.length) {
			return false;
		}
		for (int i = 0; i < l1.length; i++) {
			if (l1[i] != l2[i]) {
				equal = false;
				break;
			}
		}
		return equal;
	}

	private static Field getField(Class<?> clazz, String name) {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Method getMethod(Class<?> clazz, String name, Class<?>[] args) {
		for (Method m : clazz.getMethods()) {
			if ((m.getName().equals(name))
					&& ((args.length == 0) || (ClassListEqual(args,
							m.getParameterTypes())))) {
				m.setAccessible(true);
				return m;
			}
		}
		return null;
	}

	private static Object getHandle(Object obj) {
		try {
			return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(
					obj, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	private void reset(Player p) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager
				.getVersion() < VERSAO) {
			return;
		}
		((CraftPlayer) p).getHandle().playerConnection
				.sendPacket(new ProtocolInjector.PacketTitle(
						ProtocolInjector.PacketTitle.Action.RESET));
	}

	@SuppressWarnings("unused")
	private void clear(Player p) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager
				.getVersion() < VERSAO) {
			return;
		}
		((CraftPlayer) p).getHandle().playerConnection
				.sendPacket(new ProtocolInjector.PacketTitle(
						ProtocolInjector.PacketTitle.Action.CLEAR));
	}
}