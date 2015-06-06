package edu.nju.network;

import java.io.Serializable;

import edu.nju.model.impl.UpdateMessage;

public class TransformObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String source;
	private UpdateMessage msg;
	
	public TransformObject(String src, UpdateMessage msg){
		this.source = src;
		this.msg = msg;
	}
	
	
	public String getSource(){
		return this.source;
	}
	
	public UpdateMessage getMsg(){
		return this.msg;
	}
	
	public void setSource(String src){
		this.source = src;
	}
	
	public void setMsg(UpdateMessage msge){
		this.msg = msge;
	}

}
