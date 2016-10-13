package iVoteSim;

import java.util.List;

public abstract class QuestionResponse
{
	@Override
	abstract public boolean equals(Object o);
	
	abstract public void setResponse(Object r);
	
	abstract public List<Integer> getResponse();
	
	abstract public void randomResponse();
	
	@Override
	abstract public String toString();

	abstract public boolean contains(QuestionResponse r);
}
