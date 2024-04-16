package logic;

import java.io.Serializable;

import enums.Commands;
/**
 * Represents a message containing an object and a command.
 */
public class Message implements Serializable{
	
	/**
	 * 
	 */
	
	
	private Object obj;
	private Commands cmd;
	public Message(Object obj, Commands cmd) {
		this.obj = obj;
		this.cmd = cmd;
	}
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Commands getCmd() {
		return cmd;
	}
	public void setCmd(Commands cmd) {
		this.cmd = cmd;
	} 

}
