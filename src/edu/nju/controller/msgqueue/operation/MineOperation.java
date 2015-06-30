package edu.nju.controller.msgqueue.operation;

import java.io.Serializable;

public abstract class MineOperation implements Serializable{
	public boolean isClient = false;
	public abstract void execute();
}
