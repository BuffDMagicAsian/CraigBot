package org.usfirst.frc.team4585.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Timer;

public class TaskSequence {

	private String _SeqName = ".";
	
	private List<AutoTask> _MyTaskList = new ArrayList<AutoTask>();
	private int _SequenceSize = 0;
	private int _CurrentTaskIndex = 0;
	private AutoTask _CurrentTask;
	private boolean _TasksCompleted = false;
	
	public TaskSequence(String SeqName)
	{
		_SeqName = SeqName;
	}
	
	public String GetSequenceName()
	{
		return _SeqName;
	}
	
	protected void AddTask(AutoTask NewTask)
	{
		_MyTaskList.add(_SequenceSize++, NewTask);
	}
	
	public boolean DoCurrentTask()
	{
		if(!_TasksCompleted)
		{
			if(_CurrentTaskIndex == 0)
			{
				_TasksCompleted = StartNextTask();
			}
			else
			{
				if(_CurrentTask.DoTask())
				{
					_TasksCompleted = StartNextTask();
				}
			}
		}
		
		return _TasksCompleted;
	}
	
	private boolean StartNextTask()
	{
		boolean AllDone = false;
		
		if(_CurrentTaskIndex < _SequenceSize)
		{
			_CurrentTask = _MyTaskList.get(_CurrentTaskIndex);
			_CurrentTask.StartTask();
		}
		else
		{
			AllDone = true;
		}
		
		_CurrentTaskIndex++;
		
		return AllDone;
	}
	
	////////////////////////////////////////////////////////////////////
	
	public void AddTaskWaitForTrigger(int TriggerNum)
	{
		AddTask(new AutoTaskWaitForTrigger(TriggerNum));
	}

	public void AddTaskFireTrigger(int TriggerNum)
	{
		AddTask(new AutoTaskFireTrigger(TriggerNum));
	}

	public void AddTaskWaitForContact(ContactSensor CheckSensor, boolean OpenTrigger)
	{
		AddTask(new AutoTaskWaitForContact(CheckSensor, OpenTrigger));
	}
	
	public void AddTaskWaitForContact(ContactSensor CheckSensor)
	{
		AddTask(new AutoTaskWaitForContact(CheckSensor));
	}
	
	public void AddTaskWaitForTime(double WaitSeconds, Timer MasterTimer)
	{
		AddTask(new AutoTaskWaitForTime(WaitSeconds, MasterTimer));
	}
	
	////////////////////////////////////////////////////////////////////
	

}
