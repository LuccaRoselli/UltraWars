package com.ultrawars.lucca.utils;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class ActionBar {
	
    public void sendActionbar(Player p, String msg) {
    	try {
    	if ((ReflectionUtil.getServerVersion().equalsIgnoreCase("v1_9_R1")) || 
    	        (ReflectionUtil.getServerVersion().equalsIgnoreCase("v1_9_R2")))
    	      {
    	        Object icbc = ReflectionUtil.getNMSClass("ChatComponentText").getConstructor(new Class[] { String.class }).newInstance(new Object[] { ChatColor.translateAlternateColorCodes('&', msg) });
    	        Object ppoc = ReflectionUtil.getNMSClass("PacketPlayOutChat").getConstructor(new Class[] { ReflectionUtil.getNMSClass("IChatBaseComponent"), Byte.TYPE }).newInstance(new Object[] { icbc, (byte) 2 });
    	        Object nmsp = p.getClass().getMethod("getHandle", new Class[0]).invoke(p, new Object[0]);
    	        Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);
    	        
    	        pcon.getClass().getMethod("sendPacket", new Class[] { ReflectionUtil.getNMSClass("Packet") }).invoke(pcon, new Object[] { ppoc });
    	      }
    	      else if ((ReflectionUtil.getServerVersion().equalsIgnoreCase("v1_8_R2")) || 
    	        (ReflectionUtil.getServerVersion().equalsIgnoreCase("v1_8_R3")))
    	      {
    	        Object icbc = ReflectionUtil.getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{'text': '" + msg + "'}" });
    	        Object ppoc = ReflectionUtil.getNMSClass("PacketPlayOutChat").getConstructor(new Class[] { ReflectionUtil.getNMSClass("IChatBaseComponent"), Byte.TYPE }).newInstance(new Object[] { icbc, (byte) 2 });
    	        Object nmsp = p.getClass().getMethod("getHandle", new Class[0]).invoke(p, new Object[0]);
    	        Object pcon;
				pcon = nmsp.getClass().getField("playerConnection").get(nmsp);
    	        pcon.getClass().getMethod("sendPacket", new Class[] { ReflectionUtil.getNMSClass("Packet") }).invoke(pcon, new Object[] { ppoc });
    	      }
    	      else
    	      {
    	        Object icbc = ReflectionUtil.getNMSClass("ChatSerializer").getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{'text': '" + msg + "'}" });
    	        
    	        Object ppoc = ReflectionUtil.getNMSClass("PacketPlayOutChat").getConstructor(new Class[] { ReflectionUtil.getNMSClass("IChatBaseComponent"), Byte.TYPE }).newInstance(new Object[] { icbc,(byte) 2 });
    	        
    	        Object nmsp = p.getClass().getMethod("getHandle", new Class[0]).invoke(p, new Object[0]);
    	        
    	        Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);
    	        
					pcon.getClass().getMethod("sendPacket", new Class[] { ReflectionUtil.getNMSClass("Packet") }).invoke(pcon, new Object[] { ppoc });
			}
    	} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
	    } catch (InstantiationException e) {
			e.printStackTrace();
	    }
    }
	
}
