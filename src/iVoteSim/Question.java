package iVoteSim;

public abstract class Question
{	
	protected String questionText;
	protected QuestionResponse answer;
	
	public Question(String text)
	{
		this.questionText = text;
	}
	
	abstract QuestionResponse getAnswer();
	
	@Override
	public String toString()
	{
		return "Question: " + questionText;
	}

	abstract QuestionResponse createResponse();
}
