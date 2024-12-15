package com.itisluiz.hardcoreIsTheOnlyCore.partials;

public record LastMessage(int messageHashCode, long systemMillis)
{
	public LastMessage(String message)
	{
		this(message.hashCode(), System.currentTimeMillis());
	}

	public boolean shouldSendNext(LastMessage newMessage, long minimumDelayMillis)
	{
		return newMessage.messageHashCode() != messageHashCode ||
			newMessage.systemMillis() - systemMillis >= minimumDelayMillis;
	}
}
